package ma.theBeans.app.unit.dao.facade.core.book;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.dao.facade.core.book.CopyDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ma.theBeans.app.bean.core.book.Book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CopyDaoTest {

@Autowired
    private CopyDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindBySerialNumber(){
        String serialNumber = "serialNumber-1";
        Copy entity = new Copy();
        entity.setSerialNumber(serialNumber);
        underTest.save(entity);
        Copy loaded = underTest.findBySerialNumber(serialNumber);
        assertThat(loaded.getSerialNumber()).isEqualTo(serialNumber);
    }

    @Test
    void shouldDeleteBySerialNumber() {
        String serialNumber = "serialNumber-12345678";
        int result = underTest.deleteBySerialNumber(serialNumber);

        Copy loaded = underTest.findBySerialNumber(serialNumber);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Copy entity = new Copy();
        entity.setId(id);
        underTest.save(entity);
        Copy loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Copy entity = new Copy();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Copy loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Copy> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Copy> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Copy given = constructSample(1);
        Copy saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Copy constructSample(int i) {
		Copy given = new Copy();
        given.setSerialNumber("serialNumber-"+i);
        given.setBook(new Book(1L));
        return given;
    }

}
