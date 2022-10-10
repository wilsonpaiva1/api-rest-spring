package br.com.wilson.paiva.apirestspring.handler;

import br.com.wilson.paiva.apirestspring.exceptions.ExceptionResponse;
import br.com.wilson.paiva.apirestspring.exceptions.InvalidJwtAuthenticationException;
import br.com.wilson.paiva.apirestspring.exceptions.RequiredObjectlsNullException;
import br.com.wilson.paiva.apirestspring.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntintyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(
            Exception ex, WebRequest request){
        ExceptionResponse exceptionsResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

        @ExceptionHandler(ResourceNotFoundException.class)
        public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
                Exception ex, WebRequest request){
            ExceptionResponse exceptionsResponse = new ExceptionResponse(
                    new Date(),
                    ex.getMessage(),
                    request.getDescription(false));

            return new ResponseEntity<>(exceptionsResponse, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(RequiredObjectlsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(
            Exception ex, WebRequest request){
        ExceptionResponse exceptionsResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionsResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(
            Exception ex, WebRequest request){
        ExceptionResponse exceptionsResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionsResponse, HttpStatus.FORBIDDEN);
    }
}
