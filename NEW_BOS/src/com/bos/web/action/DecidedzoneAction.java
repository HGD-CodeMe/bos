package com.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.bos.domain.Decidedzone;
import com.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	//���շ���ID
	public String[] subareaid;
	public String[] getSubareaid() {
		return subareaid;
	}
	
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	public String add() {
		
		decidedzoneService.save(model,subareaid);
		return "list";
	}

	
    public String pageQuery() throws IOException {
		
		decidedzoneService.pageQuery(pageBean);
		
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}
    
    /**
	 * ��ѯû�й����������Ŀͻ�
	 * @return
	 * @throws IOException
	 */
	public String findnoassociationCustomers() throws IOException{
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{"station","address"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	/**
	 * ��ѯ�Ѿ�������ָ�������Ŀͻ�
	 * @return
	 * @throws IOException
	 */
	public String findhasassociationCustomers() throws IOException{
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		String[] excludes = new String[]{"station","address"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	private Integer[] customerIds;
	
	/**
	 * ���������ͻ�
	 * @return
	 */
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}

	public Integer[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
}
