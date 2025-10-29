import {BookCriteria} from './BookCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class CopyCriteria extends BaseCriteria {

    public id: number;
    public serialNumber: string;
    public serialNumberLike: string;
  public book: BookCriteria ;
  public books: Array<BookCriteria> ;

}
