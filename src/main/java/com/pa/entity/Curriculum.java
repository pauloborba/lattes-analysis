package com.pa.entity;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Curriculum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nomeCompleto;
	
	@Column
	private String nomeEmCitacoesBibliograficas;
	
	@Column
	private String nacionalidade;
	
	@Column
	private String paisDeNascimento;
	
	@Column
	private String ufNascimento;
	
	@Column
	private String cidadeNascimento;
	
	@Column
	private String permissaoDeDivulgacao;
	
	@Column
	private String dataFalecimento;
	
	@Column
	private String siglaPaisNascimento;
	
	@Column
	private String paisDeNacionalidade;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<Orientation> orientations;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<PaperPublished> paperPublished;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<WorkInEvents> workInEvents;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<BookChapter> booksAndChaptersBibliographicProduction;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<TechinicalProduction> techinicalProductions;
	
	
	public ArrayList<TechinicalProduction> getTechinicalProductions() {
		return techinicalProductions;
	}

	public void setTechinicalProductions(ArrayList<TechinicalProduction> techinicalProductions) {
		this.techinicalProductions = techinicalProductions;
	}

	public ArrayList<BookChapter> getBooksAndChaptersBibliographicProduction() {
		return booksAndChaptersBibliographicProduction;
	}

	public void setBooksAndChapters(
			ArrayList<BookChapter> booksAndChaptersBibliographicProduction) {
		this.booksAndChaptersBibliographicProduction = booksAndChaptersBibliographicProduction;
	}

	public ArrayList<PaperPublished> getPaperPublished() {
		return paperPublished;
	}

	public void setPaperPublished(ArrayList<PaperPublished> paperPublished) {
		this.paperPublished = paperPublished;
	}

	public ArrayList<WorkInEvents> getWorkInEvents() {
		return workInEvents;
	}

	public void setWorkInEvents(ArrayList<WorkInEvents> workInEvents) {
		this.workInEvents = workInEvents;
	}

	public ArrayList<Orientation> getOrientations() {
		return orientations;
	}

	public void setOrientations(ArrayList<Orientation> orientations) {
		this.orientations = orientations;
	}

	public String getNome_completo() {
		return nomeCompleto;
	}

	public void setNome_completo(String nome_completo) {
		this.nomeCompleto = nome_completo;
	}

	public String getNome_em_citacoes_bibliograficas() {
		return nomeEmCitacoesBibliograficas;
	}

	public void setNome_em_citacoes_bibliograficas(String nome_em_citacoes_bibliograficas) {
		this.nomeEmCitacoesBibliograficas = nome_em_citacoes_bibliograficas;
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeEmCitacoesBibliograficas() {
		return nomeEmCitacoesBibliograficas;
	}

	public void setNomeEmCitacoesBibliograficas(String nomeEmCitacoesBibliograficas) {
		this.nomeEmCitacoesBibliograficas = nomeEmCitacoesBibliograficas;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getPaisDeNascimento() {
		return paisDeNascimento;
	}

	public void setPaisDeNascimento(String paisDeNascimento) {
		this.paisDeNascimento = paisDeNascimento;
	}

	public String getUfNascimento() {
		return ufNascimento;
	}

	public void setUfNascimento(String ufNascimento) {
		this.ufNascimento = ufNascimento;
	}

	public String getCidadeNascimento() {
		return cidadeNascimento;
	}

	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}

	public String getPermissaoDeDivulgacao() {
		return permissaoDeDivulgacao;
	}

	public void setPermissaoDeDivulgacao(String permissaoDeDivulgacao) {
		this.permissaoDeDivulgacao = permissaoDeDivulgacao;
	}

	public String getDataFalecimento() {
		return dataFalecimento;
	}

	public void setDataFalecimento(String dataFalecimento) {
		this.dataFalecimento = dataFalecimento;
	}

	public String getSiglaPaisNascimento() {
		return siglaPaisNascimento;
	}

	public void setSiglaPaisNascimento(String siglaPaisNascimento) {
		this.siglaPaisNascimento = siglaPaisNascimento;
	}

	public String getPaisDeNacionalidade() {
		return paisDeNacionalidade;
	}

	public void setPaisDeNacionalidade(String paisDeNacionalidade) {
		this.paisDeNacionalidade = paisDeNacionalidade;
	}

	public Long getId() {
		return id;
	}


}
