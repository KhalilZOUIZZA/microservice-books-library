package ma.theBeans.app.unit.dao.facade.core.book;

import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.dao.facade.core.book.CategoryDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CategoryDaoTest {

@Autowired
    private CategoryDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Category entity = new Category();
        entity.setCode(code);
        underTest.save(entity);
        Category loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Category loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Category entity = new Category();
        entity.setId(id);
        underTest.save(entity);
        Category loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Category entity = new Category();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Category loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Category> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Category> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Category given = constructSample(1);
        Category saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Category constructSample(int i) {
		Category given = new Category();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        return given;
    }

}
