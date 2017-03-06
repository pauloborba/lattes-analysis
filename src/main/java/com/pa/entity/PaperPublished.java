package com.pa.entity;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class PaperPublished {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String natureza;
	
	@Column
	private String tituloDoTrabalho;
	
	@Column
	private String anoDoTrabalho;
	
	@Column
	private String paisDoEvento;
	
	@Column
	private String idioma;
	
	@Column
	private String meioDeDivulgavao;
	
	@Column
	private String homePageDoTrabalho;
	
	@Column
	private String flagRelevancia;
	
	@Column
	private String doi;
	
	@Column
	private String tituloIngles;
	
	@Column
	private String flagDivulgacaoCientifica;
	
	@Column
	private String classificacaoDoEvento;
	
	@Column
	private String nomeDoEvento;

	@Column
	private String cidadeDoEvento;
	
	@Column 
	private String anoDeRealizacao;
	
	@Column
	private String tituloDosAnaisProceedings;

	@Column
	private String volume;
	
	@Column
	private String fasciculo;
	
	@Column
	private String serie;
	
	@Column
	private String paginaInicial;
	
	@Column
	private String paginaFinal;
	
	@Column
	private String isbn;
	
	@Column
	private String nomeDaEditora;
	
	@Column
	private String cidadeDaEditora;
	
	@Column
	private String nomeDoEventoIngles;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<Author> autores;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<KeyWord> palavrasChaves;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<KnowledgeArea> areasDeConhecimento;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<ActivitySectors> setoresDeAtividade;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<Author> authorWorkInEvents;

	
	
	public ArrayList<Author> getAuthorWorkInEvents() {
		return authorWorkInEvents;
	}

	public void setAuthorWorkInEvents(ArrayList<Author> authorWorkInEvents) {
		this.authorWorkInEvents = authorWorkInEvents;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getTituloDoTrabalho() {
		return tituloDoTrabalho;
	}

	public void setTituloDoTrabalho(String tituloDoTrabalho) {
		this.tituloDoTrabalho = tituloDoTrabalho;
	}

	public String getAnoDoTrabalho() {
		return anoDoTrabalho;
	}

	public void setAnoDoTrabalho(String anoDoTrabalho) {
		this.anoDoTrabalho = anoDoTrabalho;
	}

	public String getPaisDoEvento() {
		return paisDoEvento;
	}

	public void setPaisDoEvento(String paisDoEvento) {
		this.paisDoEvento = paisDoEvento;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getMeioDeDivulgavao() {
		return meioDeDivulgavao;
	}

	public void setMeioDeDivulgavao(String meioDeDivulgavao) {
		this.meioDeDivulgavao = meioDeDivulgavao;
	}

	public String getHomePageDoTrabalho() {
		return homePageDoTrabalho;
	}

	public void setHomePageDoTrabalho(String homePageDoTrabalho) {
		this.homePageDoTrabalho = homePageDoTrabalho;
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

	public String getFlagDivulgacaoCientifica() {
		return flagDivulgacaoCientifica;
	}

	public void setFlagDivulgacaoCientifica(String flagDivulgacaoCientifica) {
		this.flagDivulgacaoCientifica = flagDivulgacaoCientifica;
	}

	public String getClassificacaoDoEvento() {
		return classificacaoDoEvento;
	}

	public void setClassificacaoDoEvento(String classificacaoDoEvento) {
		this.classificacaoDoEvento = classificacaoDoEvento;
	}

	public String getNomeDoEvento() {
		return nomeDoEvento;
	}

	public void setNomeDoEvento(String nomeDoEvento) {
		this.nomeDoEvento = nomeDoEvento;
	}

	public String getCidadeDoEvento() {
		return cidadeDoEvento;
	}

	public void setCidadeDoEvento(String cidadeDoEvento) {
		this.cidadeDoEvento = cidadeDoEvento;
	}

	public String getAnoDeRealizacao() {
		return anoDeRealizacao;
	}

	public void setAnoDeRealizacao(String anoDeRealizacao) {
		this.anoDeRealizacao = anoDeRealizacao;
	}

	public String getTituloDosAnaisProceedings() {
		return tituloDosAnaisProceedings;
	}

	public void setTituloDosAnaisProceedings(String tituloDosAnaisProceedings) {
		this.tituloDosAnaisProceedings = tituloDosAnaisProceedings;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getFasciculo() {
		return fasciculo;
	}

	public void setFasciculo(String fasciculo) {
		this.fasciculo = fasciculo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getPaginaInicial() {
		return paginaInicial;
	}

	public void setPaginaInicial(String paginaInicial) {
		this.paginaInicial = paginaInicial;
	}

	public String getPaginaFinal() {
		return paginaFinal;
	}

	public void setPaginaFinal(String paginaFinal) {
		this.paginaFinal = paginaFinal;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNomeDaEditora() {
		return nomeDaEditora;
	}

	public void setNomeDaEditora(String nomeDaEditora) {
		this.nomeDaEditora = nomeDaEditora;
	}

	public String getCidadeDaEditora() {
		return cidadeDaEditora;
	}

	public void setCidadeDaEditora(String cidadeDaEditora) {
		this.cidadeDaEditora = cidadeDaEditora;
	}

	public String getNomeDoEventoIngles() {
		return nomeDoEventoIngles;
	}

	public void setNomeDoEventoIngles(String nomeDoEventoIngles) {
		this.nomeDoEventoIngles = nomeDoEventoIngles;
	}

	public ArrayList<Author> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<Author> autores) {
		this.autores = autores;
	}

	public ArrayList<KeyWord> getPalavrasChaves() {
		return palavrasChaves;
	}

	public void setPalavrasChaves(ArrayList<KeyWord> palavrasChaves) {
		this.palavrasChaves = palavrasChaves;
	}

	public ArrayList<KnowledgeArea> getAreasDeConhecimento() {
		return areasDeConhecimento;
	}

	public void setAreasDeConhecimento(ArrayList<KnowledgeArea> areasDeConhecimento) {
		this.areasDeConhecimento = areasDeConhecimento;
	}

	public ArrayList<ActivitySectors> getSetoresDeAtividade() {
		return setoresDeAtividade;
	}

	public void setSetoresDeAtividade(ArrayList<ActivitySectors> setoresDeAtividade) {
		this.setoresDeAtividade = setoresDeAtividade;
	}

	public Long getId() {
		return id;
	}
	
}
