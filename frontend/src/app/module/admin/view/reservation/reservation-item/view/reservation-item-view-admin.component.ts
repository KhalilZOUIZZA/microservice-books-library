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


import {ReservationItemAdminService} from 'src/app/shared/service/admin/reservation/ReservationItemAdmin.service';
import {ReservationItemDto} from 'src/app/shared/model/reservation/ReservationItem.model';
import {ReservationItemCriteria} from 'src/app/shared/criteria/reservation/ReservationItemCriteria.model';

import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyAdminService} from 'src/app/shared/service/admin/book/CopyAdmin.service';
import {ReservationDto} from 'src/app/shared/model/reservation/Reservation.model';
import {ReservationAdminService} from 'src/app/shared/service/admin/reservation/ReservationAdmin.service';
@Component({
  selector: 'app-reservation-item-view-admin',
  templateUrl: './reservation-item-view-admin.component.html'
})
export class ReservationItemViewAdminComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;



    constructor(private service: ReservationItemAdminService, private copyService: CopyAdminService, private reservationService: ReservationAdminService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): ReservationItemCriteria {
        return this.service.criteria;
    }

    set criteria(value: ReservationItemCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
