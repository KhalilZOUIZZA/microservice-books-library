package ma.theBeans.app.ws.dto.book;

import ma.theBeans.app.thePackage.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;
    private String title  ;
    private String editionDate ;
    private String description  ;
    private Integer numberOfCopies  = 0 ;
    private Boolean available  ;
    private String imageUrl  ;

    private CategoryDto category ;
    private AuthorDto author ;

    private List<CopyDto> copies ;


    public BookDto(){
        super();
    }




    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }


    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getEditionDate(){
        return this.editionDate;
    }
    public void setEditionDate(String editionDate){
        this.editionDate = editionDate;
    }


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public Integer getNumberOfCopies(){
        return this.numberOfCopies;
    }
    public void setNumberOfCopies(Integer numberOfCopies){
        this.numberOfCopies = numberOfCopies;
    }


    public Boolean getAvailable(){
        return this.available;
    }
    public void setAvailable(Boolean available){
        this.available = available;
    }


    public String getImageUrl(){
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }


    public CategoryDto getCategory(){
        return this.category;
    }

    public void setCategory(CategoryDto category){
        this.category = category;
    }
    public AuthorDto getAuthor(){
        return this.author;
    }

    public void setAuthor(AuthorDto author){
        this.author = author;
    }



    public List<CopyDto> getCopies(){
        return this.copies;
    }

    public void setCopies(List<CopyDto> copies){
        this.copies = copies;
    }



}
