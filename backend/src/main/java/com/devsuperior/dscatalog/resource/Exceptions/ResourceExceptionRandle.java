package com.devsuperior.dscatalog.resource.Exceptions;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.Services.Exceptions.DataBaseExcpetion;
import com.devsuperior.dscatalog.Services.Exceptions.EntityNotFoundException;

@ControllerAdvice
public class ResourceExceptionRandle {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandartErro> entityNofFund( EntityNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
         StandartErro err = new StandartErro();
         err.setTimestamp(Instant.now());
         err.setStatus(status.value());
         err.setErro("resource not fund");
         err.setMessage(e.getMessage());
         err.setPath(request.getRequestURI());
         return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseExcpetion.class)
    public ResponseEntity<StandartErro> entityNofFund( DataBaseExcpetion e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
         StandartErro err = new StandartErro();
         err.setTimestamp(Instant.now());
         err.setStatus(status.value());
         err.setErro("resource not fund");
         err.setMessage(e.getMessage());
         err.setPath(request.getRequestURI());
         return ResponseEntity.status(status).body(err);
    }
}   
