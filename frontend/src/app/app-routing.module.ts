import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthGuard} from 'src/app/zynerator/security/guards/auth.guard';
import {AppLayoutComponent} from 'src/app/layout/app.layout.component';

import {HomePublicComponent} from 'src/app/public/home/home-public.component';

import {LoginAdminComponent} from 'src/app/module/admin/login-admin/login-admin.component';
import {RegisterAdminComponent} from 'src/app/module/admin/register-admin/register-admin.component';
import {ChangePasswordAdminComponent} from 'src/app/module/admin/change-password-admin/change-password-admin.component';
import {LoginClientComponent} from 'src/app/module/client/login-client/login-client.component';
import {RegisterClientComponent} from 'src/app/module/client/register-client/register-client.component';
import {ChangePasswordClientComponent} from 'src/app/module/client/change-password-client/change-password-client.component';
import { ActivationAdminComponent } from './module/admin/activation-admin/activation-admin.component';
import { ForgetPasswordAdminComponent } from './module/admin/forget-password-admin/forget-password-admin.component';
import { GuestBooksListAdminComponent } from './module/admin/guest-books-list/guest-books-list-admin.component';

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {path: '', component: HomePublicComponent},
                {path: 'admin/books', component: GuestBooksListAdminComponent },
                {path: 'admin/login', component: LoginAdminComponent },
                {path: 'admin/register', component: RegisterAdminComponent },
                {path: 'admin/changePassword', component: ChangePasswordAdminComponent },
                {path: 'admin/activation', component: ActivationAdminComponent },
                {path: 'admin/forget-password', component: ForgetPasswordAdminComponent },
                {path: 'client/login', component: LoginClientComponent },
                {path: 'client/register', component: RegisterClientComponent },
                {path: 'client/changePassword', component: ChangePasswordClientComponent },
                {
                    path: 'app',
                    component: AppLayoutComponent,
                    children: [
                        {
                            path: 'admin',
                            loadChildren: () => import( './module/admin/admin-routing.module').then(x => x.AdminRoutingModule),
                            canActivate: [AuthGuard],
                        },
                        {
                            path: 'client',
                            loadChildren: () => import( './module/client/client-routing.module').then(x => x.ClientRoutingModule),
                            canActivate: [AuthGuard],
                        },
                    ],
                    canActivate: [AuthGuard]
                },
            ],
                { scrollPositionRestoration: 'enabled' }
            ),
        ],
    exports: [RouterModule],
    })
export class AppRoutingModule { }
