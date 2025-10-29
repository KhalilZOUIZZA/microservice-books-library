package ma.theBeans.app.ws.converter.book;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.ws.dto.book.CategoryDto;

@Component
public class CategoryConverter {



    public Category toItem(CategoryDto dto) {
        if (dto == null) {
            return null;
        } else {
        Category item = new Category();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());



        return item;
        }
    }


    public CategoryDto toDto(Category item) {
        if (item == null) {
            return null;
        } else {
            CategoryDto dto = new CategoryDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());


        return dto;
        }
    }


	
    public List<Category> toItem(List<CategoryDto> dtos) {
        List<Category> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CategoryDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CategoryDto> toDto(List<Category> items) {
        List<CategoryDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Category item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CategoryDto dto, Category t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Category> copy(List<CategoryDto> dtos) {
        List<Category> result = new ArrayList<>();
        if (dtos != null) {
            for (CategoryDto dto : dtos) {
                Category instance = new Category();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
