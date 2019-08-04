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
	
	@Query("FROM Agenda WHERE paga = ?1")
	List<Agenda> findAllByEstadoPagamento(boolean pagamento);
	
}
