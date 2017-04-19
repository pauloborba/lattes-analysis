package com.pa.database.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import com.pa.database.GenericDAO;
import com.pa.entity.Author;

public class AuthorDAO extends GenericDAO<Author, Long> {

	public AuthorDAO(Class<Author> objectClass) {
		super(objectClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<Author> getEntityKlass() {
		return Author.class;
	}
	
	@Override
	public Author save(Author x) throws HibernateException {
		return super.save(x);
	}

	public Author getAuthorByName(Long name) {
		Criteria criteria = createCriteria(Author.class);

		criteria.add(Restrictions.eq("id", name));

		Author author = (Author) criteria.uniqueResult();
		
		return author;
	}

}
