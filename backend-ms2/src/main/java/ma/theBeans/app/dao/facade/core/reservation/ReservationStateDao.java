package ma.theBeans.app.dao.facade.core.reservation;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationStateDao extends AbstractRepository<ReservationState,Long>  {
    ReservationState findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW ReservationState(item.id,item.label) FROM ReservationState item")
    List<ReservationState> findAllOptimized();

}
