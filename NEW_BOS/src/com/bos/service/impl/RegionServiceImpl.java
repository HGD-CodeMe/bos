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
		//循环 写在这里可以保证只有一个事务
		for (Region region : list) {
			//这里使用这个方法，导入重复的数据会发生主键冲突
			//regionDao.save(region)
			
			//saveOrUpdate根据ID先进性查询如果有执行更新语句，否则执行update语句（前提是数据 发生了改变 ）
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
