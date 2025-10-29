package ma.theBeans.app.bean.core.book;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="category_seq",sequenceName="category_seq",allocationSize=1, initialValue = 1)
public class Category  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;



    public Category(){
        super();
    }

    public Category(Long id){
        this.id = id;
    }

    public Category(Long id,String label){
        this.id = id;
        this.label = label ;
    }
    public Category(String label){
        this.label = label ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="category_seq")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id != null && id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

