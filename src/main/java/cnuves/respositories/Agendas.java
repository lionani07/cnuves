package cnuves.respositories;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cnuves.model.Agenda;
import cnuves.model.Medico;
import cnuves.model.Paciente;

public interface Agendas extends JpaRepository<Agenda, Long> {	
	List<Agenda> findByPacienteAndData(Paciente paciente, Calendar data);
	List<Agenda> findByMedicoAndData(Medico medico, Calendar data);
	
	@Query("SELECT a FROM Agenda a WHERE a.paga = ?1")
	List<Agenda> findAllByEstadoPagamento(boolean pagamento);
	
	@Query("SELECT a FROM Agenda a where a.paciente.nome like %?1% and a.medico.nome like %?2% and a.data = ?3" )
	List<Agenda> findByPacienteAndMedico(String paciente, String medico, Calendar data);
	
}
