package ma.theBeans.app.unit.ws.facade.admin.book;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.service.impl.admin.book.CopyAdminServiceImpl;
import ma.theBeans.app.ws.facade.admin.book.CopyRestAdmin;
import ma.theBeans.app.ws.converter.book.CopyConverter;
import ma.theBeans.app.ws.dto.book.CopyDto;
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
public class CopyRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private CopyAdminServiceImpl service;
    @Mock
    private CopyConverter converter;

    @InjectMocks
    private CopyRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCopyTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CopyDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CopyDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCopyTest() throws Exception {
        // Mock data
        CopyDto requestDto = new CopyDto();
        Copy entity = new Copy();
        Copy saved = new Copy();
        CopyDto savedDto = new CopyDto();

        // Mock the converter to return the copy object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved copy DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CopyDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CopyDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved copy DTO
        assertEquals(savedDto.getSerialNumber(), responseBody.getSerialNumber());
    }

}
