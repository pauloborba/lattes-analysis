package com.pa.entity;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class TechinicalProduction {
	
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
	private String paisDoEvento;
	
	@Column
	private String idioma;
	
	@Column
	private String meioDeDivulgavao;
	
	@Column
	private String homePage;
	
	@Column
	private String nivel;
	
	@Column
	private String flagRelevancia;
	
	@Column
	private String doi;
	
	@Column
	private String tituloIngles;
	
	@Column
	private String flagDivulgacaoCientifica;
	
	@Column
	private String finalidade;
	
	@Column
	private String plataforma;
	
	@Column
	private String ambiente;
	
	@Column
	private String disponibilidade;
	
	@Column
	private String instituicaoFinanaciadora;
	
	@Column
	private String finalidadeIngles;
	
	@Column
	private String participacaoDoAutores;
	
	@Column
	private String instituicaoPromotora;
	
	@Column
	private String local;
	
	@Column
	private String cidade;
	
	@Column
	private String duracao;
	
	@Column
	private String unidade;
	
	@Column
	private String unidadeIngles;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<Author> autores;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<KeyWord> palavrasChaves;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<KnowledgeArea> areasDeConhecimento;
	
	@OneToMany(cascade=CascadeType.ALL)
	private ArrayList<ActivitySectors> setoresDeAtividade;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getParticipacaoDoAutores() {
		return participacaoDoAutores;
	}

	public void setParticipacaoDoAutores(String participacaoDoAutores) {
		this.participacaoDoAutores = participacaoDoAutores;
	}

	public String getInstituicaoPromotora() {
		return instituicaoPromotora;
	}

	public void setInstituicaoPromotora(String instituicaoPromotora) {
		this.instituicaoPromotora = instituicaoPromotora;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getUnidadeIngles() {
		return unidadeIngles;
	}

	public void setUnidadeIngles(String unidadeIngles) {
		this.unidadeIngles = unidadeIngles;
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

	public String getFlagDivulgacaoCientifica() {
		return flagDivulgacaoCientifica;
	}

	public void setFlagDivulgacaoCientifica(String flagDivulgacaoCientifica) {
		this.flagDivulgacaoCientifica = flagDivulgacaoCientifica;
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

	public String getInstituicaoFinanaciadora() {
		return instituicaoFinanaciadora;
	}

	public void setInstituicaoFinanaciadora(String instituicaoFinanaciadora) {
		this.instituicaoFinanaciadora = instituicaoFinanaciadora;
	}

	public String getFinalidadeIngles() {
		return finalidadeIngles;
	}

	public void setFinalidadeIngles(String finalidadeIngles) {
		this.finalidadeIngles = finalidadeIngles;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	

}
