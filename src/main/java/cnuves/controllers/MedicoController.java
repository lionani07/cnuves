package cnuves.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnuves.exceptions.MedicoExisteException;
import cnuves.model.Medico;
import cnuves.services.MedicoService;

@Controller
@SessionAttributes("medico")
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;	
	
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("medicos/listadoMedicos");
		mv.addObject("medicos", medicoService.findAll());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(Medico medico) {
		return new ModelAndView("medicos/cadastroMedico");
	}
	
	@PostMapping("/novo")
	public ModelAndView save(@Valid Medico medico, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			return form(medico);
		}
		try {
			medicoService.save(medico);
			status.setComplete();
		} catch (MedicoExisteException e) {
			result.rejectValue("registro", e.getMessage(), e.getMessage());
			return form(medico);
		}
		flash.addFlashAttribute("msgSuccess", "Médico cadastrado com succeso");
		return new ModelAndView("redirect:/medicos/novo");
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("redirect:/medicos");
		try {
			medicoService.delete(id);
			flash.addFlashAttribute("msgInfo", "Médico deletado com succeso");
		} catch (Exception e) {
			flash.addFlashAttribute("msgError", e.getMessage());
		}
		return mv;
		
	}

}
