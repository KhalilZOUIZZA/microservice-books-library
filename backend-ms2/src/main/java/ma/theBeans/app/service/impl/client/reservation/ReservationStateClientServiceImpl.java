package ma.theBeans.app.service.impl.client.reservation;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationStateCriteria;
import ma.theBeans.app.dao.facade.core.reservation.ReservationStateDao;
import ma.theBeans.app.dao.specification.core.reservation.ReservationStateSpecification;
import ma.theBeans.app.service.facade.client.reservation.ReservationStateClientService;

import static ma.theBeans.app.thePackage.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.theBeans.app.thePackage.util.RefelexivityUtil;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationStateClientServiceImpl implements ReservationStateClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public ReservationState update(ReservationState t) {
        ReservationState loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{ReservationState.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public ReservationState findById(Long id) {
        return dao.findById(id).orElse(null);
    }
    public ReservationState findByCode(String code) {
        return dao.findByCode(code);
    }


    public ReservationState findOrSave(ReservationState t) {
        if (t != null) {
            ReservationState result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<ReservationState> findAll() {
        return dao.findAll();
    }

    public List<ReservationState> findByCriteria(ReservationStateCriteria criteria) {
        List<ReservationState> content = null;
        if (criteria != null) {
            ReservationStateSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ReservationStateSpecification constructSpecification(ReservationStateCriteria criteria) {
        ReservationStateSpecification mySpecification =  (ReservationStateSpecification) RefelexivityUtil.constructObjectUsingOneParam(ReservationStateSpecification.class, criteria);
        return mySpecification;
    }

    public List<ReservationState> findPaginatedByCriteria(ReservationStateCriteria criteria, int page, int pageSize, String order, String sortField) {
        ReservationStateSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ReservationStateCriteria criteria) {
        ReservationStateSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
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
    public List<ReservationState> delete(List<ReservationState> list) {
		List<ReservationState> result = new ArrayList();
        if (list != null) {
            for (ReservationState t : list) {
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
    public ReservationState create(ReservationState t) {
        ReservationState loaded = findByReferenceEntity(t);
        ReservationState saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public ReservationState findWithAssociatedLists(Long id){
        ReservationState result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<ReservationState> update(List<ReservationState> ts, boolean createIfNotExist) {
        List<ReservationState> result = new ArrayList<>();
        if (ts != null) {
            for (ReservationState t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    ReservationState loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, ReservationState t, ReservationState loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public ReservationState findByReferenceEntity(ReservationState t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<ReservationState> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<ReservationState>> getToBeSavedAndToBeDeleted(List<ReservationState> oldList, List<ReservationState> newList) {
        List<List<ReservationState>> result = new ArrayList<>();
        List<ReservationState> resultDelete = new ArrayList<>();
        List<ReservationState> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<ReservationState> oldList, List<ReservationState> newList, List<ReservationState> resultUpdateOrSave, List<ReservationState> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                ReservationState myOld = oldList.get(i);
                ReservationState t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                ReservationState myNew = newList.get(i);
                ReservationState t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public ReservationStateClientServiceImpl(ReservationStateDao dao) {
        this.dao = dao;
    }

    private ReservationStateDao dao;
}
