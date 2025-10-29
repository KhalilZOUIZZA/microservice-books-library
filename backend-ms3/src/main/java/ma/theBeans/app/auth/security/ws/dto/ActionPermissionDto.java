package ma.theBeans.app.auth.security.ws.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.auth.dto.AuditBaseDto;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionPermissionDto  extends AuditBaseDto {

    private String reference  ;
    private String libelle  ;




    public ActionPermissionDto(){
        super();
    }




    public String getReference(){
        return this.reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }


    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }








}
