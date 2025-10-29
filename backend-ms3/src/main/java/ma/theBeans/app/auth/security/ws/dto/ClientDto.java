package ma.theBeans.app.auth.security.ws.dto;

import ma.theBeans.app.auth.dto.AuditBaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class ClientDto extends AuditBaseDto {
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    protected Long id  ;
    protected String email  ;
    protected Boolean accountNonExpired  ;
    protected Boolean accountNonLocked  ;
    protected String username  ;

    protected String firstName;
    protected String lastName;




    public ClientDto(){
        super();
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Boolean getAccountNonExpired(){
        return this.accountNonExpired;
    }
    public void setAccountNonExpired(Boolean accountNonExpired){
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked(){
        return this.accountNonLocked;
    }
    public void setAccountNonLocked(Boolean accountNonLocked){
        this.accountNonLocked = accountNonLocked;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
