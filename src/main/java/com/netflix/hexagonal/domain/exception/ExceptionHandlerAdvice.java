package com.netflix.hexagonal.domain.exception;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.exception.contract.MessageException;
import com.netflix.hexagonal.domain.exception.dto.ApiErrorDTO;
import com.netflix.hexagonal.domain.exception.dto.ErrorDTO;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final String UNKNOWN_ERROR_KEY = "unknown.error";

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private final MessageSource messageSource;

    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        logger.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());
        List<ErrorDTO> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> buildError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ApiErrorDTO> handlerBaseException(Throwable exception) {
        logger.error("Exception {}", exception.getClass().getName());
        MessageException messageException = (MessageException) exception;
        ErrorDTO error = buildError(messageException.getExceptionKey(),
                bindExceptionKeywords(messageException.getMapDetails(),messageException.getExceptionKey()));

        List<ErrorDTO> errors = Arrays.asList(error);
        ApiErrorDTO apiErrorDto = baseErrorBuilder(getResponseStatus(exception), errors);

        return ResponseEntity
                .status(getResponseStatus(exception))
                .body(apiErrorDto);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodThrowable(Throwable exception) {
        logger.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());
        List<ErrorDTO> errors = Arrays.asList(buildError(UNKNOWN_ERROR_KEY, exception.getMessage()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(baseErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, errors));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiErrorDTO> handlerValidationException(ValidationException exception) {
        logger.error("Exception {}", exception.getClass().getName());

        ErrorDTO error = buildError(exception.toString(),
                exception.getMessage());

        List<ErrorDTO> errors = Arrays.asList(error);
        ApiErrorDTO apiErrorDto = baseErrorBuilder(getResponseStatus(exception), errors);

        return ResponseEntity
                .status(getResponseStatus(exception))
                .body(apiErrorDto);
    }

    private ErrorDTO buildError(String code, String message) {
        return new ErrorDTO(code, message);
    }

    private ApiErrorDTO baseErrorBuilder(HttpStatus httpStatus, List<ErrorDTO> errorList) {
        return new ApiErrorDTO(
                new Date(),
                httpStatus.value(),
                httpStatus.name(),
                errorList);
    }

    private String bindExceptionKeywords(Map<String, Object> keywords, String exceptionKey) {
        String message = messageSource.getMessage(exceptionKey, null, LocaleContextHolder.getLocale());
        return Objects.nonNull(keywords) ? new StrSubstitutor(keywords).replace(message) : message;
    }

    private HttpStatus getResponseStatus(Throwable exception) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        if (exception.getClass().getAnnotation(ResponseStatus.class) == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return responseStatus.value();
    }
}
