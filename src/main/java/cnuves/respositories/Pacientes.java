package cnuves.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cnuves.model.Paciente;

public interface Pacientes extends JpaRepository<Paciente, Long> {
	
	Optional<Paciente> findByCpf(String cpf);

}
