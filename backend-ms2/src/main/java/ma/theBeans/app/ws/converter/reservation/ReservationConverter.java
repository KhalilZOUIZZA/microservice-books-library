package ma.theBeans.app.ws.converter.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.theBeans.app.thePackage.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;
import ma.theBeans.app.thePackage.util.ListUtil;

import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.ws.converter.book.CopyConverter;
import ma.theBeans.app.bean.core.reservation.Client;



import ma.theBeans.app.thePackage.util.StringUtil;
import ma.theBeans.app.thePackage.util.DateUtil;
import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.ws.dto.reservation.ReservationDto;

@Component
public class ReservationConverter {

    @Autowired
    private ReservationStateConverter reservationStateConverter ;
    @Autowired
    private ReservationItemConverter reservationItemConverter ;
    @Autowired
    private CopyConverter copyConverter ;
    @Autowired
    private ClientConverter clientConverter ;
    private boolean reservationState;
    private boolean client;
    private boolean reservationItems;

    public  ReservationConverter() {
        init(true);
    }

    public Reservation toItem(ReservationDto dto) {
        if (dto == null) {
            return null;
        } else {
            Reservation item = new Reservation();
            if (StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if (StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if (StringUtil.isNotEmpty(dto.getRequestDate()))
                item.setRequestDate(DateUtil.stringEnToDate(dto.getRequestDate()));
            if (StringUtil.isNotEmpty(dto.getTheoreticalReturnDate()))
                item.setTheoreticalReturnDate(DateUtil.stringEnToDate(dto.getTheoreticalReturnDate()));
            if (StringUtil.isNotEmpty(dto.getEffectiveReturnDate()))
                item.setEffectiveReturnDate(DateUtil.stringEnToDate(dto.getEffectiveReturnDate()));
            if (StringUtil.isNotEmpty(dto.getBooklabel()))
                item.setBooklabel(dto.getBooklabel());

            // Map the client if present in the DTO
            if (dto.getClient() != null && dto.getClient().getId() != null) {
                Client client = new Client();
                client.setId(dto.getClient().getId());
                item.setClient(client);
            }

            // Map the reservation state
            if (this.reservationState && dto.getReservationState() != null) {
                item.setReservationState(reservationStateConverter.toItem(dto.getReservationState()));
            }

            if (this.reservationItems && ListUtil.isNotEmpty(dto.getReservationItems()))
                item.setReservationItems(reservationItemConverter.toItem(dto.getReservationItems()));

            return item;
        }
    }


    public ReservationDto toDto(Reservation item) {
        if (item == null) {
            return null;
        } else {
            ReservationDto dto = new ReservationDto();
            if (StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if (StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if (item.getRequestDate() != null)
                dto.setRequestDate(DateUtil.dateTimeToString(item.getRequestDate()));
            if (item.getTheoreticalReturnDate() != null)
                dto.setTheoreticalReturnDate(DateUtil.dateTimeToString(item.getTheoreticalReturnDate()));
            if (item.getEffectiveReturnDate() != null)
                dto.setEffectiveReturnDate(DateUtil.dateTimeToString(item.getEffectiveReturnDate()));
            if (StringUtil.isNotEmpty(item.getBooklabel())) // Add this
                dto.setBooklabel(item.getBooklabel());
            if (this.reservationState && item.getReservationState() != null) {
                dto.setReservationState(reservationStateConverter.toDto(item.getReservationState()));
            }
            if (this.client && item.getClient() != null) {
                dto.setClient(clientConverter.toDto(item.getClient()));
            }
            if (this.reservationItems && ListUtil.isNotEmpty(item.getReservationItems())) {
                reservationItemConverter.init(true);
                reservationItemConverter.setReservation(false);
                dto.setReservationItems(reservationItemConverter.toDto(item.getReservationItems()));
                reservationItemConverter.setReservation(true);
            }
    
            return dto;
        }
    }
    

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.reservationItems = value;
    }
    public void initObject(boolean value) {
        this.reservationState = value;
        this.client = value;
    }
	
    public List<Reservation> toItem(List<ReservationDto> dtos) {
        List<Reservation> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ReservationDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ReservationDto> toDto(List<Reservation> items) {
        List<ReservationDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Reservation item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ReservationDto dto, Reservation t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getReservationState() == null  && dto.getReservationState() != null){
            t.setReservationState(new ReservationState());
        }else if (t.getReservationState() != null  && dto.getReservationState() != null){
            t.setReservationState(null);
            t.setReservationState(new ReservationState());
        }
        if(t.getClient() == null  && dto.getClient() != null){
            t.setClient(new Client());
        }else if (t.getClient() != null  && dto.getClient() != null){
            t.setClient(null);
            t.setClient(new Client());
        }
        if (dto.getReservationState() != null)
        reservationStateConverter.copy(dto.getReservationState(), t.getReservationState());
        if (dto.getClient() != null)
        clientConverter.copy(dto.getClient(), t.getClient());
        if (dto.getReservationItems() != null)
            t.setReservationItems(reservationItemConverter.copy(dto.getReservationItems()));
    }

    public List<Reservation> copy(List<ReservationDto> dtos) {
        List<Reservation> result = new ArrayList<>();
        if (dtos != null) {
            for (ReservationDto dto : dtos) {
                Reservation instance = new Reservation();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public ReservationStateConverter getReservationStateConverter(){
        return this.reservationStateConverter;
    }
    public void setReservationStateConverter(ReservationStateConverter reservationStateConverter ){
        this.reservationStateConverter = reservationStateConverter;
    }
    public ReservationItemConverter getReservationItemConverter(){
        return this.reservationItemConverter;
    }
    public void setReservationItemConverter(ReservationItemConverter reservationItemConverter ){
        this.reservationItemConverter = reservationItemConverter;
    }
    public CopyConverter getCopyConverter(){
        return this.copyConverter;
    }
    public void setCopyConverter(CopyConverter copyConverter ){
        this.copyConverter = copyConverter;
    }
    public ClientConverter getClientConverter(){
        return this.clientConverter;
    }
    public void setClientConverter(ClientConverter clientConverter ){
        this.clientConverter = clientConverter;
    }
    public boolean  isReservationState(){
        return this.reservationState;
    }
    public void  setReservationState(boolean reservationState){
        this.reservationState = reservationState;
    }
    public boolean  isClient(){
        return this.client;
    }
    public void  setClient(boolean client){
        this.client = client;
    }
    public boolean  isReservationItems(){
        return this.reservationItems ;
    }
    public void  setReservationItems(boolean reservationItems ){
        this.reservationItems  = reservationItems ;
    }
}
