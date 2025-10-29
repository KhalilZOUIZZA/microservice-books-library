package ma.theBeans.app.ws.converter.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.theBeans.app.ws.converter.book.CopyConverter;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.bean.core.reservation.Reservation;


import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.thePackage.util.DateUtil;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.ws.dto.reservation.ReservationItemDto;

@Component
public class ReservationItemConverter {

    @Autowired
    private CopyConverter copyConverter ;
    @Autowired
    private ReservationConverter reservationConverter ;
    private boolean copy;
    private boolean reservation;

    public  ReservationItemConverter() {
        initObject(true);
    }

    public ReservationItem toItem(ReservationItemDto dto) {
        if (dto == null) {
            return null;
        } else {
        ReservationItem item = new ReservationItem();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getReturnDate()))
                item.setReturnDate(DateUtil.stringEnToDate(dto.getReturnDate()));
            if(this.copy && dto.getCopy()!=null)
                item.setCopy(copyConverter.toItem(dto.getCopy())) ;

            if(dto.getReservation() != null && dto.getReservation().getId() != null){
                item.setReservation(new Reservation());
                item.getReservation().setId(dto.getReservation().getId());
                item.getReservation().setCode(dto.getReservation().getCode());
            }




        return item;
        }
    }


    public ReservationItemDto toDto(ReservationItem item) {
        if (item == null) {
            return null;
        } else {
            ReservationItemDto dto = new ReservationItemDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(item.getReturnDate()!=null)
                dto.setReturnDate(DateUtil.dateTimeToString(item.getReturnDate()));
            if(this.copy && item.getCopy()!=null) {
                dto.setCopy(copyConverter.toDto(item.getCopy())) ;

            }
            if(this.reservation && item.getReservation()!=null) {
                dto.setReservation(reservationConverter.toDto(item.getReservation())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.copy = value;
        this.reservation = value;
    }
	
    public List<ReservationItem> toItem(List<ReservationItemDto> dtos) {
        List<ReservationItem> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ReservationItemDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ReservationItemDto> toDto(List<ReservationItem> items) {
        List<ReservationItemDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (ReservationItem item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ReservationItemDto dto, ReservationItem t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getCopy() == null  && dto.getCopy() != null){
            t.setCopy(new Copy());
        }else if (t.getCopy() != null  && dto.getCopy() != null){
            t.setCopy(null);
            t.setCopy(new Copy());
        }
        if(t.getReservation() == null  && dto.getReservation() != null){
            t.setReservation(new Reservation());
        }else if (t.getReservation() != null  && dto.getReservation() != null){
            t.setReservation(null);
            t.setReservation(new Reservation());
        }
        if (dto.getCopy() != null)
        copyConverter.copy(dto.getCopy(), t.getCopy());
        if (dto.getReservation() != null)
        reservationConverter.copy(dto.getReservation(), t.getReservation());
    }

    public List<ReservationItem> copy(List<ReservationItemDto> dtos) {
        List<ReservationItem> result = new ArrayList<>();
        if (dtos != null) {
            for (ReservationItemDto dto : dtos) {
                ReservationItem instance = new ReservationItem();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public CopyConverter getCopyConverter(){
        return this.copyConverter;
    }
    public void setCopyConverter(CopyConverter copyConverter ){
        this.copyConverter = copyConverter;
    }
    public ReservationConverter getReservationConverter(){
        return this.reservationConverter;
    }
    public void setReservationConverter(ReservationConverter reservationConverter ){
        this.reservationConverter = reservationConverter;
    }
    public boolean  isCopy(){
        return this.copy;
    }
    public void  setCopy(boolean copy){
        this.copy = copy;
    }
    public boolean  isReservation(){
        return this.reservation;
    }
    public void  setReservation(boolean reservation){
        this.reservation = reservation;
    }
}
