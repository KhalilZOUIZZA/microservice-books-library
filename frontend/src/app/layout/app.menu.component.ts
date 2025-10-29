import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';
import {RoleService} from "../zynerator/security/shared/service/Role.service";
import {AppComponent} from "../app.component";
import {AuthService} from "../zynerator/security/shared/service/Auth.service";
import {Router} from "@angular/router";
import {AppLayoutComponent} from "./app.layout.component";

@Component({
  selector: 'app-menu',
  templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {
  model: any[];
  modelanonymous: any[];
    modelAdmin: any[];
  modelClient: any[];
constructor(public layoutService: LayoutService, public app: AppComponent, public appMain: AppLayoutComponent, private roleService: RoleService, private authService: AuthService, private router: Router) { }
  ngOnInit() {
    this.modelAdmin =
      [

				{
                    label: 'Pages',
                    icon: 'pi pi-fw pi-briefcase',
                    items: [
					  {
						label: 'Book Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste book',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/book/book/list']
								  },
								  {
									label: 'Liste category',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/book/category/list']
								  },
								  {
									label: 'Liste author',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/book/author/list']
								  },
								  {
									label: 'Liste copy',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/book/copy/list']
								  },
						]
					  },
					  {
						label: 'Reservation Management',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Liste reservation item',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/reservation/reservation-item/list']
								  },
								  {
									label: 'Liste reservation',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/reservation/reservation/list']
								  },
								  {
									label: 'Liste reservation state',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/reservation/reservation-state/list']
								  },
								  {
									label: 'Liste client',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/admin/reservation/client/list']
								  },
						]
					  },

				   {
					  label: 'Security Management',
					  icon: 'pi pi-wallet',
					  items: [
						  {
							  label: 'List User',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/user/list']
						  },
						  {
							  label: 'List Model',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/model-permission/list']
						  },
						  {
							  label: 'List Action Permission',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/action-permission/list']
						  },
					  ]
				  }
			]
	    }
    ];
    this.modelClient =
      [

				{
                    label: 'Pages',
                    icon: 'pi pi-fw pi-briefcase',
                    items: [
					  {
						label: 'Books',
						icon: 'pi pi-wallet',
						items: [
								  {
									label: 'Books List',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/client/book/book/list']
								  },
						]
					  },
					  {
						label: 'Reservation',
						icon: 'pi pi-wallet',
						items: [
								  /*
									{	
									label: 'Liste reservation item',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/client/reservation/reservation-item/list']
								  },
								  */
								  {
									label: 'Reservations List',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/client/reservation/reservation/list']
								  },
								  
								 /* {
									label: 'Liste client',
									icon: 'pi pi-fw pi-plus-circle',
									routerLink: ['/app/client/reservation/client/list']
								  },
								  */
						]
					  },

				   
{
					  label: 'Profile',
					  icon: 'pi pi-wallet',
					  items: [
						{
							label: 'Account Settings',
							icon: 'pi pi-fw pi-plus-circle',
							routerLink: ['/app/client/reservation/client/list']
						  },
						  /*

							{
							  label: 'List User',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/user/list']
						  },
						  {
							  label: 'List Model',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/model-permission/list']
						  },
						  {
							  label: 'List Action Permission',
							  icon: 'pi pi-fw pi-plus-circle',
							  routerLink: ['/app/admin/security/action-permission/list']
						  },
						  */
					  ]
				  }
				   
			]
	    }
    ];

        if (this.authService.authenticated) {
            if (this.authService.authenticatedUser.roleUsers) {
              this.authService.authenticatedUser.roleUsers.forEach(role => {
                  const roleName: string = this.getRole(role.role.authority);
                  this.roleService._role.next(roleName.toUpperCase());
                  eval('this.model = this.model' + this.getRole(role.role.authority));
              })
            }
        }
  }

    getRole(text){
        const [role, ...rest] = text.split('_');
        return this.upperCaseFirstLetter(rest.join(''));
    }

    upperCaseFirstLetter(word: string) {
      if (!word) { return word; }
      return word[0].toUpperCase() + word.substr(1).toLowerCase();
    }

    onMenuClick(event) {
        this.appMain.onMenuClick(event);
    }
}
