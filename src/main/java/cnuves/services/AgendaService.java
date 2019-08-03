package cnuves.services;

import java.util.List;

import org.joda.time.Interval;
import org.joda.time.ReadableInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnuves.exceptions.AgendaMedicoException;
import cnuves.exceptions.AgendaPacienteException;
import cnuves.exceptions.IntervalInvalidException;
import cnuves.model.Agenda;
import cnuves.respositories.Agendas;

@Service
public class AgendaService {
	
	@Autowired
	private Agendas agendas;
	
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
	
	/*
	public boolean existeIntervalxxx(List<Agenda> listagendaBD, Agenda agendaToSave) {			
		for(Agenda agendaBD : listagendaBD) {
			if(agendaToSave.getInicio().plusMinutes(new Long("1")).isAfter(agendaBD.getInicio()) && agendaToSave.getInicio().isBefore(agendaBD.getFim())) {
				return true;
			}
			if(agendaToSave.getFim().plusMinutes(new Long("1")).isAfter(agendaBD.getInicio()) && agendaToSave.getFim().isBefore(agendaBD.getFim())) {
				return true;
			}
		}		
		return false;		
	}
	
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
