package br.com.valtergodoy.testSpring.handler;

import br.com.valtergodoy.testSpring.exception.BadRequestException;
import br.com.valtergodoy.testSpring.exception.BadRequestExceptionDetail;
import br.com.valtergodoy.testSpring.exception.ExceptionDetail;
import br.com.valtergodoy.testSpring.exception.ValidationExceptionDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetail> handleBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(
                BadRequestExceptionDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, Check the Exception session on Documentation")
                .message(badRequestException.getMessage())
                .className(badRequestException.getClass().toString())
                .devMessage(badRequestException.getCause().getStackTrace().toString())
                .build(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError: fieldErrors) {
            errorMessage.append("Field: " + fieldError.getField() + " - Message: " + fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(
                ValidationExceptionDetail.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid value(s) for Field(s).")
                        .devMessage(ex.getCause().getStackTrace().toString())
                        .message("Some field(s) with invalid value(s).")
                        .fieldMessage(errorMessage.toString())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .devMessage(ex.getCause().getStackTrace().toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDetail, headers, status);
    }

}
