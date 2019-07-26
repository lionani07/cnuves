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

import cf.exceptions.TacaCambioExisteException;
import cf.model.TacaCambio;
import cf.repositories.Moedas;
import cf.services.MoedaService;
import cf.services.TacaCambioService;

@Controller
@RequestMapping("/tacaCambios")
public class TacaCambioController {
	
	@Autowired
	private Moedas moedas;
	
	@Autowired
	private TacaCambioService tacaCambioService;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/tacaCambio/listadoTacaCambios");
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(TacaCambio tacaCambio) {
		ModelAndView mv = new ModelAndView("/tacaCambio/cadastroTacaCambio");
		mv.addObject("moedas", moedas.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView save(@Valid TacaCambio tacaCambio, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return form(tacaCambio);
		}
		try {
			tacaCambioService.save(tacaCambio);
		} catch (TacaCambioExisteException e) {
			
		}
		
		return new ModelAndView("redirect:/tacaCambios/novo");
	}
	
	
}
