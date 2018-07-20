package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
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
	
		String sql = "select  product_info, trans_money, trans_time,customer_phone, open_id,is_task,task_name,task_phone, remark From sales_info a  where a.open_id='"+openId+"' order by trans_time,sales_id limit "+startIndex+" , "+indexSize+"   ";

		List<Map<String, Object>> customerReportList = jdbcTemplate.queryForList(sql);

		return customerReportList;
	}
	
	//线上订单管理
	@Override
	public String addGoodsOnlineOrder(Map<String, String> map) throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

		 String addUserGroupSql ="INSERT INTO goods_order ( goods_order_id, open_id, order_status,total_num, total_fee,receive_address_id,pay_time,create_time) VALUES (:goods_order_id, :open_id, :order_status,:total_num, :total_fee,:receive_address_id,:pay_time,:create_time)";
			paramSourceGroup.addValue("goods_order_id", map.get("goodsOrderId"));
			paramSourceGroup.addValue("open_id", map.get("openId"));
			paramSourceGroup.addValue("order_status", map.get("orderStatus"));
			paramSourceGroup.addValue("total_num", map.get("totalNum"));
			paramSourceGroup.addValue("total_fee", map.get("totalFee"));
			paramSourceGroup.addValue("receive_address_id", map.get("receiveAddressId"));
			if("02"==map.get("orderStatus"))
			{
				paramSourceGroup.addValue("pay_time", Utils.getCurrentDate());
			}else{
				paramSourceGroup.addValue("pay_time", "00:00:00");
			}
			
			paramSourceGroup.addValue("create_time", Utils.getCurrentDate());
			
		

		 int addSaleResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加销售信息结果:"+String.valueOf(addSaleResult));

		 return String.valueOf(addSaleResult);
	}
	
	//线上订单管理
	@Override
	public String addGoodsdetailList(Map<String, String> map) throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

		 String addUserGroupSql ="INSERT INTO goods_order_map ( goods_order_id,goods_id, goods_tile, goods_format,goods_price, buy_number,goods_image_url,create_time) VALUES (:goods_order_id,:goods_id, :goods_tile, :goods_format,:goods_price, :buy_number,:goods_image_url,:create_time)";
			paramSourceGroup.addValue("goods_order_id", map.get("goodsOrderId"));
			paramSourceGroup.addValue("goods_id", map.get("goods_id"));
			paramSourceGroup.addValue("open_id", map.get("openId"));
			paramSourceGroup.addValue("goods_tile", map.get("goods_title"));
			paramSourceGroup.addValue("goods_format", map.get("format_name"));
			paramSourceGroup.addValue("goods_price", map.get("price"));
			paramSourceGroup.addValue("buy_number", map.get("num"));
			paramSourceGroup.addValue("goods_image_url", map.get("goods_image_server")+map.get("goods_image_url"));
			
			paramSourceGroup.addValue("create_time", Utils.getCurrentDate());
			
		

		 int addSaleResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加销售信息结果:"+String.valueOf(addSaleResult));

		 return String.valueOf(addSaleResult);
	}
	@Override
	public List<Map<String, Object>>  getGoodsOrderListByOpenId(Map<String, String> map) throws Exception{
			
		List<Map<String, Object>> reList=new ArrayList<Map<String,Object>>();
			String openId =map.get("openId");
			
			String sql = "select a.* from goods_order_map a INNER JOIN  goods_order b on b.goods_order_id=a.goods_order_id where b.open_id  ='"+openId+"' ORDER BY a.create_time ASC  ;";
	
			List<Map<String, Object>> goodsOrderList = jdbcTemplate.queryForList(sql);
			
//			for(Map<String,Object> mapTmp:goodsClassList)
//			{
//				Map<String, Object> reMap =new HashMap<String, Object>();
//				reMap.putAll(mapTmp);
//				String goodsOrderId = (String) mapTmp.get("goods_order_id");
//				//查询订单详细列表
//				String detailsql = "select *From goods_order a   where a.open_id='"+openId+"' ORDER BY a.create_time ASC  ;";
//				
//				List<Map<String, Object>> orderDeailList = jdbcTemplate.queryForList(detailsql);
//				
//				reMap.put("orderDeailList", orderDeailList);
//				
//				//加入返回列表
//				reList.add(reMap);
//				
//			}
			
			
	
			return goodsOrderList;
	}
	
	
}
