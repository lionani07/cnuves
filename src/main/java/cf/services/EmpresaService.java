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
		Optional<Empresa> optionalEmpresa = empresas.findById(empresa.getCnpj());		
		if(optionalEmpresa.isPresent()) {
			throw new EmpresaExisteException("Empresa já cadastrada!");
		}else {
			return empresas.save(empresa);
		}
		
	}

	public void delete(String cnpj) {
		Optional<Empresa> optionalEmpresa = empresas.findById(cnpj);
		if(!optionalEmpresa.isPresent()) {
			throw new RuntimeException("Empresa não existe!");
		}
		empresas.delete(optionalEmpresa.get());		
	}

	public Empresa update(Empresa empresa) {
		Optional<Empresa> optionalEmpresa = empresas.findById(empresa.getNome());
		if(optionalEmpresa.isPresent()) {
			if(empresa.equals(optionalEmpresa.get())) {
				return empresas.save(empresa);
			}else {
				throw new EmpresaExisteException("Empresa já cadastrada");
			}
		}else {
			return empresas.save(empresa);
		}
		
	}
	
}
