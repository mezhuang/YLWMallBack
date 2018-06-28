package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
			
			String goodsId = map.get("goodsId");
			//1、新增商品信息
			String addUserGroupSql ="insert into goods_info (goods_id,goods_tile,goods_model_number,goods_org_price,goods_curr_price,goods_stock,goods_details) values(:goods_id,:goods_tile,:goods_model_number,:goods_org_price,:goods_curr_price,:goods_stock,:goods_details)";
		 	paramSourceGroup.addValue("goods_id",goodsId);
		 	paramSourceGroup.addValue("goods_tile", map.get("goodsTile"));
			paramSourceGroup.addValue("goods_model_number", map.get("goodsModelNumber"));
			paramSourceGroup.addValue("goods_org_price", map.get("goodsOrgPrice"));
			paramSourceGroup.addValue("goods_curr_price", map.get("goodsCurrPrice"));
			paramSourceGroup.addValue("goods_stock", map.get("goodsStock"));
			paramSourceGroup.addValue("goods_details", map.get("goodsDetails"));
			paramSourceGroup.addValue("create_time", map.get("createTime"));
			

		 int addGroupResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加商品记录结果addGoods_infoResult:"+String.valueOf(addGroupResult));

		//2、新增商品映射关系
			String result="";
			    for(int i=1;i<7;i++){
			    	String goodsTwolevelCode =  map.get("onelevelCode0"+String.valueOf(i));
			    	if(!"".equals(goodsTwolevelCode)&&null!=goodsTwolevelCode)
			    	{
			    		result=this.addGoodsMap(goodsId, goodsTwolevelCode);
			    		
			    	}
			    	
			    }
	  
				
			
		 return String.valueOf(addGroupResult);
	}
	//新增商品映射关系
	private String addGoodsMap(String goodsId,String goodsTwolevelCode)throws Exception{
		
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

	


		 String addGoodsMapSql ="insert into goods_class_map (goods_id,goods_twolevel_code) values(:goods_id,:goods_twolevel_code)";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("goods_twolevel_code", goodsTwolevelCode);

			

		 int addResult = namedParameterJdbcTemplate.update(addGoodsMapSql, paramSourceGroup);
			LOGGER.info("增加映射管理结果addGoodsMapResult:"+String.valueOf(addResult));
		
		
	 return String.valueOf(addResult);
}
	
	@Override 
	public String addGoodsImage(String goodsImageUrl,String goodsId )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addGoodsImageSql ="insert into goods_image (goods_id,goods_image_url) values(:goods_id,:goods_image_url)";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("goods_image_url", goodsImageUrl);
			
			 int addResult = namedParameterJdbcTemplate.update(addGoodsImageSql, paramSourceGroup);
				LOGGER.info("增加图片管理Result:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	
	
	@Override 
	public String updateGoodsRecord(Map<String, String> map) throws Exception{
		return null;
	}
	@Override 
	public String deleteGoodsInfo(Map<String, String> map) throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String deleteGoodsInfoSql ="DELETE from goods_info  where goods_id =:goods_id";
		paramSourceGroup.addValue("goods_id", map.get("id"));
		 int addResult = namedParameterJdbcTemplate.update(deleteGoodsInfoSql, paramSourceGroup);

			LOGGER.info("删除商品信息Result:"+String.valueOf(addResult));
			
			return String.valueOf(addResult);
		
	}
	@Override
	public String deleteGoodsMap(Map<String, String> map) throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		
		String deleteGoodsClassMapSql ="DELETE From goods_class_map  where goods_id =:goods_id";
		paramSourceGroup.addValue("goods_id", map.get("id"));
		 int addResult = namedParameterJdbcTemplate.update(deleteGoodsClassMapSql, paramSourceGroup);

			LOGGER.info("删除商品类型映射Result:"+String.valueOf(addResult));
			
			return String.valueOf(addResult);
		
	}
	@Override
	public String deleteGoodsImage(Map<String, String> map) throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		

		String deleteGoodsImageMapSql ="DELETE from goods_image  where goods_id ='"+map.get("id")+"'";
//		paramSourceGroup.addValue("goods_id", map.get("id"));
		 int addResult = namedParameterJdbcTemplate.update(deleteGoodsImageMapSql, paramSourceGroup);

			LOGGER.info("删除图片Result:"+String.valueOf(addResult));
			
			return String.valueOf(addResult);
		
	}

	@Override
	public List<Map<String, Object>> getGoodsRecordList(String startIndex,String indexSize) throws Exception{
		
		String sql = "select DISTINCT a.goods_id as id,a.goods_tile,a.goods_model_number,a.goods_org_price,a.goods_curr_price,a.create_time " +
				"  From goods_info a INNER JOIN goods_image b on b.goods_id =a.goods_id ORDER BY a.create_time desc  LIMIT "+startIndex+" , "+indexSize+"   ";

		List<Map<String, Object>> customerReportList = jdbcTemplate.queryForList(sql);
		

		return customerReportList;
	}
	
	@Override
	public Map<String, Object> getGoodsRecordDetail(String goodsId) throws Exception{
		
		String goodsClassSql = "select *From goods_class_map a where a.goods_id = '"+goodsId+"';";
		String goodsImageSql = "select *From goods_image a where a.goods_id = '"+goodsId+"';";
		

		List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(goodsClassSql);
		List<Map<String, Object>> goodsImageList = jdbcTemplate.queryForList(goodsImageSql);

		Map<String, Object> reMap = new HashMap<String, Object>();

		reMap.put("goodsClassList", goodsClassList);
		reMap.put("goodsImageList", goodsImageList);

		return reMap;
	}
	

	

}
