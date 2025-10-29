package ma.theBeans.app.dao.facade.core.book;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.book.Category;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryDao extends AbstractRepository<Category,Long>  {
    Category findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW Category(item.id,item.label) FROM Category item")
    List<Category> findAllOptimized();

}
