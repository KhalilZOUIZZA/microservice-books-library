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




import {ReservationItemClientService} from 'src/app/shared/service/client/reservation/ReservationItemClient.service';
import {ReservationItemDto} from 'src/app/shared/model/reservation/ReservationItem.model';
import {ReservationItemCriteria} from 'src/app/shared/criteria/reservation/ReservationItemCriteria.model';


import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyClientService} from 'src/app/shared/service/client/book/CopyClient.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationClientService} from 'src/app/shared/service/client/reservation/ReservationClient.service';

@Component({
  selector: 'app-reservation-item-edit-client',
  templateUrl: './reservation-item-edit-client.component.html'
})
export class ReservationItemEditClientComponent implements OnInit {

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




    private _validCopySerialNumber = true;
    private _validReservationCode = true;
    private _validReservationRequestDate = true;
    private _validReservationTheoreticalReturnDate = true;



    constructor(private service: ReservationItemClientService , private copyService: CopyClientService, private reservationService: ReservationClientService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.copyService.findAll().subscribe((data) => this.copys = data);
        this.reservationService.findAll().subscribe((data) => this.reservations = data);
    }

    public prepareEdit() {
        this.item.returnDate = this.service.format(this.item.returnDate);
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
            this.item = new ReservationItemDto();
        } , error =>{
            console.log(error);
        });
    }

    public hideEditDialog() {
        this.editDialog = false;
        this.setValidation(true);
    }





    public setValidation(value: boolean){
    }


    public validateForm(): void{
        this.errorMessages = new Array<string>();
    }




   public async openCreateReservation(reservation: string) {
        const isPermistted = await this.roleService.isPermitted('Reservation', 'edit');
        if (isPermistted) {
             this.reservation = new ReservationDto();
             this.createReservationDialog = true;
        }else {
             this.messageService.add({
                severity: 'error', summary: 'erreur', detail: 'problème de permission'
            });
        }
    }

    get reservation(): ReservationDto {
        return this.reservationService.item;
    }
    set reservation(value: ReservationDto) {
        this.reservationService.item = value;
    }
    get reservations(): Array<ReservationDto> {
        return this.reservationService.items;
    }
    set reservations(value: Array<ReservationDto>) {
        this.reservationService.items = value;
    }
    get createReservationDialog(): boolean {
        return this.reservationService.createDialog;
    }
    set createReservationDialog(value: boolean) {
        this.reservationService.createDialog= value;
    }
    get copy(): CopyDto {
        return this.copyService.item;
    }
    set copy(value: CopyDto) {
        this.copyService.item = value;
    }
    get copys(): Array<CopyDto> {
        return this.copyService.items;
    }
    set copys(value: Array<CopyDto>) {
        this.copyService.items = value;
    }
    get createCopyDialog(): boolean {
        return this.copyService.createDialog;
    }
    set createCopyDialog(value: boolean) {
        this.copyService.createDialog= value;
    }



    get validCopySerialNumber(): boolean {
        return this._validCopySerialNumber;
    }
    set validCopySerialNumber(value: boolean) {
        this._validCopySerialNumber = value;
    }
    get validReservationCode(): boolean {
        return this._validReservationCode;
    }
    set validReservationCode(value: boolean) {
        this._validReservationCode = value;
    }
    get validReservationRequestDate(): boolean {
        return this._validReservationRequestDate;
    }
    set validReservationRequestDate(value: boolean) {
        this._validReservationRequestDate = value;
    }
    get validReservationTheoreticalReturnDate(): boolean {
        return this._validReservationTheoreticalReturnDate;
    }
    set validReservationTheoreticalReturnDate(value: boolean) {
        this._validReservationTheoreticalReturnDate = value;
    }

	get items(): Array<ReservationItemDto> {
        return this.service.items;
    }

    set items(value: Array<ReservationItemDto>) {
        this.service.items = value;
    }

    get item(): ReservationItemDto {
        return this.service.item;
    }

    set item(value: ReservationItemDto) {
        this.service.item = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get criteria(): ReservationItemCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationItemCriteria) {
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
