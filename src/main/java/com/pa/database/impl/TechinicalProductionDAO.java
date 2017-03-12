package com.pa.database.impl;

import org.hibernate.HibernateException;

import com.pa.database.GenericDAO;
import com.pa.entity.TechinicalProduction;

public class TechinicalProductionDAO extends GenericDAO<TechinicalProduction, Long>{

	public TechinicalProductionDAO(Class<TechinicalProduction> objectClass) {
		super(objectClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<TechinicalProduction> getEntityKlass() {
		return TechinicalProduction.class;
	}
	
	@Override
	public TechinicalProduction save(TechinicalProduction x) throws HibernateException {
		return super.save(x);
	}

}
