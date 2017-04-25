package com.pa.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.pa.associator.QualisAssociatorService;
import com.pa.database.impl.DatabaseFacade;
import com.pa.database.util.HibernateUtil;
import com.pa.entity.Book;
import com.pa.entity.Chapter;
import com.pa.entity.Publication;
import com.pa.entity.QualisData;
import com.pa.util.EnumPublicationLocalType;
import com.pa.util.LattesAnalysisUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class RelatorioManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Double PERCENTAGE_SIMILARITY_TITLE = 0.90;

	private final String ENDERECO_RELATORIOS_LATTES = "/iReportLattes.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_MESTRADO_ANDAMENTO = "/iReportLattesOrientationsAndamentoMestrado.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_MESTRADO_CONCLUIDA = "/iReportLattesOrientationsConcluidasMestrado.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_DOUTORADO_ANDAMENTO = "/iReportLattesOrientationsAndamentoDoutorado.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_DOUTORADO_CONCLUIDA = "/iReportLattesOrientationsConcluidasDoutorado.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_INICIACAO_CIENTIFICA = "/iReportLattesOrientationsIniciacaoCientifica.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_CURRICULOS = "/iReportLattesCurriculos.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_LIVROS = "/iReportLattesLivros.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_CAPITULOS = "/iReportLattesChapter.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_CONFERENCIAS = "/iReportLattesConferencias.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_PERIODICOS = "/iReportLattesPeriodicos.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_PERIODICOS2 = "/iReportLattesPeriodicos2.jrxml";
	private final String ENDERECO_RELATORIOS_LATTES_PATENTES = "/iReportLattesPatentes.jrxml";
	private final String ENDERECO_DIRETORIO_RELATORIOS = System.getProperty("user.home") + "//";
	private final String NOME_DO_ARQUIVO = "RELATORIO_LATTES.html";
	private final String NOME_DO_ARQUIVO_LIVROS = "RELATORIO_LATTES_LIVROS.html";
	private final String NOME_DO_ARQUIVO_CAPITULOS = "RELATORIO_LATTES_CAPITULOS.html";
	private final String NOME_DO_ARQUIVO_CONFERENCIAS = "RELATORIO_LATTES_CONFERENCIAS.html";
	private final String NOME_DO_ARQUIVO_PERIODICOS = "RELATORIO_LATTES_PERIODICOS.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS_DOUTORADO_ANDAMENTO = "RELATORIO_LATTES_ORIENTATIONS_DOUTORADO_ANDAMENTO.html";
	private final String NOME_DO_ARQUIVO_CURRICULOS = "RELATORIO_LATTES_CURRICULOS.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS_DOUTORADO_CONCLUIDO = "RELATORIO_LATTES_ORIENTATIONS_DOUTORADO_CONCLUIDO.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS_MESTRADO_ANDAMENTO = "RELATORIO_LATTES_ORIENTATIONS_MESTRADO_ANDAMENTO.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS_MESTRADO_CONCLUIDA = "RELATORIO_LATTES_ORIENTATIONS_MESTRADO_CONCLUIDO.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS_INICIACAO_CIENTIFICA = "RELATORIO_LATTES_ORIENTATIONS_INICIACAO_CIENTIFICA.html";
	private final String NOME_DO_ARQUIVO_PATENTES = "RELATORIO_LATTES_PATENTES.html";

	private InputStream inputStream;
	private JasperDesign designInputStream;
	private JasperReport pathjrxml;
	private JasperPrint printReport;
	private Calendar dataAtual;
	private HashMap<String, Object> parametros;

	private JRBeanCollectionDataSource coll;

	public RelatorioManager() {
		this.dataAtual = Calendar.getInstance();
	}

	private HashMap<String, Object> parametrizarConsulta(String dataDeInicioParametter, String dataDeFimParametter)
			throws IOException {
		this.parametros = new HashMap<String, Object>();
		if (dataDeInicioParametter != null && dataDeFimParametter != null) {
			String dataInicio = dataDeInicioParametter;
			String dataFim = dataDeFimParametter;
			this.parametros.put("parametroDataInicio", dataInicio);
			this.parametros.put("parametroDataFim", dataFim);
		}
		return parametros;
	}

	private void compilarRelatorio(String endereco, HashMap parametros) throws JRException, SQLException {
		this.inputStream = getClass().getResourceAsStream(endereco);
		this.designInputStream = JRXmlLoader.load(inputStream);
		this.pathjrxml = JasperCompileManager.compileReport(designInputStream);
		Connection conection = HibernateUtil.getSessionFactory().getSessionFactoryOptions().getServiceRegistry()
				.getService(ConnectionProvider.class).getConnection();
		this.printReport = JasperFillManager.fillReport(this.pathjrxml, parametros, conection);

	}

	private void gerarHtmlDoRelatorio(String endereco, String nomeDoArquivo) throws JRException {
		String enderecoFinal = endereco + nomeDoArquivo;
		JasperExportManager.exportReportToHtmlFile(this.printReport, enderecoFinal);
	}

	public void gerarRelatorioLattes(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) throws JRException, SQLException, IOException {
		
//		gerarRelatoriolattes(dataDeInicioParametter, dataDeFimParametter, qualisDataMap);

//		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_DOUTORADO_ANDAMENTO,
//				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
//		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS_DOUTORADO_ANDAMENTO);
//
//		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_DOUTORADO_CONCLUIDA,
//				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
//		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS_DOUTORADO_CONCLUIDO);
//
//		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_MESTRADO_ANDAMENTO,
//				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
//		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS_MESTRADO_ANDAMENTO);
//
//		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_MESTRADO_CONCLUIDA,
//				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
//		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS_MESTRADO_CONCLUIDA);
//
//		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES_ORIENTATIONS_INICIACAO_CIENTIFICA,
//				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
//		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS_INICIACAO_CIENTIFICA);
//
//		gerarRelatorioLivros(dataDeInicioParametter, dataDeFimParametter);
//
//		gerarRelatorioCapitulos(dataDeInicioParametter, dataDeFimParametter);
//
		gerarRelatorioConferencias(dataDeInicioParametter, dataDeFimParametter, qualisDataMap);

		gerarRelatorioPeriodicos(dataDeInicioParametter, dataDeFimParametter, qualisDataMap);

	}

	private void gerarRelatoriolattes(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) throws JRException, IOException, SQLException {
		this.compilar(ENDERECO_RELATORIOS_LATTES);
		this.coll = new JRBeanCollectionDataSource(obterListConferencias(dataDeInicioParametter, dataDeFimParametter, qualisDataMap), false);
		this.printReport = JasperFillManager.fillReport(this.pathjrxml, this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter), coll);
		
		//this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES, this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO);
		
	}

	private void gerarRelatorioLivros(String dataDeInicioParametter, String dataDeFimParametter)
			throws JRException, IOException {
		this.compilar(ENDERECO_RELATORIOS_LATTES_LIVROS);
		this.coll = new JRBeanCollectionDataSource(obterListLivros(dataDeInicioParametter, dataDeFimParametter), false);
		this.printReport = JasperFillManager.fillReport(this.pathjrxml,
				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter), coll);
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_LIVROS);

	}

	private void gerarRelatorioCapitulos(String dataDeInicioParametter, String dataDeFimParametter)
			throws JRException, IOException {
		this.compilar(ENDERECO_RELATORIOS_LATTES_CAPITULOS);
		this.coll = new JRBeanCollectionDataSource(obterListCapitulos(dataDeInicioParametter, dataDeFimParametter),
				false);
		this.printReport = JasperFillManager.fillReport(this.pathjrxml,
				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter), coll);
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_CAPITULOS);

	}

	private void gerarRelatorioPeriodicos(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) throws JRException, SQLException, IOException {
		this.compilar(ENDERECO_RELATORIOS_LATTES_PERIODICOS2);
		this.coll = new JRBeanCollectionDataSource(
				obterListPeriodicos(dataDeInicioParametter, dataDeFimParametter, qualisDataMap), false);
		this.printReport = JasperFillManager.fillReport(this.pathjrxml,
				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter), coll);
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_PERIODICOS);
	}

	private void gerarRelatorioConferencias(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) throws JRException, IOException {
		this.compilar(ENDERECO_RELATORIOS_LATTES_CONFERENCIAS);
		this.coll = new JRBeanCollectionDataSource(
				obterListConferencias(dataDeInicioParametter, dataDeFimParametter, qualisDataMap), false);
		this.printReport = JasperFillManager.fillReport(this.pathjrxml,
				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter), coll);
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_CONFERENCIAS);
	}

	private Collection<?> obterListConferencias(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) {
		List<Publication> publicationsReport = new ArrayList<Publication>();

		for (Publication p : DatabaseFacade.getInstance().listAllPublications()) {
			
			if (p.getPublicationType().getType().equals(EnumPublicationLocalType.CONFERENCE)
					&& p.getYear() >= Integer.parseInt(dataDeInicioParametter)
					&& p.getYear() <= Integer.parseInt(dataDeFimParametter)) {

				if (validarDuplicatasPublicacoes(p, publicationsReport)) {
					p.setQualis(QualisAssociatorService.getInstance().getQualisForPublication(p, qualisDataMap));
					publicationsReport.add(p);
				}
			}
		}
		return publicationsReport;
	}

	private Collection<?> obterListPeriodicos(String dataDeInicioParametter, String dataDeFimParametter,
			Map<EnumPublicationLocalType, QualisData> qualisDataMap) {
		List<Publication> publicationsReport = new ArrayList<Publication>();

		for (Publication p : DatabaseFacade.getInstance().listAllPublications()) {

			if (p.getPublicationType().getType().equals(EnumPublicationLocalType.PERIODIC)
					&& p.getYear() >= Integer.parseInt(dataDeInicioParametter)
					&& p.getYear() <= Integer.parseInt(dataDeFimParametter)) {

				if (validarDuplicatasPublicacoes(p, publicationsReport)) {
					p.setQualis(QualisAssociatorService.getInstance().getQualisForPublication(p, qualisDataMap));
					publicationsReport.add(p);
				}
			}
		}
		Collections.sort(publicationsReport);
		return publicationsReport;
	}

	private Collection<?> obterListCapitulos(String dataDeInicioParametter, String dataDeFimParametter) {
		List<Chapter> chapterReport = new ArrayList<Chapter>();

		for (Chapter p : DatabaseFacade.getInstance().listAllChapters()) {

			if (p.getAno() >= Integer.parseInt(dataDeInicioParametter)
					&& p.getAno() <= Integer.parseInt(dataDeFimParametter)) {

				if (validarDuplicatasCapitulos(p, chapterReport)) {
					chapterReport.add(p);
				}
			}
		}
		Collections.sort(chapterReport);
		return chapterReport;
	}

	private Collection<?> obterListLivros(String dataDeInicioParametter, String dataDeFimParametter) {
		List<Book> booksReport = new ArrayList<Book>();

		for (Book b : DatabaseFacade.getInstance().listAllBooks()) {

			if (b.getAno() >= Integer.parseInt(dataDeInicioParametter)
					&& b.getAno() <= Integer.parseInt(dataDeFimParametter)) {

				if (validarDuplicatasLivros(b, booksReport)) {
					booksReport.add(b);
				}
			}
		}
		Collections.sort(booksReport);
		return booksReport;
	}

	private boolean validarDuplicatasPublicacoes(Publication p, List<Publication> publicationsReport) {
		for (Publication publication : publicationsReport) {
			if (LattesAnalysisUtil.computeStringSimilarity(publication.getTitle().toUpperCase(), p.getTitle().toUpperCase()) >= PERCENTAGE_SIMILARITY_TITLE) {
				return false;
			}
		}
		return true;
	}

	private boolean validarDuplicatasCapitulos(Chapter p, List<Chapter> chaptersReport) {
		for (Chapter publication : chaptersReport) {
			if (publication.getTitulo().toUpperCase().equalsIgnoreCase(p.getTitulo().toUpperCase())) {
				return false;
			}
		}
		return true;
	}

	private boolean validarDuplicatasLivros(Book p, List<Book> booksReport) {
		for (Book book : booksReport) {
			if (book.getTitulo().toUpperCase().equalsIgnoreCase(p.getTitulo().toUpperCase())) {
				return false;
			}
		}
		return true;
	}

	private void compilar(String enderecoRelatorio) throws JRException {
		this.inputStream = getClass().getResourceAsStream(enderecoRelatorio);
		this.designInputStream = JRXmlLoader.load(inputStream);
		this.pathjrxml = JasperCompileManager.compileReport(designInputStream);
	}
	
	

}
