package com.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.bos.utils.PageBean;

/**
 * 抽取持久层通用方法
 * @author HGD
 *
 * @param <T>
 */

public interface IBaseDao<T> {
	
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);//不管什么基本类型，都实现了序列胡接口
	public List<T> findAll();
	public void saveOrUpdate(T entity);
	public List<T> findByCriteria(DetachedCriteria detachedCriteria); 
	
	//提供通用修改方法
	public void executeUpdate(String queryName, Object ...objects);
	//分页查询
	public void pageQuery(PageBean pageBean);

}
