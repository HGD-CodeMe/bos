package com.crm;

import java.util.List;

import cn.itcast.crm.domain.Customer;

// 客户服务接口 
public interface CustomerService {
	// 未关联定区客�?
	public List<Customer> findnoassociationCustomers();

	// 查询已经关联指定定区的客�?
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// 将未关联定区客户关联到定区上
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
	
	//根据电话号码关联指定用户信息
	public Customer findCustomerByPhoneNum(String phoneNum);
	
	 //根据取件地址查询定区ID
	public String findDecidedzoneIdByPickaddress(String address);
}
