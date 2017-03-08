package com.pa.entity;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Orientation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String natureza;
	
	@Column
	private String TipoOrientacao;
	
	@Column
	private String titulo;
	
	@Column
	private String ano;
	
	@Column
	private String pais;
	
	@Column
	private String idioma;
	
	@Column
	private String homePage;
	
	@Column
	private String flagRelevancia;
	
	@Column
	private String doi;
	
	@Column
	private String tituloIngles;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<KnowledgeArea> areasDeConhecimento;
	
	

	public ArrayList<KnowledgeArea> getAreasDeConhecimento() {
		return areasDeConhecimento;
	}

	public void setAreasDeConhecimento(ArrayList<KnowledgeArea> areasDeConhecimento) {
		this.areasDeConhecimento = areasDeConhecimento;
	}

	public String getTipoOrientacao() {
		return TipoOrientacao;
	}

	public void setTipoOrientacao(String tipoOrientacao) {
		TipoOrientacao = tipoOrientacao;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getFlagRelevancia() {
		return flagRelevancia;
	}

	public void setFlagRelevancia(String flagRelevancia) {
		this.flagRelevancia = flagRelevancia;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getTituloIngles() {
		return tituloIngles;
	}

	public void setTituloIngles(String tituloIngles) {
		this.tituloIngles = tituloIngles;
	}

	public Long getId() {
		return id;
	}
	
	
}
