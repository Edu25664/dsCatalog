package com.devsuperior.dscatalog.resource.Exceptions;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.Services.Exceptions.EntityNotFoundException;

@ControllerAdvice
public class ResourceExceptionRandle {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandartErro> entityNofFund( EntityNotFoundException e, HttpServletRequest request){
         StandartErro err = new StandartErro();
         err.setTimestamp(Instant.now());
         err.setStatus(HttpStatus.NOT_FOUND.value());
         err.setErro("resource not fund");
         err.setMessage(e.getMessage());
         err.setPath(request.getRequestURI());
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}   
