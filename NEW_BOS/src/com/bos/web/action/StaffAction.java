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
 * ȡ��Ա����
 * @author HGD
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	
	/**
	 * ���ȡ��Ա
	 */
	public String add() {
		
		staffService.save(model);
		return "list";
	}
	
	public String pageQuery() throws IOException {
		
		//�����������ĸ�service��һ��
		staffService.pageQuery(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","decidedzones"});
		
		return NONE;
	}
	
	//����IDS���� 
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * ɾ��ȡ��Ա���߼�ɾ��
	 * @return
	 */
	@RequiresPermissions(value="staff")//ִ�е�ǰ������Ҫ����staffȨ��
	@RequiresRoles(value="abc") 
	public String delete() {
		
		staffService.deleteBatch(ids);
		
		return "list";
				
	}
	
	public String edit() {
		//��ʾ��ѯ���ݿ��е�ԭʼ����
		Staff staff = staffService.findById(model.getId());
		
		staff.setName(model.getName());
		staff.setDecidedzones(model.getDecidedzones());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		
		//��һ�仰д��action���Ǳ���Ҫ�ģ������service������Ҫ 
		staffService.update(staff);
		return "list";
		
	}
	/**
	 * ��ѯû�����ϵ�ȡ��Ա
	 * @throws IOException 
	 */
	public String listajax() throws IOException {
		
		List<Staff> list = staffService.findListNotDelete();
		String[] excludes = new String[]{"decidedzones","station"," standard"}; 

		this.writeList2Json(list, excludes);

		return NONE;
	}
}
