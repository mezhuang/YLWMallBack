package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.dao.CustomerReportDao;
import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.entity.UserInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 创建时间：2015-1-30 上午11:25:30
 * 
 * @author andy
 * @version 2.2
 * 
 * CustomerInfoDao实现类
 */
@Repository("CustomerReport")
public class CustomerReportDaoImpl implements CustomerReportDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public CustomerReportPo getById(Integer id) {

		String sql = "SELECT * FROM customer_report WHERE customer_report_id = ?";

		CustomerReportPo CustomerReport = jdbcTemplate.queryForObject(sql, new CustomerReportPo(),
				new Object[] { id });

		return CustomerReport;
	}

	@Override
	public List<CustomerReportPo> findAll() {
		String sql = "SELECT * FROM customer_report";
		List<CustomerReportPo> CustomerReports = jdbcTemplate.query(sql, new CustomerReportPo());
		return CustomerReports;
	}

	@Override
	public Integer save(CustomerReportPo entity) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		String sql = "INSERT INTO customer_report(customer_phone, visit_time,task_phone,is_task,guide_phone,report_time,remark,open_id) VALUES(:customer_phone, :visit_time, :task_phone,:is_task,:guide_phone,:visit_time,:remark,:open_id)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("customer_phone", entity.getCustomerPhone().trim());
		paramSource.addValue("visit_time", entity.getVisitTime().trim());
		paramSource.addValue("task_phone", entity.getTaskPhone().trim());
		paramSource.addValue("is_task", entity.getIsTask().trim());
		paramSource.addValue("guide_phone", entity.getGuidePhone().trim());
		paramSource.addValue("remark", entity.getRemark().trim());
		paramSource.addValue("open_id", entity.getOpenId().trim());
		int result = namedParameterJdbcTemplate.update(sql, paramSource);
		
		return result;
	}
	@Override
	public List<Map<String, Object>> getAllReportcustomerList(String openId,String startIndex,String indexSize) {
	
		String sql = "SELECT customer_phone,visit_time,task_phone,is_task,guide_phone,remark FROM customer_report where open_id='"+openId+"' limit "+startIndex+" , "+indexSize+" ";

		List<Map<String, Object>> customerReportList = jdbcTemplate.queryForList(sql);

		return customerReportList;
	}
	
	public boolean isreportExpire(String customerPhone)
	{
			boolean flag =false;
			
			
			
			return flag;
		
		
	}


}
