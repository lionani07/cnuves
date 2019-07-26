package cf.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.exceptions.TacaCambioExisteException;
import cf.model.Moeda;
import cf.model.TacaCambio;
import cf.repositories.Moedas;
import cf.repositories.TacaCambios;

@Service
public class TacaCambioService {
	
	@Autowired
	private TacaCambios tacaCambios;
	@Autowired
	private Moedas moedas;
	
	public void save(TacaCambio tacaCambio) {
		Optional<Moeda> moeda = moedas.findById(tacaCambio.getMoeda().getId());		
		Optional<TacaCambio> optionalTaca = tacaCambios.findByMoedaAndData(moeda.get(), tacaCambio.getData());		
		if(optionalTaca.isPresent()) {
			throw new TacaCambioExisteException("Taça de cambio já cadastrada!");
		}
		tacaCambios.save(tacaCambio);
	}

}
