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

	//ʵ������
	private Class<T> entityClass;
	
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}
	/**
	 * �ڹ��췽���ж�̬��õ�ʵ������
	 */
	public BaseDaoImpl() {
		//��ø��ࣨbaseDaoImpl������
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
	    //��ø����ϵķ�������
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
		Session session = this.getSession();//�ӱ����̻߳��session����
		Query query = session.getNamedQuery(queryName);
		
		session.createQuery("FROM User").list();
		//ΪHQL��丳ֵ
		int i = 0;
		for(Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();
		
	}
	
	/**
	 * ��ҳ��ѯ����,�������������ƴ��SQL���
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//��������
		//�ı�hibernate��ܷ�����SQL��ʽ
		detachedCriteria.setProjection(Projections.rowCount());//����������Ե��úܶ����ݿ⺯��
		//���﷽���ѷ����������Ͱ�װ��long��
		List<Long> findByCriteria = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
		Long total = findByCriteria.get(0);
		pageBean.setTotal(total.intValue());//������������
		
		//��ǰҳչʾ������
		//�ָ�SQL��䷢����ʽ
		detachedCriteria.setProjection(null);
		//���ñ�����ӳ��Ĺ�ϵ,��ԭ�����ص���long�ͣ�
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
