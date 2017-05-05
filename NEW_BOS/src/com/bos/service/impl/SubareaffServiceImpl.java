package com.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.ISubareaffDao;
import com.bos.domain.Subarea;
import com.bos.service.ISubareaffService;
import com.bos.utils.PageBean;

@Service
@Transactional
public class SubareaffServiceImpl implements ISubareaffService{
	
	@Resource
	private ISubareaffDao subareaffDao;

	@Override
	public void save(Subarea model) {
		subareaffDao.save(model);
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		subareaffDao.pageQuery(pageBean);
		
	}

	@Override
	public List<Subarea> findAll() {
		
		return subareaffDao.findAll();
	}
    
	/**
	 * 查询没有关联到到 定区的分区
	 */
	@Override
	public List<Subarea> finListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaffDao.findByCriteria(detachedCriteria);
	}
	
	
}
