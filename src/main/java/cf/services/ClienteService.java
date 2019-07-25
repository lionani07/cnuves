package cf.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.exceptions.ClienteExisteException;
import cf.model.Cliente;
import cf.repositories.Clientes;

@Service
public class ClienteService {
	
	@Autowired
	private Clientes clientes;
	
	public Cliente save(Cliente cliente) {
		Optional<Cliente> optionalCliente = clientes.findByNome(cliente.getNome());
		if(optionalCliente.isPresent()) {
			throw new ClienteExisteException("Cliente já existe");
		}
		return clientes.save(cliente);				
	}
	
	public Cliente update(Cliente cliente) {
		Optional<Cliente> optionalCliente = clientes.findByNome(cliente.getNome());
		if(!optionalCliente.isPresent()) {
			return clientes.save(cliente);
		}else {
			if(cliente.equals(optionalCliente.get())) {
				return clientes.save(cliente);
			}
			throw new ClienteExisteException("Cliente já existe");
		}
		
	}

	public void delete(Long id) {
		Optional<Cliente> optionalCliente = clientes.findById(id);
		if(!optionalCliente.isPresent()) {
			throw new RuntimeException("Cliente não existe");
		}
		clientes.delete(optionalCliente.get());		
	}
	
	
	
}
