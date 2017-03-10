package com.pa.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Orientation implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String natureza;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private OrientationType TipoOrientacao;
	
	@Column
	private String titulo;
	
	@Column
	private String ano;
	
	@Column
	private String idioma;
	
	
	public Orientation(String natureza, OrientationType tipoOrientacao, String titulo, String ano, String idioma) {
		super();
		this.natureza = natureza;
		TipoOrientacao = tipoOrientacao;
		this.titulo = titulo;
		this.ano = ano;
		this.idioma = idioma;
	}

	public OrientationType getTipoOrientacao() {
		return TipoOrientacao;
	}

	public void setTipoOrientacao(OrientationType tipoOrientacao) {
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

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Long getId() {
		return id;
	}
	
	
}
