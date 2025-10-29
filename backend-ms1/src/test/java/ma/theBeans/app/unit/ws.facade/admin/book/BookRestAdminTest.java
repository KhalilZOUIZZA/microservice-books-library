package ma.theBeans.app.unit.ws.facade.admin.book;

import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.service.impl.admin.book.BookAdminServiceImpl;
import ma.theBeans.app.ws.facade.admin.book.BookRestAdmin;
import ma.theBeans.app.ws.converter.book.BookConverter;
import ma.theBeans.app.ws.dto.book.BookDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private BookAdminServiceImpl service;
    @Mock
    private BookConverter converter;

    @InjectMocks
    private BookRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllBookTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<BookDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<BookDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    /*@Test
    public void itShouldSaveBookTest() throws Exception {
        // Mock data
        BookDto requestDto = new BookDto();
        Book entity = new Book();
        Book saved = new Book();
        BookDto savedDto = new BookDto();

        // Mock the converter to return the book object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved book DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<BookDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        BookDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved book DTO
        assertEquals(savedDto.getCode(), responseBody.getCode());
        assertEquals(savedDto.getLabel(), responseBody.getLabel());
        assertEquals(savedDto.getTitle(), responseBody.getTitle());
        assertEquals(savedDto.getEditionDate(), responseBody.getEditionDate());
        assertEquals(savedDto.getDescription(), responseBody.getDescription());
        assertEquals(savedDto.getNumberOfCopies(), responseBody.getNumberOfCopies());
        assertEquals(savedDto.getAvailable(), responseBody.getAvailable());
        assertEquals(savedDto.getImageUrl(), responseBody.getImageUrl());
    }*/

}
