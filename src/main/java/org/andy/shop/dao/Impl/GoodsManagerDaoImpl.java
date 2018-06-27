package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.GoodsManagerDao;
import org.andy.shop.dao.GroupMapDao;
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
import java.util.UUID;
/**
 * 创建时间：2015-1-30 上午11:25:30
 * 
 * @author andy
 * @version 2.2
 * 
 * CustomerInfoDao实现类
 */
@Repository("GoodsManagerDao")
public class GoodsManagerDaoImpl implements GoodsManagerDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	@Override
	public String addGoodsRecord(Map<String,String> map)throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
			//产生uuid
			String uuid = UUID.randomUUID().toString();
			
		//1、新增商品信息
		 String addUserGroupSql ="insert into goods_info (goods_id,goods_tile,goods_model_number,goods_org_price,goods_curr_price,goods_stock,goods_details) values(:goods_id,:goods_tile,:goods_model_number,:goods_org_price,:goods_curr_price,:goods_stock,:goods_details)";
		 	paramSourceGroup.addValue("goods_id",uuid);
		 	paramSourceGroup.addValue("goods_tile", map.get("goodsTile"));
			paramSourceGroup.addValue("goods_model_number", map.get("goodsModelNumber"));
			paramSourceGroup.addValue("goods_org_price", map.get("goodsOrgPrice"));
			paramSourceGroup.addValue("goods_curr_price", map.get("goodsCurrPrice"));
			paramSourceGroup.addValue("goods_stock", map.get("goodsStock"));
			paramSourceGroup.addValue("goods_details", map.get("goodsDetails"));
			

		 int addGroupResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加商品记录结果addGoods_infoResult:"+String.valueOf(addGroupResult));

		//2、新增商品映射关系
			String result="";
			    for(int i=1;i<7;i++){
			    	String goodsTwolevelCode =  map.get("onelevelCode0"+String.valueOf(i));
			    	if(!"".equals(goodsTwolevelCode)&&null!=goodsTwolevelCode)
			    	{
			    		result=this.addGoodsMap(uuid, goodsTwolevelCode);
			    	}
			    	
			    }
				
			
		 return String.valueOf(addGroupResult);
	}
	
	private String addGoodsMap(String goodsId,String goodsTwolevelCode)throws Exception{
		
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

	

	//2、新增商品映射关系
		 String addGoodsMapSql ="insert into goods_map (goods_id,goods_twolevel_code) values(:goods_id,:goods_twolevel_code)";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("goods_twolevel_code", goodsTwolevelCode);

			

		 int addResult = namedParameterJdbcTemplate.update(addGoodsMapSql, paramSourceGroup);
			LOGGER.info("增加映射管理结果addGoodsMapResult:"+String.valueOf(addResult));
		
		
	 return String.valueOf(addResult);
}
	@Override
	public
	String updateGoodsRecord(Map<String, String> map) throws Exception{
		return null;
	}
	@Override
	public String deleteGoodsReCord(Map<String, String> map) throws Exception{
		return null;
	}
	@Override
	public List<Map<String, Object>> getGoodsRecordList(Map<String, String> map)throws Exception{
		
		return null;
	}

	

}
