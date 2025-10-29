package ma.theBeans.app.ws.dto.reservation;

import ma.theBeans.app.thePackage.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDto  extends AuditBaseDto {

    private String code  ;
    private String requestDate ;
    private String theoreticalReturnDate ;
    private String effectiveReturnDate ;
    

    private String booklabel;

    private ReservationStateDto reservationState ;
    private ClientDto client ;


    private List<ReservationItemDto> reservationItems ;


    public ReservationDto(){
        super();
    }

//me
    public String getBooklabel(){
        return this.booklabel;
    }
    public void setBooklabel(String code){
        this.booklabel = code;
    }

//me
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getRequestDate(){
        return this.requestDate;
    }
    public void setRequestDate(String requestDate){
        this.requestDate = requestDate;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getTheoreticalReturnDate(){
        return this.theoreticalReturnDate;
    }
    public void setTheoreticalReturnDate(String theoreticalReturnDate){
        this.theoreticalReturnDate = theoreticalReturnDate;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getEffectiveReturnDate(){
        return this.effectiveReturnDate;
    }
    public void setEffectiveReturnDate(String effectiveReturnDate){
        this.effectiveReturnDate = effectiveReturnDate;
    }


    public ReservationStateDto getReservationState(){
        return this.reservationState;
    }

    public void setReservationState(ReservationStateDto reservationState){
        this.reservationState = reservationState;
    }
    public ClientDto getClient(){
        return this.client;
    }

    public void setClient(ClientDto client){
        this.client = client;
    }



    public List<ReservationItemDto> getReservationItems(){
        return this.reservationItems;
    }

    public void setReservationItems(List<ReservationItemDto> reservationItems){
        this.reservationItems = reservationItems;
    }



}
