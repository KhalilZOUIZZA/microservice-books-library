package ma.theBeans.app.service.facade.admin.book;

import java.util.List;
import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.dao.criteria.core.book.CategoryCriteria;


public interface CategoryAdminService {







	Category create(Category t);

    Category update(Category t);

    List<Category> update(List<Category> ts,boolean createIfNotExist);

    Category findById(Long id);

    Category findOrSave(Category t);

    Category findByReferenceEntity(Category t);

    Category findWithAssociatedLists(Long id);

    List<Category> findAllOptimized();

    List<Category> findAll();

    List<Category> findByCriteria(CategoryCriteria criteria);

    List<Category> findPaginatedByCriteria(CategoryCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CategoryCriteria criteria);

    List<Category> delete(List<Category> ts);

    boolean deleteById(Long id);

    List<List<Category>> getToBeSavedAndToBeDeleted(List<Category> oldList, List<Category> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
