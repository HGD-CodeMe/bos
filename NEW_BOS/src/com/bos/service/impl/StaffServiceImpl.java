package com.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.IstaffDao;
import com.bos.domain.Staff;
import com.bos.service.IStaffService;
import com.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{
	
	//注入dao
	@Autowired
	private IstaffDao staffDao;

	@Override
	public void save(Staff model) {
		staffDao.save(model);
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
		
	}

	@Override
	public void deleteBatch(String ids) {
		String[] staffIds = ids.split(",");
		for (String id : staffIds) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}

	@Override
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	@Override
	public void update(Staff staff) {
		staffDao.update(staff);
		
	}

	/**
	 * 查询没有作废的取派员
	 */
	@Override
	public List<Staff> findListNotDelete() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.ne("deltag", "1"));
		
		return staffDao.findByCriteria(detachedCriteria);
	}

}
