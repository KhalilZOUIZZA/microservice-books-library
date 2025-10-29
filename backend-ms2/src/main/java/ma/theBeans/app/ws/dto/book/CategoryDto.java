package ma.theBeans.app.ws.dto.book;

import ma.theBeans.app.thePackage.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto  extends AuditBaseDto {

    private String code  ;
    private String label  ;




    public CategoryDto(){
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








}
