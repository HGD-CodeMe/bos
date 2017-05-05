package com.bos.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.IDecidedzoneDao;
import com.bos.dao.ISubareaffDao;
import com.bos.domain.Decidedzone;
import com.bos.domain.Subarea;
import com.bos.service.IDecidedzoneService;
import com.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	
	@Autowired
	private IDecidedzoneDao decidedzoneDao;

	@Autowired
	private ISubareaffDao subareaffDao;
		
	
	/**
	 * 添加定区的方法
	 */
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		
		decidedzoneDao.save(model);
		for (String sid : subareaid) {
			Subarea subarea = subareaffDao.findById(sid);
			//分区对象关联定区对象--多方关联一方
			subarea.setDecidedzone(model);
		}
	}


	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
		
	}


	

	
}
