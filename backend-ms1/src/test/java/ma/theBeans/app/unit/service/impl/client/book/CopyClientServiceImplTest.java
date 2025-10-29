package ma.theBeans.app.unit.service.impl.client.book;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.dao.facade.core.book.CopyDao;
import ma.theBeans.app.service.impl.client.book.CopyClientServiceImpl;

import ma.theBeans.app.bean.core.book.Book;
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
class CopyClientServiceImplTest {

    @Mock
    private CopyDao repository;
    private AutoCloseable autoCloseable;
    private CopyClientServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CopyClientServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCopy() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCopy() {
        // Given
        Copy toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCopy() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCopyById() {
        // Given
        Long idToRetrieve = 1L; // Example Copy ID to retrieve
        Copy expected = new Copy(); // You need to replace Copy with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Copy result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Copy constructSample(int i) {
		Copy given = new Copy();
        given.setSerialNumber("serialNumber-"+i);
        given.setBook(new Book(1L));
        return given;
    }

}
