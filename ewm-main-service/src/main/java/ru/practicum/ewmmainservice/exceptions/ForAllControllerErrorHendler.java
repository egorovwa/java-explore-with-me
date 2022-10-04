package ru.practicum.ewmmainservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.models.apiError.ApiError;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice("ru.practicum.ewmmainservice")
public class ForAllControllerErrorHendler {
    final DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    @ExceptionHandler(IncorrectPageValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError incorrectPageValueException(IncorrectPageValueException e) {
        return ApiError.builder()
                .reason(String.format("The page parameter %s with the value %s is not correct.", e.getParam(), e.value))
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .description("The from parameter cannot be negative, the size parameter must be greater than 0.")
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationError(MethodArgumentNotValidException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(formatter.format(LocalDateTime.now()))
                .reason("One or more fields are not valid.")
                .errors(e.getStackTrace())
                .build();
    }

    @ExceptionHandler(FiledParamNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError filedParamNotFoundException(FiledParamNotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .timestamp(formatter.format(LocalDateTime.now()))
                .reason("One or more class fields do not exist.")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(IlegalUserIdException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError ilegalUserIdException(IlegalUserIdException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.reason)
                .status(HttpStatus.FORBIDDEN)
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(IllegalTimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError illegalTimeException(IllegalTimeException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .timestamp(formatter.format(LocalDateTime.now()))
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(ModelAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError modelAlreadyExistsException(ModelAlreadyExistsException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.reason)
                .status(HttpStatus.CONFLICT)
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(NotRequiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError notRequiredException(NotRequiredException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .status(HttpStatus.FORBIDDEN)
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(RelatedObjectsPresent.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError relatedObjectsPresent(RelatedObjectsPresent e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.getReason())
                .status(HttpStatus.CONFLICT)
                .errors(e.getRelatedObjects().toArray())
                .timestamp(formatter.format(LocalDateTime.now()))
                .build();
    }

    @ExceptionHandler(StatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError statusException(StatusException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.reason)
                .timestamp(formatter.format(LocalDateTime.now()))
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(NumberParticipantsExceededException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError statusException(NumberParticipantsExceededException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason(e.reason)
                .timestamp(formatter.format(LocalDateTime.now()))
                .status(HttpStatus.FORBIDDEN)
                .build();
    }
}
