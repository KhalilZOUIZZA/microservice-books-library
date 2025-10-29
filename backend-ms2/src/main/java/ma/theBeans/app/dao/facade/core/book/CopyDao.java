package ma.theBeans.app.dao.facade.core.book;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.book.Copy;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CopyDao extends AbstractRepository<Copy,Long>  {
    Copy findBySerialNumber(String serialNumber);
    int deleteBySerialNumber(String serialNumber);

    List<Copy> findByBookId(Long id);
    int deleteByBookId(Long id);
    long countByBookCode(String code);

    @Query("SELECT NEW Copy(item.id,item.serialNumber) FROM Copy item")
    List<Copy> findAllOptimized();

}
