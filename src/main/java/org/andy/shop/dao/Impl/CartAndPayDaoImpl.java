package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.Utils;
import org.andy.shop.controller.CustomerReportController;
import org.andy.shop.dao.CartAndPayDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
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
 * cartAndPayDao实现类
 */
@Repository("cartAndPayDao")
public class CartAndPayDaoImpl implements CartAndPayDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public UserInfoPo getById(Integer id) {

		String sql = "SELECT * FROM user_info WHERE user_id = ?";

		UserInfoPo userInfo = jdbcTemplate.queryForObject(sql, new UserInfoPo(),
				new Object[] { id });

		return userInfo;
	}
	@Override 
	public String addToShoppingCart(Map<String,String> map )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addShoppingCartSql ="INSERT INTO yuanlian.shopping_cart (open_id,goods_image_server, goods_image_url,goods_id,goods_title,goods_Price, format_code,buy_number, is_select,create_time) VALUES (:open_id,:goods_image_server, :goods_image_url,:goods_id, :goods_title,:goods_price,:format_code,:buy_number, :is_select,:create_time);";
			paramSourceGroup.addValue("open_id", map.get("openId"));
			paramSourceGroup.addValue("goods_image_server", map.get("goodsImageServer"));
			paramSourceGroup.addValue("goods_image_url", map.get("goodsImageUrl"));
			paramSourceGroup.addValue("goods_id", map.get("goodsId"));
			paramSourceGroup.addValue("goods_title", map.get("goodsTitle")); 
			paramSourceGroup.addValue("goods_price", map.get("goodsPrice"));
			paramSourceGroup.addValue("format_code", map.get("formatCode"));
			paramSourceGroup.addValue("buy_number", map.get("buyNumber"));
			paramSourceGroup.addValue("is_select", map.get("isSelect"));
			paramSourceGroup.addValue("create_time", Utils.getCurrentDate());
			
			 int addResult = namedParameterJdbcTemplate.update(addShoppingCartSql, paramSourceGroup);
				LOGGER.info("增加购物车记录:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	//
	
	@Override 
	public String deleteShoppingCart(Map<String,String> map )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addShoppingCartSql ="DELETE from yuanlian.shopping_cart where shopping_cart_id=:shopping_cart_id";
			paramSourceGroup.addValue("shopping_cart_id", map.get("shoppingCartId"));
			
			
			 int addResult = namedParameterJdbcTemplate.update(addShoppingCartSql, paramSourceGroup);
				LOGGER.info("增加购物车记录:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	@Override 
	public String updateShoppingCartNum(Map<String,String> map )throws Exception{
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String addShoppingCartSql ="update shopping_cart SET buy_number =:buy_number where shopping_cart_id=:shopping_cart_id";
			paramSourceGroup.addValue("buy_number", map.get("buyNumber"));
			paramSourceGroup.addValue("shopping_cart_id", map.get("shoppingCartId"));
			
			
			 int addResult = namedParameterJdbcTemplate.update(addShoppingCartSql, paramSourceGroup);
				LOGGER.info("增加购买数量:"+String.valueOf(addResult));
				
				return String.valueOf(addResult);
			
			
	}
	@Override
	public List<Map<String, Object>>  getShoppingCartListByOpenId(Map<String, String> map) throws Exception{
			
			String openId =map.get("openId");
			
			String sql = "select a.shopping_cart_id,a.open_id,a.goods_id,a.goods_title,a.goods_image_server,a.goods_image_url, CONVERT(a.goods_price,SIGNED)*CONVERT(a.buy_number,SIGNED) as  price,a.format_code,b.format_name,CONVERT(a.buy_number,SIGNED) as num,if(a.is_select='1',TRUE,FALSE) as isSelect From shopping_cart a LEFT JOIN goods_formatprice b on b.format_price_id =a.format_code  where a.open_id='"+openId+"' ORDER BY shopping_cart_id ASC  ;";
	
			List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(sql);
			
	
			return goodsClassList;
	}
	
	

	

}
