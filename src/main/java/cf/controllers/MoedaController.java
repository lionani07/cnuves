package cf.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cf.exceptions.MoedaExisteException;
import cf.model.Moeda;
import cf.repositories.Moedas;
import cf.services.MoedaService;

@Controller
@RequestMapping("/moedas")
public class MoedaController {
	
	@Autowired
	private MoedaService moedaService;
	
	@Autowired
	private Moedas moedas;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("moeda/listadoMoeda");
		mv.addObject("moedas", moedas.findAll());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(Moeda moeda) {
		return new ModelAndView("moeda/cadastroMoeda");
	}
	
	@PostMapping("/novo")
	public ModelAndView save(@Valid Moeda moeda, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return form(moeda);
		}
		try {
			moedaService.save(moeda);
		} catch (MoedaExisteException e) {			
			result.rejectValue(e.getField(), e.getMsg(), e.getMsg());
			return form(moeda);
		}
		flash.addFlashAttribute("msgSuccess", "Moeda cadastrada com successo");
		return new ModelAndView("redirect:/moedas/novo");
	}
	
}
