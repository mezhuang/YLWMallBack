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
import org.andy.shop.common.YLConstant;
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
			LOGGER.info("addCmmission map:"+map);
			
//			commisionMap.put("commi_ratio",String.valueOf(Constant.refereeManagerNoTaskRadio));
//			commisionMap.put("commi_money", String.valueOf(commissionSize));
//			
//		}
//		commisionMap.put("commi_status", "1");
//		commisionMap.put("referee_phone", reFereeUserPhone);
//		commisionMap.put("customer_phone", customerPhone);
//		commisionMap.put("customer_name", customerName);

		 String addUserGroupSql ="INSERT INTO commission_info (product_info , commi_ratio,commi_money, commi_status,customer_name,customer_phone,is_task, referee_phone) VALUES (:product_info, :commi_ratio,:commi_money, :commi_status,:customer_name,:customer_phone,:is_task, :referee_phone)";
			paramSourceGroup.addValue("product_info", map.get("productInfo"));
			paramSourceGroup.addValue("commi_ratio", map.get("commiRatio"));
			paramSourceGroup.addValue("commi_money", map.get("commiMoney"));
			paramSourceGroup.addValue("commi_status", map.get("commiStatus"));
			paramSourceGroup.addValue("customer_name", map.get("customerName"));
			paramSourceGroup.addValue("is_task", map.get("isTask"));
			paramSourceGroup.addValue("customer_phone", map.get("customerPhone"));
			paramSourceGroup.addValue("referee_phone", map.get("refereePhone"));
		

		 int addSaleResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加结算佣金结果:"+String.valueOf(addSaleResult));

		 return String.valueOf(addSaleResult);
	}
	@Override
	public List<Map<String, Object>> getCommissioinInfo(String openId) throws Exception{
	
		String sql = "select a.user_name ,a.user_phone,b.commi_ratio,b.commi_money,b.commi_status,b.customer_phone,b.customer_name From user_info a INNER JOIN commission_info b ON b.referee_phone=a.user_phone where a.open_id='"+openId+"'";

		List<Map<String, Object>> userinfoinfo = jdbcTemplate.queryForList(sql);

		return userinfoinfo;
	}

}
