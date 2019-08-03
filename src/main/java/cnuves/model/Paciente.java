package cnuves.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import cnuves.model.enums.Sexo;

@Entity
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar dataNascimento;	
	
	
	
	private String telefone;
	private String cpf;
	
	@OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
	private List<Agenda> agendas = new ArrayList<Agenda>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}	
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
		Paciente other = (Paciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public boolean isCPFValid() {
		return removerCaracteres().length()==11;		
	}
	
	public boolean isTelefoneValid() {
		return removerCarateresTelefone().length()==11;
	}
	
	private String removerCaracteres(){ 
		String cpfValidar = this.cpf.replace("-","");		
		cpfValidar = cpfValidar.replace(".","");
		return cpfValidar;
     }
	
	private String removerCarateresTelefone() {			
		String telfValidar = this.telefone.replace(".", "");
		telfValidar = telfValidar.replace("(", "");
		telfValidar = telfValidar.replace(")", "");
		telfValidar = telfValidar.replace(" ", "");
		return telfValidar;
	}

}
