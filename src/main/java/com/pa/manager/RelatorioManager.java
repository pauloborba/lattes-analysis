package com.pa.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.pa.database.util.HibernateUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class RelatorioManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String MENSAGEM_ERRO_DATA_INICIAL_MAIOR_QUE_DATA_FINAL_MES = "Atenção. O mês inicial informado é maior do que o mês final informado.";
	private final String MENSAGEM_ERRO_DATA_INICIAL_MAIOR_QUE_DATA_FINAL_ANO = "Atenção. O ano inicial informado é maior do que o ano final informado.";
	private final String MENSAGEM_ERRO_DATA_MAIOR_QUE_DATA_ATUAL = "Atenção. A data informada é maior do que a data atual.";
	private final String ENDERECO_RELATORIOS_LATTES = "/iReportLattes.jrxml";
	private final String ENDERECO_DIRETORIO_RELATORIOS = System.getProperty("user.home") + "//";
	private final String NOME_DO_ARQUIVO = "RELATORIO_LATTES.html";
	private final String NOME_DO_ARQUIVO_ORIENTATIONS = "RELATORIO_LATTES_ORIENTATIONS.html";

	private InputStream inputStream;
	private JasperDesign designInputStream;
	private JasperReport pathjrxml;
	private JasperPrint printReport;
	private Calendar dataAtual;
	private HashMap<String, String> parametros;

	public RelatorioManager() {
		this.dataAtual = Calendar.getInstance();
	}

	private HashMap<String, String> parametrizarConsulta(String dataDeInicioParametter, String dataDeFimParametter)
			throws IOException {
		this.parametros = new HashMap<String, String>();
		if (dataDeInicioParametter != null && dataDeFimParametter != null) {
			String dataInicio = dataDeInicioParametter;
			String dataFim = dataDeFimParametter;
			this.parametros.put("parametroDataInicio", dataInicio);
			this.parametros.put("parametroDataFim", dataFim);
		}
		return parametros;
	}

	private void validarCamposData(String dataDeInicioParametter, String dataDeFimParametter) throws IOException {
		String dataInicio = formatarData(dataDeInicioParametter);
		String dataFim = formatarData(dataDeFimParametter);

		if (dataInicio != null && dataFim != null) {
			String dataDiaInicioAux = dataInicio.substring(8, 10);
			String dataDiaFimAux = dataFim.substring(8, 10);

			String dataMesInicioAux = dataInicio.substring(5, 7);
			String dataMesFimAux = dataFim.substring(5, 7);

			String dataAnoInicioAux = dataInicio.substring(0, 4);
			String dataAnoFimAux = dataFim.substring(0, 4);

			if (Integer.parseInt(dataMesInicioAux) > Integer.parseInt(dataMesFimAux)) {
				throw new IOException(MENSAGEM_ERRO_DATA_INICIAL_MAIOR_QUE_DATA_FINAL_MES);
			} else if (Integer.parseInt(dataAnoInicioAux) > Integer.parseInt(dataAnoFimAux)) {
				throw new IOException(MENSAGEM_ERRO_DATA_INICIAL_MAIOR_QUE_DATA_FINAL_ANO);
			}
		}
	}

	private String formatarData(String data) throws IOException {
		Calendar dataAtual = Calendar.getInstance();
		String dataAux = data.replace("/", "").replace(" ", "");
		if (dataAux.length() == 7) {
			if (dataAtual.get(Calendar.DAY_OF_MONTH) == 1) {
				dataAux = "0" + dataAux;
				System.out.println(dataAux);
			}
			if (dataAtual.get(Calendar.MONTH) == 0) {
				dataAux = dataAux.substring(0, 2) + "0" + dataAux.substring(2, 7);
				System.out.println(dataAux);
			}
		}
		if (dataAux.length() == 6) {
			dataAux = "0" + dataAux;
			dataAux = dataAux.substring(0, 2) + "0" + dataAux.substring(2, 7);
			System.out.println(dataAux);
		}
		if (!dataAux.isEmpty()) {
			String dia = dataAux.substring(0, 2);
			String mes = dataAux.substring(2, 4);
			String ano = dataAux.substring(4, 8);
			String novaDataFormatada = ano + "-" + mes + "-" + dia;
			validarComDataAtual(dia, mes, ano);
			return novaDataFormatada;
		} else {
			return null;
		}
	}

	private void validarComDataAtual(String dia, String mes, String ano) throws IOException {
		this.dataAtual = Calendar.getInstance();
		if (Integer.parseInt(mes) > (dataAtual.get(Calendar.MONTH) + 1)
				|| Integer.parseInt(ano) > dataAtual.get(Calendar.YEAR)) {
			throw new IOException(MENSAGEM_ERRO_DATA_MAIOR_QUE_DATA_ATUAL);
		}

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

	public void gerarRelatorioLattes(String dataDeInicioParametter, String dataDeFimParametter)
			throws JRException, SQLException, IOException {
		// this.validarCamposData(dataDeInicioParametter, dataDeFimParametter);
		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES,
				this.parametrizarConsulta(dataDeInicioParametter, dataDeFimParametter));
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO);
	}

	public void gerarRelatorioLattesOrientations(String dataInicial, String dataInicial2)
			throws JRException, SQLException, IOException {
		this.compilarRelatorio(ENDERECO_RELATORIOS_LATTES, this.parametrizarConsulta(dataInicial, dataInicial2));
		this.gerarHtmlDoRelatorio(ENDERECO_DIRETORIO_RELATORIOS, NOME_DO_ARQUIVO_ORIENTATIONS);

	}

}
