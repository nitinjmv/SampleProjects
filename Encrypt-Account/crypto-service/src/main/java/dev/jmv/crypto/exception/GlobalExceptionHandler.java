package dev.jmv.crypto.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Generic Exception caught {}", ex.getLocalizedMessage());

        var details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generic Exception", details));

    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex) {
        log.error("RecordNotFoundException caught {}", ex.getLocalizedMessage());

        var details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND.value(), "Record Not Found", details));
    }

    private ErrorResponse buildErrorResponse(int errorCode, String errorMessage, List<String> details) {
        return ErrorResponse.builder()
                .httpErrorCode(errorCode)
                .errorMessage(errorMessage)
                .errorDetails(details)
                .build();
    }
}
