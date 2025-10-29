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


import {CopyAdminService} from 'src/app/shared/service/admin/book/CopyAdmin.service';
import {CopyDto} from 'src/app/shared/model/book/Copy.model';
import {CopyCriteria} from 'src/app/shared/criteria/book/CopyCriteria.model';

import {BookDto} from 'src/app/shared/model/book/Book.model';
import {BookAdminService} from 'src/app/shared/service/admin/book/BookAdmin.service';
@Component({
  selector: 'app-copy-view-admin',
  templateUrl: './copy-view-admin.component.html'
})
export class CopyViewAdminComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;



    constructor(private service: CopyAdminService, private bookService: BookAdminService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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

    public hideViewDialog() {
        this.viewDialog = false;
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

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): CopyCriteria {
        return this.service.criteria;
    }

    set criteria(value: CopyCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
