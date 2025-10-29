package ma.theBeans.app.dao.facade.core.reservation;

import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReservationItemDao extends AbstractRepository<ReservationItem,Long>  {

    List<ReservationItem> findByCopyId(Long id);
    int deleteByCopyId(Long id);
    long countByCopySerialNumber(String serialNumber);
    List<ReservationItem> findByReservationId(Long id);
    int deleteByReservationId(Long id);
    long countByReservationCode(String code);


}
