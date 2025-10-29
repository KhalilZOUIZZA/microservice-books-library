import {BookDto} from './Book.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class CopyDto extends BaseDto{

    public serialNumber: string;

    public book: BookDto ;


    constructor() {
        super();

        this.serialNumber = '';
        this.book = new BookDto() ;

        }

}
