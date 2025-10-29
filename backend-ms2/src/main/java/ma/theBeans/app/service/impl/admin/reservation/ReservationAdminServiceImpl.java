package ma.theBeans.app.service.impl.admin.reservation;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationCriteria;
import ma.theBeans.app.dao.facade.core.reservation.ReservationDao;
import ma.theBeans.app.dao.specification.core.reservation.ReservationSpecification;
import ma.theBeans.app.service.facade.admin.reservation.ReservationAdminService;

import static ma.theBeans.app.thePackage.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.theBeans.app.thePackage.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.theBeans.app.service.facade.admin.reservation.ReservationStateAdminService;
import ma.theBeans.app.service.facade.admin.reservation.ReservationItemAdminService;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.service.facade.admin.book.CopyAdminService;
import ma.theBeans.app.service.facade.admin.reservation.ClientAdminService;

@Service
public class ReservationAdminServiceImpl implements ReservationAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reservation update(Reservation t) {
        Reservation loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Reservation.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public Reservation findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Reservation findOrSave(Reservation t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Reservation result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Reservation> findAll() {
        return dao.findAll();
    }

    public List<Reservation> findByCriteria(ReservationCriteria criteria) {
        List<Reservation> content = null;
        if (criteria != null) {
            ReservationSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ReservationSpecification constructSpecification(ReservationCriteria criteria) {
        ReservationSpecification mySpecification =  (ReservationSpecification) RefelexivityUtil.constructObjectUsingOneParam(ReservationSpecification.class, criteria);
        return mySpecification;
    }

    public List<Reservation> findPaginatedByCriteria(ReservationCriteria criteria, int page, int pageSize, String order, String sortField) {
        ReservationSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ReservationCriteria criteria) {
        ReservationSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Reservation> findByReservationStateCode(String code){
        return dao.findByReservationStateCode(code);
    }
    public List<Reservation> findByReservationStateId(Long id){
        return dao.findByReservationStateId(id);
    }
    public int deleteByReservationStateCode(String code){
        return dao.deleteByReservationStateCode(code);
    }
    public int deleteByReservationStateId(Long id){
        return dao.deleteByReservationStateId(id);
    }
    public long countByReservationStateCode(String code){
        return dao.countByReservationStateCode(code);
    }
    public List<Reservation> findByClientId(Long id){
        return dao.findByClientId(id);
    }
    public int deleteByClientId(Long id){
        return dao.deleteByClientId(id);
    }
    public long countByClientEmail(String email){
        return dao.countByClientEmail(email);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            deleteAssociatedLists(id);
            dao.deleteById(id);
        }
        return condition;
    }

    public void deleteAssociatedLists(Long id) {
        reservationItemService.deleteByReservationId(id);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reservation> delete(List<Reservation> list) {
		List<Reservation> result = new ArrayList();
        if (list != null) {
            for (Reservation t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Reservation create(Reservation t) {
        Reservation loaded = findByReferenceEntity(t);
        Reservation saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getReservationItems() != null) {
                t.getReservationItems().forEach(element-> {
                    if (element.getCopy() != null) {
                        element.getCopy().setId(null);
                        element.setCopy(copyService.create(element.getCopy()));
                    }
                    element.setReservation(saved);
                    reservationItemService.create(element);
                });
            }
        }else {
            saved = null;
        }
        return saved;
    }

    public Reservation findWithAssociatedLists(Long id){
        Reservation result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setReservationItems(reservationItemService.findByReservationId(id));
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Reservation> update(List<Reservation> ts, boolean createIfNotExist) {
        List<Reservation> result = new ArrayList<>();
        if (ts != null) {
            for (Reservation t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Reservation loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Reservation t, Reservation loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(Reservation reservation){
    if(reservation !=null && reservation.getId() != null){
        List<List<ReservationItem>> resultReservationItems= reservationItemService.getToBeSavedAndToBeDeleted(reservationItemService.findByReservationId(reservation.getId()),reservation.getReservationItems());
            reservationItemService.delete(resultReservationItems.get(1));
        emptyIfNull(resultReservationItems.get(0)).forEach(e -> e.setReservation(reservation));
        reservationItemService.update(resultReservationItems.get(0),true);
        }
    }








    public Reservation findByReferenceEntity(Reservation t){
        return t==null? null : dao.findByCode(t.getCode());
    }
    public void findOrSaveAssociatedObject(Reservation t){
        if( t != null) {
            t.setReservationState(reservationStateService.findOrSave(t.getReservationState()));
            t.setClient(clientService.findOrSave(t.getClient()));
        }
    }



    public List<Reservation> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Reservation>> getToBeSavedAndToBeDeleted(List<Reservation> oldList, List<Reservation> newList) {
        List<List<Reservation>> result = new ArrayList<>();
        List<Reservation> resultDelete = new ArrayList<>();
        List<Reservation> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Reservation> oldList, List<Reservation> newList, List<Reservation> resultUpdateOrSave, List<Reservation> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Reservation myOld = oldList.get(i);
                Reservation t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Reservation myNew = newList.get(i);
                Reservation t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }







    @Autowired
    private ReservationStateAdminService reservationStateService ;
    @Autowired
    private ReservationItemAdminService reservationItemService ;
    @Autowired
    private CopyAdminService copyService ;
    @Autowired
    private ClientAdminService clientService ;

    public ReservationAdminServiceImpl(ReservationDao dao) {
        this.dao = dao;
    }

    private ReservationDao dao;
}
