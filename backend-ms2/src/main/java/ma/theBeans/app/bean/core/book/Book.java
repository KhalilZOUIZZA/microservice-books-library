package ma.theBeans.app.bean.core.book;

import java.util.List;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="book_seq",sequenceName="book_seq",allocationSize=1, initialValue = 1)
public class Book  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String label;

    @Column(length = 500)
    private String title;

    private LocalDateTime editionDate ;

    private String description;

    private Integer numberOfCopies = 0;

    @Column(columnDefinition = "boolean default false")
    private Boolean available = false;

    @Column(length = 500)
    private String imageUrl;

    private Category category ;
    private Author author ;

    private List<Copy> copies ;

    public Book(){
        super();
    }

    public Book(Long id){
        this.id = id;
    }

    public Book(Long id,String code){
        this.id = id;
        this.code = code ;
    }
    public Book(String code){
        this.code = code ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="book_seq")
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
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public LocalDateTime getEditionDate(){
        return this.editionDate;
    }
    public void setEditionDate(LocalDateTime editionDate){
        this.editionDate = editionDate;
    }
      @Column(columnDefinition="TEXT")
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
    public Boolean  getAvailable(){
        return this.available;
    }
    public void setAvailable(Boolean available){
        this.available = available;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    public Category getCategory(){
        return this.category;
    }
    public void setCategory(Category category){
        this.category = category;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    public Author getAuthor(){
        return this.author;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    @OneToMany(mappedBy = "book")
    public List<Copy> getCopies(){
        return this.copies;
    }

    public void setCopies(List<Copy> copies){
        this.copies = copies;
    }
    public String getImageUrl(){
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id != null && id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

