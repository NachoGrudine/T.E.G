
package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.dtos.common.ErrorApi;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(TestSecurityConfig.class)
class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    void handleEntityNotFoundException_returnsNotFound() {
        EntityNotFoundException ex = new EntityNotFoundException("No encontrado");
        var response = handler.handleEntityNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("No encontrado"));
    }

    @Test
    void handleGeneralException_returnsInternalServerError() {
        Exception ex = new Exception("Error grave");
        var response = handler.handleGeneralException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Error grave"));
    }

    @Test
    void handleHttpMessageNotReadable_returnsBadRequest() {
        Exception cause = new Exception("json mal");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("error", cause, null);
        var response = handler.handleHttpMessageNotReadable(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("json mal"));
    }

    @Test
    void handleValidationException_returnsBadRequest() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("obj", "campo", "msg")));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);

        var response = handler.handleValidationException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("campo: msg"));
    }
}