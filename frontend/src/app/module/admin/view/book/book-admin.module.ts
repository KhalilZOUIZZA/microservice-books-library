import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {EditorModule} from "primeng/editor";

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';
import {TranslateModule} from '@ngx-translate/core';
import {FileUploadModule} from 'primeng/fileupload';
import {FullCalendarModule} from '@fullcalendar/angular';
import {CardModule} from "primeng/card";
import {TagModule} from "primeng/tag";

import { BookCreateAdminComponent } from './book/create/book-create-admin.component';
import { BookEditAdminComponent } from './book/edit/book-edit-admin.component';
import { BookViewAdminComponent } from './book/view/book-view-admin.component';
import { BookListAdminComponent } from './book/list/book-list-admin.component';
import { CategoryCreateAdminComponent } from './category/create/category-create-admin.component';
import { CategoryEditAdminComponent } from './category/edit/category-edit-admin.component';
import { CategoryViewAdminComponent } from './category/view/category-view-admin.component';
import { CategoryListAdminComponent } from './category/list/category-list-admin.component';
import { AuthorCreateAdminComponent } from './author/create/author-create-admin.component';
import { AuthorEditAdminComponent } from './author/edit/author-edit-admin.component';
import { AuthorViewAdminComponent } from './author/view/author-view-admin.component';
import { AuthorListAdminComponent } from './author/list/author-list-admin.component';
import { CopyCreateAdminComponent } from './copy/create/copy-create-admin.component';
import { CopyEditAdminComponent } from './copy/edit/copy-edit-admin.component';
import { CopyViewAdminComponent } from './copy/view/copy-view-admin.component';
import { CopyListAdminComponent } from './copy/list/copy-list-admin.component';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import {MessagesModule} from 'primeng/messages';
import {PaginatorModule} from 'primeng/paginator';



@NgModule({
  declarations: [

    BookCreateAdminComponent,
    BookListAdminComponent,
    BookViewAdminComponent,
    BookEditAdminComponent,
    CategoryCreateAdminComponent,
    CategoryListAdminComponent,
    CategoryViewAdminComponent,
    CategoryEditAdminComponent,
    AuthorCreateAdminComponent,
    AuthorListAdminComponent,
    AuthorViewAdminComponent,
    AuthorEditAdminComponent,
    CopyCreateAdminComponent,
    CopyListAdminComponent,
    CopyViewAdminComponent,
    CopyEditAdminComponent,
  ],
  imports: [
    CommonModule,
    ToastModule,
    ToolbarModule,
    TableModule,
    ConfirmDialogModule,
    DialogModule,
    PasswordModule,
    InputTextModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    SplitButtonModule,
    DropdownModule,
    TabViewModule,
    InputSwitchModule,
    InputTextareaModule,
    CalendarModule,
    PanelModule,
    MessageModule,
    MessagesModule,
    InputNumberModule,
    BadgeModule,
    MultiSelectModule,
    PaginatorModule,
    TranslateModule,
    FileUploadModule,
    FullCalendarModule,
    CardModule,
    EditorModule,
    TagModule,


  ],
  exports: [
  BookCreateAdminComponent,
  BookListAdminComponent,
  BookViewAdminComponent,
  BookEditAdminComponent,
  CategoryCreateAdminComponent,
  CategoryListAdminComponent,
  CategoryViewAdminComponent,
  CategoryEditAdminComponent,
  AuthorCreateAdminComponent,
  AuthorListAdminComponent,
  AuthorViewAdminComponent,
  AuthorEditAdminComponent,
  CopyCreateAdminComponent,
  CopyListAdminComponent,
  CopyViewAdminComponent,
  CopyEditAdminComponent,
  ],
})
export class BookAdminModule { }
