
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class AuthorDto extends BaseDto{

    public code: string;

    public label: string;

    public fullName: string;



    constructor() {
        super();

        this.code = '';
        this.label = '';
        this.fullName = '';

        }

}
