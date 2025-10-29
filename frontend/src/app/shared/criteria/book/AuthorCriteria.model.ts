
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class AuthorCriteria extends BaseCriteria {

    public id: number;
    public code: string;
    public codeLike: string;
    public label: string;
    public labelLike: string;
    public fullName: string;
    public fullNameLike: string;

}
