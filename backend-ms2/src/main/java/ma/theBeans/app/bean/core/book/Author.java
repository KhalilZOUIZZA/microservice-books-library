package ma.theBeans.app.bean.core.book;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="author_seq",sequenceName="author_seq",allocationSize=1, initialValue = 1)
public class Author  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String fullName;



    public Author(){
        super();
    }

    public Author(Long id){
        this.id = id;
    }

    public Author(Long id,String fullName){
        this.id = id;
        this.fullName = fullName ;
    }
    public Author(String fullName){
        this.fullName = fullName ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="author_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
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
    public String getFullName(){
        return this.fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id != null && id.equals(author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

