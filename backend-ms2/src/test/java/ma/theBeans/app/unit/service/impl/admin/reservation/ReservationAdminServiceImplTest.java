package ma.theBeans.app.unit.service.impl.admin.reservation;

import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.dao.facade.core.reservation.ReservationDao;
import ma.theBeans.app.service.impl.admin.reservation.ReservationAdminServiceImpl;

import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.bean.core.reservation.Client;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
class ReservationAdminServiceImplTest {

    @Mock
    private ReservationDao repository;
    private AutoCloseable autoCloseable;
    private ReservationAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ReservationAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllReservation() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveReservation() {
        // Given
        Reservation toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteReservation() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetReservationById() {
        // Given
        Long idToRetrieve = 1L; // Example Reservation ID to retrieve
        Reservation expected = new Reservation(); // You need to replace Reservation with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Reservation result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Reservation constructSample(int i) {
		Reservation given = new Reservation();
        given.setCode("code-"+i);
        given.setRequestDate(LocalDateTime.now());
        given.setTheoreticalReturnDate(LocalDateTime.now());
        given.setEffectiveReturnDate(LocalDateTime.now());
        given.setReservationState(new ReservationState(1L));
        given.setClient(new Client(1L));
        List<ReservationItem> reservationItems = IntStream.rangeClosed(1, 3)
                                             .mapToObj(id -> {
                                                ReservationItem element = new ReservationItem();
                                                element.setId((long)id);
                                                element.setCopy(new Copy(Long.valueOf(1)));
                                                element.setReservation(new Reservation(Long.valueOf(2)));
                                                element.setReturnDate(LocalDateTime.now());
                                                return element;
                                             })
                                             .collect(Collectors.toList());
        given.setReservationItems(reservationItems);
        return given;
    }

}
