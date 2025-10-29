package ma.theBeans.app.dao.criteria.core.book;



import ma.theBeans.app.thePackage.criteria.BaseCriteria;

import java.util.List;

public class CopyCriteria extends  BaseCriteria  {

    private String serialNumber;
    private String serialNumberLike;

    private BookCriteria book ;
    private List<BookCriteria> books ;


    public String getSerialNumber(){
        return this.serialNumber;
    }
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }
    public String getSerialNumberLike(){
        return this.serialNumberLike;
    }
    public void setSerialNumberLike(String serialNumberLike){
        this.serialNumberLike = serialNumberLike;
    }


    public BookCriteria getBook(){
        return this.book;
    }

    public void setBook(BookCriteria book){
        this.book = book;
    }
    public List<BookCriteria> getBooks(){
        return this.books;
    }

    public void setBooks(List<BookCriteria> books){
        this.books = books;
    }
}
