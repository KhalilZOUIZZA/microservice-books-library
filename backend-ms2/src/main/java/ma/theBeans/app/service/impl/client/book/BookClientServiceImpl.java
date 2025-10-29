package ma.theBeans.app.service.impl.client.book;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.dao.criteria.core.book.BookCriteria;
import ma.theBeans.app.dao.facade.core.book.BookDao;
import ma.theBeans.app.dao.specification.core.book.BookSpecification;
import ma.theBeans.app.service.facade.client.book.BookClientService;

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

import ma.theBeans.app.service.facade.client.book.CategoryClientService;
import ma.theBeans.app.service.facade.client.book.CopyClientService;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.service.facade.client.book.AuthorClientService;

@Service
public class BookClientServiceImpl implements BookClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Book update(Book t) {
        Book loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Book.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public Book findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Book findOrSave(Book t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Book result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Book> findAll() {
        return dao.findAll();
    }

    public List<Book> findByCriteria(BookCriteria criteria) {
        List<Book> content = null;
        if (criteria != null) {
            BookSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private BookSpecification constructSpecification(BookCriteria criteria) {
        BookSpecification mySpecification =  (BookSpecification) RefelexivityUtil.constructObjectUsingOneParam(BookSpecification.class, criteria);
        return mySpecification;
    }

    public List<Book> findPaginatedByCriteria(BookCriteria criteria, int page, int pageSize, String order, String sortField) {
        BookSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(BookCriteria criteria) {
        BookSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Book> findByCategoryId(Long id){
        return dao.findByCategoryId(id);
    }
    public int deleteByCategoryId(Long id){
        return dao.deleteByCategoryId(id);
    }
    public long countByCategoryCode(String code){
        return dao.countByCategoryCode(code);
    }
    public List<Book> findByAuthorId(Long id){
        return dao.findByAuthorId(id);
    }
    public int deleteByAuthorId(Long id){
        return dao.deleteByAuthorId(id);
    }
    public long countByAuthorCode(String code){
        return dao.countByAuthorCode(code);
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
        copyService.deleteByBookId(id);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Book> delete(List<Book> list) {
		List<Book> result = new ArrayList();
        if (list != null) {
            for (Book t : list) {
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
    public Book create(Book t) {
        if (t.getCategory() != null) {
            t.getCategory().setId(null);
            t.setCategory(categoryService.create(t.getCategory()));
        }
        if (t.getAuthor() != null) {
            t.getAuthor().setId(null);
            t.setAuthor(authorService.create(t.getAuthor()));
        }
        Book loaded = findByReferenceEntity(t);
        Book saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getCopies() != null) {
                t.getCopies().forEach(element-> {
                    if (element.getBook() != null) {
                        element.getBook().setId(null);
                        element.setBook(bookService.create(element.getBook()));
                    }
                    element.setBook(saved);
                    copyService.create(element);
                });
            }
        }else {
            saved = null;
        }
        return saved;
    }

    public Book findWithAssociatedLists(Long id){
        Book result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setCopies(copyService.findByBookId(id));
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Book> update(List<Book> ts, boolean createIfNotExist) {
        List<Book> result = new ArrayList<>();
        if (ts != null) {
            for (Book t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Book loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Book t, Book loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(Book book){
    if(book !=null && book.getId() != null){
        List<List<Copy>> resultCopies= copyService.getToBeSavedAndToBeDeleted(copyService.findByBookId(book.getId()),book.getCopies());
            copyService.delete(resultCopies.get(1));
        emptyIfNull(resultCopies.get(0)).forEach(e -> e.setBook(book));
        copyService.update(resultCopies.get(0),true);
        }
    }








    public Book findByReferenceEntity(Book t){
        return t==null? null : dao.findByCode(t.getCode());
    }
    public void findOrSaveAssociatedObject(Book t){
        if( t != null) {
        }
    }



    public List<Book> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Book>> getToBeSavedAndToBeDeleted(List<Book> oldList, List<Book> newList) {
        List<List<Book>> result = new ArrayList<>();
        List<Book> resultDelete = new ArrayList<>();
        List<Book> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Book> oldList, List<Book> newList, List<Book> resultUpdateOrSave, List<Book> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Book myOld = oldList.get(i);
                Book t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Book myNew = newList.get(i);
                Book t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
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
    private BookClientService bookService ;

    @Autowired
    private CategoryClientService categoryService ;
    @Autowired
    private CopyClientService copyService ;
    @Autowired
    private AuthorClientService authorService ;

    public BookClientServiceImpl(BookDao dao) {
        this.dao = dao;
    }

    private BookDao dao;
}
