package ma.theBeans.app.ws.dto.reservation;

import com.fasterxml.jackson.annotation.JsonInclude;

import ma.theBeans.app.thePackage.security.bean.Role;
import java.util.Collection;
import ma.theBeans.app.thePackage.security.ws.dto.UserDto;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto  extends UserDto {

    private String description  ;




    private Collection<Role> roles;
    public ClientDto(){
        super();
    }




    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

















    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
