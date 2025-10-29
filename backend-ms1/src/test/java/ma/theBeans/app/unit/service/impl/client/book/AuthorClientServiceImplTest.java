package ma.theBeans.app.unit.service.impl.client.book;

import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.dao.facade.core.book.AuthorDao;
import ma.theBeans.app.service.impl.client.book.AuthorClientServiceImpl;

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
class AuthorClientServiceImplTest {

    @Mock
    private AuthorDao repository;
    private AutoCloseable autoCloseable;
    private AuthorClientServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AuthorClientServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllAuthor() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveAuthor() {
        // Given
        Author toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteAuthor() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetAuthorById() {
        // Given
        Long idToRetrieve = 1L; // Example Author ID to retrieve
        Author expected = new Author(); // You need to replace Author with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Author result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Author constructSample(int i) {
		Author given = new Author();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setFullName("fullName-"+i);
        return given;
    }

}
