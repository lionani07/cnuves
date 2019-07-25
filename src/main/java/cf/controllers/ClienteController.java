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

import cf.exceptions.ClienteExisteException;
import cf.model.Cliente;
import cf.repositories.Clientes;
import cf.services.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("cliente/listadoClientes");
		mv.addObject("clientes", clientes.findAll());
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView form(Cliente cliente) {
		return  new ModelAndView("cliente/cadastroCliente");
	}
	
	@PostMapping("/novo")
	public ModelAndView add(@Valid Cliente cliente, BindingResult result, RedirectAttributes flash) {		
		if(result.hasErrors()) {
			return form(cliente);
		}
		try {
			clienteService.save(cliente);
		} catch (ClienteExisteException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return form(cliente);
		}
		flash.addFlashAttribute("msgSuccess", cliente.getNome() + " cadastrado com successo");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("cliente/editCliente");
		Optional<Cliente> optionalCliente = clientes.findById(id);
		if(optionalCliente.isPresent()) {
			mv.addObject("cliente", optionalCliente.get());
		}else {
			mv.setViewName("redirect:/clientes");
		}
		return mv;
	}
	
	@PostMapping("/update/{id}")
	public ModelAndView update(@PathVariable Long id, @Valid Cliente cliente, BindingResult result, RedirectAttributes flash) {		
		ModelAndView mv = new ModelAndView("cliente/editCliente");
		if(result.hasErrors()) {
			return mv;
		}
		try {
			clienteService.update(cliente);
		} catch (ClienteExisteException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());			
			return mv;
		}
		flash.addFlashAttribute("msgInfo", "Cliente editado com successo");
		return new ModelAndView("redirect:/clientes");
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes flash) {
		ModelAndView mv = new ModelAndView("redirect:/clientes");
		try {
			clienteService.delete(id);
			flash.addFlashAttribute("msgInfo", "Cliente deletado com successo");
		} catch (Exception e) {
			flash.addFlashAttribute("msgError", e.getMessage());
		}
		return mv;
	}

}
