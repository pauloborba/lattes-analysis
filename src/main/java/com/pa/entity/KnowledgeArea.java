package com.pa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class KnowledgeArea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nomeGrandeAreaDoConhecimento;
	
	@Column
	private String nomeAreaDoConhecimento;
	
	@Column
	private String nomeSubAreaDoConhecimento;
	
	@Column
	private String nomeDaEspecialidade;

	public String getNomeGrandeAreaDoConhecimento() {
		return nomeGrandeAreaDoConhecimento;
	}

	public void setNomeGrandeAreaDoConhecimento(String nomeGrandeAreaDoConhecimento) {
		this.nomeGrandeAreaDoConhecimento = nomeGrandeAreaDoConhecimento;
	}

	public String getNomeAreaDoConhecimento() {
		return nomeAreaDoConhecimento;
	}

	public void setNomeAreaDoConhecimento(String nomeAreaDoConhecimento) {
		this.nomeAreaDoConhecimento = nomeAreaDoConhecimento;
	}

	public String getNomeSubAreaDoConhecimento() {
		return nomeSubAreaDoConhecimento;
	}

	public void setNomeSubAreaDoConhecimento(String nomeSubAreaDoConhecimento) {
		this.nomeSubAreaDoConhecimento = nomeSubAreaDoConhecimento;
	}

	public String getNomeDaEspecialidade() {
		return nomeDaEspecialidade;
	}

	public void setNomeDaEspecialidade(String nomeDaEspecialidade) {
		this.nomeDaEspecialidade = nomeDaEspecialidade;
	}

	public Long getId() {
		return id;
	}
	

	
}
