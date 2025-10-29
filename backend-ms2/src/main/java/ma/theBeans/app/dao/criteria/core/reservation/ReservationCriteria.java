package ma.theBeans.app.dao.criteria.core.reservation;



import ma.theBeans.app.thePackage.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;

public class ReservationCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private LocalDateTime requestDate;
    private LocalDateTime requestDateFrom;
    private LocalDateTime requestDateTo;
    private LocalDateTime theoreticalReturnDate;
    private LocalDateTime theoreticalReturnDateFrom;
    private LocalDateTime theoreticalReturnDateTo;
    private LocalDateTime effectiveReturnDate;
    private LocalDateTime effectiveReturnDateFrom;
    private LocalDateTime effectiveReturnDateTo;

    private ReservationStateCriteria reservationState ;
    private List<ReservationStateCriteria> reservationStates ;
    private ClientCriteria client ;
    private List<ClientCriteria> clients ;


    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public LocalDateTime getRequestDate(){
        return this.requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate){
        this.requestDate = requestDate;
    }
    public LocalDateTime getRequestDateFrom(){
        return this.requestDateFrom;
    }
    public void setRequestDateFrom(LocalDateTime requestDateFrom){
        this.requestDateFrom = requestDateFrom;
    }
    public LocalDateTime getRequestDateTo(){
        return this.requestDateTo;
    }
    public void setRequestDateTo(LocalDateTime requestDateTo){
        this.requestDateTo = requestDateTo;
    }
    public LocalDateTime getTheoreticalReturnDate(){
        return this.theoreticalReturnDate;
    }
    public void setTheoreticalReturnDate(LocalDateTime theoreticalReturnDate){
        this.theoreticalReturnDate = theoreticalReturnDate;
    }
    public LocalDateTime getTheoreticalReturnDateFrom(){
        return this.theoreticalReturnDateFrom;
    }
    public void setTheoreticalReturnDateFrom(LocalDateTime theoreticalReturnDateFrom){
        this.theoreticalReturnDateFrom = theoreticalReturnDateFrom;
    }
    public LocalDateTime getTheoreticalReturnDateTo(){
        return this.theoreticalReturnDateTo;
    }
    public void setTheoreticalReturnDateTo(LocalDateTime theoreticalReturnDateTo){
        this.theoreticalReturnDateTo = theoreticalReturnDateTo;
    }
    public LocalDateTime getEffectiveReturnDate(){
        return this.effectiveReturnDate;
    }
    public void setEffectiveReturnDate(LocalDateTime effectiveReturnDate){
        this.effectiveReturnDate = effectiveReturnDate;
    }
    public LocalDateTime getEffectiveReturnDateFrom(){
        return this.effectiveReturnDateFrom;
    }
    public void setEffectiveReturnDateFrom(LocalDateTime effectiveReturnDateFrom){
        this.effectiveReturnDateFrom = effectiveReturnDateFrom;
    }
    public LocalDateTime getEffectiveReturnDateTo(){
        return this.effectiveReturnDateTo;
    }
    public void setEffectiveReturnDateTo(LocalDateTime effectiveReturnDateTo){
        this.effectiveReturnDateTo = effectiveReturnDateTo;
    }

    public ReservationStateCriteria getReservationState(){
        return this.reservationState;
    }

    public void setReservationState(ReservationStateCriteria reservationState){
        this.reservationState = reservationState;
    }
    public List<ReservationStateCriteria> getReservationStates(){
        return this.reservationStates;
    }

    public void setReservationStates(List<ReservationStateCriteria> reservationStates){
        this.reservationStates = reservationStates;
    }
    public ClientCriteria getClient(){
        return this.client;
    }

    public void setClient(ClientCriteria client){
        this.client = client;
    }
    public List<ClientCriteria> getClients(){
        return this.clients;
    }

    public void setClients(List<ClientCriteria> clients){
        this.clients = clients;
    }
}
