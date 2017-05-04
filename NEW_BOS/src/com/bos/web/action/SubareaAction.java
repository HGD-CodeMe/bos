package com.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bos.domain.Region;
import com.bos.domain.Subarea;
import com.bos.utils.FileUtils;
import com.bos.web.action.base.BaseAction;


@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	
	
	public String add() {
		
		subareaffService.save(model);
		return "list";
	}
	
	public String pageQuery() throws IOException {
		//查询之间封装条件
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		Region region = model.getRegion();
		if(StringUtils.isNotBlank(addresskey)) {
			//按照地址关键 字模糊查询 
			detachedCriteria.add(Restrictions.like("addresskey", addresskey));
		}
		
		if(region != null) {
			//为查询对象起一个别名，用于多表关联查询
			detachedCriteria.createAlias("region", "r");
			String city = region.getCity();
			String province = region.getProvince();
			String district = region.getDistrict();
			
			if(StringUtils.isNotBlank(province)) {
				//按照省市关联查询，这个方法不支持联级查询，但可以通过取一个别名来解决这个问题
				detachedCriteria.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			
			if(StringUtils.isNotBlank(city)) {
				//按照省市关联查询，这个方法不支持联级查询，但可以通过取一个别名来解决这个问题
				detachedCriteria.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			
			if(StringUtils.isNotBlank(district)) {
				//按照省市关联查询，这个方法不支持联级查询，但可以通过取一个别名来解决这个问题
				detachedCriteria.add(Restrictions.like("r.district",  "%"+district+"%"));
			}
			
		}
		 subareaffService.pageQuery(pageBean);
		 
		 String[] excludes = new String[] { "detachedCriteria", "currentPage",
					"pageSize", "decidedzone", "subareas" };
		 this.writePageBean2Json(pageBean, excludes);
		 return null;
	}
	
	/**
	 * 使用poi写入Excel表，提供下载
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		
		List<Subarea> list = subareaffService.findAll();
		
		
		//在内存中创建以一个Excel文件，通过输出流写到客户端提供下载
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
		}
		
		//这是中文就不好了
		String filename = "分区数据.xls";
		//获取当前浏览器的类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//解决 文件名中文乱码
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//一个流两个头
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	
	public String listajax() throws IOException {
		List<Subarea> list = subareaffService.finListNotAssociation();
		//这里可以根据 页面的需要来排除不需的要数据
		String[] excludes = new String[]{"decidedzone","region","endnum","startnum"};
		this.writePageBean2Json(list, excludes);
		return NONE;
	}
	
	

}
