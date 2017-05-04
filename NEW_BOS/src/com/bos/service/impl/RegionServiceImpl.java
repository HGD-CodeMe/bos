package com.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.IRegionDao;
import com.bos.domain.Region;
import com.bos.service.IRegionService;
import com.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	
	@Resource
	private IRegionDao regionDao;

	@Override
	public void saveBatch(List<Region> list) {
		//ѭ�� д��������Ա�ֻ֤��һ������
		for (Region region : list) {
			//����ʹ����������������ظ������ݻᷢ��������ͻ
			//regionDao.save(region)
			
			//saveOrUpdate����ID�Ƚ��Բ�ѯ�����ִ�и�����䣬����ִ��update��䣨ǰ�������� �����˸ı� ��
			regionDao.saveOrUpdate(region);
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	@Override
	public List<Region> findAll() {
		return regionDao.findAll();
		
	}

	@Override
	public List<Region> findByQ(String q) {
		
		return regionDao.findByQ(q);
	}
}
