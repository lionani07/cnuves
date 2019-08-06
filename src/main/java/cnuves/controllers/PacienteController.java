package cnuves.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnuves.exceptions.InvalidFieldExcpetion;
import cnuves.model.Paciente;
import cnuves.model.enums.Sexo;
import cnuves.services.PacienteService;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("pacientes/listadoPacientes");
		mv.addObject("pacientes", pacienteService.findAll());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(Paciente paciente) {
		ModelAndView mv = new ModelAndView("pacientes/cadastroPaciente");
		mv.addObject("sexos", Sexo.values());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView save(@Valid Paciente paciente, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return form(paciente);
		}
		try {
			pacienteService.save(paciente);
		} catch (InvalidFieldExcpetion e) {
			result.rejectValue(e.getField(), e.getMessage(), e.getMessage());
			return form(paciente);
		}
		flash.addFlashAttribute("msgSuccess", "Paciente cadastrado com succeso");
		return new ModelAndView("redirect:/pacientes/novo");
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("redirect:/pacientes");
		try {
			pacienteService.delete(id);
			flash.addFlashAttribute("msgInfo", "Paciente deletado com succeso");
		} catch (Exception e) {
			flash.addFlashAttribute("msgError", e.getMessage());
		}
		return mv;		
	}	
	

}
