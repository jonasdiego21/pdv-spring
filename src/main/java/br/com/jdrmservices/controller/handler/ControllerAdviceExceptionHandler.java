package br.com.jdrmservices.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.jdrmservices.exception.GlobalException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<String> HandlerGlobalException(GlobalException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
		// implementar informar ao usuário possiveis ações dependendo do erro
	}
}