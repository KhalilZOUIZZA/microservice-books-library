import {CopyCriteria} from '../book/CopyCriteria.model';
import {ReservationCriteria} from './ReservationCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class ReservationItemCriteria extends BaseCriteria {

    public id: number;
    public returnDate: Date;
    public returnDateFrom: Date;
    public returnDateTo: Date;

}
