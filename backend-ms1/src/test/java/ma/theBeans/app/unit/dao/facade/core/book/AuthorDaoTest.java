package ma.theBeans.app.unit.dao.facade.core.book;

import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.dao.facade.core.book.AuthorDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AuthorDaoTest {

@Autowired
    private AuthorDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Author entity = new Author();
        entity.setCode(code);
        underTest.save(entity);
        Author loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Author loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Author entity = new Author();
        entity.setId(id);
        underTest.save(entity);
        Author loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Author entity = new Author();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Author loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Author> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Author> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Author given = constructSample(1);
        Author saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Author constructSample(int i) {
		Author given = new Author();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setFullName("fullName-"+i);
        return given;
    }

}
