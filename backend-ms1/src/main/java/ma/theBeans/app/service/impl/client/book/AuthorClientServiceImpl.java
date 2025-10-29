package ma.theBeans.app.service.impl.client.book;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.dao.criteria.core.book.AuthorCriteria;
import ma.theBeans.app.dao.facade.core.book.AuthorDao;
import ma.theBeans.app.dao.specification.core.book.AuthorSpecification;
import ma.theBeans.app.service.facade.client.book.AuthorClientService;

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
public class AuthorClientServiceImpl implements AuthorClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Author update(Author t) {
        Author loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Author.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Author findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Author findOrSave(Author t) {
        if (t != null) {
            Author result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Author> findAll() {
        return dao.findAll();
    }

    public List<Author> findByCriteria(AuthorCriteria criteria) {
        List<Author> content = null;
        if (criteria != null) {
            AuthorSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private AuthorSpecification constructSpecification(AuthorCriteria criteria) {
        AuthorSpecification mySpecification =  (AuthorSpecification) RefelexivityUtil.constructObjectUsingOneParam(AuthorSpecification.class, criteria);
        return mySpecification;
    }

    public List<Author> findPaginatedByCriteria(AuthorCriteria criteria, int page, int pageSize, String order, String sortField) {
        AuthorSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(AuthorCriteria criteria) {
        AuthorSpecification mySpecification = constructSpecification(criteria);
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
    public List<Author> delete(List<Author> list) {
		List<Author> result = new ArrayList();
        if (list != null) {
            for (Author t : list) {
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
    public Author create(Author t) {
        Author loaded = findByReferenceEntity(t);
        Author saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Author findWithAssociatedLists(Long id){
        Author result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Author> update(List<Author> ts, boolean createIfNotExist) {
        List<Author> result = new ArrayList<>();
        if (ts != null) {
            for (Author t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Author loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Author t, Author loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Author findByReferenceEntity(Author t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<Author> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Author>> getToBeSavedAndToBeDeleted(List<Author> oldList, List<Author> newList) {
        List<List<Author>> result = new ArrayList<>();
        List<Author> resultDelete = new ArrayList<>();
        List<Author> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Author> oldList, List<Author> newList, List<Author> resultUpdateOrSave, List<Author> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Author myOld = oldList.get(i);
                Author t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Author myNew = newList.get(i);
                Author t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }








    public AuthorClientServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    private AuthorDao dao;
}
