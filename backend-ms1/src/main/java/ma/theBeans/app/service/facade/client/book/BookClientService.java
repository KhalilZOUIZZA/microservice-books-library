package ma.theBeans.app.service.facade.client.book;

import java.util.List;
import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.dao.criteria.core.book.BookCriteria;


public interface BookClientService {



    List<Book> findByCategoryId(Long id);
    int deleteByCategoryId(Long id);
    long countByCategoryCode(String code);
    List<Book> findByAuthorId(Long id);
    int deleteByAuthorId(Long id);
    long countByAuthorCode(String code);




	Book create(Book t);

    Book update(Book t);

    List<Book> update(List<Book> ts,boolean createIfNotExist);

    Book findById(Long id);

    Book findOrSave(Book t);

    Book findByReferenceEntity(Book t);

    Book findWithAssociatedLists(Long id);

    List<Book> findAllOptimized();

    List<Book> findAll();

    List<Book> findByCriteria(BookCriteria criteria);

    List<Book> findPaginatedByCriteria(BookCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(BookCriteria criteria);

    List<Book> delete(List<Book> ts);

    boolean deleteById(Long id);

    List<List<Book>> getToBeSavedAndToBeDeleted(List<Book> oldList, List<Book> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
