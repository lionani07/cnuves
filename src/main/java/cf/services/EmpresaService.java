package cf.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.exceptions.EmpresaExisteException;
import cf.model.Empresa;
import cf.repositories.Empresas;

@Service
public class EmpresaService {
	
	@Autowired
	private Empresas empresas;
	
	public Empresa save(Empresa empresa) {
		Optional<Empresa> optionalEmpresa = empresas.findByCnpj(empresa.getCnpj());
		if(optionalEmpresa.isPresent()) {
			throw new EmpresaExisteException("Empresa já cadastrada");
		}
		return empresas.save(empresa);
	}
}
