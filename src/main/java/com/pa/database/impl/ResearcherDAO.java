package com.pa.database.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.pa.database.GenericDAO;
import com.pa.entity.Author;
import com.pa.entity.Researcher;
import com.pa.entity.Publication;

class ResearcherDAO extends GenericDAO<Researcher, Long> {

	public ResearcherDAO(Class<Researcher> objectClass) {
		super(objectClass);
	}
	

	@Override
	public Researcher save(Researcher x) throws HibernateException {
		PublicationDAO pDAO = new PublicationDAO(Publication.class);
		
		if(x.getPublications()!=null && !x.getPublications().isEmpty()) {
			for (Publication publication : x.getPublications()) {
				pDAO.save(publication);
			}
		}
		
		return super.save(x);
	}
	
	public Researcher getCurriculoByName(String name) throws HibernateException {

		Criteria criteria = createCriteria(Researcher.class);

		criteria.add(Restrictions.eq("name", name));

		Researcher curriculo = (Researcher) criteria.uniqueResult();
		
		return curriculo;
	}
	
	@Override
	protected Class<Researcher> getEntityKlass() {
		return Researcher.class;
	}

}
