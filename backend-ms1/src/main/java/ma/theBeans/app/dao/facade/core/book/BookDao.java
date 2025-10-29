package ma.theBeans.app.dao.facade.core.book;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.book.Book;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookDao extends AbstractRepository<Book,Long>  {
    Book findByCode(String code);
    int deleteByCode(String code);

    List<Book> findByCategoryId(Long id);
    int deleteByCategoryId(Long id);
    long countByCategoryCode(String code);
    List<Book> findByAuthorId(Long id);
    int deleteByAuthorId(Long id);
    long countByAuthorCode(String code);

    @Query("SELECT NEW Book(item.id,item.code) FROM Book item")
    List<Book> findAllOptimized();

}
