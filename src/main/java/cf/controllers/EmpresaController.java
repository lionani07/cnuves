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

import cf.exceptions.EmpresaExisteException;
import cf.model.Empresa;
import cf.services.EmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
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
		flash.addFlashAttribute("msgSucesso", "Empresa " + empresa.getNome() + " cadastrada com sucesso");
		ModelAndView mv = new ModelAndView("redirect:/empresas/novo");
		return mv;
	}
	
	@GetMapping()
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("empresa/listadoEmpresas");
		return mv;
	}
	
	
}
