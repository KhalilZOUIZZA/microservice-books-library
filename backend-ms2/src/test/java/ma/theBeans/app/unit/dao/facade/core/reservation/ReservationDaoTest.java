package ma.theBeans.app.unit.dao.facade.core.reservation;

import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.dao.facade.core.reservation.ReservationDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.bean.core.reservation.Client;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ReservationDaoTest {

@Autowired
    private ReservationDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Reservation entity = new Reservation();
        entity.setCode(code);
        underTest.save(entity);
        Reservation loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Reservation loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Reservation entity = new Reservation();
        entity.setId(id);
        underTest.save(entity);
        Reservation loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Reservation entity = new Reservation();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Reservation loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Reservation> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Reservation> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Reservation given = constructSample(1);
        Reservation saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Reservation constructSample(int i) {
		Reservation given = new Reservation();
        given.setCode("code-"+i);
        given.setRequestDate(LocalDateTime.now());
        given.setTheoreticalReturnDate(LocalDateTime.now());
        given.setEffectiveReturnDate(LocalDateTime.now());
        given.setReservationState(new ReservationState(1L));
        given.setClient(new Client(1L));
        return given;
    }

}
