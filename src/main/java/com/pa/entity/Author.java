package com.pa.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author implements Serializable {
	
	/**
	 * Alex J Costa
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nomeCompleto;
	
	private String nomeCitacao;
	
	private String nroIdCnpq;
	
	public Author(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeCitacao() {
		return nomeCitacao;
	}

	public void setNomeCitacao(String nomeCitacao) {
		this.nomeCitacao = nomeCitacao;
	}

	public String getNroIdCnpq() {
		return nroIdCnpq;
	}

	public void setNroIdCnpq(String nroIdCnpq) {
		this.nroIdCnpq = nroIdCnpq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeCitacao == null) ? 0 : nomeCitacao.hashCode());
		result = prime * result + ((nomeCompleto == null) ? 0 : nomeCompleto.hashCode());
		result = prime * result + ((nroIdCnpq == null) ? 0 : nroIdCnpq.hashCode());
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
		Author other = (Author) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeCitacao == null) {
			if (other.nomeCitacao != null)
				return false;
		} else if (!nomeCitacao.equals(other.nomeCitacao))
			return false;
		if (nomeCompleto == null) {
			if (other.nomeCompleto != null)
				return false;
		} else if (!nomeCompleto.equals(other.nomeCompleto))
			return false;
		if (nroIdCnpq == null) {
			if (other.nroIdCnpq != null)
				return false;
		} else if (!nroIdCnpq.equals(other.nroIdCnpq))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeCitacao;
	}
	
	
}
