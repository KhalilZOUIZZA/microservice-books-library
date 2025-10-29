import {ReservationStateDto} from './ReservationState.model';
import {ReservationItemDto} from './ReservationItem.model';
import {CopyDto} from '../book/Copy.model';
import {ClientDto} from './Client.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';
import {BookDto} from "../book/Book.model";


export class ReservationDto extends BaseDto{

    public code: string;

   public requestDate: Date;

   public theoreticalReturnDate: Date;

   public effectiveReturnDate: Date;

   book?: BookDto; // Add this property

    public reservationState: ReservationStateDto ;
    public client: ClientDto ;
     public reservationItems: Array<ReservationItemDto>;


    constructor() {
        super();

        this.code = '';
        this.requestDate = null;
        this.theoreticalReturnDate = null;
        this.effectiveReturnDate = null;
        this.reservationItems = new Array<ReservationItemDto>();

        }

}
