package com.api.TCCnnect.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErroUserAlreadyExists(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<String> tratarErroUsernameNotFound(UsernameNotFoundException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Void> tratarErro404() {
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
            var erros = ex.getFieldErrors();
            return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
        }

        private record DadosErroValidacao(String campo, String mensagem) {
            public DadosErroValidacao(FieldError erro) {
                this(erro.getField(), erro.getDefaultMessage());
            }
        }

    }

