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
	 * ��Ӷ����ķ���
	 */
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		
		decidedzoneDao.save(model);
		for (String sid : subareaid) {
			Subarea subarea = subareaffDao.findById(sid);
			//�������������������--�෽����һ��
			subarea.setDecidedzone(model);
		}
	}


	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
		
	}


	

	
}
