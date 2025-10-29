import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {ReservationStateAdminService} from 'src/app/shared/service/admin/reservation/ReservationStateAdmin.service';
import {ReservationStateDto} from 'src/app/shared/model/reservation/ReservationState.model';
import {ReservationStateCriteria} from 'src/app/shared/criteria/reservation/ReservationStateCriteria.model';



@Component({
  selector: 'app-reservation-state-edit-admin',
  templateUrl: './reservation-state-edit-admin.component.html'
})
export class ReservationStateEditAdminComponent implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;
    private _file: any;
    private _files: any;



    private _validReservationStateCode = true;
    private _validReservationStateLabel = true;
    private _validReservationStateStyle = true;




    constructor(private service: ReservationStateAdminService , @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
    }

    public prepareEdit() {
    }



 public edit(): void {
        this.submitted = true;
        this.prepareEdit();
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.editWithShowOption(false);
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Erreurs',
                detail: 'Merci de corrigé les erreurs sur le formulaire'
            });
        }
    }

    public editWithShowOption(showList: boolean) {
        this.service.edit().subscribe(religion=>{
            const myIndex = this.items.findIndex(e => e.id === this.item.id);
            this.items[myIndex] = religion;
            this.editDialog = false;
            this.submitted = false;
            this.item = new ReservationStateDto();
        } , error =>{
            console.log(error);
        });
    }

    public hideEditDialog() {
        this.editDialog = false;
        this.setValidation(true);
    }





    public setValidation(value: boolean){
        this.validReservationStateCode = value;
        this.validReservationStateLabel = value;
        this.validReservationStateStyle = value;
    }


    public validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateReservationStateCode();
        this.validateReservationStateLabel();
        this.validateReservationStateStyle();
    }

    public validateReservationStateCode(){
        if (this.stringUtilService.isEmpty(this.item.code)) {
            this.errorMessages.push('Code non valide');
            this.validReservationStateCode = false;
        } else {
            this.validReservationStateCode = true;
        }
    }

    public validateReservationStateLabel(){
        if (this.stringUtilService.isEmpty(this.item.label)) {
            this.errorMessages.push('Label non valide');
            this.validReservationStateLabel = false;
        } else {
            this.validReservationStateLabel = true;
        }
    }

    public validateReservationStateStyle(){
        if (this.stringUtilService.isEmpty(this.item.style)) {
            this.errorMessages.push('Style non valide');
            this.validReservationStateStyle = false;
        } else {
            this.validReservationStateStyle = true;
        }
    }







    get validReservationStateCode(): boolean {
        return this._validReservationStateCode;
    }
    set validReservationStateCode(value: boolean) {
        this._validReservationStateCode = value;
    }
    get validReservationStateLabel(): boolean {
        return this._validReservationStateLabel;
    }
    set validReservationStateLabel(value: boolean) {
        this._validReservationStateLabel = value;
    }
    get validReservationStateStyle(): boolean {
        return this._validReservationStateStyle;
    }
    set validReservationStateStyle(value: boolean) {
        this._validReservationStateStyle = value;
    }


	get items(): Array<ReservationStateDto> {
        return this.service.items;
    }

    set items(value: Array<ReservationStateDto>) {
        this.service.items = value;
    }

    get item(): ReservationStateDto {
        return this.service.item;
    }

    set item(value: ReservationStateDto) {
        this.service.item = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get criteria(): ReservationStateCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationStateCriteria) {
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
