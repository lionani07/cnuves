package cnuves.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cnuves.exceptions.MedicoExisteException;
import cnuves.model.Medico;
import cnuves.respositories.Medicos;
import cnuves.services.MedicoService;

@Controller
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;	
	
	@Autowired
	private Medicos medicos;
	
	
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
	public ModelAndView save(@Valid Medico medico, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return form(medico);
		}
		try {
			medicoService.save(medico);			
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
	
	@GetMapping("/edit/{id}")
	public ModelAndView showUpdateForm(@PathVariable Long id, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("medicos/editMedico");
		Optional<Medico> optionalMedico = medicos.findById(id);
		if(optionalMedico.isPresent()) {
			mv.addObject("medico", optionalMedico.get());
			return mv;
		}
		flash.addFlashAttribute("msgError", "Medico com id: " + id +"não existe");
		return new ModelAndView("redirect:/medicos");		
	}
	
	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid Medico medico, BindingResult result, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("medicos/editMedico");
		if(result.hasErrors()) {
			return mv;
		}
		try {
			medicoService.update(medico);			
		} catch (MedicoExisteException e) {
			result.rejectValue("registro", e.getMessage(), e.getMessage());
			return mv;
		}
		flash.addFlashAttribute("msgInfo", "Médico editado com succeso");
		return new ModelAndView("redirect:/medicos");
		
	}

}
