package com.pa.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pa.entity.ActivitySectors;
import com.pa.entity.Author;
import com.pa.entity.BookChapter;
import com.pa.entity.Curriculum;
import com.pa.entity.KeyWord;
import com.pa.entity.KnowledgeArea;
import com.pa.entity.Orientation;
import com.pa.entity.PaperPublished;
import com.pa.entity.TechinicalProduction;
import com.pa.entity.WorkInEvents;

public class XmlService {

	private FileInputStream stream;
	private InputStreamReader reader;
	private BufferedReader br;
	private Curriculum curriculum;

	public XmlService(File xml) throws FileNotFoundException {
		this.stream = new FileInputStream(xml);
		this.reader = new InputStreamReader(stream);
		this.br = new BufferedReader(reader);
		this.curriculum = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xml);
			this.curriculum = this.consumerDocumentCurriculum(document);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Curriculum consumerDocumentCurriculum(Document document) {
		Curriculum curriculum = new Curriculum();
		Properties prop = getProperties();
		curriculum = consumerGeneralDataCurriculum(curriculum, document, prop, prop.getProperty("GENERAL_DATA"));
		curriculum = consumerOrientationDataCurriculum(curriculum, document, prop,
				prop.getProperty("ORIENTACOES-CONCLUIDAS"));
		curriculum = consumerWorksDataCurriculum(curriculum, document, prop, "TRABALHOS-EM-EVENTOS");
		curriculum = consumerPapersDataCurriculum(curriculum, document, prop, "ARTIGOS-PUBLICADOS");
		curriculum = consumerBooksAndChaptersDataCurriculum(curriculum, document, prop, "LIVROS-E-CAPITULOS");
		curriculum = consumerTechnicalProduction(curriculum, document, prop, "PRODUCAO-TECNICA");

		return curriculum;
	}

	private Curriculum consumerTechnicalProduction(Curriculum curriculum, Document document, Properties prop,
			String string) {
		ArrayList<TechinicalProduction> listTechinicalProduction = new ArrayList<TechinicalProduction>();
		ArrayList<Author> listAuthor = new ArrayList<Author>();
		ArrayList<KeyWord> listKeyWord = new ArrayList<KeyWord>();
		ArrayList<KnowledgeArea> listKnowledgeArea = new ArrayList<KnowledgeArea>();
		ArrayList<ActivitySectors> listActivitySectors = new ArrayList<ActivitySectors>();

		NodeList paperDataNodeList = document.getElementsByTagName(string);
		Node dataNode = paperDataNodeList.item(0);
		NodeList childsDataNode = dataNode.getChildNodes();
		TechinicalProduction techinicalProduction = new TechinicalProduction();
		for (int i = 1; i <= childsDataNode.getLength(); i++) {
			Node childNode = childsDataNode.item(i);
			if (childNode != null) {
				NodeList dataNodeList = childNode.getChildNodes();
				for (int j = 0; j <= dataNodeList.getLength(); j++) {
					Node node = dataNodeList.item(j);
					if (node != null) {
						if (node.getNodeName().equals("APRESENTACAO-DE-TRABALHO")
								|| node.getNodeName().equals("CURSO-DE-CURTA-DURACAO-MINISTRADO")
								|| node.getNodeName().equals("ORGANIZACAO-DE-EVENTO")) {
							NodeList dataNodeList2 = node.getChildNodes();
							for (int k = 0; k <= dataNodeList2.getLength(); k++) {
								Node nodeChild = dataNodeList2.item(k);
								if (nodeChild != null) {
									if (nodeChild.getNodeName()
											.equals("DADOS-BASICOS-DE-CURSOS-CURTA-DURACAO-MINISTRADO")) {
										techinicalProduction = consumerBasicDataTechinicalProductionFastCourse(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName()
											.equals("DETALHAMENTO-DE-CURSOS-CURTA-DURACAO-MINISTRADO")) {
										techinicalProduction = consumerDatailTechinicalProductionFastCourse(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName().equals("DADOS-BASICOS-DA-ORGANIZACAO-DE-EVENTO")) {
										techinicalProduction = consumerBasicDataTechinicalProductionOganizationEvent(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName().equals("DETALHAMENTO-DA-ORGANIZACAO-DE-EVENTO")) {
										techinicalProduction = consumerDatailTechinicalProductionOrganizationEvent(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName().equals("DADOS-BASICOS-DA-APRESENTACAO-DE-TRABALHO")) {
										techinicalProduction = consumerBasicDataTechinicalProductionPresentWork(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName().equals("DETALHAMENTO-DA-APRESENTACAO-DE-TRABALHO")) {
										techinicalProduction = consumerDatailTechinicalProductionPresentWork(
												techinicalProduction, nodeChild);
									}
									if (nodeChild.getNodeName().equals("AUTORES")) {
										Author author = consumerAuthor(nodeChild);
										listAuthor.add(author);
									}
									if (nodeChild.getNodeName().equals("PALAVRAS-CHAVE")) {
										KeyWord keyWord = consumerKeyWord(nodeChild);
										listKeyWord.add(keyWord);
									}
									if (nodeChild.getNodeName().equals("AREAS-DO-CONHECIMENTO")
											&& nodeChild.hasAttributes()) {
										KnowledgeArea knowledgeArea = consumerKnowledArea(nodeChild);
										listKnowledgeArea.add(knowledgeArea);
									}
									if (nodeChild.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
										ActivitySectors activitySectors = consumerActivitySectorys(nodeChild);
										listActivitySectors.add(activitySectors);
									}
								}
							}
							setListsInTechinicalProduction(listTechinicalProduction, listAuthor, listKnowledgeArea,
									listActivitySectors, techinicalProduction);
							listAuthor = new ArrayList<Author>();
							listKnowledgeArea = new ArrayList<KnowledgeArea>();
							listActivitySectors = new ArrayList<ActivitySectors>();
							techinicalProduction = new TechinicalProduction();
						}

						if (node.getNodeName().equals("DADOS-BASICOS-DO-SOFTWARE")) {
							techinicalProduction = consumerBasicDataTechinicalProduction(techinicalProduction, node);
						}
						if (node.getNodeName().equals("DETALHAMENTO-DO-SOFTWARE")) {
							techinicalProduction = consumerDatailTechinicalProduction(techinicalProduction, node);
						}
						if (node.getNodeName().equals("AUTORES")) {
							Author author = consumerAuthor(node);
							listAuthor.add(author);
						}
						if (node.getNodeName().equals("PALAVRAS-CHAVE")) {
							KeyWord keyWord = consumerKeyWord(node);
							listKeyWord.add(keyWord);
						}
						if (node.getNodeName().equals("AREAS-DO-CONHECIMENTO") && node.hasAttributes()) {
							KnowledgeArea knowledgeArea = consumerKnowledArea(node);
							listKnowledgeArea.add(knowledgeArea);
						}
						if (node.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
							ActivitySectors activitySectors = consumerActivitySectorys(node);
							listActivitySectors.add(activitySectors);
						}
					}
				}
				if (techinicalProduction.getTitulo() != null) {
					setListsInTechinicalProduction(listTechinicalProduction, listAuthor, listKnowledgeArea,
							listActivitySectors, techinicalProduction);
				}
				listAuthor = new ArrayList<Author>();
				listKnowledgeArea = new ArrayList<KnowledgeArea>();
				listActivitySectors = new ArrayList<ActivitySectors>();
				techinicalProduction = new TechinicalProduction();
			}
		}

		curriculum.setTechinicalProductions(listTechinicalProduction);
		return curriculum;
	}

	private void setListsInTechinicalProduction(ArrayList<TechinicalProduction> listTechinicalProduction,
			ArrayList<Author> listAuthor, ArrayList<KnowledgeArea> listKnowledgeArea,
			ArrayList<ActivitySectors> listActivitySectors, TechinicalProduction techinicalProduction) {
		techinicalProduction.setAutores(listAuthor);
		techinicalProduction.setAreasDeConhecimento(listKnowledgeArea);
		techinicalProduction.setSetoresDeAtividade(listActivitySectors);
		listTechinicalProduction.add(techinicalProduction);

	}

	private TechinicalProduction consumerBasicDataTechinicalProductionPresentWork(
			TechinicalProduction techinicalProduction, Node nodeChild) {
		techinicalProduction.setAno(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDoi(nodeChild.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFlagDivulgacaoCientifica(nodeChild.getAttributes().item(2).getNodeValue());
		techinicalProduction.setFlagRelevancia(nodeChild.getAttributes().item(3).getNodeValue());
		techinicalProduction.setIdioma(nodeChild.getAttributes().item(4).getNodeValue());
		techinicalProduction.setNatureza(nodeChild.getAttributes().item(5).getNodeValue());
		techinicalProduction.setPaisDoEvento(nodeChild.getAttributes().item(6).getNodeValue());
		techinicalProduction.setTipo(nodeChild.getAttributes().item(7).getNodeValue());
		techinicalProduction.setTituloIngles(nodeChild.getAttributes().item(8).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerDatailTechinicalProductionPresentWork(
			TechinicalProduction techinicalProduction, Node nodeChild) {
		techinicalProduction.setCidade(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setInstituicaoPromotora(nodeChild.getAttributes().item(1).getNodeValue());
		techinicalProduction.setLocal(nodeChild.getAttributes().item(2).getNodeValue());
		techinicalProduction.setTitulo(nodeChild.getAttributes().item(3).getNodeValue());
		techinicalProduction.setTituloIngles(nodeChild.getAttributes().item(4).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerBasicDataTechinicalProductionOganizationEvent(
			TechinicalProduction techinicalProduction, Node nodeChild) {
		techinicalProduction.setAno(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDoi(nodeChild.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFlagRelevancia(nodeChild.getAttributes().item(2).getNodeValue());
		techinicalProduction.setHomePage(nodeChild.getAttributes().item(3).getNodeValue());
		techinicalProduction.setIdioma(nodeChild.getAttributes().item(4).getNodeValue());
		techinicalProduction.setMeioDeDivulgavao(nodeChild.getAttributes().item(5).getNodeValue());
		techinicalProduction.setNatureza(nodeChild.getAttributes().item(6).getNodeValue());
		techinicalProduction.setPaisDoEvento(nodeChild.getAttributes().item(7).getNodeValue());
		techinicalProduction.setTipo(nodeChild.getAttributes().item(8).getNodeValue());
		techinicalProduction.setTitulo(nodeChild.getAttributes().item(9).getNodeValue());
		techinicalProduction.setTituloIngles(nodeChild.getAttributes().item(10).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerDatailTechinicalProductionOrganizationEvent(
			TechinicalProduction techinicalProduction, Node nodeChild) {
		techinicalProduction.setCidade(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDuracao(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setInstituicaoPromotora(nodeChild.getAttributes().item(4).getNodeValue());
		techinicalProduction.setLocal(nodeChild.getAttributes().item(5).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerBasicDataTechinicalProductionFastCourse(
			TechinicalProduction techinicalProduction, Node nodeChild) {
		techinicalProduction.setAno(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDoi(nodeChild.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFlagDivulgacaoCientifica(nodeChild.getAttributes().item(2).getNodeValue());
		techinicalProduction.setFlagRelevancia(nodeChild.getAttributes().item(3).getNodeValue());
		techinicalProduction.setHomePage(nodeChild.getAttributes().item(4).getNodeValue());
		techinicalProduction.setIdioma(nodeChild.getAttributes().item(5).getNodeValue());
		techinicalProduction.setMeioDeDivulgavao(nodeChild.getAttributes().item(6).getNodeValue());
		techinicalProduction.setNivel(nodeChild.getAttributes().item(7).getNodeValue());
		techinicalProduction.setTitulo(nodeChild.getAttributes().item(8).getNodeValue());
		techinicalProduction.setTituloIngles(nodeChild.getAttributes().item(9).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerDatailTechinicalProductionFastCourse(TechinicalProduction techinicalProduction,
			Node nodeChild) {
		techinicalProduction.setCidade(nodeChild.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDuracao(nodeChild.getAttributes().item(1).getNodeValue());
		techinicalProduction.setInstituicaoPromotora(nodeChild.getAttributes().item(2).getNodeValue());
		techinicalProduction.setLocal(nodeChild.getAttributes().item(3).getNodeValue());
		techinicalProduction.setParticipacaoDoAutores(nodeChild.getAttributes().item(4).getNodeValue());
		techinicalProduction.setUnidade(nodeChild.getAttributes().item(5).getNodeValue());
		techinicalProduction.setUnidadeIngles(nodeChild.getAttributes().item(6).getNodeValue());
		return techinicalProduction;
	}

	private TechinicalProduction consumerDatailTechinicalProduction(TechinicalProduction techinicalProduction,
			Node node) {
		techinicalProduction.setAmbiente(node.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDisponibilidade(node.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFinalidade(node.getAttributes().item(2).getNodeValue());
		techinicalProduction.setFinalidadeIngles(node.getAttributes().item(3).getNodeValue());
		techinicalProduction.setInstituicaoFinanaciadora(node.getAttributes().item(4).getNodeValue());
		techinicalProduction.setPlataforma(node.getAttributes().item(5).getNodeValue());
		return techinicalProduction;
	}

	private Curriculum consumerBooksAndChaptersDataCurriculum(Curriculum curriculum, Document document, Properties prop,
			String bookAndchapter) {
		ArrayList<BookChapter> listPapersPublished = new ArrayList<BookChapter>();
		ArrayList<Author> listAuthorPapersPublished = new ArrayList<Author>();
		ArrayList<KeyWord> listKeyWordPapersPublished = new ArrayList<KeyWord>();
		ArrayList<KnowledgeArea> listKnowledgeAreaPapersPublished = new ArrayList<KnowledgeArea>();
		ArrayList<ActivitySectors> listActivitySectorsPapersPublished = new ArrayList<ActivitySectors>();

		NodeList paperDataNodeList = document.getElementsByTagName(bookAndchapter);
		Node dataNode = paperDataNodeList.item(0);
		if (dataNode != null) {
			NodeList childsDataNode = dataNode.getChildNodes();
			BookChapter paperPublished = new BookChapter();
			for (int i = 1; i <= childsDataNode.getLength(); i++) {
				Node childNode = childsDataNode.item(i);
				if (childNode != null) {
					NodeList dataNodeList = childNode.getChildNodes();
					for (int j = 0; j <= dataNodeList.getLength(); j++) {
						Node node = dataNodeList.item(j);
						if (node != null) {
							if (node.getNodeName().equals("LIVRO-PUBLICADO-OU-ORGANIZADO")) {
								NodeList dataNodeList2 = node.getChildNodes();
								for (int k = 0; k <= dataNodeList2.getLength(); k++) {
									Node nodeChild = dataNodeList2.item(k);
									if (nodeChild != null) {
										if (nodeChild.getNodeName().equals("DADOS-BASICOS-DO-LIVRO")) {
											paperPublished = consumerBasicDataBibliographicProduction(paperPublished,
													nodeChild);
										}
										if (nodeChild.getNodeName().equals("DETALHAMENTO-DO-LIVRO")) {
											paperPublished = consumerDatailBibliographicProduction(paperPublished,
													nodeChild);
										}
										if (nodeChild.getNodeName().equals("AUTORES")) {
											Author author = consumerAuthor(nodeChild);
											listAuthorPapersPublished.add(author);
										}
										if (nodeChild.getNodeName().equals("PALAVRAS-CHAVE")) {
											KeyWord keyWord = consumerKeyWord(nodeChild);
											listKeyWordPapersPublished.add(keyWord);
										}
										if (nodeChild.getNodeName().equals("AREAS-DO-CONHECIMENTO")
												&& node.hasAttributes()) {
											KnowledgeArea knowledgeArea = consumerKnowledArea(nodeChild);
											listKnowledgeAreaPapersPublished.add(knowledgeArea);
										}
										if (nodeChild.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
											ActivitySectors activitySectors = consumerActivitySectorys(nodeChild);
											listActivitySectorsPapersPublished.add(activitySectors);
										}
									}
								}
								if (paperPublished.getIsbn() != null) {
									setListsInBookChapter(listPapersPublished, listAuthorPapersPublished,
											listKnowledgeAreaPapersPublished, listActivitySectorsPapersPublished,
											paperPublished);
								}
								listAuthorPapersPublished = new ArrayList<Author>();
								listKnowledgeAreaPapersPublished = new ArrayList<KnowledgeArea>();
								listActivitySectorsPapersPublished = new ArrayList<ActivitySectors>();
								paperPublished = new BookChapter();

							}
							if (node.getNodeName().equals("CAPITULO-DE-LIVRO-PUBLICADO")) {
								NodeList dataNodeList2 = node.getChildNodes();
								for (int k = 0; k <= dataNodeList2.getLength(); k++) {
									Node nodeChild = dataNodeList2.item(k);
									if (nodeChild != null) {
										if (nodeChild.getNodeName().equals("DADOS-BASICOS-DO-CAPITULO")) {
											paperPublished = consumerBasicDataBibliographicProduction(paperPublished,
													nodeChild);
										}
										if (nodeChild.getNodeName().equals("DETALHAMENTO-DO-CAPITULO")) {
											paperPublished = consumerDatailBibliographicProduction(paperPublished,
													nodeChild);
										}
										if (nodeChild.getNodeName().equals("AUTORES")) {
											Author author = consumerAuthor(nodeChild);
											listAuthorPapersPublished.add(author);
										}
										if (nodeChild.getNodeName().equals("PALAVRAS-CHAVE")) {
											KeyWord keyWord = consumerKeyWord(nodeChild);
											listKeyWordPapersPublished.add(keyWord);
										}
										if (nodeChild.getNodeName().equals("AREAS-DO-CONHECIMENTO")
												&& node.hasAttributes()) {
											KnowledgeArea knowledgeArea = consumerKnowledArea(nodeChild);
											listKnowledgeAreaPapersPublished.add(knowledgeArea);
										}
										if (nodeChild.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
											ActivitySectors activitySectors = consumerActivitySectorys(nodeChild);
											listActivitySectorsPapersPublished.add(activitySectors);
										}
									}
								}
								if (paperPublished.getIsbn() != null) {
									setListsInBookChapter(listPapersPublished, listAuthorPapersPublished,
											listKnowledgeAreaPapersPublished, listActivitySectorsPapersPublished,
											paperPublished);
								}
								listAuthorPapersPublished = new ArrayList<Author>();
								listKnowledgeAreaPapersPublished = new ArrayList<KnowledgeArea>();
								listActivitySectorsPapersPublished = new ArrayList<ActivitySectors>();
								paperPublished = new BookChapter();
							}
						}
					}
					curriculum.setBooksAndChapters(listPapersPublished);
				}
			}
		}
		curriculum.setBooksAndChapters(listPapersPublished);

		return curriculum;
	}

	private Curriculum consumerPapersDataCurriculum(Curriculum curriculum, Document document, Properties prop,
			String papersData) {
		ArrayList<PaperPublished> listPapersPublished = new ArrayList<PaperPublished>();
		ArrayList<Author> listAuthorPapersPublished = new ArrayList<Author>();
		ArrayList<KeyWord> listKeyWordPapersPublished = new ArrayList<KeyWord>();
		ArrayList<KnowledgeArea> listKnowledgeAreaPapersPublished = new ArrayList<KnowledgeArea>();
		ArrayList<ActivitySectors> listActivitySectorsPapersPublished = new ArrayList<ActivitySectors>();

		NodeList paperDataNodeList = document.getElementsByTagName(papersData);
		Node dataNode = paperDataNodeList.item(0);
		NodeList childsDataNode = dataNode.getChildNodes();
		PaperPublished paperPublished = new PaperPublished();
		for (int i = 1; i <= childsDataNode.getLength(); i++) {
			Node childNode = childsDataNode.item(i);
			if (childNode != null) {
				NodeList dataNodeList = childNode.getChildNodes();
				for (int j = 0; j <= dataNodeList.getLength(); j++) {
					Node node = dataNodeList.item(j);
					if (node != null) {
						if (node.getNodeName().equals("DADOS-BASICOS-DO-ARTIGO")) {
							paperPublished = consumerBasicDataPaperPublished(paperPublished, node);
						}
						if (node.getNodeName().equals("DETALHAMENTO-DO-ARTIGO")) {
							paperPublished = consumerDatailWorkPublished(paperPublished, node);
						}
						if (node.getNodeName().equals("AUTORES")) {
							Author author = consumerAuthor(node);
							listAuthorPapersPublished.add(author);
						}
						if (node.getNodeName().equals("PALAVRAS-CHAVE")) {
							KeyWord keyWord = consumerKeyWord(node);
							listKeyWordPapersPublished.add(keyWord);
						}
						if (node.getNodeName().equals("AREAS-DO-CONHECIMENTO") && node.hasAttributes()) {
							KnowledgeArea knowledgeArea = consumerKnowledArea(node);
							listKnowledgeAreaPapersPublished.add(knowledgeArea);
						}
						if (node.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
							ActivitySectors activitySectors = consumerActivitySectorys(node);
							listActivitySectorsPapersPublished.add(activitySectors);
						}
					}
				}
				if (paperPublished.getIsbn() != null) {
					setListsInPaperPublished(listPapersPublished, listAuthorPapersPublished,
							listKnowledgeAreaPapersPublished, listActivitySectorsPapersPublished, paperPublished);
				}
				listAuthorPapersPublished = new ArrayList<Author>();
				listKnowledgeAreaPapersPublished = new ArrayList<KnowledgeArea>();
				listActivitySectorsPapersPublished = new ArrayList<ActivitySectors>();
				paperPublished = new PaperPublished();
			}
		}

		curriculum.setPaperPublished(listPapersPublished);
		return curriculum;

	}

	private Curriculum consumerWorksDataCurriculum(Curriculum curriculum, Document document, Properties prop,
			String worksData) {
		ArrayList<WorkInEvents> listWorkInEvents = new ArrayList<WorkInEvents>();
		ArrayList<Author> listAuthorWorkInEvents = new ArrayList<Author>();
		ArrayList<KeyWord> listKeyWordWorkInEvents = new ArrayList<KeyWord>();
		ArrayList<KnowledgeArea> listKnowledgeAreaWorkInEvents = new ArrayList<KnowledgeArea>();
		ArrayList<ActivitySectors> listActivitySectorsWorkInEvents = new ArrayList<ActivitySectors>();

		NodeList worksDataNodeList = document.getElementsByTagName(worksData);
		Node dataNode = worksDataNodeList.item(0);
		NodeList childsDataNode = dataNode.getChildNodes();
		WorkInEvents workInEvents = new WorkInEvents();
		for (int i = 1; i <= childsDataNode.getLength(); i++) {
			Node childNode = childsDataNode.item(i);
			if (childNode != null) {
				NodeList dataNodeList = childNode.getChildNodes();
				for (int j = 0; j <= dataNodeList.getLength(); j++) {
					Node node = dataNodeList.item(j);
					if (node != null) {
						if (node.getNodeName().equals("DADOS-BASICOS-DO-TRABALHO")) {
							workInEvents = consumerBasicData(workInEvents, node);
						}
						if (node.getNodeName().equals("DETALHAMENTO-DO-TRABALHO")) {
							workInEvents = consumerDatailWork(workInEvents, node);
						}
						if (node.getNodeName().equals("AUTORES")) {
							Author author = consumerAuthor(node);
							listAuthorWorkInEvents.add(author);
						}
						if (node.getNodeName().equals("PALAVRAS-CHAVE")) {
							KeyWord keyWord = consumerKeyWord(node);
							listKeyWordWorkInEvents.add(keyWord);
						}
						if (node.getNodeName().equals("AREAS-DO-CONHECIMENTO") && node.hasAttributes()) {
							KnowledgeArea knowledgeArea = consumerKnowledArea(node);
							listKnowledgeAreaWorkInEvents.add(knowledgeArea);
						}
						if (node.getNodeName().equals("SETORES-DE-ATIVIDADE")) {
							ActivitySectors activitySectors = consumerActivitySectorys(node);
							listActivitySectorsWorkInEvents.add(activitySectors);
						}
					}
				}
				if (workInEvents.getAnoDeRealizacao() != null) {
					setListsInWorkInEvents(listWorkInEvents, listAuthorWorkInEvents, listKnowledgeAreaWorkInEvents,
							listActivitySectorsWorkInEvents, workInEvents);
				}
				listAuthorWorkInEvents = new ArrayList<Author>();
				listKnowledgeAreaWorkInEvents = new ArrayList<KnowledgeArea>();
				listActivitySectorsWorkInEvents = new ArrayList<ActivitySectors>();
				workInEvents = new WorkInEvents();
			}
		}
		curriculum.setWorkInEvents(listWorkInEvents);
		return curriculum;

	}

	private void setListsInWorkInEvents(ArrayList<WorkInEvents> listWorkInEvents,
			ArrayList<Author> listAuthorWorkInEvents, ArrayList<KnowledgeArea> listKnowledgeAreaWorkInEvents,
			ArrayList<ActivitySectors> listActivitySectorsWorkInEvents, WorkInEvents workInEvents) {
		workInEvents.setAutores(listAuthorWorkInEvents);
		workInEvents.setAreasDeConhecimento(listKnowledgeAreaWorkInEvents);
		workInEvents.setSetoresDeAtividade(listActivitySectorsWorkInEvents);
		listWorkInEvents.add(workInEvents);
	}

	private void setListsInPaperPublished(ArrayList<PaperPublished> listWorkInEvents,
			ArrayList<Author> listAuthorWorkInEvents, ArrayList<KnowledgeArea> listKnowledgeAreaWorkInEvents,
			ArrayList<ActivitySectors> listActivitySectorsWorkInEvents, PaperPublished workInEvents) {
		workInEvents.setAutores(listAuthorWorkInEvents);
		workInEvents.setAreasDeConhecimento(listKnowledgeAreaWorkInEvents);
		workInEvents.setSetoresDeAtividade(listActivitySectorsWorkInEvents);
		listWorkInEvents.add(workInEvents);
	}

	private void setListsInBookChapter(ArrayList<BookChapter> listBibliographicProduction,
			ArrayList<Author> listAuthorWorkInEvents, ArrayList<KnowledgeArea> listKnowledgeAreaWorkInEvents,
			ArrayList<ActivitySectors> listActivitySectorsWorkInEvents, BookChapter bibliographicProduction) {
		bibliographicProduction.setAutores(listAuthorWorkInEvents);
		bibliographicProduction.setAreasDeConhecimento(listKnowledgeAreaWorkInEvents);
		bibliographicProduction.setSetoresDeAtividade(listActivitySectorsWorkInEvents);
		listBibliographicProduction.add(bibliographicProduction);
	}

	private ActivitySectors consumerActivitySectorys(Node node) {
		ActivitySectors activitySectors = new ActivitySectors();
		activitySectors.setSetorDeAtividade1(node.getAttributes().item(0).getNodeValue());
		activitySectors.setSetorDeAtividade2(node.getAttributes().item(1).getNodeValue());
		activitySectors.setSetorDeAtividade3(node.getAttributes().item(2).getNodeValue());
		return activitySectors;
	}

	private KnowledgeArea consumerKnowledArea(Node node) {
		KnowledgeArea knowledgeArea = new KnowledgeArea();
		NodeList dataNodeList = node.getChildNodes();
		for (int k = 0; k < dataNodeList.getLength(); k++) {
			if (dataNodeList.item(k).hasAttributes()) {
				knowledgeArea.setNomeAreaDoConhecimento(dataNodeList.item(k).getAttributes().item(0).getNodeValue());
				knowledgeArea.setNomeDaEspecialidade(dataNodeList.item(k).getAttributes().item(1).getNodeValue());
				knowledgeArea
						.setNomeGrandeAreaDoConhecimento(dataNodeList.item(k).getAttributes().item(2).getNodeValue());
				knowledgeArea.setNomeSubAreaDoConhecimento(dataNodeList.item(k).getAttributes().item(3).getNodeValue());
			}
		}
		return knowledgeArea;
	}

	private KeyWord consumerKeyWord(Node node) {
		KeyWord keyWord = new KeyWord();
		keyWord.setPalavraChave1(node.getAttributes().item(0).getNodeValue());
		keyWord.setPalavraChave2(node.getAttributes().item(1).getNodeValue());
		keyWord.setPalavraChave3(node.getAttributes().item(2).getNodeValue());
		keyWord.setPalavraChave4(node.getAttributes().item(3).getNodeValue());
		keyWord.setPalavraChave5(node.getAttributes().item(4).getNodeValue());
		keyWord.setPalavraChave6(node.getAttributes().item(5).getNodeValue());
		return keyWord;
	}

	private Author consumerAuthor(Node node) {
		Author author = new Author();
		author.setNomeCompleto(node.getAttributes().item(0).getNodeValue());
		author.setNomeParaCitacao(node.getAttributes().item(1).getNodeValue());
		author.setNroIdCnpq(node.getAttributes().item(2).getNodeValue());
		author.setOrdemParaAutoria(node.getAttributes().item(3).getNodeValue());
		return author;
	}

	private WorkInEvents consumerDatailWork(WorkInEvents workInEvents, Node node) {
		workInEvents.setAnoDeRealizacao(node.getAttributes().item(0).getNodeValue());
		workInEvents.setCidadeDaEditora(node.getAttributes().item(1).getNodeValue());
		workInEvents.setCidadeDoEvento(node.getAttributes().item(2).getNodeValue());
		workInEvents.setClassificacaoDoEvento(node.getAttributes().item(3).getNodeValue());
		workInEvents.setFasciculo(node.getAttributes().item(4).getNodeValue());
		workInEvents.setIsbn(node.getAttributes().item(5).getNodeValue());
		workInEvents.setNomeDaEditora(node.getAttributes().item(6).getNodeValue());
		workInEvents.setNomeDoEvento(node.getAttributes().item(7).getNodeValue());
		workInEvents.setNomeDoEventoIngles(node.getAttributes().item(8).getNodeValue());
		workInEvents.setPaginaFinal(node.getAttributes().item(9).getNodeValue());
		workInEvents.setPaginaInicial(node.getAttributes().item(10).getNodeValue());
		workInEvents.setSerie(node.getAttributes().item(11).getNodeValue());
		workInEvents.setTituloDosAnaisProceedings(node.getAttributes().item(12).getNodeValue());
		workInEvents.setVolume(node.getAttributes().item(13).getNodeValue());
		return workInEvents;
	}

	private PaperPublished consumerDatailWorkPublished(PaperPublished workInEvents, Node node) {
		workInEvents.setFasciculo(node.getAttributes().item(0).getNodeValue());
		workInEvents.setIsbn(node.getAttributes().item(1).getNodeValue());
		workInEvents.setNomeDoEvento(node.getAttributes().item(6).getNodeValue());
		workInEvents.setPaginaFinal(node.getAttributes().item(3).getNodeValue());
		workInEvents.setPaginaInicial(node.getAttributes().item(4).getNodeValue());
		workInEvents.setSerie(node.getAttributes().item(5).getNodeValue());
		workInEvents.setVolume(node.getAttributes().item(7).getNodeValue());
		return workInEvents;
	}

	private BookChapter consumerDatailBibliographicProduction(BookChapter bookChapter, Node node) {
		bookChapter.setCidadeDaEditora(node.getAttributes().item(0).getNodeValue());
		bookChapter.setIsbn(node.getAttributes().item(1).getNodeValue());
		bookChapter.setNomeDaEditora(node.getAttributes().item(2).getNodeValue());
		bookChapter.setSerie(node.getAttributes().item(4).getNodeValue());
		return bookChapter;
	}

	private WorkInEvents consumerBasicData(WorkInEvents workInEvents, Node node) {
		workInEvents.setAnoDoTrabalho(node.getAttributes().item(0).getNodeValue());
		workInEvents.setDoi(node.getAttributes().item(1).getNodeValue());
		workInEvents.setFlagDivulgacaoCientifica(node.getAttributes().item(1).getNodeValue());
		workInEvents.setFlagRelevancia(node.getAttributes().item(2).getNodeValue());
		workInEvents.setHomePageDoTrabalho(node.getAttributes().item(3).getNodeValue());
		workInEvents.setIdioma(node.getAttributes().item(4).getNodeValue());
		workInEvents.setMeioDeDivulgavao(node.getAttributes().item(5).getNodeValue());
		workInEvents.setNatureza(node.getAttributes().item(6).getNodeValue());
		workInEvents.setPaisDoEvento(node.getAttributes().item(7).getNodeValue());
		workInEvents.setTituloDoTrabalho(node.getAttributes().item(8).getNodeValue());
		workInEvents.setTituloIngles(node.getAttributes().item(9).getNodeValue());
		return workInEvents;
	}

	private PaperPublished consumerBasicDataPaperPublished(PaperPublished workInEvents, Node node) {
		workInEvents.setAnoDoTrabalho(node.getAttributes().item(0).getNodeValue());
		workInEvents.setDoi(node.getAttributes().item(1).getNodeValue());
		workInEvents.setFlagDivulgacaoCientifica(node.getAttributes().item(1).getNodeValue());
		workInEvents.setFlagRelevancia(node.getAttributes().item(2).getNodeValue());
		workInEvents.setHomePageDoTrabalho(node.getAttributes().item(3).getNodeValue());
		workInEvents.setIdioma(node.getAttributes().item(4).getNodeValue());
		workInEvents.setMeioDeDivulgavao(node.getAttributes().item(5).getNodeValue());
		workInEvents.setNatureza(node.getAttributes().item(6).getNodeValue());
		workInEvents.setPaisDoEvento(node.getAttributes().item(7).getNodeValue());
		workInEvents.setTituloDoTrabalho(node.getAttributes().item(8).getNodeValue());
		workInEvents.setTituloIngles(node.getAttributes().item(9).getNodeValue());
		return workInEvents;
	}

	private TechinicalProduction consumerBasicDataTechinicalProduction(TechinicalProduction techinicalProduction,
			Node node) {
		techinicalProduction.setAno(node.getAttributes().item(0).getNodeValue());
		techinicalProduction.setDoi(node.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFlagDivulgacaoCientifica(node.getAttributes().item(1).getNodeValue());
		techinicalProduction.setFlagRelevancia(node.getAttributes().item(2).getNodeValue());
		techinicalProduction.setHomePage(node.getAttributes().item(3).getNodeValue());
		techinicalProduction.setIdioma(node.getAttributes().item(4).getNodeValue());
		techinicalProduction.setMeioDeDivulgavao(node.getAttributes().item(5).getNodeValue());
		techinicalProduction.setNatureza(node.getAttributes().item(6).getNodeValue());
		techinicalProduction.setPaisDoEvento(node.getAttributes().item(7).getNodeValue());
		techinicalProduction.setTitulo(node.getAttributes().item(8).getNodeValue());
		techinicalProduction.setTituloIngles(node.getAttributes().item(9).getNodeValue());
		return techinicalProduction;
	}

	private BookChapter consumerBasicDataBibliographicProduction(BookChapter bookChapter, Node node) {
		bookChapter.setAnoDoTrabalho(node.getAttributes().item(0).getNodeValue());
		bookChapter.setDoi(node.getAttributes().item(1).getNodeValue());
		bookChapter.setFlagDivulgacaoCientifica(node.getAttributes().item(1).getNodeValue());
		bookChapter.setFlagRelevancia(node.getAttributes().item(2).getNodeValue());
		bookChapter.setHomePageDoTrabalho(node.getAttributes().item(3).getNodeValue());
		bookChapter.setIdioma(node.getAttributes().item(4).getNodeValue());
		bookChapter.setMeioDeDivulgavao(node.getAttributes().item(5).getNodeValue());
		bookChapter.setNatureza(node.getAttributes().item(6).getNodeValue());
		bookChapter.setPaisDoEvento(node.getAttributes().item(7).getNodeValue());
		bookChapter.setTituloDoTrabalho(node.getAttributes().item(8).getNodeValue());
		bookChapter.setTituloIngles(node.getAttributes().item(9).getNodeValue());
		return bookChapter;
	}

	private Curriculum consumerOrientationDataCurriculum(Curriculum curriculum, Document document, Properties prop,
			String orientationData) {
		NodeList generalDataNodeList = document.getElementsByTagName(orientationData);
		Node dataNode = generalDataNodeList.item(0);
		if (dataNode != null) {
			NodeList childNodesOrientation = dataNode.getChildNodes();
			ArrayList<Orientation> listOrientations = new ArrayList<Orientation>();
			ArrayList<KnowledgeArea> listKnowledgeArea = new ArrayList<KnowledgeArea>();
			for (int i = 0; i < childNodesOrientation.getLength(); i++) {

				Node childNode = childNodesOrientation.item(i);
				NodeList childNodeOrientationNodeList = document.getElementsByTagName(childNode.getNodeName());
				dataNode = childNodeOrientationNodeList.item(i);

				if (dataNode != null) {
					NodeList filhosDeIde2 = dataNode.getChildNodes();
					Orientation orientation = new Orientation();
					BookChapter bibliographicProduction = new BookChapter();
					for (int j = 0; j < filhosDeIde2.getLength(); j++) {
						Node node = filhosDeIde2.item(j);
						if (node.getNodeName().equals("DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")) {
							bibliographicProduction = consumerBasicDataBibliographicProduction(bibliographicProduction,
									node);
						}
						if (node.getNodeName().equals("DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")) {
							bibliographicProduction = consumerDatailBibliographicProduction(bibliographicProduction,
									node);
						}
						if (node.getNodeName().equals("AREAS-DO-CONHECIMENTO")) {
							// KnowledgeArea knowledgeArea =
							// consumerKnowledArea(node);
							// listKnowledgeArea.add(knowledgeArea);
						}
					}
					orientation.setAreasDeConhecimento(listKnowledgeArea);
					listOrientations.add(orientation);
					listKnowledgeArea = new ArrayList<KnowledgeArea>();
				}
			}
			curriculum.setOrientations(listOrientations);
		}
		return curriculum;
	}

	private Curriculum consumerGeneralDataCurriculum(Curriculum curriculum, Document document, Properties prop,
			String generalData) {
		Node dataNode = loadDataNode(document, generalData);

		curriculum.setNome_completo(consumerDataXml(dataNode, prop.getProperty("NOME_COMPLETO")));
		curriculum.setNome_em_citacoes_bibliograficas(
				consumerDataXml(dataNode, prop.getProperty("NOME_EM_CITACOES_BIBLIOGRAFICAS")));
		curriculum.setNacionalidade(consumerDataXml(dataNode, prop.getProperty("NACIONALIDADE")));
		curriculum.setPaisDeNascimento(consumerDataXml(dataNode, prop.getProperty("PAIS_DE_NASCIMENTO")));
		curriculum.setUfNascimento(consumerDataXml(dataNode, prop.getProperty("UF_NASCIMENTO")));
		curriculum.setCidadeNascimento(consumerDataXml(dataNode, prop.getProperty("CIDADE_NASCIMENTO")));
		curriculum.setPermissaoDeDivulgacao(consumerDataXml(dataNode, prop.getProperty("PERMISSAO_DE_DIVULGACAO")));
		curriculum.setDataFalecimento(consumerDataXml(dataNode, prop.getProperty("DATA_FALECIMENTO")));
		curriculum.setSiglaPaisNascimento(consumerDataXml(dataNode, prop.getProperty("SIGLA_PAIS_NACIONALIDADE")));
		curriculum.setPaisDeNacionalidade(consumerDataXml(dataNode, prop.getProperty("PAIS_DE_NACIONALIDADE")));

		return curriculum;
	}

	private Node loadDataNode(Document document, String tagName) {
		NodeList generalDataNodeList = document.getElementsByTagName(tagName);
		Node dataNode = generalDataNodeList.item(0);
		return dataNode;
	}

	protected String consumerDataXml(Node dataNode, String nameItem) {
		return dataNode.getAttributes().getNamedItem(nameItem).getNodeValue();
	}

	private Properties getProperties() {
		Properties properties = null;
		try {
			properties = new Properties();
			FileInputStream file = new FileInputStream("./properties/properties.properties");
			properties.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

}
