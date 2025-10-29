import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {AuthorAdminService} from 'src/app/shared/service/admin/book/AuthorAdmin.service';
import {AuthorDto} from 'src/app/shared/model/book/Author.model';
import {AuthorCriteria} from 'src/app/shared/criteria/book/AuthorCriteria.model';
@Component({
  selector: 'app-author-create-admin',
  templateUrl: './author-create-admin.component.html'
})
export class AuthorCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;



   private _validAuthorCode = true;
   private _validAuthorLabel = true;
   private _validAuthorFullName = true;

	constructor(private service: AuthorAdminService , @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
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
                this.item = new AuthorDto();
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
        this.validAuthorCode = value;
        this.validAuthorLabel = value;
        this.validAuthorFullName = value;
    }



    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateAuthorCode();
        this.validateAuthorLabel();
        this.validateAuthorFullName();
    }

    public validateAuthorCode(){
        if (this.stringUtilService.isEmpty(this.item.code)) {
        this.errorMessages.push('Code non valide');
        this.validAuthorCode = false;
        } else {
            this.validAuthorCode = true;
        }
    }
    public validateAuthorLabel(){
        if (this.stringUtilService.isEmpty(this.item.label)) {
        this.errorMessages.push('Label non valide');
        this.validAuthorLabel = false;
        } else {
            this.validAuthorLabel = true;
        }
    }
    public validateAuthorFullName(){
        if (this.stringUtilService.isEmpty(this.item.fullName)) {
        this.errorMessages.push('Full name non valide');
        this.validAuthorFullName = false;
        } else {
            this.validAuthorFullName = true;
        }
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



    get items(): Array<AuthorDto> {
        return this.service.items;
    }

    set items(value: Array<AuthorDto>) {
        this.service.items = value;
    }

    get item(): AuthorDto {
        return this.service.item;
    }

    set item(value: AuthorDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): AuthorCriteria {
        return this.service.criteria;
    }

    set criteria(value: AuthorCriteria) {
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
