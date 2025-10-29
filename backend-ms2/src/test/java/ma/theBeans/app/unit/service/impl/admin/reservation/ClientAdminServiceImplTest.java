package ma.theBeans.app.unit.service.impl.admin.reservation;

import ma.theBeans.app.bean.core.reservation.Client;
import ma.theBeans.app.dao.facade.core.reservation.ClientDao;
import ma.theBeans.app.service.impl.admin.reservation.ClientAdminServiceImpl;

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
class ClientAdminServiceImplTest {

    @Mock
    private ClientDao repository;
    private AutoCloseable autoCloseable;
    private ClientAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ClientAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllClient() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveClient() {
        // Given
        Client toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteClient() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetClientById() {
        // Given
        Long idToRetrieve = 1L; // Example Client ID to retrieve
        Client expected = new Client(); // You need to replace Client with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Client result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Client constructSample(int i) {
		Client given = new Client();
        given.setDescription("description-"+i);
        given.setPasswordChanged(false);
        given.setUsername("username-"+i);
        given.setAccountNonExpired(false);
        given.setCredentialsNonExpired(false);
        given.setEnabled(false);
        given.setEmail("email-"+i);
        given.setPassword("password-"+i);
        given.setAccountNonLocked(false);
        return given;
    }

}
