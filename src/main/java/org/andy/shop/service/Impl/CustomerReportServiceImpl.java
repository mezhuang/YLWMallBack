package org.andy.shop.service.Impl;

import java.util.List;
import java.util.Map;

import org.andy.shop.dao.CustomerReportDao;
import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.service.CustomerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建时间：2015-1-30 上午11:40:19
 * 
 * @author andy
 * @version 2.2 实现接口
 */

@Service("CustomerReportService")
public class CustomerReportServiceImpl implements CustomerReportService {

	@Autowired
	private CustomerReportDao CustomerReportDao;

	@Override
	public CustomerReportPo getById(Integer id) {
		return CustomerReportDao.getById(id);
	}

	@Override
	public List<CustomerReportPo> findAll() {
		return CustomerReportDao.findAll();
	}

	@Override
	public Integer save(CustomerReportPo customerReport) {
		return CustomerReportDao.save(customerReport);
	}

	@Override
	public List<Map<String, Object>> getAllReportcustomerList(String openId,
			String startIndex, String indexSize) {
		List<Map<String, Object>> reportCustomerList= CustomerReportDao.getAllReportcustomerList(openId, startIndex, indexSize);
		return reportCustomerList;
	}
	
//	@Override
//	public List<Map<String, Object>> getAllReportcustomerList(String openId,String startIndex,String indexSize) {
//		return CustomerReportDao.getAllReportcustomerList(openId, startIndex, indexSize);
//	}
	
	
	

}
