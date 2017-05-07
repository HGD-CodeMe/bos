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
import com.bos.service.IFunctionService;
import com.bos.service.INoticebillService;
import com.bos.service.IRegionService;
import com.bos.service.IRoleService;
import com.bos.service.IStaffService;
import com.bos.service.ISubareaffService;
import com.bos.service.IUserService;
import com.bos.service.IWorkordermanageService;
import com.bos.utils.PageBean;
import com.bos.web.action.DecidedzoneAction;
import com.crm.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	//模型对象
	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	
	//注入service
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
	@Autowired
	protected INoticebillService noticebillService;
	@Autowired
	protected IWorkordermanageService workordermanageService;
	@Autowired
	protected IFunctionService functionService;
	@Autowired
	protected IRoleService roleService;
	//分页
	DetachedCriteria detachedCriteria = null;
		
	protected PageBean pageBean  = new PageBean();
	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	/**
	 * 在构造方法中动态获得实现类型，通过反射创建模型对象
	 */
	public BaseAction() {
		ParameterizedType genericSuperclass = null;
		if(this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
			genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		}else {//当前为这个action创建了代理
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//获得实体类型
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		try {
			//实例化 并赋值
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void writePageBean2Json(PageBean pageBean, String[] excludes) throws IOException {
		// 将pagebean对象装换为json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);

		// 将pagebean对象装换为json对象
		JSONObject fromObject = JSONObject.fromObject(pageBean,jsonConfig);

		String json = fromObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	



	protected void writeList2Json(List list, String[] excludes) throws IOException {

		// 将pagebean对象装换为json
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(excludes);

				// 将pagebean对象装换为json对象
				JSONArray fromObject = JSONArray.fromObject(list,jsonConfig);

				String json = fromObject.toString();
				ServletActionContext.getResponse().setContentType(
						"text/json;charset=UTF-8");
				ServletActionContext.getResponse().getWriter().print(json);
		
	}
	
	//这个最通用
	public void writeObject2Json(Object object, String[] excludes) throws IOException {
		// 将pagebean对象装换为json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);

		// 将pagebean对象装换为json对象
		JSONObject fromObject = JSONObject.fromObject(object,jsonConfig);

		String json = fromObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
}
