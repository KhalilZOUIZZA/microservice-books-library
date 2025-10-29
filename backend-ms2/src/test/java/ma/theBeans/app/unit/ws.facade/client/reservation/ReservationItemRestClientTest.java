package ma.theBeans.app.unit.ws.facade.client.reservation;

import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.service.impl.client.reservation.ReservationItemClientServiceImpl;
import ma.theBeans.app.ws.facade.client.reservation.ReservationItemRestClient;
import ma.theBeans.app.ws.converter.reservation.ReservationItemConverter;
import ma.theBeans.app.ws.dto.reservation.ReservationItemDto;
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
public class ReservationItemRestClientTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationItemClientServiceImpl service;
    @Mock
    private ReservationItemConverter converter;

    @InjectMocks
    private ReservationItemRestClient controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllReservationItemTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<ReservationItemDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<ReservationItemDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveReservationItemTest() throws Exception {
        // Mock data
        ReservationItemDto requestDto = new ReservationItemDto();
        ReservationItem entity = new ReservationItem();
        ReservationItem saved = new ReservationItem();
        ReservationItemDto savedDto = new ReservationItemDto();

        // Mock the converter to return the reservationItem object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved reservationItem DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<ReservationItemDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        ReservationItemDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved reservationItem DTO
        assertEquals(savedDto.getReturnDate(), responseBody.getReturnDate());
    }

}
