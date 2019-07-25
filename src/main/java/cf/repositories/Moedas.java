package cf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cf.model.Moeda;

public interface Moedas extends JpaRepository<Moeda, Long> {
	
	List<Moeda> findByNomeOrSigla(String nome, String sigla);

}
