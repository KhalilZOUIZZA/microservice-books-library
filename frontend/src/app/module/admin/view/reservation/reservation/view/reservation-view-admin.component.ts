import {Component, OnInit} from '@angular/core';


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
import {ConfirmationService, MessageService,MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';


import {ReservationAdminService} from 'src/app/shared/service/admin/reservation/ReservationAdmin.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationCriteria} from 'src/app/shared/criteria/reservation/ReservationCriteria.model';

import {ReservationStateDto} from 'src/app/shared/model/reservation/ReservationState.model';
import {ReservationStateAdminService} from 'src/app/shared/service/admin/reservation/ReservationStateAdmin.service';
import {ReservationItemDto} from 'src/app/shared/model/reservation/ReservationItem.model';
import {ReservationItemAdminService} from 'src/app/shared/service/admin/reservation/ReservationItemAdmin.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyAdminService} from 'src/app/shared/service/admin/book/CopyAdmin.service';
import {ClientDto} from 'src/app/shared/model/reservation/Client.model';
import {ClientAdminService} from 'src/app/shared/service/admin/reservation/ClientAdmin.service';
@Component({
  selector: 'app-reservation-view-admin',
  templateUrl: './reservation-view-admin.component.html'
})
export class ReservationViewAdminComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;


    reservationItems = new ReservationItemDto();
    reservationItemss: Array<ReservationItemDto> = [];

    constructor(private service: ReservationAdminService, private reservationStateService: ReservationStateAdminService, private reservationItemService: ReservationItemAdminService, private copyService: CopyAdminService, private clientService: ClientAdminService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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

    public hideViewDialog() {
        this.viewDialog = false;
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

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): ReservationCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
