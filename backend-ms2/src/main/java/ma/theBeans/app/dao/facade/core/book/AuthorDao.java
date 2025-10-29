package ma.theBeans.app.dao.facade.core.book;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.book.Author;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorDao extends AbstractRepository<Author,Long>  {
    Author findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW Author(item.id,item.fullName) FROM Author item")
    List<Author> findAllOptimized();

}
