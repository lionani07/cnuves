package cf.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cf.model.Moeda;
import cf.model.TacaCambio;

public interface TacaCambios extends JpaRepository<TacaCambio, Long>{
	
	Optional<TacaCambio> findByMoedaAndData(Moeda moeda, Date data);

}
