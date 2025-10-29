package ma.theBeans.app.unit.service.impl.client.book;

import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.dao.facade.core.book.CategoryDao;
import ma.theBeans.app.service.impl.client.book.CategoryClientServiceImpl;

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
class CategoryClientServiceImplTest {

    @Mock
    private CategoryDao repository;
    private AutoCloseable autoCloseable;
    private CategoryClientServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CategoryClientServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCategory() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCategory() {
        // Given
        Category toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCategory() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCategoryById() {
        // Given
        Long idToRetrieve = 1L; // Example Category ID to retrieve
        Category expected = new Category(); // You need to replace Category with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Category result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Category constructSample(int i) {
		Category given = new Category();
        given.setCode("code-"+i);
        given.setLabel("label-"+i);
        return given;
    }

}
