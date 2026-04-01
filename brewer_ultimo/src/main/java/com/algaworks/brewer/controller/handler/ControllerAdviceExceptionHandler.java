package com.algaworks.brewer.controller.handler;

import com.algaworks.brewer.services.exception.ObjetoJaExisteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(ObjetoJaExisteException.class)
    public ResponseEntity<String> handlerObjetoJaExisteException(ObjetoJaExisteException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
