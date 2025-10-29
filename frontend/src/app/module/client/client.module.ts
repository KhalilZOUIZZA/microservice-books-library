import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import {MessagesModule} from 'primeng/messages';

import { LoginClientComponent } from './login-client/login-client.component';
import { RegisterClientComponent } from './register-client/register-client.component';
import { ChangePasswordClientComponent } from './change-password-client/change-password-client.component';
import { ActivationClientComponent } from './activation-client/activation-client.component';
import { ForgetPasswordClientComponent } from './forget-password-client/forget-password-client.component';


import { BookClientModule } from './view/book/book-client.module';
import { BookClientRoutingModule } from './view/book/book-client-routing.module';
import { ReservationClientModule } from './view/reservation/reservation-client.module';
import { ReservationClientRoutingModule } from './view/reservation/reservation-client-routing.module';

import {SecurityModule} from 'src/app/module/security/security.module';
import {SecurityRoutingModule} from 'src/app/module/security/security-routing.module';


@NgModule({
  declarations: [
   LoginClientComponent,
   RegisterClientComponent,
   ChangePasswordClientComponent,
   ActivationClientComponent,
   ForgetPasswordClientComponent
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
  BookClientModule,
  BookClientRoutingModule,
  ReservationClientModule,
  ReservationClientRoutingModule,
   SecurityModule,
   SecurityRoutingModule
  ],
  exports: [
    LoginClientComponent,
    RegisterClientComponent,
    ChangePasswordClientComponent,
    ActivationClientComponent,
    ForgetPasswordClientComponent,

    BookClientModule,
    ReservationClientModule,
    SecurityModule
  ],

})
export class ClientModule { }
