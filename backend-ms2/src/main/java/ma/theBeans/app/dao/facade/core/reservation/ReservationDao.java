package ma.theBeans.app.dao.facade.core.reservation;

import com.linecorp.armeria.server.annotation.Param;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationCriteria;
import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.reservation.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationDao extends AbstractRepository<Reservation,Long>  {
    Reservation findByCode(String code);
    int deleteByCode(String code);

    List<Reservation> findByReservationStateCode(String code);
    List<Reservation> findByReservationStateId(Long id);
    int deleteByReservationStateId(Long id);
    int deleteByReservationStateCode(String code);
    long countByReservationStateCode(String code);
    List<Reservation> findByClientId(Long id);
    int deleteByClientId(Long id);
    long countByClientEmail(String email);

    @Query("SELECT NEW Reservation(item.id,item.code) FROM Reservation item")
    List<Reservation> findAllOptimized();



}
