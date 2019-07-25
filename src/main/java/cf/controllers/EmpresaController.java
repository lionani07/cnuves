package cf.controllers;

import java.util.Optional;

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

import cf.exceptions.EmpresaExisteException;
import cf.model.Empresa;
import cf.repositories.Empresas;
import cf.services.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private Empresas empresas;
	
	@GetMapping("/novo")
	public ModelAndView form(Empresa empresa) {
		ModelAndView mv = new ModelAndView("empresa/cadastroEmpresa");
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView add(@Valid Empresa empresa, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return form(empresa);
		}
		try {
			empresaService.save(empresa);			
		} catch (EmpresaExisteException e) {			
			result.rejectValue("cnpj", e.getMessage(), e.getMessage());			
			return form(empresa);
		}	
		flash.addFlashAttribute("msgSuccess", "Empresa " + empresa.getNome() + " cadastrada com sucesso");
		ModelAndView mv = new ModelAndView("redirect:/empresas/novo");
		return mv;
	}
	
	@GetMapping()
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("empresa/listadoEmpresas");
		mv.addObject("empresas", empresas.findAll());
		return mv;
	}
	
	@GetMapping("/delete/{cnpj}")
	public ModelAndView delete(@PathVariable String cnpj, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("redirect:/empresas");
		try {
			empresaService.delete(cnpj);
			flash.addFlashAttribute("msgInfo", "Empresa deletada com successo");
		} catch (Exception e) {
			flash.addFlashAttribute("msgError", e.getMessage());
		}		
		return mv;
	}
	
	@GetMapping("/edit/{cnpj}")
	public ModelAndView edit(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView("redirect:/empresas");
		Optional<Empresa> optionalEmpresa = empresas.findById(cnpj);
		if(optionalEmpresa.isPresent()) {
			mv.addObject("empresa", optionalEmpresa.get());
			mv.setViewName("/empresa/editEmpresa");			
		}
		return mv;
	}
	
	@PostMapping("/update")
	public ModelAndView update(@Valid Empresa empresa, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) {
			return new ModelAndView("/empresa/editEmpresa");
		}
		try {
			empresaService.update(empresa);
		} catch (EmpresaExisteException e) {
			result.rejectValue("cnpj", e.getMessage(), e.getMessage());
			return new ModelAndView("/empresa/editEmpresa");
		}
		flash.addFlashAttribute("msgInfo", "Empresa editada com succeso");
		return new ModelAndView("redirect:/empresas");
	}
	
	
}
