package cnuves.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnuves.exceptions.AgendaMedicoException;
import cnuves.exceptions.AgendaPacienteException;
import cnuves.exceptions.IntervalInvalidException;
import cnuves.model.Agenda;
import cnuves.model.enums.IndicadorPagamento;
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
	private AgendaService agendaService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendaService.findAllSort());
		return mv;
	}
	
	@PostMapping("/filtrar")
	public ModelAndView test(Agenda agenda, String estadoPagamento) {			
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendaService.filtrar(agenda, estadoPagamento));
		return mv;
	}
	
	@GetMapping("/pagas")
	public ModelAndView pagas() {
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendaService.findAllEstadoPagamento(true));
		return mv;
	}
	
	@GetMapping("/pendentesPagamento")
	public ModelAndView pendentesPagamento() {
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendaService.findAllEstadoPagamento(false));
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
	
	@GetMapping("/efetuarPagamento/{id}")
	public ResponseEntity<?> efectuarPagamento(@PathVariable Long id) {		
		try {
			agendaService.efectuarPagamento(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
		return ResponseEntity.ok("Pagamento efectuado com succeso");
	}
	
	/*
	@PostMapping("/filtrar")
	public ModelAndView filtrar(Agenda agenda, @RequestParam String estadoPagamento) {	
		System.out.println("estado Pagamento" + estadoPagamento);
		ModelAndView mv = new ModelAndView("agendas/listadoAgendas");
		mv.addObject("agendas", agendaService.filtar(agenda));		
		return mv;
	}
	*/
	
	/*
	@GetMapping("/efetuarPagamento/{id}")
	public ModelAndView efectuarPagamento(@PathVariable Long id, RedirectAttributes flash) {
		try {
			agendaService.efectuarPagamento(id);
		} catch (Exception e) {
			flash.addFlashAttribute("msgError", e.getMessage());
		}
		flash.addFlashAttribute("msgInfo", "Pagamento efetuado com sucesso");
		return new ModelAndView("redirect:/agendas");
		
	}
	*/
	
}
