package com.bos.service;

import com.bos.domain.Decidedzone;
import com.bos.utils.PageBean;


public interface IDecidedzoneService {

	

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

	

}
