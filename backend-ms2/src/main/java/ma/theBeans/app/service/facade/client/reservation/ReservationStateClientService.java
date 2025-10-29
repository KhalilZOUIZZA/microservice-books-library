package ma.theBeans.app.service.facade.client.reservation;

import java.util.List;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationStateCriteria;


public interface ReservationStateClientService {







	ReservationState create(ReservationState t);

    ReservationState update(ReservationState t);

    List<ReservationState> update(List<ReservationState> ts,boolean createIfNotExist);

    ReservationState findById(Long id);
    ReservationState findByCode(String code);

    ReservationState findOrSave(ReservationState t);

    ReservationState findByReferenceEntity(ReservationState t);

    ReservationState findWithAssociatedLists(Long id);

    List<ReservationState> findAllOptimized();

    List<ReservationState> findAll();

    List<ReservationState> findByCriteria(ReservationStateCriteria criteria);

    List<ReservationState> findPaginatedByCriteria(ReservationStateCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ReservationStateCriteria criteria);

    List<ReservationState> delete(List<ReservationState> ts);

    boolean deleteById(Long id);

    List<List<ReservationState>> getToBeSavedAndToBeDeleted(List<ReservationState> oldList, List<ReservationState> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;


}
