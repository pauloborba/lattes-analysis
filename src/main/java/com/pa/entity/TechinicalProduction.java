package com.pa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "techinical_production")
public class TechinicalProduction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String natureza;
	
	@Column
	private String titulo;
	
	@Column
	private String tipo;
	
	@Column
	private String ano;
	
	@Column
	private String idioma;
	
	@Column
	private String nivel;
	
	@Column
	private String finalidade;
	
	@Column
	private String plataforma;
	
	@Column
	private String ambiente;
	
	@Column
	private String disponibilidade;
	
//	@OneToMany(cascade=CascadeType.ALL)
//	private ArrayList<Author> autores;
	
//	@OneToMany(cascade=CascadeType.ALL)
//	private ArrayList<KeyWord> palavrasChaves;
//	
//	@OneToMany(cascade=CascadeType.ALL)
//	private ArrayList<KnowledgeArea> areasDeConhecimento;
//	
//	@OneToMany(cascade=CascadeType.ALL)
//	private ArrayList<ActivitySectors> setoresDeAtividade;
	
	public TechinicalProduction(){}

	public TechinicalProduction(String titulo, String ano, String idioma) {
		super();
		this.titulo = titulo;
		this.ano = ano;
		this.idioma = idioma;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public Long getId() {
		return id;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	

}
