package com.bos.web.action;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bos.domain.Decidedzone;
import com.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	
	//接收分区ID
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
}
