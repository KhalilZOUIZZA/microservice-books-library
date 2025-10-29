package ma.theBeans.app.bean.core.book;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "copy")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="copy_seq",sequenceName="copy_seq",allocationSize=1, initialValue = 1)
public class Copy  extends BaseEntity     {




    @Column(length = 500)
    private String serialNumber;

    private Book book ;


    public Copy(){
        super();
    }

    public Copy(Long id){
        this.id = id;
    }

    public Copy(Long id,String serialNumber){
        this.id = id;
        this.serialNumber = serialNumber ;
    }
    public Copy(String serialNumber){
        this.serialNumber = serialNumber ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="copy_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getSerialNumber(){
        return this.serialNumber;
    }
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    public Book getBook(){
        return this.book;
    }
    public void setBook(Book book){
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copy copy = (Copy) o;
        return id != null && id.equals(copy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

