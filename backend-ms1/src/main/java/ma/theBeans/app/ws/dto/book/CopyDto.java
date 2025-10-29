package ma.theBeans.app.ws.dto.book;

import ma.theBeans.app.thePackage.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class CopyDto  extends AuditBaseDto {

    private String serialNumber  ;

    private BookDto book ;



    public CopyDto(){
        super();
    }




    public String getSerialNumber(){
        return this.serialNumber;
    }
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }


    public BookDto getBook(){
        return this.book;
    }

    public void setBook(BookDto book){
        this.book = book;
    }






}
