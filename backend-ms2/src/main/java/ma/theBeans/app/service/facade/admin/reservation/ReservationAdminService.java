package ma.theBeans.app.service.facade.admin.reservation;

import java.util.List;
import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationCriteria;


public interface ReservationAdminService {



    List<Reservation> findByReservationStateCode(String code);
    List<Reservation> findByReservationStateId(Long id);
    int deleteByReservationStateId(Long id);
    int deleteByReservationStateCode(String code);
    long countByReservationStateCode(String code);
    List<Reservation> findByClientId(Long id);
    int deleteByClientId(Long id);
    long countByClientEmail(String email);




	Reservation create(Reservation t);

    Reservation update(Reservation t);

    List<Reservation> update(List<Reservation> ts,boolean createIfNotExist);

    Reservation findById(Long id);

    Reservation findOrSave(Reservation t);

    Reservation findByReferenceEntity(Reservation t);

    Reservation findWithAssociatedLists(Long id);

    List<Reservation> findAllOptimized();

    List<Reservation> findAll();

    List<Reservation> findByCriteria(ReservationCriteria criteria);

    List<Reservation> findPaginatedByCriteria(ReservationCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ReservationCriteria criteria);

    List<Reservation> delete(List<Reservation> ts);

    boolean deleteById(Long id);

    List<List<Reservation>> getToBeSavedAndToBeDeleted(List<Reservation> oldList, List<Reservation> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
