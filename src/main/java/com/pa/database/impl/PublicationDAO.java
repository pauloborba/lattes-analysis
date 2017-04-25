package com.pa.database.impl;

import org.hibernate.HibernateException;

import com.pa.database.GenericDAO;
import com.pa.entity.Author;
import com.pa.entity.Publication;
import com.pa.entity.PublicationType;

class PublicationDAO extends GenericDAO<Publication, Long> {

	public PublicationDAO(Class<Publication> objectClass) {
		super(objectClass);
	}

	@Override
	protected Class<Publication> getEntityKlass() {
		return Publication.class;
	}

	@Override
	public Publication save(Publication x) throws HibernateException {
		
		if(x.getPublicationType() != null && x.getPublicationType().getName()!=null) {
			PublicationTypeDAO pTDAO = new PublicationTypeDAO(PublicationType.class);
			
			PublicationType pT = pTDAO.getPublicationTypeByNameAndType(x.getPublicationType().getName(), x.getPublicationType().getType());
			
			if(pT == null) {
				pT = pTDAO.save(x.getPublicationType());
			}
			
			x.setPublicationType(pT);
		}
		
		Author at = null;
		if ( x.getAuthors() != null && !x.getAuthors().isEmpty()) {
			AuthorDAO aDAO = new AuthorDAO(Author.class);
			for (int i = 0; i < x.getAuthors().size(); i++) {
				System.out.println(x.getAuthors().get(i).getNomeCompleto());
				at = aDAO.getAuthorByName(x.getAuthors().get(i).getId());
				
				if (at == null) {
					aDAO.save(x.getAuthors().get(i));
				}
				else{
					//aDAO.merge(x.getAuthors().get(i));
					//aDAO.update(at);
					//x.getAuthors().set(i, at);
				}
			}
		}
		at = null;
		System.out.println(x.getTitle());
		return super.save(x);
	}
}
