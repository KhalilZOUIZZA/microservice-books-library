import {CopyDto} from '../book/Copy.model';
import {ReservationDto} from './Reservation.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class ReservationItemDto extends BaseDto{

   public returnDate: Date;

    public copy: CopyDto ;
    public reservation: ReservationDto ;


    constructor() {
        super();

        this.returnDate = null;

        }

}
