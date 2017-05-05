package com.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bos.dao.base.IBaseDao;
import com.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	//实体类型
	private Class<T> entityClass;
	
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}
	/**
	 * 在构造方法中动态获得的实体类型
	 */
	public BaseDaoImpl() {
		//获得父类（baseDaoImpl）类型
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
	    //获得父类上的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
		
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
		
		
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
		
		
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		
		String hql = " from " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSession();//从本地线程获得session对象
		Query query = session.getNamedQuery(queryName);
		
		session.createQuery("FROM User").list();
		//为HQL语句赋值
		int i = 0;
		for(Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();
		
	}
	
	/**
	 * 分页查询方法,这个方法避免了拼接SQL语句
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//总数据量
		//改变hibernate框架发出的SQL形式
		detachedCriteria.setProjection(Projections.rowCount());//这个方法可以调用很多数据库函数
		//这里方法把返回数据类型包装成long型
		List<Long> findByCriteria = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
		Long total = findByCriteria.get(0);
		pageBean.setTotal(total.intValue());//设置总数据量
		
		//当前页展示的数据
		//恢复SQL语句发出形式
		detachedCriteria.setProjection(null);
		//重置表与类映射的关系,（原来返回的是long型）
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = pageSize;
		
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria,firstResult,maxResult);
	    pageBean.setRows(rows);
	}
	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}
	
	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
