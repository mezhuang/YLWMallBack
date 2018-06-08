package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.SaleInfoDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.common.Constant;
import org.andy.shop.controller.CustomerReportController;
import org.apache.log4j.Logger;
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
@Repository("SaleInfoDao")
public class SaleInfoDaoImpl implements SaleInfoDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	@Override
	public String addSaleInfo(Map<String, String> map) throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

		 String addUserGroupSql ="INSERT INTO sales_info ( product_info, trans_money, trans_time,customer_phone, open_id,is_task,task_name,task_phone, remark) VALUES (:product_info, :trans_money, :trans_time,:customer_phone, :open_id,:is_task,:task_name,:task_phone,:remark)";
			paramSourceGroup.addValue("product_info", map.get("productInfo"));
			paramSourceGroup.addValue("trans_money", map.get("transMoney"));
			paramSourceGroup.addValue("trans_time", map.get("transTime"));
			paramSourceGroup.addValue("customer_phone", map.get("customerPhone"));
			paramSourceGroup.addValue("open_id", map.get("openId"));
			paramSourceGroup.addValue("is_task", map.get("isTask"));
			paramSourceGroup.addValue("task_name", map.get("taskName"));
			paramSourceGroup.addValue("task_phone", map.get("taskPhone"));
			paramSourceGroup.addValue("remark", map.get("remark"));
		

		 int addSaleResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加销售信息结果:"+String.valueOf(addSaleResult));

		 return String.valueOf(addSaleResult);
	}

	@Override
	public List<Map<String, Object>> getSaleInfoList(String openId,String startIndex,String indexSize) throws Exception{
	
		String sql = "select  product_info, trans_money, trans_time,customer_phone, open_id,is_task,task_name,task_phone, remark From sales_info a  where a.open_id='"+openId+"' limit "+startIndex+" , "+indexSize+"   ORDER BY trans_time desc ";

		List<Map<String, Object>> customerReportList = jdbcTemplate.queryForList(sql);

		return customerReportList;
	}
}
