package com.bos.service;

import java.util.List;

import com.bos.domain.Staff;
import com.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	

	/**
	 * @param ids
	 */
	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

	public void pageQuery(PageBean pageBean);



	public List<Staff> findListNotDelete();

	
	

}
