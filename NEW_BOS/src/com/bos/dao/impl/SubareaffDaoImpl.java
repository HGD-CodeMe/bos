package com.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.bos.dao.ISubareaffDao;
import com.bos.dao.base.impl.BaseDaoImpl;
import com.bos.domain.Subarea;

@Repository
public class SubareaffDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaffDao{

	@Override
	public void save(Subarea model) {
		this.getHibernateTemplate().save(model);
	}
	
	
}
