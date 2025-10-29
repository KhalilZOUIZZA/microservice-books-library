package ma.theBeans.app.service.facade.admin.book;

import java.util.List;
import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.dao.criteria.core.book.AuthorCriteria;


public interface AuthorAdminService {







	Author create(Author t);

    Author update(Author t);

    List<Author> update(List<Author> ts,boolean createIfNotExist);

    Author findById(Long id);

    Author findOrSave(Author t);

    Author findByReferenceEntity(Author t);

    Author findWithAssociatedLists(Long id);

    List<Author> findAllOptimized();

    List<Author> findAll();

    List<Author> findByCriteria(AuthorCriteria criteria);

    List<Author> findPaginatedByCriteria(AuthorCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(AuthorCriteria criteria);

    List<Author> delete(List<Author> ts);

    boolean deleteById(Long id);

    List<List<Author>> getToBeSavedAndToBeDeleted(List<Author> oldList, List<Author> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
