package cnuves.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnuves.exceptions.InvalidFieldExcpetion;
import cnuves.exceptions.PacienteExisteException;
import cnuves.model.Paciente;
import cnuves.respositories.Pacientes;

@Service
public class PacienteService {
	
	@Autowired
	private Pacientes pacientes;
	
	public List<Paciente> findAll() {
		return pacientes.findAll();
	}
	
	public void save(Paciente paciente) {
		if(!paciente.getTelefone().isEmpty()) {
			if(!paciente.isTelefoneValid()) {
				throw new InvalidFieldExcpetion("telefone", "Telefone inválido");
			}
		}
		if(!paciente.getCpf().isEmpty()) {
			if(!paciente.isCPFValid()) {
				throw new InvalidFieldExcpetion("cpf", "CPF inválido");
			}
			Optional<Paciente> optionalPaciente = pacientes.findByCpf(paciente.getCpf());
			if(optionalPaciente.isPresent()) {
				throw new PacienteExisteException("Paciente com cpf " + paciente.getCpf() + " já existe");
			}
			pacientes.save(paciente);
		}
		pacientes.save(paciente);		
	}

	public void delete(Long id) {
		Optional<Paciente> optionalPaciente = pacientes.findById(id);
		if(optionalPaciente.isPresent()) {
			pacientes.delete(optionalPaciente.get());
		}else {
			throw new RuntimeException("Paciente com id " + id + " ñao existe");
		}
	}

}
