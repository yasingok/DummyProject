package com.example.order_adapter.common;

import com.example.order_adapter.exceptions.CrustNotSupportedException;
import com.example.order_adapter.exceptions.FlavorNotSupportedException;
import com.example.order_adapter.exceptions.OrderException;
import com.example.order_adapter.exceptions.SizeNotSupportedException;
import com.example.order_adapter.exceptions.UserNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

@ControllerAdvice
public class ExceptionHandlingController {
    private static final String EXCEPTION_DEBUG_MESSAGE = "Handling exception: {}; {}";
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);


    @ExceptionHandler(CrustNotSupportedException.class)
    public ResponseEntity<?> handleCrustNotSupportedException(final CrustNotSupportedException ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(FlavorNotSupportedException.class)
    public ResponseEntity<?> handleFlavorNotSupportedException(final FlavorNotSupportedException ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SizeNotSupportedException.class)
    public ResponseEntity<?> handleSizeNotSupportedException(final SizeNotSupportedException ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(final UserNotFoundException ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(final OrderException ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class, UnexpectedTypeException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> validationExceptions(final Exception  ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(final Exception ex) {
        logger.debug(EXCEPTION_DEBUG_MESSAGE, ex.getMessage(),ExceptionUtils.getStackTrace(ex));
        RestErrorResource response = new RestErrorResource();
        response.setDescription(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
