package br.com.jdrmservices.controller;

import static br.com.jdrmservices.util.Constants.VIEW_LOGIN;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.jdrmservices.PdvBasicoApplication;

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
	
	@ModelAttribute("version")
	public String getVersion() throws IOException {
	    Manifest manif = new Manifest(PdvBasicoApplication.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
	    String version = (String) manif.getMainAttributes().get(Attributes.Name.IMPLEMENTATION_VERSION);
	    return version;
	}
}