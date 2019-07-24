package cf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cf.model.Empresa;

public interface Empresas extends JpaRepository<Empresa, String> {
}
