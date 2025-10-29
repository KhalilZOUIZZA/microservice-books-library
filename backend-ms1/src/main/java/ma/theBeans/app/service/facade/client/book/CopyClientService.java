package ma.theBeans.app.service.facade.client.book;

import java.util.List;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.dao.criteria.core.book.CopyCriteria;


public interface CopyClientService {



    List<Copy> findByBookId(Long id);
    int deleteByBookId(Long id);
    long countByBookCode(String code);




	Copy create(Copy t);

    Copy update(Copy t);

    List<Copy> update(List<Copy> ts,boolean createIfNotExist);

    Copy findById(Long id);

    Copy findOrSave(Copy t);

    Copy findByReferenceEntity(Copy t);

    Copy findWithAssociatedLists(Long id);

    List<Copy> findAllOptimized();

    List<Copy> findAll();

    List<Copy> findByCriteria(CopyCriteria criteria);

    List<Copy> findPaginatedByCriteria(CopyCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CopyCriteria criteria);

    List<Copy> delete(List<Copy> ts);

    boolean deleteById(Long id);

    List<List<Copy>> getToBeSavedAndToBeDeleted(List<Copy> oldList, List<Copy> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}
