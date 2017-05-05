package com.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.bos.domain.Region;
import com.bos.service.IDecidedzoneService;
import com.bos.service.IRegionService;
import com.bos.service.IStaffService;
import com.bos.service.ISubareaffService;
import com.bos.service.IUserService;
import com.bos.utils.PageBean;
import com.bos.web.action.DecidedzoneAction;
import com.crm.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	//ģ�Ͷ���
	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	
	//ע��service
	@Resource
	protected IUserService userService;
	@Resource
	protected CustomerService customerService;
	@Autowired
	protected IRegionService regionService;
	@Autowired
	protected IStaffService staffService;
	@Autowired
	protected ISubareaffService subareaffService;
	@Autowired
	protected IDecidedzoneService decidedzoneService;
	
	
	//��ҳ
	DetachedCriteria detachedCriteria = null;
		
	protected PageBean pageBean  = new PageBean();
	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	/**
	 * �ڹ��췽���ж�̬���ʵ�����ͣ�ͨ�����䴴��ģ�Ͷ���
	 */
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//���ʵ������
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		try {
			//ʵ���� ����ֵ
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void writePageBean2Json(PageBean pageBean, String[] excludes) throws IOException {
		// ��pagebean����װ��Ϊjson
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);

		// ��pagebean����װ��Ϊjson����
		JSONObject fromObject = JSONObject.fromObject(pageBean,jsonConfig);

		String json = fromObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	



	protected void writeList2Json(List list, String[] excludes) throws IOException {

		// ��pagebean����װ��Ϊjson
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(excludes);

				// ��pagebean����װ��Ϊjson����
				JSONArray fromObject = JSONArray.fromObject(list,jsonConfig);

				String json = fromObject.toString();
				ServletActionContext.getResponse().setContentType(
						"text/json;charset=UTF-8");
				ServletActionContext.getResponse().getWriter().print(json);
		
	}
}
