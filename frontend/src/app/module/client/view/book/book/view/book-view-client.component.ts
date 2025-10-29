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
  selector: 'app-book-view-client',
  templateUrl: './book-view-client.component.html'
})
export class BookViewClientComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;


    copies = new CopyDto();
    copiess: Array<CopyDto> = [];

    constructor(private service: BookClientService, private categoryService: CategoryClientService, private copyService: CopyClientService, private authorService: AuthorClientService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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

    public hideViewDialog() {
        this.viewDialog = false;
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

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
