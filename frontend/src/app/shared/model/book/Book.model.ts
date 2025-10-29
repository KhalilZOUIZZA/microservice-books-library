import {CategoryDto} from './Category.model';
import {CopyDto} from './Copy.model';
import {AuthorDto} from './Author.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class BookDto extends BaseDto{

    public code: string;

    public label: string;

    public title: string;

   public editionDate: Date;

    public description: string;

    public numberOfCopies: null | number;

   public available: null | boolean;

    public imageBase64: string; // New Property

    public imageUrl: string;

    public category: CategoryDto ;
    public author: AuthorDto ;
     public copies: Array<CopyDto>;


    constructor() {
        super();

        this.code = '';
        this.label = '';
        this.title = '';
        this.editionDate = null;
        this.description = '';
        this.numberOfCopies = null;
        this.available = null;
        this.imageUrl = '';
        this.imageBase64 = ''; // Initialize to empty

        this.copies = new Array<CopyDto>();

        }

}
