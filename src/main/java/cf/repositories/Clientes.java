package cf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cf.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByNome(String nome);

}
