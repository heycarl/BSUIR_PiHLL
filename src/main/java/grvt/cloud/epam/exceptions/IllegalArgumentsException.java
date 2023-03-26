package grvt.cloud.epam.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "invalid data")
public class IllegalArgumentsException extends Exception {
    private static final Logger logger = LogManager.getLogger();

    public IllegalArgumentsException() {
    }

    public IllegalArgumentsException(String message) {
        super(message);
        logger.error(message);
    }

    public IllegalArgumentsException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause.getMessage());
    }

    public IllegalArgumentsException(Throwable cause) {
        super(cause);
        logger.error(cause.getMessage());
    }
}