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

import { ReservationItemCreateClientComponent } from './reservation-item/create/reservation-item-create-client.component';
import { ReservationItemEditClientComponent } from './reservation-item/edit/reservation-item-edit-client.component';
import { ReservationItemViewClientComponent } from './reservation-item/view/reservation-item-view-client.component';
import { ReservationItemListClientComponent } from './reservation-item/list/reservation-item-list-client.component';
import { ReservationCreateClientComponent } from './reservation/create/reservation-create-client.component';
import { ReservationEditClientComponent } from './reservation/edit/reservation-edit-client.component';
import { ReservationViewClientComponent } from './reservation/view/reservation-view-client.component';
import { ReservationListClientComponent } from './reservation/list/reservation-list-client.component';
import { ClientCreateClientComponent } from './client/create/client-create-client.component';
import { ClientEditClientComponent } from './client/edit/client-edit-client.component';
import { ClientViewClientComponent } from './client/view/client-view-client.component';
import { ClientListClientComponent } from './client/list/client-list-client.component';

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

    ReservationItemCreateClientComponent,
    ReservationItemListClientComponent,
    ReservationItemViewClientComponent,
    ReservationItemEditClientComponent,
    ReservationCreateClientComponent,
    ReservationListClientComponent,
    ReservationViewClientComponent,
    ReservationEditClientComponent,
    ClientCreateClientComponent,
    ClientListClientComponent,
    ClientViewClientComponent,
    ClientEditClientComponent,
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
  ReservationItemCreateClientComponent,
  ReservationItemListClientComponent,
  ReservationItemViewClientComponent,
  ReservationItemEditClientComponent,
  ReservationCreateClientComponent,
  ReservationListClientComponent,
  ReservationViewClientComponent,
  ReservationEditClientComponent,
  ClientCreateClientComponent,
  ClientListClientComponent,
  ClientViewClientComponent,
  ClientEditClientComponent,
  ],
})
export class ReservationClientModule { }
