package ma.theBeans.app.unit.dao.facade.core.reservation;

import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.dao.facade.core.reservation.ReservationItemDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.bean.core.reservation.Reservation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ReservationItemDaoTest {

@Autowired
    private ReservationItemDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        ReservationItem entity = new ReservationItem();
        entity.setId(id);
        underTest.save(entity);
        ReservationItem loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        ReservationItem entity = new ReservationItem();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        ReservationItem loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<ReservationItem> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<ReservationItem> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        ReservationItem given = constructSample(1);
        ReservationItem saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private ReservationItem constructSample(int i) {
		ReservationItem given = new ReservationItem();
        given.setCopy(new Copy(1L));
        given.setReservation(new Reservation(1L));
        given.setReturnDate(LocalDateTime.now());
        return given;
    }

}
