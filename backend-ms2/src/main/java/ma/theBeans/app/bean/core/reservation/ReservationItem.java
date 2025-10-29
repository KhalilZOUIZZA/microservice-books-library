package ma.theBeans.app.bean.core.reservation;


import java.time.LocalDateTime;


import ma.theBeans.app.bean.core.book.Copy;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.theBeans.app.thePackage.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reservation_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="reservation_item_seq",sequenceName="reservation_item_seq",allocationSize=1, initialValue = 1)
public class ReservationItem  extends BaseEntity     {




    private LocalDateTime returnDate ;

    private Copy copy ;
    private Reservation reservation ;


    public ReservationItem(){
        super();
    }

    public ReservationItem(Long id){
        this.id = id;
    }





    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="reservation_item_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy")
    public Copy getCopy(){
        return this.copy;
    }
    public void setCopy(Copy copy){
        this.copy = copy;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation")
    public Reservation getReservation(){
        return this.reservation;
    }
    public void setReservation(Reservation reservation){
        this.reservation = reservation;
    }
    public LocalDateTime getReturnDate(){
        return this.returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate){
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationItem reservationItem = (ReservationItem) o;
        return id != null && id.equals(reservationItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

