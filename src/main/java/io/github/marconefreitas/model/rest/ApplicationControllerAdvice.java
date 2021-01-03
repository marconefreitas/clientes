package io.github.marconefreitas.model.rest;


import io.github.marconefreitas.model.rest.exception.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex){
       BindingResult bd =  ex.getBindingResult();
       List<String> errors =  bd.getAllErrors()
               .stream()
               .map(objectError -> objectError.getDefaultMessage())
               .collect(Collectors.toList());
       return new ApiErrors(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException e){
        String mes = e.getReason();
        HttpStatus hj = e.getStatus();

        ApiErrors api = new ApiErrors(mes);
        return new ResponseEntity(api, hj);

    }
}
