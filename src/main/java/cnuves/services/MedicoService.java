package cnuves.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnuves.exceptions.MedicoExisteException;
import cnuves.model.Medico;
import cnuves.respositories.Medicos;

@Service
public class MedicoService {
	
	@Autowired
	private Medicos medicos;
	
	public List<Medico> findAll() {
		return medicos.findAll();
	}
	
	public void save(Medico medico) {
		if(medico.getRegistro()!=null) {
			Optional<Medico> optionalMedico = medicos.findByRegistro(medico.getRegistro());
			if(optionalMedico.isPresent()) {
				throw new MedicoExisteException("Registro " + medico.getRegistro() + " já cadastrado");
			}
			medicos.save(medico);
		}
		medicos.save(medico);
		
	}

	public void delete(Long id) {
		Optional<Medico> optionalMedico = medicos.findById(id);
		if(optionalMedico.isPresent()) {
			if(!optionalMedico.get().getAgendas().isEmpty()) {
				throw new RuntimeException("Error ao deletar Médico, existem agendas asociadas");
			}
			medicos.delete(optionalMedico.get());
		}else {
			throw new RuntimeException("Médico com id " + id + " ñao existe");
		}
		
	}

	public void update(@Valid Medico medico) {
		if(medico.getRegistro()!=null) {
			Optional<Medico> optionalMedico = medicos.findByRegistro(medico.getRegistro());
			if(optionalMedico.isPresent() && !optionalMedico.get().equals(medico)) {
				throw new MedicoExisteException("Registro " + medico.getRegistro() + " já cadastrado");
			}
			medicos.save(medico);
		}
		medicos.save(medico);
		
	}

}
