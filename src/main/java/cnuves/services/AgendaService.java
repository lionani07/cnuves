package cnuves.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.Interval;
import org.joda.time.ReadableInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnuves.exceptions.AgendaMedicoException;
import cnuves.exceptions.AgendaNaoExisteException;
import cnuves.exceptions.AgendaPacienteException;
import cnuves.exceptions.IntervalInvalidException;
import cnuves.model.Agenda;
import cnuves.respositories.Agendas;

@Service
public class AgendaService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private Agendas agendas;
	
	public List<Agenda> findAllSort(){		
		List<Agenda> list = agendas.findAll();
		Collections.sort(list);		
		return list;
	}
	
	public List<Agenda> findAllEstadoPagamento(boolean estadoPagamento){		
		List<Agenda> list = agendas.findAllByEstadoPagamento(estadoPagamento);
		Collections.sort(list);		
		return list;
	}
	
	
	public List<Agenda> filtrar(Agenda agendaCriteria, String estadoPagamento) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);        
        
        Predicate data= null, paga= null, medico = null, paciente = null;
        
        Root<Agenda> agenda = cq.from(Agenda.class); 
        List<Predicate> predicates = new ArrayList<>();
        
        if(agendaCriteria.getData()!=null) {
        	data = cb.equal(agenda.get("data"), agendaCriteria.getData());
        	predicates.add(data);
        } 
        if(estadoPagamento.length()>0) {
        	paga = cb.equal(agenda.get("paga"), new Long(estadoPagamento));
        	predicates.add(paga);
        } 
        if(agendaCriteria.getMedico().getNome().length()>0) {
        	medico = cb.like(agenda.get("medico").get("nome"), "%" + agendaCriteria.getMedico().getNome() + "%"); 
        	predicates.add(medico);
        }
        if(agendaCriteria.getPaciente().getNome().length()>0) {
        	paciente = cb.like(agenda.get("paciente").get("nome"), "%" + agendaCriteria.getPaciente().getNome() + "%");  
        	predicates.add(paciente);
        }         
        cq.where(predicates.toArray(new Predicate[0]));        
        return em.createQuery(cq).getResultList();
       
	}
	
	public void save(Agenda agenda) {
		if(agenda.getFim().isBefore(agenda.getInicio().plusMinutes(new Long("29"))) || agenda.getFim().equals(agenda.getInicio().plusMinutes(new Long("29")))) {
			throw new IntervalInvalidException("Intervalo inválido, escolha mímimo 30 minutos");
		}
		List<Agenda> agendasBDCliente = agendas.findByPacienteAndData(agenda.getPaciente(), agenda.getData());
		if(agenda.getMedico()!=null) {
			List<Agenda> agendasBDMedico = agendas.findByMedicoAndData(agenda.getMedico(), agenda.getData());
			if(existInertval(agendasBDMedico, agenda)) {
				throw new AgendaMedicoException("Médico já agendando neste horario");
			}
		}		
		if(existInertval(agendasBDCliente, agenda)) {
			throw new AgendaPacienteException("Cliente já agendando neste horario");
		}		
		agendas.save(agenda);
	}	
	
	private boolean existInertval(List<Agenda> agendasBD, Agenda agendaToSave) {
		for(Agenda agendaBD : agendasBD) {
			if(overlapsIntervals(agendaBD.getInterval(), agendaToSave.getInterval())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean overlapsIntervals(Interval intervalDB, Interval intervalToSave) {
		ReadableInterval readableIntervalToSave = intervalToSave;		
		return intervalDB.overlaps(readableIntervalToSave);
	}

	public void efectuarPagamento(Long id) {
		Optional<Agenda> optionalAgenda = agendas.findById(id);
		if(!optionalAgenda.isPresent()) {
			throw new AgendaNaoExisteException("Agenda não existe");
		}
		optionalAgenda.get().setPaga(true);
		agendas.save(optionalAgenda.get());
	}

	public List<Agenda> filtar(Agenda agenda) {		
		return agendas.findByPacienteAndMedico(agenda.getPaciente().getNome(), agenda.getMedico().getNome(), agenda.getData());		
	}
	
	/*	
	public boolean existeInterval(List<Agenda> listagendaBD, Agenda agendaToSave) {			
		for(Agenda agendaBD : listagendaBD) {
			if(agendaToSave.getInicio().plusMinutes(new Long("1")).isAfter(agendaBD.getInicio()) && agendaToSave.getInicio().isBefore(agendaBD.getFim())) {
				return true;
			}
			if(agendaToSave.getFim().plusMinutes(new Long("1")).isAfter(agendaBD.getInicio()) && agendaToSave.getFim().isBefore(agendaBD.getFim())) {
				return true;
			}
			
			if(agendaBD.getInicio().isAfter(agendaToSave.getInicio()) && agendaBD.getInicio().isBefore(agendaToSave.getFim())) {
				return true;
			}
			if(agendaBD.getFim().isAfter(agendaToSave.getInicio()) && agendaBD.getFim().isBefore(agendaToSave.getFim())) {
				return true;
			}
			
		}		
		return false;		
	}
	*/
	
}
