package com.bos.dao;

import java.util.List;

import com.bos.dao.base.IBaseDao;
import com.bos.domain.Region;

public interface IRegionDao extends  IBaseDao<Region>{

	List<Region> findByQ(String q);

}
