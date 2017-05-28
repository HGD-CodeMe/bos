package com.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
		
		//这里无论用哪个service都一样
		staffService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","decidedzones"});
		
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
	@RequiresPermissions(value="staff")//执行当前方法需要具有staff权限
	@RequiresRoles(value="abc") 
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
		String[] excludes = new String[]{"decidedzones","station"," standard"}; 

		this.writeList2Json(list, excludes);

		return NONE;
	}
}
