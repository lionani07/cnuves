package cnuves.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cnuves.model.Medico;

public interface Medicos extends JpaRepository<Medico, Long> {
	
	Optional<Medico> findByRegistro(String registro);

}
