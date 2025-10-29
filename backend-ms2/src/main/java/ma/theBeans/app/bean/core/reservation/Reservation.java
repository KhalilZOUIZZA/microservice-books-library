package ma.theBeans.app.bean.core.reservation;

import java.util.List;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reservation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="reservation_seq",sequenceName="reservation_seq",allocationSize=1, initialValue = 1)
public class Reservation  extends BaseEntity     {




    @Column(length = 500)
    private String code;

    private LocalDateTime requestDate ;

    private LocalDateTime theoreticalReturnDate ;

    private LocalDateTime effectiveReturnDate ;

    @JsonProperty("bookLabel") // Add this annotation
    private String booklabel;

    private ReservationState reservationState ;
    private Client client ;

    private List<ReservationItem> reservationItems ;

    public Reservation(){
        super();
    }

    public Reservation(Long id){
        this.id = id;
    }

    public Reservation(Long id,String code){
        this.id = id;
        this.code = code ;
    }
    public Reservation(String code){
        this.code = code ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="reservation_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    // me
    public String getBooklabel(){
        return this.booklabel;
    }
    public void setBooklabel(String code){
        this.booklabel = code;
    }
    // me
    public LocalDateTime getRequestDate(){
        return this.requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate){
        this.requestDate = requestDate;
    }
    public LocalDateTime getTheoreticalReturnDate(){
        return this.theoreticalReturnDate;
    }
    public void setTheoreticalReturnDate(LocalDateTime theoreticalReturnDate){
        this.theoreticalReturnDate = theoreticalReturnDate;
    }
    public LocalDateTime getEffectiveReturnDate(){
        return this.effectiveReturnDate;
    }
    public void setEffectiveReturnDate(LocalDateTime effectiveReturnDate){
        this.effectiveReturnDate = effectiveReturnDate;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_state")
    public ReservationState getReservationState(){
        return this.reservationState;
    }
    public void setReservationState(ReservationState reservationState){
        this.reservationState = reservationState;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    public Client getClient(){
        return this.client;
    }
    public void setClient(Client client){
        this.client = client;
    }
    @OneToMany(mappedBy = "reservation")
    public List<ReservationItem> getReservationItems(){
        return this.reservationItems;
    }

    public void setReservationItems(List<ReservationItem> reservationItems){
        this.reservationItems = reservationItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return id != null && id.equals(reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

