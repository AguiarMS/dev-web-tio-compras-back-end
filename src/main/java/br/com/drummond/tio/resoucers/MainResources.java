package br.com.drummond.tio.resoucers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainResources {
	
	@GetMapping
	public ResponseEntity<String> apresentacao() {
		return ResponseEntity.ok("TIO 6° semestre desenvolvimento API Ciencia da computação / Sistemas de Informação");
	}
}