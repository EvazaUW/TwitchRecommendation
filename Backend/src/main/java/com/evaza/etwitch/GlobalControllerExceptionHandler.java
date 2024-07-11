package com.evaza.etwitch;

import com.evaza.etwitch.model.TwitchErrorResponse;
import com.evaza.etwitch.user.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

// 所有的controller throw 的 exception 都可以被 handle
// AOP: Aspect-oriented programming
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    // 最 Generic 类： catch all kinds of exception， 返回 internal_server_error
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<TwitchErrorResponse> handleDefaultException(Exception e) {
        logger.error("", e);
        return new ResponseEntity<>(
                new TwitchErrorResponse("Something went wrong, please try again later.",
                        e.getClass().getName(),
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // catch specific error - you can change this "ResponseStatusException" to "DuplicatedEntry",
    // as long as it corresponds to your written code in "FavoriteController"
    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<TwitchErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        logger.error("", e.getCause());
        return new ResponseEntity<>(
                new TwitchErrorResponse(e.getReason(), e.getCause().getClass().getName(), e.getCause().getMessage()),
                e.getStatusCode()
        );
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<TwitchErrorResponse> handleResponseStatusException(UserAlreadyExistException e) {
        return new ResponseEntity<>(
                new TwitchErrorResponse(e.getMessage(),
                        e.getClass().getName(),
                        null
                ),
                HttpStatus.CONFLICT
        );
    }
}
