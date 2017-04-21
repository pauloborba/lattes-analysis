package com.pa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Chapter implements Serializable, Comparable<Chapter>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String titulo;
	
	@Column 
	private Integer ano;
	
	@Column
	private String tituloDoLivro;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Author> autores;

	public Chapter(){}
	
	public Chapter(String titulo, Integer ano, String tituloDoLivro, List<Author> autores) {
		super();
		this.titulo = titulo;
		this.ano = ano;
		this.tituloDoLivro = tituloDoLivro;
		this.autores = autores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public String getTituloDoLivro() {
		return tituloDoLivro;
	}

	public void setTituloDoLivro(String tituloDoLivro) {
		this.tituloDoLivro = tituloDoLivro;
	}

	public List<Author> getAutores() {
		return autores;
	}

	public void setAutores(List<Author> autores) {
		this.autores = autores;
	}
	
	@Override
	public int compareTo(Chapter o) {
		if (this.ano < o.getAno()) {
			return -1;
		}
		else if (this.ano > o.getAno()) {
			return 1;
		}
		return 0;
	}

}
