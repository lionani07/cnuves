package cf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cf.model.Empresa;

public interface Empresas extends JpaRepository<Empresa, String> {
	
	Optional<Empresa> findByCnpj(String cnpj);

}
