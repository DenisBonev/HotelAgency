package bg.softuni.hotelagency.model.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFoundHandler() {
        return "error400";
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public String conflict() {
        return "error400";
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError() {
        return "error400";
    }

    @ExceptionHandler(Exception.class)
    public String handleError() {
        return "error500";
    }
}
