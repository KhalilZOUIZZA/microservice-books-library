package ma.theBeans.app.ws.dto.reservation;

import ma.theBeans.app.thePackage.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonFormat;


import ma.theBeans.app.ws.dto.book.CopyDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationItemDto  extends AuditBaseDto {

    private String returnDate ;

    private CopyDto copy ;
    private ReservationDto reservation ;



    public ReservationItemDto(){
        super();
    }




    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getReturnDate(){
        return this.returnDate;
    }
    public void setReturnDate(String returnDate){
        this.returnDate = returnDate;
    }


    public CopyDto getCopy(){
        return this.copy;
    }

    public void setCopy(CopyDto copy){
        this.copy = copy;
    }
    public ReservationDto getReservation(){
        return this.reservation;
    }

    public void setReservation(ReservationDto reservation){
        this.reservation = reservation;
    }






}
