package com.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bos.dao.IRegionDao;
import com.bos.dao.base.impl.BaseDaoImpl;
import com.bos.domain.Region;
import com.bos.utils.PageBean;


@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	@Override
	public List<Region> findByQ(String q) {
		String Hql = "FORM Region WHERE provice LIKE ? OR city LIKE ? OR district LIKE ? ";
		
		return this.getHibernateTemplate().find(Hql,"%"+q+"%","%"+q+"%","%"+q+"%");
	}

	

}
