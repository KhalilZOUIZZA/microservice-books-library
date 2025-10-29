import {Component, OnInit} from '@angular/core';
import {BookAdminService} from 'src/app/shared/service/admin/book/BookAdmin.service';
import {BookDto} from 'src/app/shared/model/book/Book.model';
import {BookCriteria} from 'src/app/shared/criteria/book/BookCriteria.model';


import {ConfirmationService, MessageService,MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';

import {AuthService} from 'src/app/zynerator/security/shared/service/Auth.service';
import {ExportService} from 'src/app/zynerator/util/Export.service';


import {CategoryDto} from 'src/app/shared/model/book/Category.model';
import {CategoryAdminService} from 'src/app/shared/service/admin/book/CategoryAdmin.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyAdminService} from 'src/app/shared/service/admin/book/CopyAdmin.service';
import {AuthorDto} from 'src/app/shared/model/book/Author.model';
import {AuthorAdminService} from 'src/app/shared/service/admin/book/AuthorAdmin.service';


@Component({
  selector: 'app-book-list-admin',
  templateUrl: './book-list-admin.component.html'
})
export class BookListAdminComponent implements OnInit {

    protected fileName = 'Book';

    protected findByCriteriaShow = false;
    protected cols: any[] = [];
    protected excelPdfButons: MenuItem[];
    protected exportData: any[] = [];
    protected criteriaData: any[] = [];
    protected _totalRecords = 0;
    private _pdfName: string;


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    protected authService: AuthService;
    protected exportService: ExportService;
    protected excelFile: File | undefined;
    protected enableSecurity = false;


     yesOrNoAvailable: any[] = [];
    categorys: Array<CategoryDto>;
    authors: Array<AuthorDto>;


    constructor(private bookService: BookAdminService , private service: BookAdminService  , private categoryService: CategoryAdminService, private copyService: CopyAdminService, private authorService: AuthorAdminService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.authService = ServiceLocator.injector.get(AuthService);
        this.exportService = ServiceLocator.injector.get(ExportService);

    }

    ngOnInit(): void {
        this.findPaginatedByCriteria();
        this.initExport();
        this.initCol();
        this.loadCategory();
        this.loadAuthor();
        this.yesOrNoAvailable =  [{label: 'Available', value: null},{label: 'Oui', value: 1},{label: 'Non', value: 0}];
        this.loadBooks();
    }

//mehdi
    public books: Array<BookDto> = [];

    private loadBooks(): void {
        this.bookService.findAll().subscribe((books: Array<BookDto>) => {
            this.books = books;
            this.books.forEach((book) => {
                this.bookService.getSignedImageUrl(book.imageUrl).subscribe((signedUrl: string) => {
                    book.imageUrl = signedUrl; // Replace with signed URL
                });
            });
        });
    }
//

    public onExcelFileSelected(event: any): void {
        const input = event.target as HTMLInputElement;
        if (input.files && input.files.length > 0) {
            this.excelFile = input.files[0];
        }
    }

    public importExcel(): void {
        if (this.excelFile) {
            this.service.importExcel(this.excelFile).subscribe(
                response => {
                    this.items = response;
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Success',
                        detail: 'File uploaded successfully!',
                        life: 3000
                    });
                },
                error => {
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'File uploaded with Error!',
                        life: 3000
                    });
                }
            );
        }
    }
// get image
    getFullImageUrl(imagePath: string): string {
        return this.service.getFullImageUrl(imagePath);
    }

    loading: boolean = false;


    public findPaginatedByCriteria(): void {
        this.loading = true;
        this.service.findPaginatedByCriteria(this.criteria).subscribe(
            (paginatedItems) => {
                this.items = paginatedItems.list;
                this.totalRecords = paginatedItems.dataSize;
                this.selections = new Array<BookDto>();
                this.loading = false;
            },
            (error) => {
                console.error(error);
                this.loading = false;
            }
        );
    }

    public onPage(event: any) {
        this.criteria.page = event.page;
        this.criteria.maxResults = event.rows;
        this.findPaginatedByCriteria();
    }

    public async edit(dto: BookDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            console.log(res);
            this.editDialog = true;
        });

    }

    public async view(dto: BookDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(res => {
            this.item = res;
            this.viewDialog = true;
        });
    }

    public async openCreate() {
        this.item = new BookDto();
        this.createDialog = true;
    }

    public async deleteMultiple() {
        this.confirmationService.confirm({
            message: 'Voulez-vous supprimer ces éléments ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteMultiple().subscribe(() => {
                    for (let selection of this.selections) {
                        let index = this.items.findIndex(element => element.id === selection.id);
                        this.items.splice(index,1);
                    }
                    this.selections = new Array<BookDto>();
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Succès',
                        detail: 'Les éléments sélectionnés ont été supprimés',
                        life: 3000
                    });

                }, error => console.log(error));
            }
        });
    }


    public isSelectionDisabled(): boolean {
        return this.selections == null || this.selections.length == 0;
    }


    public async delete(dto: BookDto) {

        this.confirmationService.confirm({
            message: 'Voulez-vous supprimer cet élément ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.delete(dto).subscribe(status => {
                    if (status > 0) {
                        const position = this.items.indexOf(dto);
                        position > -1 ? this.items.splice(position, 1) : false;
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Succès',
                            detail: 'Element Supprimé',
                            life: 3000
                        });
                    }

                }, error => console.log(error));
            }
        });

    }

    public async duplicate(dto: BookDto) {
        this.service.findByIdWithAssociatedList(dto).subscribe(
            res => {
                this.initDuplicate(res);
                this.item = res;
                this.item.id = null;
                this.createDialog = true;
            });
    }

    // TODO : check if correct
    public initExport(): void {
        this.excelPdfButons = [
            {
                label: 'CSV', icon: 'pi pi-file', command: () => {
                    this.prepareColumnExport().then(() => {
                        this.exportService.exporterCSV(this.criteriaData, this.exportData, this.fileName);
                    });
                }
            },
            {
                label: 'XLS', icon: 'pi pi-file-excel', command: () => {
                    this.prepareColumnExport().then(() => {
                        this.exportService.exporterExcel(this.criteriaData, this.exportData, this.fileName);
                    });
                }
            },
            {
                label: 'PDF', icon: 'pi pi-file-pdf', command: () => {
                    this.prepareColumnExport().then(() => {
                        this.exportService.exporterPdf(this.criteriaData, this.exportData, this.fileName);
                    });
                }
            }
        ];
    }


    public exportPdf(dto: BookDto): void {
        this.service.exportPdf(dto).subscribe((data: ArrayBuffer) => {
            const blob = new Blob([data], {type: 'application/pdf'});
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = this.pdfName;
            link.setAttribute('target', '_blank'); // open link in new tab
            link.click();
            window.URL.revokeObjectURL(url);
        }, (error) => {
            console.error(error); // handle any errors that occur
        });
    }

    public showSearch(): void {
        this.findByCriteriaShow = !this.findByCriteriaShow;
    }


    update() {
        this.service.edit().subscribe(data => {
            const myIndex = this.items.findIndex(e => e.id === this.item.id);
            this.items[myIndex] = data;
            this.editDialog = false;
            this.item = new BookDto();
        } , error => {
            console.log(error);
        });
    }

    public save() {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;


                this.item = new BookDto();
            } else {
                this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }
        }, error => {
            console.log(error);
        });
    }

// add


    public initCol() {
        this.cols = [
            {field: 'code', header: 'Code'},
            {field: 'label', header: 'Label'},
            {field: 'title', header: 'Title'},
            {field: 'editionDate', header: 'Edition date'},
            {field: 'numberOfCopies', header: 'Number of copies'},
            {field: 'available', header: 'Available'},
            {field: 'category?.label', header: 'Category'},
            {field: 'author?.fullName', header: 'Author'},
            {field: 'imageUrl', header: 'Image url'},
        ];
    }


    public async loadCategory(){
        this.categoryService.findAllOptimized().subscribe(categorys => this.categorys = categorys, error => console.log(error))
    }
    public async loadAuthor(){
        this.authorService.findAllOptimized().subscribe(authors => this.authors = authors, error => console.log(error))
    }


	public initDuplicate(res: BookDto) {
        if (res.copies != null) {
             res.copies.forEach(d => { d.book = null; d.id = null; });
        }
	}


    public prepareColumnExport(): Promise<void> {
        return new Promise((resolve, reject) => {
            this.service.findByCriteria(this.criteria).subscribe(
                (allItems) => {
                    this.exportData = allItems.map(e => ({
                        'Code': e.code,
                        'Label': e.label,
                        'Title': e.title,
                        'Edition date': this.datePipe.transform(e.editionDate, 'dd/MM/yyyy hh:mm'),
                        'Description': e.description,
                        'Number of copies': e.numberOfCopies,
                        'Available': e.available ? 'Vrai' : 'Faux',
                        'Category': e.category?.label,
                        'Author': e.author?.fullName,
                        'Image url': e.imageUrl,
                    }));

                    this.criteriaData = [{
                        'Code': this.criteria.code ? this.criteria.code : environment.emptyForExport,
                        'Label': this.criteria.label ? this.criteria.label : environment.emptyForExport,
                        'Title': this.criteria.title ? this.criteria.title : environment.emptyForExport,
                        'Edition date Min': this.criteria.editionDateFrom ? this.datePipe.transform(this.criteria.editionDateFrom, this.dateFormat) : environment.emptyForExport,
                        'Edition date Max': this.criteria.editionDateTo ? this.datePipe.transform(this.criteria.editionDateTo, this.dateFormat) : environment.emptyForExport,
                        'Description': this.criteria.description ? this.criteria.description : environment.emptyForExport,
                        'Number of copies Min': this.criteria.numberOfCopiesMin ? this.criteria.numberOfCopiesMin : environment.emptyForExport,
                        'Number of copies Max': this.criteria.numberOfCopiesMax ? this.criteria.numberOfCopiesMax : environment.emptyForExport,
                        'Available': this.criteria.available ? (this.criteria.available ? environment.trueValue : environment.falseValue) : environment.emptyForExport,
                        'Image url': this.criteria.imageUrl ? this.criteria.imageUrl : environment.emptyForExport,
                    }];
                    resolve(); // Resolve the promise once data preparation is complete
                },
                (error) => {
                    console.error(error); // Handle errors
                    reject(error); // Reject the promise in case of an error
                }
            );
        });
    }



    get items(): Array<BookDto> {
        return this.service.items;
    }

    set items(value: Array<BookDto>) {
        this.service.items = value;
    }

    get selections(): Array<BookDto> {
        return this.service.selections;
    }

    set selections(value: Array<BookDto>) {
        this.service.selections = value;
    }

    get item(): BookDto {
        return this.service.item;
    }

    set item(value: BookDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): BookCriteria {
        return this.service.criteria;
    }

    set criteria(value: BookCriteria) {
        this.service.criteria = value;
    }

    get dateFormat() {
        return environment.dateFormatList;
    }


    get totalRecords(): number {
        return this._totalRecords;
    }

    set totalRecords(value: number) {
        this._totalRecords = value;
    }

    get pdfName(): string {
        return this._pdfName;
    }

    set pdfName(value: string) {
        this._pdfName = value;
    }

    get createActionIsValid(): boolean {
        return this.service.createActionIsValid;
    }

    set createActionIsValid(value: boolean) {
        this.service.createActionIsValid = value;
    }


    get editActionIsValid(): boolean {
        return this.service.editActionIsValid;
    }

    set editActionIsValid(value: boolean) {
        this.service.editActionIsValid = value;
    }

    get listActionIsValid(): boolean {
        return this.service.listActionIsValid;
    }

    set listActionIsValid(value: boolean) {
        this.service.listActionIsValid = value;
    }

    get deleteActionIsValid(): boolean {
        return this.service.deleteActionIsValid;
    }

    set deleteActionIsValid(value: boolean) {
        this.service.deleteActionIsValid = value;
    }


    get viewActionIsValid(): boolean {
        return this.service.viewActionIsValid;
    }

    set viewActionIsValid(value: boolean) {
        this.service.viewActionIsValid = value;
    }

    get duplicateActionIsValid(): boolean {
        return this.service.duplicateActionIsValid;
    }

    set duplicateActionIsValid(value: boolean) {
        this.service.duplicateActionIsValid = value;
    }

    get createAction(): string {
        return this.service.createAction;
    }

    set createAction(value: string) {
        this.service.createAction = value;
    }

    get listAction(): string {
        return this.service.listAction;
    }

    set listAction(value: string) {
        this.service.listAction = value;
    }

    get editAction(): string {
        return this.service.editAction;
    }

    set editAction(value: string) {
        this.service.editAction = value;
    }

    get deleteAction(): string {
        return this.service.deleteAction;
    }

    set deleteAction(value: string) {
        this.service.deleteAction = value;
    }

    get viewAction(): string {
        return this.service.viewAction;
    }

    set viewAction(value: string) {
        this.service.viewAction = value;
    }

    get duplicateAction(): string {
        return this.service.duplicateAction;
    }

    set duplicateAction(value: string) {
        this.service.duplicateAction = value;
    }

    get entityName(): string {
        return this.service.entityName;
    }

    set entityName(value: string) {
        this.service.entityName = value;
    }
}
