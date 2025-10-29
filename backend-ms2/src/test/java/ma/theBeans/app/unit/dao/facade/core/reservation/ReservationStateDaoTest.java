package ma.theBeans.app.unit.dao.facade.core.reservation;

import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.dao.facade.core.reservation.ReservationStateDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ReservationStateDaoTest {

@Autowired
    private ReservationStateDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        ReservationState entity = new ReservationState();
        entity.setCode(code);
        underTest.save(entity);
        ReservationState loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        ReservationState loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        ReservationState entity = new ReservationState();
        entity.setId(id);
        underTest.save(entity);
        ReservationState loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        ReservationState entity = new ReservationState();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        ReservationState loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<ReservationState> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<ReservationState> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        ReservationState given = constructSample(1);
        ReservationState saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private ReservationState constructSample(int i) {
		ReservationState given = new ReservationState();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setStyle("style-"+i);
        return given;
    }

}
