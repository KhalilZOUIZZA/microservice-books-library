package ma.theBeans.app.unit.service.impl.admin.reservation;

import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.dao.facade.core.reservation.ReservationItemDao;
import ma.theBeans.app.service.impl.admin.reservation.ReservationItemAdminServiceImpl;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.bean.core.reservation.Reservation;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class ReservationItemAdminServiceImplTest {

    @Mock
    private ReservationItemDao repository;
    private AutoCloseable autoCloseable;
    private ReservationItemAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ReservationItemAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllReservationItem() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveReservationItem() {
        // Given
        ReservationItem toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteReservationItem() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetReservationItemById() {
        // Given
        Long idToRetrieve = 1L; // Example ReservationItem ID to retrieve
        ReservationItem expected = new ReservationItem(); // You need to replace ReservationItem with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        ReservationItem result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private ReservationItem constructSample(int i) {
		ReservationItem given = new ReservationItem();
        given.setCopy(new Copy(1L));
        given.setReservation(new Reservation(1L));
        given.setReturnDate(LocalDateTime.now());
        return given;
    }

}
