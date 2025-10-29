package ma.theBeans.app.dao.criteria.core.book;



import ma.theBeans.app.thePackage.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;

public class BookCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String label;
    private String labelLike;
    private String title;
    private String titleLike;
    private LocalDateTime editionDate;
    private LocalDateTime editionDateFrom;
    private LocalDateTime editionDateTo;
    private String description;
    private String descriptionLike;
    private String numberOfCopies;
    private String numberOfCopiesMin;
    private String numberOfCopiesMax;
    private Boolean available;
    private String imageUrl;
    private String imageUrlLike;

    private CategoryCriteria category ;
    private List<CategoryCriteria> categorys ;
    private AuthorCriteria author ;
    private List<AuthorCriteria> authors ;


    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabelLike(){
        return this.labelLike;
    }
    public void setLabelLike(String labelLike){
        this.labelLike = labelLike;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitleLike(){
        return this.titleLike;
    }
    public void setTitleLike(String titleLike){
        this.titleLike = titleLike;
    }

    public LocalDateTime getEditionDate(){
        return this.editionDate;
    }
    public void setEditionDate(LocalDateTime editionDate){
        this.editionDate = editionDate;
    }
    public LocalDateTime getEditionDateFrom(){
        return this.editionDateFrom;
    }
    public void setEditionDateFrom(LocalDateTime editionDateFrom){
        this.editionDateFrom = editionDateFrom;
    }
    public LocalDateTime getEditionDateTo(){
        return this.editionDateTo;
    }
    public void setEditionDateTo(LocalDateTime editionDateTo){
        this.editionDateTo = editionDateTo;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescriptionLike(){
        return this.descriptionLike;
    }
    public void setDescriptionLike(String descriptionLike){
        this.descriptionLike = descriptionLike;
    }

    public String getNumberOfCopies(){
        return this.numberOfCopies;
    }
    public void setNumberOfCopies(String numberOfCopies){
        this.numberOfCopies = numberOfCopies;
    }   
    public String getNumberOfCopiesMin(){
        return this.numberOfCopiesMin;
    }
    public void setNumberOfCopiesMin(String numberOfCopiesMin){
        this.numberOfCopiesMin = numberOfCopiesMin;
    }
    public String getNumberOfCopiesMax(){
        return this.numberOfCopiesMax;
    }
    public void setNumberOfCopiesMax(String numberOfCopiesMax){
        this.numberOfCopiesMax = numberOfCopiesMax;
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
    public String getImageUrlLike(){
        return this.imageUrlLike;
    }
    public void setImageUrlLike(String imageUrlLike){
        this.imageUrlLike = imageUrlLike;
    }


    public CategoryCriteria getCategory(){
        return this.category;
    }

    public void setCategory(CategoryCriteria category){
        this.category = category;
    }
    public List<CategoryCriteria> getCategorys(){
        return this.categorys;
    }

    public void setCategorys(List<CategoryCriteria> categorys){
        this.categorys = categorys;
    }
    public AuthorCriteria getAuthor(){
        return this.author;
    }

    public void setAuthor(AuthorCriteria author){
        this.author = author;
    }
    public List<AuthorCriteria> getAuthors(){
        return this.authors;
    }

    public void setAuthors(List<AuthorCriteria> authors){
        this.authors = authors;
    }
}
