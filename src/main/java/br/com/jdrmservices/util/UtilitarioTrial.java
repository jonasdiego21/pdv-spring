package br.com.jdrmservices.util;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class UtilitarioTrial {
	
	private static Path path = Paths.get("C:\\Users\\Default\\app.log");

	public static boolean isExpired() {
		
		if(!Files.exists(path)) {
			writeFile(path);
		}

		return LocalDateTime.now().isAfter(LocalDateTime.parse(readFile(path))) ? true : false;
		//return LocalDateTime.parse(readFile(path)).isBefore(LocalDateTime.now()) ? true : false;
	}

	public static void writeFile(Path path) {		
		try (OutputStream outputStream = Files.newOutputStream(path)) {
			outputStream.write(LocalDateTime.now().plusDays(30).toString().getBytes());
			outputStream.flush();
		} catch (Exception e) {
			System.out.println("Não foi possível iniciar o modo teste (trial).");
		}	
	}
	
	public static String readFile(Path path) {
		String lines = null;
		
		try {
			lines = Files.readAllLines(path).get(0);
		} catch (Exception e) {
			System.out.println("Erro ao ler o arquivo.");
		}
		
		return lines;	
	}
}
