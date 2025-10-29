
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class CategoryDto extends BaseDto{

    public code: string;

    public label: string;



    constructor() {
        super();

        this.code = '';
        this.label = '';

        }

}
