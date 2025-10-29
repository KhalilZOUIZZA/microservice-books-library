package ma.theBeans.app.service.impl.admin.book;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.dao.criteria.core.book.CopyCriteria;
import ma.theBeans.app.dao.facade.core.book.CopyDao;
import ma.theBeans.app.dao.specification.core.book.CopySpecification;
import ma.theBeans.app.service.facade.admin.book.CopyAdminService;

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

import ma.theBeans.app.service.facade.admin.book.BookAdminService;

@Service
public class CopyAdminServiceImpl implements CopyAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Copy update(Copy t) {
        Copy loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Copy.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Copy findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Copy findOrSave(Copy t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Copy result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Copy> findAll() {
        return dao.findAll();
    }

    public List<Copy> findByCriteria(CopyCriteria criteria) {
        List<Copy> content = null;
        if (criteria != null) {
            CopySpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CopySpecification constructSpecification(CopyCriteria criteria) {
        CopySpecification mySpecification =  (CopySpecification) RefelexivityUtil.constructObjectUsingOneParam(CopySpecification.class, criteria);
        return mySpecification;
    }

    public List<Copy> findPaginatedByCriteria(CopyCriteria criteria, int page, int pageSize, String order, String sortField) {
        CopySpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CopyCriteria criteria) {
        CopySpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Copy> findByBookId(Long id){
        return dao.findByBookId(id);
    }
    public int deleteByBookId(Long id){
        return dao.deleteByBookId(id);
    }
    public long countByBookCode(String code){
        return dao.countByBookCode(code);
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
    public List<Copy> delete(List<Copy> list) {
		List<Copy> result = new ArrayList();
        if (list != null) {
            for (Copy t : list) {
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
    public Copy create(Copy t) {
        if (t.getBook() != null) {
            t.getBook().setId(null);
            t.setBook(bookService.create(t.getBook()));
        }
        Copy loaded = findByReferenceEntity(t);
        Copy saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Copy findWithAssociatedLists(Long id){
        Copy result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Copy> update(List<Copy> ts, boolean createIfNotExist) {
        List<Copy> result = new ArrayList<>();
        if (ts != null) {
            for (Copy t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Copy loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Copy t, Copy loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Copy findByReferenceEntity(Copy t){
        return t==null? null : dao.findBySerialNumber(t.getSerialNumber());
    }
    public void findOrSaveAssociatedObject(Copy t){
        if( t != null) {
            t.setBook(bookService.findOrSave(t.getBook()));
        }
    }



    public List<Copy> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Copy>> getToBeSavedAndToBeDeleted(List<Copy> oldList, List<Copy> newList) {
        List<List<Copy>> result = new ArrayList<>();
        List<Copy> resultDelete = new ArrayList<>();
        List<Copy> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Copy> oldList, List<Copy> newList, List<Copy> resultUpdateOrSave, List<Copy> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Copy myOld = oldList.get(i);
                Copy t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Copy myNew = newList.get(i);
                Copy t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private BookAdminService bookService ;

    public CopyAdminServiceImpl(CopyDao dao) {
        this.dao = dao;
    }

    private CopyDao dao;
}
