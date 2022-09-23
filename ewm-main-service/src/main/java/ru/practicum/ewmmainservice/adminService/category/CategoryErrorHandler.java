package ru.practicum.ewmmainservice.adminService.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.RelatedObjectsPresent;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.Instant;

@RestControllerAdvice("ru.practicum.ewmmainservice.adminService.category")
public class CategoryErrorHandler {
    @ExceptionHandler(ModelAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError alreadyExistsException(ModelAlreadyExistsException e){
        return ApiError.builder()
                .description(String.format("The category with name %s already exists.", e.getValue()))
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("Category already exists")
                .build();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(NotFoundException e){
        return ApiError.builder()
                .description(String.format("The category with id %s  not found.", e.getValue()))
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("The required object was not found.")
                .build();
    }

    @ExceptionHandler(RelatedObjectsPresent.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError relatedObjectsPresent(RelatedObjectsPresent e){
        return ApiError.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .reason("The category is associated with events.")
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT)
                .build();
    }

}
