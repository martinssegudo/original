package br.com.controlePessoa.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "TB_PESSOA")
@GenericGenerator(name = "SEQ_PESSOA", 
				  strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", 
				  parameters = {
						  @Parameter(name = "sequence_name", value = "SEQ_PESSOA"), 
						  @Parameter(name = "initial_value", value = "1"),
						  @Parameter(name = "increment_size", value = "1") 
				  })
public class Pessoa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3364293570265347676L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PESSOA")
	@Column(name = "ID_PESSOA")
	private Long id;

	@Column(name = "DS_NOME")
	private String nome;

	@Column(name = "NM_CPF")
	private String cpf;

	public Pessoa(String nome, String cpf) {
		this.setNome(nome);
		this.setCpf(cpf);
	}
	
	public Pessoa(Long id, String nome, String cpf) {
		this.setId(id);
		this.setNome(nome);
		this.setCpf(cpf);
	}
	
	public Pessoa() {
		
	}
	
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
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
