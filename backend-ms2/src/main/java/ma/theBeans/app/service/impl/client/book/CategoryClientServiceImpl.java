package ma.theBeans.app.service.impl.client.book;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.dao.criteria.core.book.CategoryCriteria;
import ma.theBeans.app.dao.facade.core.book.CategoryDao;
import ma.theBeans.app.dao.specification.core.book.CategorySpecification;
import ma.theBeans.app.service.facade.client.book.CategoryClientService;

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
public class CategoryClientServiceImpl implements CategoryClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Category update(Category t) {
        Category loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Category.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Category findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Category findOrSave(Category t) {
        if (t != null) {
            Category result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Category> findAll() {
        return dao.findAll();
    }

    public List<Category> findByCriteria(CategoryCriteria criteria) {
        List<Category> content = null;
        if (criteria != null) {
            CategorySpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CategorySpecification constructSpecification(CategoryCriteria criteria) {
        CategorySpecification mySpecification =  (CategorySpecification) RefelexivityUtil.constructObjectUsingOneParam(CategorySpecification.class, criteria);
        return mySpecification;
    }

    public List<Category> findPaginatedByCriteria(CategoryCriteria criteria, int page, int pageSize, String order, String sortField) {
        CategorySpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CategoryCriteria criteria) {
        CategorySpecification mySpecification = constructSpecification(criteria);
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
    public List<Category> delete(List<Category> list) {
		List<Category> result = new ArrayList();
        if (list != null) {
            for (Category t : list) {
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
    public Category create(Category t) {
        Category loaded = findByReferenceEntity(t);
        Category saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Category findWithAssociatedLists(Long id){
        Category result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Category> update(List<Category> ts, boolean createIfNotExist) {
        List<Category> result = new ArrayList<>();
        if (ts != null) {
            for (Category t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Category loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Category t, Category loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Category findByReferenceEntity(Category t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Category> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Category>> getToBeSavedAndToBeDeleted(List<Category> oldList, List<Category> newList) {
        List<List<Category>> result = new ArrayList<>();
        List<Category> resultDelete = new ArrayList<>();
        List<Category> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Category> oldList, List<Category> newList, List<Category> resultUpdateOrSave, List<Category> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Category myOld = oldList.get(i);
                Category t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Category myNew = newList.get(i);
                Category t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public CategoryClientServiceImpl(CategoryDao dao) {
        this.dao = dao;
    }

    private CategoryDao dao;
}
