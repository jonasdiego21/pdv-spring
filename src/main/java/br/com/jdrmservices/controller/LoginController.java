package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.VIEW_LOGIN;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String Login(@AuthenticationPrincipal User user) {
		if(user != null) {
			return "redirect:/";
		}
		
		return VIEW_LOGIN;
	}
}