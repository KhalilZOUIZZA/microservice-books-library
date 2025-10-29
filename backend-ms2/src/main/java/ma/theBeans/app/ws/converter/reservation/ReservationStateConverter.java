package ma.theBeans.app.ws.converter.reservation;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.ws.dto.reservation.ReservationStateDto;

@Component
public class ReservationStateConverter {



    public ReservationState toItem(ReservationStateDto dto) {
        if (dto == null) {
            return null;
        } else {
        ReservationState item = new ReservationState();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLabel()))
                item.setLabel(dto.getLabel());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());



        return item;
        }
    }


    public ReservationStateDto toDto(ReservationState item) {
        if (item == null) {
            return null;
        } else {
            ReservationStateDto dto = new ReservationStateDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLabel()))
                dto.setLabel(item.getLabel());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());


        return dto;
        }
    }


	
    public List<ReservationState> toItem(List<ReservationStateDto> dtos) {
        List<ReservationState> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ReservationStateDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ReservationStateDto> toDto(List<ReservationState> items) {
        List<ReservationStateDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (ReservationState item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ReservationStateDto dto, ReservationState t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<ReservationState> copy(List<ReservationStateDto> dtos) {
        List<ReservationState> result = new ArrayList<>();
        if (dtos != null) {
            for (ReservationStateDto dto : dtos) {
                ReservationState instance = new ReservationState();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
