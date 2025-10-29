package ma.theBeans.app.dao.criteria.core.reservation;


import ma.theBeans.app.dao.criteria.core.book.CopyCriteria;

import ma.theBeans.app.thePackage.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;

public class ReservationItemCriteria extends  BaseCriteria  {

    private LocalDateTime returnDate;
    private LocalDateTime returnDateFrom;
    private LocalDateTime returnDateTo;

    private CopyCriteria copy ;
    private List<CopyCriteria> copys ;
    private ReservationCriteria reservation ;
    private List<ReservationCriteria> reservations ;


    public LocalDateTime getReturnDate(){
        return this.returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate){
        this.returnDate = returnDate;
    }
    public LocalDateTime getReturnDateFrom(){
        return this.returnDateFrom;
    }
    public void setReturnDateFrom(LocalDateTime returnDateFrom){
        this.returnDateFrom = returnDateFrom;
    }
    public LocalDateTime getReturnDateTo(){
        return this.returnDateTo;
    }
    public void setReturnDateTo(LocalDateTime returnDateTo){
        this.returnDateTo = returnDateTo;
    }

    public CopyCriteria getCopy(){
        return this.copy;
    }

    public void setCopy(CopyCriteria copy){
        this.copy = copy;
    }
    public List<CopyCriteria> getCopys(){
        return this.copys;
    }

    public void setCopys(List<CopyCriteria> copys){
        this.copys = copys;
    }
    public ReservationCriteria getReservation(){
        return this.reservation;
    }

    public void setReservation(ReservationCriteria reservation){
        this.reservation = reservation;
    }
    public List<ReservationCriteria> getReservations(){
        return this.reservations;
    }

    public void setReservations(List<ReservationCriteria> reservations){
        this.reservations = reservations;
    }
}
