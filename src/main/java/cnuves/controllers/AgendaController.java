package cnuves.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnuves.exceptions.AgendaMedicoException;
import cnuves.exceptions.AgendaPacienteException;
import cnuves.exceptions.IntervalInvalidException;
import cnuves.exceptions.MedicoExisteException;
import cnuves.model.Agenda;
import cnuves.model.enums.IndicadorPagamento;
import cnuves.respositories.Agendas;
import cnuves.respositories.Medicos;
import cnuves.respositories.Pacientes;
import cnuves.services.AgendaService;

@Controller
@RequestMapping("/agendas")
public class AgendaController {
	
	@Autowired
	private Medicos medicos;	
	@Autowired
	private Pacientes pacientes;
	
	@Autowired
	private Agendas agendas;
	
	@Autowired
	private AgendaService agendaService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendas.findAll());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(Agenda agenda) {
		ModelAndView mv = new ModelAndView("agendas/cadastroAgenda");
		mv.addObject("medicos", medicos.findAll());
		mv.addObject("pacientes", pacientes.findAll());
		mv.addObject("indicadoresPagamentos", IndicadorPagamento.values());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView save(@Valid Agenda agenda, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()){
			return form(agenda);
		}
		try {
			agendaService.save(agenda);
		} catch (IntervalInvalidException e) {
			result.rejectValue("fim", e.getMessage(), e.getMessage());
			return form(agenda);
		}catch (AgendaPacienteException e) {
			result.rejectValue("paciente", e.getMessage(), e.getMessage());
			return form(agenda);
		}catch (AgendaMedicoException e) {
			result.rejectValue("medico", e.getMessage(), e.getMessage());
			return form(agenda);
		}		
		flash.addFlashAttribute("msgSuccess", "Agenda criada com succeso");
		return new ModelAndView("redirect:/agendas/novo");
	}
	
	
}
