package com.bos.service;

import java.util.List;

import com.bos.domain.Subarea;
import com.bos.utils.PageBean;



public interface ISubareaffService {
	
	

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public List<Subarea> finListNotAssociation();

}
