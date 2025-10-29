package ma.theBeans.app.ws.converter.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.theBeans.app.bean.core.book.Book;


import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.ws.dto.book.CopyDto;

@Component
public class CopyConverter {

    @Autowired
    private BookConverter bookConverter ;
    private boolean book;

    public  CopyConverter() {
        initObject(true);
    }

    public Copy toItem(CopyDto dto) {
        if (dto == null) {
            return null;
        } else {
        Copy item = new Copy();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getSerialNumber()))
                item.setSerialNumber(dto.getSerialNumber());
            if(dto.getBook() != null && dto.getBook().getId() != null){
                item.setBook(new Book());
                item.getBook().setId(dto.getBook().getId());
                item.getBook().setCode(dto.getBook().getCode());
            }




        return item;
        }
    }


    public CopyDto toDto(Copy item) {
        if (item == null) {
            return null;
        } else {
            CopyDto dto = new CopyDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getSerialNumber()))
                dto.setSerialNumber(item.getSerialNumber());
            if(this.book && item.getBook()!=null) {
                dto.setBook(bookConverter.toDto(item.getBook())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.book = value;
    }
	
    public List<Copy> toItem(List<CopyDto> dtos) {
        List<Copy> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CopyDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CopyDto> toDto(List<Copy> items) {
        List<CopyDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Copy item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CopyDto dto, Copy t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getBook() == null  && dto.getBook() != null){
            t.setBook(new Book());
        }else if (t.getBook() != null  && dto.getBook() != null){
            t.setBook(null);
            t.setBook(new Book());
        }
        if (dto.getBook() != null)
        bookConverter.copy(dto.getBook(), t.getBook());
    }

    public List<Copy> copy(List<CopyDto> dtos) {
        List<Copy> result = new ArrayList<>();
        if (dtos != null) {
            for (CopyDto dto : dtos) {
                Copy instance = new Copy();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public BookConverter getBookConverter(){
        return this.bookConverter;
    }
    public void setBookConverter(BookConverter bookConverter ){
        this.bookConverter = bookConverter;
    }
    public boolean  isBook(){
        return this.book;
    }
    public void  setBook(boolean book){
        this.book = book;
    }
}
