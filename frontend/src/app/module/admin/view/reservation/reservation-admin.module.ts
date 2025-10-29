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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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

import { ReservationItemCreateAdminComponent } from './reservation-item/create/reservation-item-create-admin.component';
import { ReservationItemEditAdminComponent } from './reservation-item/edit/reservation-item-edit-admin.component';
import { ReservationItemViewAdminComponent } from './reservation-item/view/reservation-item-view-admin.component';
import { ReservationItemListAdminComponent } from './reservation-item/list/reservation-item-list-admin.component';
import { ReservationCreateAdminComponent } from './reservation/create/reservation-create-admin.component';
import { ReservationEditAdminComponent } from './reservation/edit/reservation-edit-admin.component';
import { ReservationViewAdminComponent } from './reservation/view/reservation-view-admin.component';
import { ReservationListAdminComponent } from './reservation/list/reservation-list-admin.component';
import { ReservationStateCreateAdminComponent } from './reservation-state/create/reservation-state-create-admin.component';
import { ReservationStateEditAdminComponent } from './reservation-state/edit/reservation-state-edit-admin.component';
import { ReservationStateViewAdminComponent } from './reservation-state/view/reservation-state-view-admin.component';
import { ReservationStateListAdminComponent } from './reservation-state/list/reservation-state-list-admin.component';
import { ClientCreateAdminComponent } from './client/create/client-create-admin.component';
import { ClientEditAdminComponent } from './client/edit/client-edit-admin.component';
import { ClientViewAdminComponent } from './client/view/client-view-admin.component';
import { ClientListAdminComponent } from './client/list/client-list-admin.component';

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

    ReservationItemCreateAdminComponent,
    ReservationItemListAdminComponent,
    ReservationItemViewAdminComponent,
    ReservationItemEditAdminComponent,
    ReservationCreateAdminComponent,
    ReservationListAdminComponent,
    ReservationViewAdminComponent,
    ReservationEditAdminComponent,
    ReservationStateCreateAdminComponent,
    ReservationStateListAdminComponent,
    ReservationStateViewAdminComponent,
    ReservationStateEditAdminComponent,
    ClientCreateAdminComponent,
    ClientListAdminComponent,
    ClientViewAdminComponent,
    ClientEditAdminComponent,
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
    BrowserAnimationsModule,
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
  ReservationItemCreateAdminComponent,
  ReservationItemListAdminComponent,
  ReservationItemViewAdminComponent,
  ReservationItemEditAdminComponent,
  ReservationCreateAdminComponent,
  ReservationListAdminComponent,
  ReservationViewAdminComponent,
  ReservationEditAdminComponent,
  ReservationStateCreateAdminComponent,
  ReservationStateListAdminComponent,
  ReservationStateViewAdminComponent,
  ReservationStateEditAdminComponent,
  ClientCreateAdminComponent,
  ClientListAdminComponent,
  ClientViewAdminComponent,
  ClientEditAdminComponent,
  ],
})
export class ReservationAdminModule { }
