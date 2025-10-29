import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {BookClientService} from 'src/app/shared/service/client/book/BookClient.service';
import {BookDto} from 'src/app/shared/model/book/Book.model';
import {BookCriteria} from 'src/app/shared/criteria/book/BookCriteria.model';
import {CategoryDto} from 'src/app/shared/model/book/Category.model';
import {CategoryClientService} from 'src/app/shared/service/client/book/CategoryClient.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyClientService} from 'src/app/shared/service/client/book/CopyClient.service';
import {AuthorDto} from 'src/app/shared/model/book/Author.model';
import {AuthorClientService} from 'src/app/shared/service/client/book/AuthorClient.service';
@Component({
  selector: 'app-book-create-client',
  templateUrl: './book-create-client.component.html'
})
export class BookCreateClientComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;
    protected copiesIndex = -1;

    private _copiesElement = new CopyDto();


   private _validBookCode = true;
   private _validBookLabel = true;
   private _validBookTitle = true;
   private _validBookEditionDate = true;
   private _validBookNumberOfCopies = true;
   private _validBookAvailable = true;
    private _validCategoryCode = true;
    private _validCategoryLabel = true;
    private _validAuthorCode = true;
    private _validAuthorLabel = true;
    private _validAuthorFullName = true;
    private _validCopiesSerialNumber = true;

	constructor(private service: BookClientService , private categoryService: CategoryClientService, private copyService: CopyClientService, private authorService: AuthorClientService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.categoryService.findAll().subscribe((data) => this.categorys = data);
        this.authorService.findAll().subscribe((data) => this.authors = data);
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
                this.item = new BookDto();
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



    validateCopies(){
        this.errorMessages = new Array();
        this.validateCopiesSerialNumber();
    }


    public  setValidation(value: boolean){
        this.validBookCode = value;
        this.validBookLabel = value;
        this.validBookTitle = value;
        this.validBookEditionDate = value;
        this.validBookNumberOfCopies = value;
        this.validBookAvailable = value;
        this.validCopiesSerialNumber = value;
    }

    public addCopies() {
        if( this.item.copies == null )
            this.item.copies = new Array<CopyDto>();

       this.validateCopies();

       if (this.errorMessages.length === 0) {
            if (this.copiesIndex == -1){
                this.item.copies.push({... this.copiesElement});
            }else {
                this.item.copies[this.copiesIndex] =this.copiesElement;
            }
              this.copiesElement = new CopyDto();
              this.copiesIndex = -1;
       }else{
           this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs suivant : ' + this.errorMessages});
       }
    }

    public deleteCopies(p: CopyDto, index: number) {
        this.item.copies.splice(index, 1);
    }

    public editCopies(p: CopyDto, index: number) {
        this.copiesElement = {... p};
        this.copiesIndex = index;
        this.activeTab = 0;
    }


    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateBookCode();
        this.validateBookLabel();
        this.validateBookTitle();
        this.validateBookEditionDate();
        this.validateBookNumberOfCopies();
        this.validateBookAvailable();
    }

    public validateBookCode(){
        if (this.stringUtilService.isEmpty(this.item.code)) {
        this.errorMessages.push('Code non valide');
        this.validBookCode = false;
        } else {
            this.validBookCode = true;
        }
    }
    public validateBookLabel(){
        if (this.stringUtilService.isEmpty(this.item.label)) {
        this.errorMessages.push('Label non valide');
        this.validBookLabel = false;
        } else {
            this.validBookLabel = true;
        }
    }
    public validateBookTitle(){
        if (this.stringUtilService.isEmpty(this.item.title)) {
        this.errorMessages.push('Title non valide');
        this.validBookTitle = false;
        } else {
            this.validBookTitle = true;
        }
    }
    public validateBookEditionDate(){
        if (this.stringUtilService.isEmpty(this.item.editionDate)) {
        this.errorMessages.push('Edition date non valide');
        this.validBookEditionDate = false;
        } else {
            this.validBookEditionDate = true;
        }
    }
    public validateBookNumberOfCopies(){
        if (this.stringUtilService.isEmpty(this.item.numberOfCopies)) {
        this.errorMessages.push('Number of copies non valide');
        this.validBookNumberOfCopies = false;
        } else {
            this.validBookNumberOfCopies = true;
        }
    }
    public validateBookAvailable(){
        if (this.stringUtilService.isEmpty(this.item.available)) {
        this.errorMessages.push('Available non valide');
        this.validBookAvailable = false;
        } else {
            this.validBookAvailable = true;
        }
    }

    public validateCopiesSerialNumber(){
        if (this.copiesElement.serialNumber == null) {
            this.errorMessages.push('SerialNumber de la copy est  invalide');
            this.validCopiesSerialNumber = false;
        } else {
            this.validCopiesSerialNumber = true;
        }
    }

    public async openCreateCategory(category: string) {
    const isPermistted = await this.roleService.isPermitted('Category', 'add');
    if(isPermistted) {
         this.category = new CategoryDto();
         this.createCategoryDialog = true;
    }else{
        this.messageService.add({
        severity: 'error', summary: 'erreur', detail: 'problème de permission'
        });
     }
    }
    public async openCreateAuthor(author: string) {
    const isPermistted = await this.roleService.isPermitted('Author', 'add');
    if(isPermistted) {
         this.author = new AuthorDto();
         this.createAuthorDialog = true;
    }else{
        this.messageService.add({
        severity: 'error', summary: 'erreur', detail: 'problème de permission'
        });
     }
    }

    get category(): CategoryDto {
        return this.categoryService.item;
    }
    set category(value: CategoryDto) {
        this.categoryService.item = value;
    }
    get categorys(): Array<CategoryDto> {
        return this.categoryService.items;
    }
    set categorys(value: Array<CategoryDto>) {
        this.categoryService.items = value;
    }
    get createCategoryDialog(): boolean {
        return this.categoryService.createDialog;
    }
    set createCategoryDialog(value: boolean) {
        this.categoryService.createDialog= value;
    }
    get author(): AuthorDto {
        return this.authorService.item;
    }
    set author(value: AuthorDto) {
        this.authorService.item = value;
    }
    get authors(): Array<AuthorDto> {
        return this.authorService.items;
    }
    set authors(value: Array<AuthorDto>) {
        this.authorService.items = value;
    }
    get createAuthorDialog(): boolean {
        return this.authorService.createDialog;
    }
    set createAuthorDialog(value: boolean) {
        this.authorService.createDialog= value;
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

    get validCategoryCode(): boolean {
        return this._validCategoryCode;
    }
    set validCategoryCode(value: boolean) {
        this._validCategoryCode = value;
    }
    get validCategoryLabel(): boolean {
        return this._validCategoryLabel;
    }
    set validCategoryLabel(value: boolean) {
        this._validCategoryLabel = value;
    }
    get validAuthorCode(): boolean {
        return this._validAuthorCode;
    }
    set validAuthorCode(value: boolean) {
        this._validAuthorCode = value;
    }
    get validAuthorLabel(): boolean {
        return this._validAuthorLabel;
    }
    set validAuthorLabel(value: boolean) {
        this._validAuthorLabel = value;
    }
    get validAuthorFullName(): boolean {
        return this._validAuthorFullName;
    }
    set validAuthorFullName(value: boolean) {
        this._validAuthorFullName = value;
    }
    get validCopiesSerialNumber(): boolean {
        return this._validCopiesSerialNumber;
    }
    set validCopiesSerialNumber(value: boolean) {
        this._validCopiesSerialNumber = value;
    }

    get copiesElement(): CopyDto {
        if( this._copiesElement == null )
            this._copiesElement = new CopyDto();
        return this._copiesElement;
    }

    set copiesElement(value: CopyDto) {
        this._copiesElement = value;
    }

    get items(): Array<BookDto> {
        return this.service.items;
    }

    set items(value: Array<BookDto>) {
        this.service.items = value;
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

    get criteria(): BookCriteria {
        return this.service.criteria;
    }

    set criteria(value: BookCriteria) {
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
