import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {CopyAdminService} from 'src/app/shared/service/admin/book/CopyAdmin.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyCriteria} from 'src/app/shared/criteria/book/CopyCriteria.model';
import {BookDto} from 'src/app/shared/model/book/Book.model';
import {BookAdminService} from 'src/app/shared/service/admin/book/BookAdmin.service';
@Component({
  selector: 'app-copy-create-admin',
  templateUrl: './copy-create-admin.component.html'
})
export class CopyCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;



   private _validCopySerialNumber = true;
    private _validBookCode = true;
    private _validBookLabel = true;
    private _validBookTitle = true;
    private _validBookEditionDate = true;
    private _validBookNumberOfCopies = true;
    private _validBookAvailable = true;

	constructor(private service: CopyAdminService , private bookService: BookAdminService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.bookService.findAll().subscribe((data) => this.books = data);
    }



    public save(): void {
        this.submitted = true;
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.saveWithShowOption(false);
        } else {
            this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs sur le formulaire'});
        }
    }

    public saveWithShowOption(showList: boolean) {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;
                this.submitted = false;
                this.item = new CopyDto();
            } else {
                this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }

        }, error => {
            console.log(error);
        });
    }


    public hideCreateDialog() {
        this.createDialog = false;
        this.setValidation(true);
    }





    public  setValidation(value: boolean){
        this.validCopySerialNumber = value;
    }



    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateCopySerialNumber();
    }

    public validateCopySerialNumber(){
        if (this.stringUtilService.isEmpty(this.item.serialNumber)) {
        this.errorMessages.push('Serial number non valide');
        this.validCopySerialNumber = false;
        } else {
            this.validCopySerialNumber = true;
        }
    }


    public async openCreateBook(book: string) {
    const isPermistted = await this.roleService.isPermitted('Book', 'add');
    if(isPermistted) {
         this.book = new BookDto();
         this.createBookDialog = true;
    }else{
        this.messageService.add({
        severity: 'error', summary: 'erreur', detail: 'problème de permission'
        });
     }
    }

    get book(): BookDto {
        return this.bookService.item;
    }
    set book(value: BookDto) {
        this.bookService.item = value;
    }
    get books(): Array<BookDto> {
        return this.bookService.items;
    }
    set books(value: Array<BookDto>) {
        this.bookService.items = value;
    }
    get createBookDialog(): boolean {
        return this.bookService.createDialog;
    }
    set createBookDialog(value: boolean) {
        this.bookService.createDialog= value;
    }



    get validCopySerialNumber(): boolean {
        return this._validCopySerialNumber;
    }

    set validCopySerialNumber(value: boolean) {
         this._validCopySerialNumber = value;
    }

    get validBookCode(): boolean {
        return this._validBookCode;
    }
    set validBookCode(value: boolean) {
        this._validBookCode = value;
    }
    get validBookLabel(): boolean {
        return this._validBookLabel;
    }
    set validBookLabel(value: boolean) {
        this._validBookLabel = value;
    }
    get validBookTitle(): boolean {
        return this._validBookTitle;
    }
    set validBookTitle(value: boolean) {
        this._validBookTitle = value;
    }
    get validBookEditionDate(): boolean {
        return this._validBookEditionDate;
    }
    set validBookEditionDate(value: boolean) {
        this._validBookEditionDate = value;
    }
    get validBookNumberOfCopies(): boolean {
        return this._validBookNumberOfCopies;
    }
    set validBookNumberOfCopies(value: boolean) {
        this._validBookNumberOfCopies = value;
    }
    get validBookAvailable(): boolean {
        return this._validBookAvailable;
    }
    set validBookAvailable(value: boolean) {
        this._validBookAvailable = value;
    }


    get items(): Array<CopyDto> {
        return this.service.items;
    }

    set items(value: Array<CopyDto>) {
        this.service.items = value;
    }

    get item(): CopyDto {
        return this.service.item;
    }

    set item(value: CopyDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): CopyCriteria {
        return this.service.criteria;
    }

    set criteria(value: CopyCriteria) {
        this.service.criteria = value;
    }

    get dateFormat() {
        return environment.dateFormatCreate;
    }

    get dateFormatColumn() {
        return environment.dateFormatCreate;
    }

    get submitted(): boolean {
        return this._submitted;
    }

    set submitted(value: boolean) {
        this._submitted = value;
    }

    get errorMessages(): string[] {
        if (this._errorMessages == null) {
            this._errorMessages = new Array<string>();
        }
        return this._errorMessages;
    }

    set errorMessages(value: string[]) {
        this._errorMessages = value;
    }

    get validate(): boolean {
        return this.service.validate;
    }

    set validate(value: boolean) {
        this.service.validate = value;
    }


    get activeTab(): number {
        return this._activeTab;
    }

    set activeTab(value: number) {
        this._activeTab = value;
    }

}
