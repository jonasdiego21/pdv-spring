package br.com.jdrmservices.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeracaoSenha {

public static void main(String[] args) {		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));		
	}
}
