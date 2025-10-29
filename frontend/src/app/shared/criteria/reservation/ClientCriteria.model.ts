
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class ClientCriteria extends BaseCriteria {

    public description: string;
    public descriptionLike: string;
    public passwordChanged: null | boolean;
    public username: string;
    public usernameLike: string;
    public accountNonExpired: null | boolean;
    public credentialsNonExpired: null | boolean;
    public enabled: null | boolean;
    public email: string;
    public emailLike: string;
    public password: string;
    public passwordLike: string;
    public accountNonLocked: null | boolean;

}
