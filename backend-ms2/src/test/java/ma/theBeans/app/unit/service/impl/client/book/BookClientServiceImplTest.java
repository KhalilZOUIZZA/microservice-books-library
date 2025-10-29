package ma.theBeans.app.unit.service.impl.client.book;

import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.dao.facade.core.book.BookDao;
import ma.theBeans.app.service.impl.client.book.BookClientServiceImpl;

import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.bean.core.book.Author;
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
class BookClientServiceImplTest {

    @Mock
    private BookDao repository;
    private AutoCloseable autoCloseable;
    private BookClientServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BookClientServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllBook() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveBook() {
        // Given
        Book toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteBook() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetBookById() {
        // Given
        Long idToRetrieve = 1L; // Example Book ID to retrieve
        Book expected = new Book(); // You need to replace Book with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Book result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Book constructSample(int i) {
		Book given = new Book();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        given.setTitle("title-"+i);
        given.setEditionDate(LocalDateTime.now());
        given.setDescription("description-"+i);
        given.setNumberOfCopies(i);
        given.setAvailable(false);
        given.setCategory(new Category(1L));
        given.setAuthor(new Author(1L));
        List<Copy> copies = IntStream.rangeClosed(1, 3)
                                             .mapToObj(id -> {
                                                Copy element = new Copy();
                                                element.setId((long)id);
                                                element.setSerialNumber("serialNumber"+id);
                                                element.setBook(new Book(Long.valueOf(2)));
                                                return element;
                                             })
                                             .collect(Collectors.toList());
        given.setCopies(copies);
        given.setImageUrl("imageUrl-"+i);
        return given;
    }

}
