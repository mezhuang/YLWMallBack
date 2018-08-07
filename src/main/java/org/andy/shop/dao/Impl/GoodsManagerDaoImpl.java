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
			String addUserGroupSql ="insert into goods_info (goods_id,goods_title,goods_model_number,goods_stock,image_file_no,format_price_no,create_name,create_time) values(:goods_id,:goods_title,:goods_model_number,:goods_stock,:image_file_no,:format_price_no,:create_name,:create_time)";
		 	paramSourceGroup.addValue("goods_id",goodsId);
		 	paramSourceGroup.addValue("goods_title", map.get("goodsTitle"));
			paramSourceGroup.addValue("goods_model_number", map.get("goodsModelNumber"));

			paramSourceGroup.addValue("goods_stock", map.get("goodsStock"));
			paramSourceGroup.addValue("image_file_no", map.get("imageFileNO"));
			paramSourceGroup.addValue("format_price_no", map.get("formatAndPriceNO"));
			paramSourceGroup.addValue("create_name", map.get("createName"));
			paramSourceGroup.addValue("create_time", Utils.getCurrentDate());
			

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
	public String updateGoodsRecord(Map<String,String> map)throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
			
			String goodsId = map.get("goodsId");
			//1、修改商品信息
			String addUserGroupSql ="update goods_info set goods_title=:goods_title,goods_model_number=:goods_model_number,goods_stock=:goods_stock,image_file_no=:image_file_no,format_price_no=:format_price_no,create_name=:create_name,create_time=:create_time  where goods_id=:goods_id";
		 	paramSourceGroup.addValue("goods_id",goodsId);
		 	paramSourceGroup.addValue("goods_title", map.get("goodsTitle"));
			paramSourceGroup.addValue("goods_model_number", map.get("goodsModelNumber"));

			paramSourceGroup.addValue("goods_stock", map.get("goodsStock"));
			paramSourceGroup.addValue("image_file_no", map.get("imageFileNO"));
			paramSourceGroup.addValue("format_price_no", map.get("formatAndPriceNO"));
			paramSourceGroup.addValue("create_name", map.get("createName"));
			paramSourceGroup.addValue("create_time", Utils.getCurrentDate());
			

		 int addGroupResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("修改商品记录结果addGoods_infoResult:"+String.valueOf(addGroupResult));

		//2、更新商品映射关系，采用删除再修改方式
			this.deleteGoodsMap(map);
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
	
	
	@Override 
	public String addGoodsImage(String goodsImageServer,String goodsImageUrl,String goodsImageUrlSl,String goodsId,String positionCode,String positionName )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addGoodsImageSql ="insert into goods_image (goods_id,goods_image_server,goods_image_url,goods_image_url_sl,position_code,position_name) values(:goods_id,:goods_image_server,:goods_image_url,:goods_image_url_sl,:position_code,:position_name)";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("goods_image_server", goodsImageServer);
			paramSourceGroup.addValue("goods_image_url", goodsImageUrl);
			paramSourceGroup.addValue("goods_image_url_sl", goodsImageUrlSl);
			paramSourceGroup.addValue("position_code", positionCode);
			paramSourceGroup.addValue("position_name", positionName);
			
			 int addResult = namedParameterJdbcTemplate.update(addGoodsImageSql, paramSourceGroup);
				LOGGER.info("增加图片管理Result:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	@Override 
	public String addGoodsFormatAndPrice(String goodsId,String formatName,String orgPrice,String currPrice )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addGoodsFormatPriceSql ="insert into goods_formatprice (goods_id,format_name,org_price,curr_price) values(:goods_id,:format_name,:org_price,:curr_price)";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("format_name", formatName);
//			paramSourceGroup.addValue("format_code", formatCode);
			paramSourceGroup.addValue("org_price", orgPrice);
			paramSourceGroup.addValue("curr_price", currPrice);
			
			 int addResult = namedParameterJdbcTemplate.update(addGoodsFormatPriceSql, paramSourceGroup);
				LOGGER.info("增加规格和价格Result:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	@Override 
	public String updateGoodsFormatAndPrice(String goodsId,String formatName,String orgPrice,String currPrice )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addGoodsFormatPriceSql ="update goods_formatprice set format_name=:format_name,org_price=:org_price,curr_price=:curr_price where goods_id=:goods_id";
			paramSourceGroup.addValue("goods_id", goodsId);
			paramSourceGroup.addValue("format_name", formatName);
//			paramSourceGroup.addValue("format_code", formatCode);
			paramSourceGroup.addValue("org_price", orgPrice);
			paramSourceGroup.addValue("curr_price", currPrice);
			
			 int addResult = namedParameterJdbcTemplate.update(addGoodsFormatPriceSql, paramSourceGroup);
				LOGGER.info("增加规格和价格Result:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
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
	public String deleteGoodsFormatPrice(Map<String, String> map) throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		
		String deleteGoodsClassMapSql ="DELETE From goods_formatprice  where goods_id =:goods_id";
		paramSourceGroup.addValue("goods_id", map.get("id"));
		 int addResult = namedParameterJdbcTemplate.update(deleteGoodsClassMapSql, paramSourceGroup);

			LOGGER.info("删除商品规格和价格Result:"+String.valueOf(addResult));
			
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
		
		String sql = "select DISTINCT a.goods_id as id,a.goods_title,a.goods_model_number,a.create_time " +
				"  From goods_info a  ORDER BY a.create_time desc  LIMIT "+startIndex+" , "+indexSize+"   ";

		List<Map<String, Object>> customerReportList = jdbcTemplate.queryForList(sql);
		

		return customerReportList;
	}
	
	@Override
	public List<Map<String, Object>> getGoodsRecordDetail(String goodsId) throws Exception{
		String goodsInfoSql = "select *From goods_info a where a.goods_id = '"+goodsId+"';";
		String goodsClassSql = "select b.* From goods_class_map a inner JOIN goods_class b on b.twolevel_code=a.goods_twolevel_code where a.goods_id  = '"+goodsId+"' ORDER BY twolevel_code asc;";
		String goodsImageSql = "select *From goods_image a where a.goods_id = '"+goodsId+"' ORDER BY goods_image_url asc;";
		String goodsFormatPriceSql = "select * from yuanlian.goods_formatprice a where a.goods_id=  '"+goodsId+"' ORDER BY format_price_id ASC";
		List<Map<String, Object>> goodsinfoList = jdbcTemplate.queryForList(goodsInfoSql);
		List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(goodsClassSql);
		List<Map<String, Object>> goodsImageList = jdbcTemplate.queryForList(goodsImageSql);
		List<Map<String, Object>> goodsFormatList = jdbcTemplate.queryForList(goodsFormatPriceSql);
		
		List<Map<String, Object>> reList =  new ArrayList<Map<String, Object>>();
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		reMap.put("goodsClassList", goodsClassList);
		reMap.put("goodsImageList", goodsImageList);
		reMap.put("goodsFormatList", goodsFormatList);
		reMap.putAll(goodsinfoList.get(0));
		reList.add(reMap);
		return reList;
	}
	@Override
	public List<Map<String, Object>>  getGoodsOnelevelClassListByClassType(Map<String, String> map) throws Exception{
			
			String classType=null;
			
			classType =map.get("classType");
			
			String oneLevelSql = "select DISTINCT a.onelevel_code,a.onelevel_name from goods_class a where a.class_type='"+classType+"' ORDER BY onelevel_code ASC;";

			

			List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(oneLevelSql);
			
	
			return goodsClassList;
	}
	
	@Override
	public List<Map<String, Object>>  getGoodsTwoLevelClassListByOneLevelCode(Map<String, String> map) throws Exception{
			
			String onelevelCode =map.get("onelevelCode");
			
			String sql = "select DISTINCT a.twolevel_code,a.twolevel_name,CONCAT(a.goods_class_url,class_image_name) as class_image_url ,a.remark1,a.remark2 "+
			             "From goods_class a  where a.onelevel_code='"+onelevelCode+"' ORDER BY a.twolevel_code ASC";
	
			List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(sql);
			
	
			return goodsClassList;
	}
	//获取商品初次显示信息
	@Override
	public List<Map<String, Object>>  getGoodsInfoBytwolevelCode(Map<String, String> map) throws Exception{
			
			String twolevelCode =map.get("twolevelCode");
			String imagePositionCode =map.get("imagePositionCode");
			String sql=null;
			
			if(null!=imagePositionCode&&""!=imagePositionCode)
			{
				imagePositionCode = "and position_code = '"+imagePositionCode+"'";
			}
			
			
			
			 sql = "select b.goods_id,a.twolevel_code,a.twolevel_name,a.remark3,c.goods_title,c.goods_model_number,d.goods_image_server,d.goods_image_url,min(e.curr_price) as curr_price ,min(e.org_price) as org_price,d.goods_image_url_sl,d.goods_image_text From goods_class a INNER JOIN goods_class_map b on b.goods_twolevel_code= a.twolevel_code"+ 
						" INNER JOIN  goods_info c on c.goods_id=b.goods_id  INNER JOIN  goods_image d on d.goods_id=b.goods_id INNER JOIN  goods_formatprice e on e.goods_id=b.goods_id  "+																
						" where b.goods_twolevel_code='"+twolevelCode+"' "+imagePositionCode+"  GROUP BY goods_id ORDER BY a.twolevel_code ASC;";
	
			List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(sql);
			
	
			return goodsClassList;
	}
}
