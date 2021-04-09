package bg.softuni.hotelagency.model.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFoundHandler(RuntimeException e) {
        return "error404";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public String conflict(DataIntegrityViolationException e) {
    return "error400";
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(Throwable e) throws Throwable {
        return "error403";
    }

    @ExceptionHandler(Throwable.class)
    public String handleError(Throwable throwable) throws Throwable {
        return "error";
    }
}
