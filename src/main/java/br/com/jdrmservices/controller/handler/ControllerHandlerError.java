package br.com.jdrmservices.controller.handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerHandlerError implements ErrorController {

	@RequestMapping("/error")
	public String handlerError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {//(status != null) ou apenas (status)
			Integer statusCode = Integer.valueOf(status.toString());
			
			if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return "/400";
			} else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return "/403";
			} else if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "/404";
			} else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "/500";
			} else if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return "/505";
			} else if(statusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
				return "/504";
			} 
		} 
		
		return "error";
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}