package com.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.bos.utils.PageBean;

/**
 * ��ȡ�־ò�ͨ�÷���
 * @author HGD
 *
 * @param <T>
 */

public interface IBaseDao<T> {
	
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);//����ʲô�������ͣ���ʵ�������к��ӿ�
	public List<T> findAll();
	public void saveOrUpdate(T entity);
	public List<T> findByCriteria(DetachedCriteria detachedCriteria); 
	
	//�ṩͨ���޸ķ���
	public void executeUpdate(String queryName, Object ...objects);
	//��ҳ��ѯ
	public void pageQuery(PageBean pageBean);

}
