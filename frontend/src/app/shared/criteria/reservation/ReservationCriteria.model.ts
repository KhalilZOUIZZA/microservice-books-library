import {ReservationStateCriteria} from './ReservationStateCriteria.model';
import {ReservationItemCriteria} from './ReservationItemCriteria.model';
import {CopyCriteria} from '../book/CopyCriteria.model';
import {ClientCriteria} from './ClientCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class ReservationCriteria extends BaseCriteria {

    public id: number;
    public code: string;
    public codeLike: string;
    public requestDate: Date;
    public requestDateFrom: Date;
    public requestDateTo: Date;
    public theoreticalReturnDate: Date;
    public theoreticalReturnDateFrom: Date;
    public theoreticalReturnDateTo: Date;
    public effectiveReturnDate: Date;
    public effectiveReturnDateFrom: Date;
    public effectiveReturnDateTo: Date;
      public reservationItems: Array<ReservationItemCriteria>;

}
