package br.com.drummond.tio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.drummond.tio.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	
	List<Compra> findByProdutoContaining(String produto);
	

}
