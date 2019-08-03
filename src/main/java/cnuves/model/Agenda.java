package cnuves.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import cnuves.model.enums.IndicadorPagamento;

@Entity
public class Agenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Paciente é obrigatório")
	@ManyToOne
	private Paciente paciente;
	
	@ManyToOne
	private Medico medico;
	
	@NotNull(message = "Data da agenda é obrigatório")	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar data;
	
	@NotNull(message = "Hora de inicio é obrigatório")		
	private LocalTime inicio;
	
	@NotNull(message = "Hora de Fim é obrigatório")		
	private LocalTime fim;
	
	@NotNull(message = "Valor é obrigatório")	
	@Digits(integer=6, fraction=2, message="Informe um valor válido")
    @DecimalMin("0.01")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	@NotNull(message = "Indicador de pagamento é obrigatório")
	@Enumerated(EnumType.STRING)
	private IndicadorPagamento indicadorPagamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}	

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFim() {
		return fim;
	}

	public void setFim(LocalTime fim) {
		this.fim = fim;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public IndicadorPagamento getIndicadorPagamento() {
		return indicadorPagamento;
	}

	public void setIndicadorPagamento(IndicadorPagamento indicadorPagamento) {
		this.indicadorPagamento = indicadorPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Interval getInterval() {
		int year = this.data.get(Calendar.YEAR);
		int diaMes = this.data.get(Calendar.DAY_OF_MONTH);
		int mes = this.data.get(Calendar.MONTH);
		DateTime dateTimeInicio = new DateTime(year, mes, diaMes, inicio.getHour(), inicio.getMinute());		
		DateTime dateTimeFim = new DateTime(year, mes, diaMes, fim.getHour(), fim.getMinute());
		return new Interval(dateTimeInicio, dateTimeFim);
	}

	@Override
	public String toString() {
		return "Agenda [id=" + id + ", paciente=" + paciente + ", medico=" + medico + ", data=" + data + ", inicio="
				+ inicio + ", fim=" + fim + ", valor=" + valor + ", indicadorPagamento=" + indicadorPagamento + "]";
	}
	
	
	

}
