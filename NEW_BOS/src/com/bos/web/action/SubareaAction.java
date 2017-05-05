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
		//��ѯ֮���װ����
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		Region region = model.getRegion();
		if(StringUtils.isNotBlank(addresskey)) {
			//���յ�ַ�ؼ� ��ģ����ѯ 
			detachedCriteria.add(Restrictions.like("addresskey", addresskey));
		}
		
		if(region != null) {
			//Ϊ��ѯ������һ�����������ڶ�������ѯ
			detachedCriteria.createAlias("region", "r");
			String city = region.getCity();
			String province = region.getProvince();
			String district = region.getDistrict();
			
			if(StringUtils.isNotBlank(province)) {
				//����ʡ�й�����ѯ�����������֧��������ѯ��������ͨ��ȡһ������������������
				detachedCriteria.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			
			if(StringUtils.isNotBlank(city)) {
				//����ʡ�й�����ѯ�����������֧��������ѯ��������ͨ��ȡһ������������������
				detachedCriteria.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			
			if(StringUtils.isNotBlank(district)) {
				//����ʡ�й�����ѯ�����������֧��������ѯ��������ͨ��ȡһ������������������
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
	 * ʹ��poiд��Excel���ṩ����
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		
		List<Subarea> list = subareaffService.findAll();
		
		
		//���ڴ��д�����һ��Excel�ļ���ͨ�������д���ͻ����ṩ����
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		//����һ��sheetҳ
		HSSFSheet sheet = workbook.createSheet("��������");
		
		//����������
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("�������");
		headRow.createCell(1).setCellValue("������");
		headRow.createCell(2).setCellValue("��ַ�ؼ���");
		headRow.createCell(3).setCellValue("ʡ����");
		
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
		}
		
		//�������ľͲ�����
		String filename = "��������.xls";
		//��ȡ��ǰ�����������
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//��� �ļ�����������
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//һ��������ͷ
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	
	public String listajax() throws IOException {
		List<Subarea> list = subareaffService.finListNotAssociation();
		//������Ը��� ҳ�����Ҫ���ų������Ҫ����
		String[] excludes = new String[]{"decidedzone","region","endnum","startnum"};
		this.writePageBean2Json(list, excludes);
		return NONE;
	}
	
	

}
