package br.com.drummond.tio.resoucers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.drummond.tio.model.Compra;
import br.com.drummond.tio.repositories.CompraRepository;

@RestController
@RequestMapping("/compras")
@CrossOrigin
public class CompraResources {

	@Autowired
	private CompraRepository cRepository;

	@GetMapping
	public ResponseEntity<?> coletarCompra() {
		List<Compra> compras = cRepository.findAll();
		if (compras.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sem registros de compras.");
		} else {
			return ResponseEntity.ok(compras);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Compra> receberCompra(@PathVariable int id) {
		Optional<Compra> compra = cRepository.findById(id);
		if (compra.isPresent()) {
			return ResponseEntity.ok(compra.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não existe nenhum registro de compra neste ID.");
		}
	}
	
	
	@GetMapping("/buscar-produto")
	public ResponseEntity<?> pegarArtigosPeloTituloOuArea(@RequestParam("search") String search) {
		List<Compra> compra = cRepository.findByProduto(search);
		if(compra.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não existem produtos registrados com essa procura!");
		}else {
			return ResponseEntity.ok(compra);
		}
	}
	
	
	
	
	@PostMapping
	public ResponseEntity<Compra> registrarCompra(@Valid @RequestBody Compra compra) {
		Compra compraCadastro = cRepository.save(compra);
		return ResponseEntity.status(HttpStatus.CREATED).body(compraCadastro);
	}
	
	

	@PutMapping
	public ResponseEntity<Compra> alterarCompra(@PathVariable Integer id, @Valid @RequestBody Compra compra)
			throws IllegalArgumentException, IllegalAccessException {
		Optional<Compra> compraExistente = cRepository.findById(id);
		if (compraExistente.isPresent()) {
			BeanUtils.copyProperties(compra, compraExistente.get(), "id");
			return ResponseEntity.ok(cRepository.save(compraExistente.get()));
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Compra não foi encontrada.");
		}
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarCompra(@PathVariable Integer id) {
		Optional<Compra> compraExistente = cRepository.findById(id);
		if(compraExistente.isPresent()) {
			cRepository.deleteById(compraExistente.get().getId());
			return ResponseEntity.status(HttpStatus.OK).body("O produto "+ compraExistente.get().getProduto() +" foi excluido !");
		} else {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Compra não encontrada.");
		}
	}

}
