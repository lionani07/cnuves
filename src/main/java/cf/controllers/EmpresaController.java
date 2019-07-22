package cf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
	
	@GetMapping("/novo")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("empresa/cadastroEmpresa");
		return mv;
	}
}
