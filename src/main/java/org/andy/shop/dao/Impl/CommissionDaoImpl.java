package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.CommissioinDao;
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
@Repository("CommissioinDao")
public class CommissionDaoImpl implements CommissioinDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	@Override
	public String addCommissioin(Map<String, String> map) throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();



		 String addUserGroupSql ="INSERT INTO commission_info ( product_id, transaction_price, transaction_time,customer_phone, guide_phone,is_task,task_name,task_phone, remark) VALUES (:product_id, :transaction_price, :customer_phone, :guide_phone,:is_task,:task_name,:task_phone,:remark)";
			paramSourceGroup.addValue("product_id", map.get("productId"));
			paramSourceGroup.addValue("transaction_price", map.get("transactionPrice"));
			paramSourceGroup.addValue("transaction_time", map.get("transactionTime"));
			paramSourceGroup.addValue("customer_phone", map.get("customerPhone"));
			paramSourceGroup.addValue("guide_phone", map.get("guidePhone"));
			paramSourceGroup.addValue("is_task", map.get("isTask"));
			paramSourceGroup.addValue("task_name", map.get("taskName"));
			paramSourceGroup.addValue("task_phone", map.get("taskPhone"));
			paramSourceGroup.addValue("remark", map.get("remark"));
		

		 int addSaleResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加销售信息结果:"+String.valueOf(addSaleResult));

		 return String.valueOf(addSaleResult);
	}
	@Override
	public List<Map<String, Object>> getCommissioinInfo(String openId) throws Exception{
	
		String sql = "select a.user_name ,a.user_phone,b.commi_ratio,b.commi_money,b.commi_status,b.customer_phone,b.customer_name From user_info a INNER JOIN commission_info b ON b.referee_phone=a.user_phone where a.open_id='"+openId+"'";

		List<Map<String, Object>> userinfoinfo = jdbcTemplate.queryForList(sql);

		return userinfoinfo;
	}

}
