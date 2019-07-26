package cf.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TacaCambio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Data é obrigatório")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;
	
	
	@NotNull(message = "Valor e obrigatório")
	@Min(value = 1, message = "Valor e obrigatório")
	private BigDecimal valor;
	
	@NotNull(message = "Moeda é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moeda_id")
	private Moeda moeda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((moeda == null) ? 0 : moeda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TacaCambio other = (TacaCambio) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (moeda == null) {
			if (other.moeda != null)
				return false;
		} else if (!moeda.equals(other.moeda))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TacaCambio [id=" + id + ", data=" + data + ", valor=" + valor + ", moeda=" + moeda.getNome() + "]";
	}
	
	
	

}
