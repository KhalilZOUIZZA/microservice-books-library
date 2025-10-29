package ma.theBeans.app.ws.converter.book;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.ws.dto.book.AuthorDto;

@Component
public class AuthorConverter {



    public Author toItem(AuthorDto dto) {
        if (dto == null) {
            return null;
        } else {
        Author item = new Author();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getFullName()))
                item.setFullName(dto.getFullName());



        return item;
        }
    }


    public AuthorDto toDto(Author item) {
        if (item == null) {
            return null;
        } else {
            AuthorDto dto = new AuthorDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getFullName()))
                dto.setFullName(item.getFullName());


        return dto;
        }
    }


	
    public List<Author> toItem(List<AuthorDto> dtos) {
        List<Author> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (AuthorDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<AuthorDto> toDto(List<Author> items) {
        List<AuthorDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Author item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(AuthorDto dto, Author t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Author> copy(List<AuthorDto> dtos) {
        List<Author> result = new ArrayList<>();
        if (dtos != null) {
            for (AuthorDto dto : dtos) {
                Author instance = new Author();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
