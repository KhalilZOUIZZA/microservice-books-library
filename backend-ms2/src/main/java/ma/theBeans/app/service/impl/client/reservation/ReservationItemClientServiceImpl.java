package ma.theBeans.app.service.impl.client.reservation;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationItemCriteria;
import ma.theBeans.app.dao.facade.core.reservation.ReservationItemDao;
import ma.theBeans.app.dao.specification.core.reservation.ReservationItemSpecification;
import ma.theBeans.app.service.facade.client.reservation.ReservationItemClientService;

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

import ma.theBeans.app.service.facade.client.book.CopyClientService;
import ma.theBeans.app.service.facade.client.reservation.ReservationClientService;

@Service
public class ReservationItemClientServiceImpl implements ReservationItemClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public ReservationItem update(ReservationItem t) {
        ReservationItem loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{ReservationItem.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public ReservationItem findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public ReservationItem findOrSave(ReservationItem t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            ReservationItem result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<ReservationItem> findAll() {
        return dao.findAll();
    }

    public List<ReservationItem> findByCriteria(ReservationItemCriteria criteria) {
        List<ReservationItem> content = null;
        if (criteria != null) {
            ReservationItemSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ReservationItemSpecification constructSpecification(ReservationItemCriteria criteria) {
        ReservationItemSpecification mySpecification =  (ReservationItemSpecification) RefelexivityUtil.constructObjectUsingOneParam(ReservationItemSpecification.class, criteria);
        return mySpecification;
    }

    public List<ReservationItem> findPaginatedByCriteria(ReservationItemCriteria criteria, int page, int pageSize, String order, String sortField) {
        ReservationItemSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ReservationItemCriteria criteria) {
        ReservationItemSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<ReservationItem> findByCopyId(Long id){
        return dao.findByCopyId(id);
    }
    public int deleteByCopyId(Long id){
        return dao.deleteByCopyId(id);
    }
    public long countByCopySerialNumber(String serialNumber){
        return dao.countByCopySerialNumber(serialNumber);
    }
    public List<ReservationItem> findByReservationId(Long id){
        return dao.findByReservationId(id);
    }
    public int deleteByReservationId(Long id){
        return dao.deleteByReservationId(id);
    }
    public long countByReservationCode(String code){
        return dao.countByReservationCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<ReservationItem> delete(List<ReservationItem> list) {
		List<ReservationItem> result = new ArrayList();
        if (list != null) {
            for (ReservationItem t : list) {
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
    public ReservationItem create(ReservationItem t) {
        if (t.getCopy() != null) {
            t.getCopy().setId(null);
            t.setCopy(copyService.create(t.getCopy()));
        }
        ReservationItem loaded = findByReferenceEntity(t);
        ReservationItem saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public ReservationItem findWithAssociatedLists(Long id){
        ReservationItem result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<ReservationItem> update(List<ReservationItem> ts, boolean createIfNotExist) {
        List<ReservationItem> result = new ArrayList<>();
        if (ts != null) {
            for (ReservationItem t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    ReservationItem loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, ReservationItem t, ReservationItem loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public ReservationItem findByReferenceEntity(ReservationItem t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(ReservationItem t){
        if( t != null) {
            t.setReservation(reservationService.findOrSave(t.getReservation()));
        }
    }



    public List<ReservationItem> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<ReservationItem>> getToBeSavedAndToBeDeleted(List<ReservationItem> oldList, List<ReservationItem> newList) {
        List<List<ReservationItem>> result = new ArrayList<>();
        List<ReservationItem> resultDelete = new ArrayList<>();
        List<ReservationItem> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<ReservationItem> oldList, List<ReservationItem> newList, List<ReservationItem> resultUpdateOrSave, List<ReservationItem> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                ReservationItem myOld = oldList.get(i);
                ReservationItem t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                ReservationItem myNew = newList.get(i);
                ReservationItem t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private CopyClientService copyService ;
    @Autowired
    private ReservationClientService reservationService ;

    public ReservationItemClientServiceImpl(ReservationItemDao dao) {
        this.dao = dao;
    }

    private ReservationItemDao dao;
}
