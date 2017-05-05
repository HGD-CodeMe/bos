package com.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bos.domain.Staff;
import com.bos.web.action.base.BaseAction;

/**
 * 取派员管理
 * @author HGD
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	
	/**
	 * 添加取派员
	 */
	public String add() {
		
		staffService.save(model);
		return "list";
	}
	
	public String pageQuery() throws IOException {
		
		staffService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria"});
		
		return NONE;
	}
	
	//接收IDS属性 
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 删除取派员，逻辑删除
	 * @return
	 */
	public String delete() {
		
		staffService.deleteBatch(ids);
		
		return "list";
				
	}
	
	public String edit() {
		//显示查询数据库中的原始数据
		Staff staff = staffService.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setDecidedzones(model.getDecidedzones());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		
		//这一句话写在action中是必须要的，如果在service中则不需要 
		staffService.update(staff);
		return "list";
		
	}
	/**
	 * 查询没有作废的取派员
	 * @throws IOException 
	 */
	public String listajax() throws IOException {
		
		List<Staff> list = staffService.findListNotDelete();
		String[] excludes = new String[]{"decidedzones","station"," standard","telephone"}; 
		this.writePageBean2Json(list, excludes);
		return NONE;
	}
}
