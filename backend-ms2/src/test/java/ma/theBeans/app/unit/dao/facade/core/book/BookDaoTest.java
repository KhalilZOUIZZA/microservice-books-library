package ma.theBeans.app.unit.dao.facade.core.book;

import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.dao.facade.core.book.BookDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.bean.core.book.Author;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BookDaoTest {

@Autowired
    private BookDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Book entity = new Book();
        entity.setCode(code);
        underTest.save(entity);
        Book loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Book loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Book entity = new Book();
        entity.setId(id);
        underTest.save(entity);
        Book loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Book entity = new Book();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Book loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Book> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Book> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Book given = constructSample(1);
        Book saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Book constructSample(int i) {
		Book given = new Book();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setTitle("title-"+i);
        given.setEditionDate(LocalDateTime.now());
        given.setDescription("description-"+i);
        given.setNumberOfCopies(i);
        given.setAvailable(false);
        given.setCategory(new Category(1L));
        given.setAuthor(new Author(1L));
        given.setImageUrl("imageUrl-"+i);
        return given;
    }

}
