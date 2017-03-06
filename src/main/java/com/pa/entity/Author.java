package com.pa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nomeCompleto;
	
	@Column
	private String nomeParaCitacao;
	
	@Column
	private String ordemParaAutoria;

	@Column
	private String nroIdCnpq;


	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeParaCitacao() {
		return nomeParaCitacao;
	}

	public void setNomeParaCitacao(String nomeParaCitacao) {
		this.nomeParaCitacao = nomeParaCitacao;
	}

	public String getOrdemParaAutoria() {
		return ordemParaAutoria;
	}

	public void setOrdemParaAutoria(String ordemParaAutoria) {
		this.ordemParaAutoria = ordemParaAutoria;
	}

	public String getNroIdCnpq() {
		return nroIdCnpq;
	}

	public void setNroIdCnpq(String nroIdCnpq) {
		this.nroIdCnpq = nroIdCnpq;
	}

	public Long getId() {
		return id;
	}
	
}
