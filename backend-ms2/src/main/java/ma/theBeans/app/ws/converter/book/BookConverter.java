package ma.theBeans.app.ws.converter.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;
import ma.theBeans.app.thePackage.util.ListUtil;

import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.bean.core.book.Author;



import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.thePackage.util.DateUtil;
import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.ws.dto.book.BookDto;

@Component
public class BookConverter {

    @Autowired
    private CategoryConverter categoryConverter ;
    @Autowired
    private CopyConverter copyConverter ;
    @Autowired
    private AuthorConverter authorConverter ;
    private boolean category;
    private boolean author;
    private boolean copies;

    public  BookConverter() {
        init(true);
    }

    public Book toItem(BookDto dto) {
        if (dto == null) {
            return null;
        } else {
        Book item = new Book();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getTitle()))
                item.setTitle(dto.getTitle());
            if(StringUtil.isNotEmpty(dto.getEditionDate()))
                item.setEditionDate(DateUtil.stringEnToDate(dto.getEditionDate()));
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(StringUtil.isNotEmpty(dto.getNumberOfCopies()))
                item.setNumberOfCopies(dto.getNumberOfCopies());
            if(dto.getAvailable() != null)
                item.setAvailable(dto.getAvailable());
            if(StringUtil.isNotEmpty(dto.getImageUrl()))
                item.setImageUrl(dto.getImageUrl());
            if(this.category && dto.getCategory()!=null)
                item.setCategory(categoryConverter.toItem(dto.getCategory())) ;

            if(this.author && dto.getAuthor()!=null)
                item.setAuthor(authorConverter.toItem(dto.getAuthor())) ;


            if(this.copies && ListUtil.isNotEmpty(dto.getCopies()))
                item.setCopies(copyConverter.toItem(dto.getCopies()));


        return item;
        }
    }


    public BookDto toDto(Book item) {
        if (item == null) {
            return null;
        } else {
            BookDto dto = new BookDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getTitle()))
                dto.setTitle(item.getTitle());
            if(item.getEditionDate()!=null)
                dto.setEditionDate(DateUtil.dateTimeToString(item.getEditionDate()));
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if(StringUtil.isNotEmpty(item.getNumberOfCopies()))
                dto.setNumberOfCopies(item.getNumberOfCopies());
                dto.setAvailable(item.getAvailable());
            if(StringUtil.isNotEmpty(item.getImageUrl()))
                dto.setImageUrl(item.getImageUrl());
            if(this.category && item.getCategory()!=null) {
                dto.setCategory(categoryConverter.toDto(item.getCategory())) ;

            }
            if(this.author && item.getAuthor()!=null) {
                dto.setAuthor(authorConverter.toDto(item.getAuthor())) ;

            }
        if(this.copies && ListUtil.isNotEmpty(item.getCopies())){
            copyConverter.init(true);
            copyConverter.setBook(false);
            dto.setCopies(copyConverter.toDto(item.getCopies()));
            copyConverter.setBook(true);

        }


        return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.copies = value;
    }
    public void initObject(boolean value) {
        this.category = value;
        this.author = value;
    }
	
    public List<Book> toItem(List<BookDto> dtos) {
        List<Book> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (BookDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<BookDto> toDto(List<Book> items) {
        List<BookDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Book item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(BookDto dto, Book t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getCategory() == null  && dto.getCategory() != null){
            t.setCategory(new Category());
        }else if (t.getCategory() != null  && dto.getCategory() != null){
            t.setCategory(null);
            t.setCategory(new Category());
        }
        if(t.getAuthor() == null  && dto.getAuthor() != null){
            t.setAuthor(new Author());
        }else if (t.getAuthor() != null  && dto.getAuthor() != null){
            t.setAuthor(null);
            t.setAuthor(new Author());
        }
        if (dto.getCategory() != null)
        categoryConverter.copy(dto.getCategory(), t.getCategory());
        if (dto.getAuthor() != null)
        authorConverter.copy(dto.getAuthor(), t.getAuthor());
        if (dto.getCopies() != null)
            t.setCopies(copyConverter.copy(dto.getCopies()));
    }

    public List<Book> copy(List<BookDto> dtos) {
        List<Book> result = new ArrayList<>();
        if (dtos != null) {
            for (BookDto dto : dtos) {
                Book instance = new Book();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public CategoryConverter getCategoryConverter(){
        return this.categoryConverter;
    }
    public void setCategoryConverter(CategoryConverter categoryConverter ){
        this.categoryConverter = categoryConverter;
    }
    public CopyConverter getCopyConverter(){
        return this.copyConverter;
    }
    public void setCopyConverter(CopyConverter copyConverter ){
        this.copyConverter = copyConverter;
    }
    public AuthorConverter getAuthorConverter(){
        return this.authorConverter;
    }
    public void setAuthorConverter(AuthorConverter authorConverter ){
        this.authorConverter = authorConverter;
    }
    public boolean  isCategory(){
        return this.category;
    }
    public void  setCategory(boolean category){
        this.category = category;
    }
    public boolean  isAuthor(){
        return this.author;
    }
    public void  setAuthor(boolean author){
        this.author = author;
    }
    public boolean  isCopies(){
        return this.copies ;
    }
    public void  setCopies(boolean copies ){
        this.copies  = copies ;
    }
}
