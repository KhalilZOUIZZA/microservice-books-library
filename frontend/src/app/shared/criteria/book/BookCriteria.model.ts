import {CategoryCriteria} from './CategoryCriteria.model';
import {CopyCriteria} from './CopyCriteria.model';
import {AuthorCriteria} from './AuthorCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class BookCriteria extends BaseCriteria {

    public id: number;
    public code: string;
    public codeLike: string;
    public label: string;
    public labelLike: string;
    public title: string;
    public titleLike: string;
    public sortOrder: string;
    public editionDate: Date;
    public editionDateFrom: Date;
    public editionDateTo: Date;
    public description: string;
    public descriptionLike: string;
     public numberOfCopies: number;
     public numberOfCopiesMin: number;
     public numberOfCopiesMax: number;
    public available: null | boolean;
    public imageUrl: string;
    public imageUrlLike: string;
      public copies: Array<CopyCriteria>;

}
