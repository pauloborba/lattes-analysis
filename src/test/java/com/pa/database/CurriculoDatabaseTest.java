package com.pa.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pa.database.impl.DatabaseFacade;
import com.pa.database.util.HibernateUtil;
import com.pa.entity.Researcher;
import com.pa.entity.Publication;
import com.pa.entity.PublicationType;
import com.pa.util.EnumPublicationLocalType;

public class CurriculoDatabaseTest {

	
	@Before
	public void createSessionFactory() {
		HibernateUtil.createSessionFactory("hibernateTest.cfg.xml");
	}
	
	@After
	public void closeSession() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		if(sessionFactory.getCurrentSession().isOpen()) {
			sessionFactory.getCurrentSession().close();
		}
		
		if(!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
	
	@Test
	public void saveCurriculoTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		assertNotNull(curriculo.getId());
	}
	
	@Test
	public void saveCurriculoWithPublicationTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		curriculo.getPublications().add(new Publication("Publication1", 2015, new PublicationType("TipoDePublicacao1", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		
		List<Publication> publications = DatabaseFacade.getInstance().listAllPublications();
		
		assertEquals(publications.size(), 1);
	}
	
	@Test
	public void deleteCurriculoTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		DatabaseFacade.getInstance().deleteCurriculo(curriculo);
		
		Researcher curriculoBD = DatabaseFacade.getInstance().getCurriculoById(curriculo.getId());
		
		assertNull(curriculoBD);
	}
	
	@Test
	public void updatePublicationTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		curriculo.getPublications().add(new Publication("Publication1", 2015, new PublicationType("TipoDePublicacao1", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		
		curriculo.getPublications().add(new Publication("Publication2", 2015, new PublicationType("TipoDePublicacao2", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().updateCurriculo(curriculo);
		
		assertEquals(curriculo.getPublications().size(), 2);
	}
	
	@Test
	public void listAllPublicationTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		curriculo.getPublications().add(new Publication("Publication1", 2015, new PublicationType("TipoDePublicacao1", EnumPublicationLocalType.CONFERENCE), null));
		
		Researcher curriculo2 = new Researcher("Curriculo2", new Date());
		curriculo2.setId(10257l);
		curriculo2.getPublications().add(new Publication("Publication2", 2015, new PublicationType("TipoDePublicacao2", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		DatabaseFacade.getInstance().saveCurriculo(curriculo2);
		
		List<Researcher> qualisList = DatabaseFacade.getInstance().listAllCurriculos();
		
		assertEquals(qualisList.size(), 2);
	}
	
	@Test
	public void listAllPublicationByObjectTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		curriculo.getPublications().add(new Publication("Publication1", 2015, new PublicationType("TipoDePublicacao1", EnumPublicationLocalType.CONFERENCE), null));
		
		Researcher curriculo2 = new Researcher("Curriculo2", new Date());
		curriculo2.setId(10257l);
		curriculo2.getPublications().add(new Publication("Publication2", 2015, new PublicationType("TipoDePublicacao2", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		DatabaseFacade.getInstance().saveCurriculo(curriculo2);
		
		List<Researcher> list = DatabaseFacade.getInstance().listAllCurriculos(curriculo);
		
		assertEquals(list.size(), 1);
	}
	
	@Test
	public void listAllCurriculosByQueryTest() {
		Researcher curriculo = new Researcher("Curriculo1", new Date());
		curriculo.setId(10256l);
		curriculo.getPublications().add(new Publication("Publication1", 2015, new PublicationType("TipoDePublicacao1", EnumPublicationLocalType.CONFERENCE), null));
		
		Researcher curriculo2 = new Researcher("Curriculo2", new Date());
		curriculo2.setId(10257l);
		curriculo2.getPublications().add(new Publication("Publication2", 2015, new PublicationType("TipoDePublicacao2", EnumPublicationLocalType.CONFERENCE), null));
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		DatabaseFacade.getInstance().saveCurriculo(curriculo2);
		
		List<Researcher> cList = DatabaseFacade.getInstance().listAllCurriculosByQuery("from Curriculo");
		
		assertEquals(cList.size(), 2);
	}
	
	@Test
	public void getCurriculoByNameTest() {
		String name = "Curriculo1";
		
		Researcher curriculo = new Researcher(name, new Date());
		curriculo.setId(10256l);
		
		DatabaseFacade.getInstance().saveCurriculo(curriculo);
		Researcher cAux = DatabaseFacade.getInstance().getCurriculoByName(name);
		
		assertNotNull(cAux);
	}
}
