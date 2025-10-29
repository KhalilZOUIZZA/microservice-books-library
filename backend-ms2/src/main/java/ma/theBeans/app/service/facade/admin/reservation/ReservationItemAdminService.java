package ma.theBeans.app.service.facade.admin.reservation;

import java.util.List;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationItemCriteria;


public interface ReservationItemAdminService {



    List<ReservationItem> findByCopyId(Long id);
    int deleteByCopyId(Long id);
    long countByCopySerialNumber(String serialNumber);
    List<ReservationItem> findByReservationId(Long id);
    int deleteByReservationId(Long id);
    long countByReservationCode(String code);




	ReservationItem create(ReservationItem t);

    ReservationItem update(ReservationItem t);

    List<ReservationItem> update(List<ReservationItem> ts,boolean createIfNotExist);

    ReservationItem findById(Long id);

    ReservationItem findOrSave(ReservationItem t);

    ReservationItem findByReferenceEntity(ReservationItem t);

    ReservationItem findWithAssociatedLists(Long id);

    List<ReservationItem> findAllOptimized();

    List<ReservationItem> findAll();

    List<ReservationItem> findByCriteria(ReservationItemCriteria criteria);

    List<ReservationItem> findPaginatedByCriteria(ReservationItemCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ReservationItemCriteria criteria);

    List<ReservationItem> delete(List<ReservationItem> ts);

    boolean deleteById(Long id);

    List<List<ReservationItem>> getToBeSavedAndToBeDeleted(List<ReservationItem> oldList, List<ReservationItem> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
