package cf.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.exceptions.MoedaExisteException;
import cf.model.Moeda;
import cf.repositories.Moedas;

@Service
public class MoedaService {
	
	@Autowired
	private Moedas moedas;
	
	public void save(Moeda moeda) {
		if(!existe(moeda)) {
			moedas.save(moeda);
		}		
	}
	
	private boolean existe(Moeda moeda) {
		List<Moeda> listMoedas = moedas.findByNomeOrSigla(moeda.getNome(), moeda.getSigla());
		for(Moeda m : listMoedas) {
			if(!m.equals(moeda)) {
				if(moeda.getNome().equalsIgnoreCase(m.getNome())) {
					throw new MoedaExisteException("nome", "Moeda com nome " + moeda.getNome() + " existe");					
				}else {
					throw new MoedaExisteException("sigla", "Moeda com sigla " + moeda.getSigla() + " existe");	
				}				
			}
		}
		return false;
	}
	
}
