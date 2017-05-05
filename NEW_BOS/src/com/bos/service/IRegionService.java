package com.bos.service;

import java.util.List;

import javax.annotation.Resource;



import com.bos.dao.IRegionDao;
import com.bos.domain.Region;
import com.bos.utils.PageBean;


public interface IRegionService {

	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findByQ(String q);

	
}
