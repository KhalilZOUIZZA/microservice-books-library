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




import {ReservationClientService} from 'src/app/shared/service/client/reservation/ReservationClient.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationCriteria} from 'src/app/shared/criteria/reservation/ReservationCriteria.model';


import {ReservationStateDto} from 'src/app/shared/model/reservation/ReservationState.model';
import {ReservationStateClientService} from 'src/app/shared/service/client/reservation/ReservationStateClient.service';
import {ReservationItemDto} from 'src/app/shared/model/reservation/ReservationItem.model';
import {ReservationItemClientService} from 'src/app/shared/service/client/reservation/ReservationItemClient.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyClientService} from 'src/app/shared/service/client/book/CopyClient.service';
import {ClientDto} from 'src/app/shared/model/reservation/Client.model';
import {ClientClientService} from 'src/app/shared/service/client/reservation/ClientClient.service';

@Component({
  selector: 'app-reservation-edit-client',
  templateUrl: './reservation-edit-client.component.html'
})
export class ReservationEditClientComponent implements OnInit {

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

    protected reservationItemsIndex = -1;

    private _reservationItemsElement = new ReservationItemDto();

    private _validReservationCode = true;
    private _validReservationRequestDate = true;
    private _validReservationTheoreticalReturnDate = true;

    private _validReservationStateCode = true;
    private _validReservationStateLabel = true;
    private _validReservationStateStyle = true;



    constructor(private service: ReservationClientService , private reservationStateService: ReservationStateClientService, private reservationItemService: ReservationItemClientService, private copyService: CopyClientService, private clientService: ClientClientService, @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.reservationItemsElement.copy = new CopyDto();
        this.copyService.findAll().subscribe((data) => this.copys = data);

        this.reservationStateService.findAll().subscribe((data) => this.reservationStates = data);
        this.clientService.findAll().subscribe((data) => this.clients = data);
    }

    public prepareEdit() {
        this.item.requestDate = this.service.format(this.item.requestDate);
        this.item.theoreticalReturnDate = this.service.format(this.item.theoreticalReturnDate);
        this.item.effectiveReturnDate = this.service.format(this.item.effectiveReturnDate);
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
            this.item = new ReservationDto();
        } , error =>{
            console.log(error);
        });
    }

    public hideEditDialog() {
        this.editDialog = false;
        this.setValidation(true);
    }





    public validateReservationItems(){
        this.errorMessages = new Array();
    }

    public setValidation(value: boolean){
        this.validReservationCode = value;
        this.validReservationRequestDate = value;
        this.validReservationTheoreticalReturnDate = value;
    }

    public addReservationItems() {
        if( this.item.reservationItems == null )
            this.item.reservationItems = new Array<ReservationItemDto>();

       this.validateReservationItems();

       if (this.errorMessages.length === 0) {
            if (this.reservationItemsIndex == -1){
                this.item.reservationItems.push({... this.reservationItemsElement});
            }else {
                this.item.reservationItems[this.reservationItemsIndex] =this.reservationItemsElement;
            }
              this.reservationItemsElement = new ReservationItemDto();
              this.reservationItemsIndex = -1;
       }else{
           this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs suivant : ' + this.errorMessages});
       }
    }

    public deleteReservationItems(p: ReservationItemDto, index: number) {
        this.item.reservationItems.splice(index, 1);
    }

    public editReservationItems(p: ReservationItemDto, index: number) {
        this.reservationItemsElement = {... p};
        this.reservationItemsIndex = index;
        this.activeTab = 0;
    }

    public validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateReservationCode();
        this.validateReservationRequestDate();
        this.validateReservationTheoreticalReturnDate();
    }

    public validateReservationCode(){
        if (this.stringUtilService.isEmpty(this.item.code)) {
            this.errorMessages.push('Code non valide');
            this.validReservationCode = false;
        } else {
            this.validReservationCode = true;
        }
    }

    public validateReservationRequestDate(){
        if (this.stringUtilService.isEmpty(this.item.requestDate)) {
            this.errorMessages.push('Request date non valide');
            this.validReservationRequestDate = false;
        } else {
            this.validReservationRequestDate = true;
        }
    }

    public validateReservationTheoreticalReturnDate(){
        if (this.stringUtilService.isEmpty(this.item.theoreticalReturnDate)) {
            this.errorMessages.push('Theoretical return date non valide');
            this.validReservationTheoreticalReturnDate = false;
        } else {
            this.validReservationTheoreticalReturnDate = true;
        }
    }




   public async openCreateClient(client: string) {
        const isPermistted = await this.roleService.isPermitted('Client', 'edit');
        if (isPermistted) {
             this.client = new ClientDto();
             this.createClientDialog = true;
        }else {
             this.messageService.add({
                severity: 'error', summary: 'erreur', detail: 'problème de permission'
            });
        }
    }
   public async openCreateReservationState(reservationState: string) {
        const isPermistted = await this.roleService.isPermitted('ReservationState', 'edit');
        if (isPermistted) {
             this.reservationState = new ReservationStateDto();
             this.createReservationStateDialog = true;
        }else {
             this.messageService.add({
                severity: 'error', summary: 'erreur', detail: 'problème de permission'
            });
        }
    }

    get client(): ClientDto {
        return this.clientService.item;
    }
    set client(value: ClientDto) {
        this.clientService.item = value;
    }
    get clients(): Array<ClientDto> {
        return this.clientService.items;
    }
    set clients(value: Array<ClientDto>) {
        this.clientService.items = value;
    }
    get createClientDialog(): boolean {
        return this.clientService.createDialog;
    }
    set createClientDialog(value: boolean) {
        this.clientService.createDialog= value;
    }
    get reservationState(): ReservationStateDto {
        return this.reservationStateService.item;
    }
    set reservationState(value: ReservationStateDto) {
        this.reservationStateService.item = value;
    }
    get reservationStates(): Array<ReservationStateDto> {
        return this.reservationStateService.items;
    }
    set reservationStates(value: Array<ReservationStateDto>) {
        this.reservationStateService.items = value;
    }
    get createReservationStateDialog(): boolean {
        return this.reservationStateService.createDialog;
    }
    set createReservationStateDialog(value: boolean) {
        this.reservationStateService.createDialog= value;
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

    get reservationItemsElement(): ReservationItemDto {
        if( this._reservationItemsElement == null )
            this._reservationItemsElement = new ReservationItemDto();
         return this._reservationItemsElement;
    }

    set reservationItemsElement(value: ReservationItemDto) {
        this._reservationItemsElement = value;
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

	get items(): Array<ReservationDto> {
        return this.service.items;
    }

    set items(value: Array<ReservationDto>) {
        this.service.items = value;
    }

    get item(): ReservationDto {
        return this.service.item;
    }

    set item(value: ReservationDto) {
        this.service.item = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get criteria(): ReservationCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationCriteria) {
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
