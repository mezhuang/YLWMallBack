package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.Utils;
import org.andy.shop.controller.CustomerReportController;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 创建时间：2015-1-30 上午11:25:30
 * 
 * @author andy
 * @version 2.2
 * 
 * CustomerInfoDao实现类
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl implements UserInfoDao {
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
	public List<UserInfoPo> findAll() {
		String sql = "SELECT * FROM user_info";
		List<UserInfoPo> userInfos = jdbcTemplate.query(sql, new UserInfoPo());
		return userInfos;
	}

	@Override
	public Integer save(UserInfoPo entity) {

		String sql = "INSERT INTO user_info(user_id, user_name,user_phone,user_reg_time) VALUES(:user_id, :user_Name,:user_Phone,:user_Reg_Time)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("user_id", entity.getUserId());
		paramSource.addValue("user_Name", entity.getUserName());
		paramSource.addValue("user_Phone", entity.getUserPhone());
		paramSource.addValue("user_Reg_Time", Utils.getCurrentDate());
		int result = namedParameterJdbcTemplate.update(sql, paramSource);
		
		return result;
	}
	
	@Override
	public Integer getByCustomerPhone(String customerPhone) {

		String sql = "SELECT count(1) FROM user_info WHERE user_phone = ?";

		int userinfoCount = jdbcTemplate.queryForObject(sql,Integer.class,
				new Object[] { customerPhone });

		return userinfoCount;
	}
	@Override
	public List<Map<String, Object>> getUserListByGroupCode(String groupCode)throws Exception {
	
		String sql = "select  a.group_code,d.user_id,d.user_name,d.user_phone from group_info a " +
				"INNER JOIN group_user_map b on a.group_id=b.group_id " +
				"INNER JOIN user_info d on d.user_id=b.user_id where a.group_code='"+groupCode+"'  ";

		List<Map<String, Object>> userinfoinfo = jdbcTemplate.queryForList(sql);

		return userinfoinfo;
	}
	
	@Override
	public List<Map<String, Object>> getRefereeInfobyCustomerPhone(String customerPhone) throws Exception{
	
		String sql = "select c.user_name,c.user_phone,c.manager_phone,b.report_time From sales_info a INNER JOIN customer_report b ON b.customer_phone=a.customer_phone INNER JOIN user_info c on c.open_id=b.open_id where a.customer_phone='"+customerPhone+"';";

		List<Map<String, Object>> userinfo = jdbcTemplate.queryForList(sql);

		return userinfo;
	}
	@Override
	public Integer addReceiGoodsAdress(Map<String, String> map) {
//		receive_goods_id
//		receive_name
//		receive_phone
//		receive_area
//		detailed_address
//		create_time

		String sql = "INSERT INTO receive_goods_address(open_id,receive_name, receive_phone,receive_area,detailed_address,create_time) VALUES(:open_id,:receive_name, :receive_phone,:receive_area,:detailed_address,:create_time)";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("open_id", map.get("openId"));
		paramSource.addValue("receive_name", map.get("receiveName"));
		paramSource.addValue("receive_phone", map.get("receivePhone"));
		paramSource.addValue("receive_area", map.get("receiveArea"));
		paramSource.addValue("detailed_address", map.get("detailedAddress"));
		paramSource.addValue("create_time", Utils.getCurrentDate());
		int result = namedParameterJdbcTemplate.update(sql, paramSource);
		
		return result;
	}
	@Override
	public Integer updateReceiGoodsAdress(Map<String, String> map) {
//		receive_goods_id
//		receive_name
//		receive_phone
//		receive_area
//		detailed_address
//		create_time

		String sql = "update receive_goods_address set receive_name=:receive_name, receive_phone=:receive_phone,receive_area=:receive_area,detailed_address=:detailed_address where receive_goods_id=:receive_goods_id";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
	
		paramSource.addValue("receive_name", map.get("receiveName"));
		paramSource.addValue("receive_phone", map.get("receivePhone"));
		paramSource.addValue("receive_area", map.get("receiveArea"));
		paramSource.addValue("detailed_address", map.get("detailedAddress"));
		paramSource.addValue("receive_goods_id", map.get("receiveGoodsId"));
		int result = namedParameterJdbcTemplate.update(sql, paramSource);
		
		return result;
	}
	@Override
	public List<Map<String, Object>>  getReceiGoodsAdressByOpenId(Map<String, String> map) throws Exception{
			
			String openId =map.get("openId");
			
			String sql = "select *from receive_goods_address a where a.open_id='"+openId+"' ORDER BY create_time ASC  ;";
	
			List<Map<String, Object>> goodsClassList = jdbcTemplate.queryForList(sql);
			
	
			return goodsClassList;
	}
	

}
