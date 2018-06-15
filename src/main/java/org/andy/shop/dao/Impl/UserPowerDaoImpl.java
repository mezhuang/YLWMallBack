package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
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
@Repository("userPowerDao")
public class UserPowerDaoImpl implements UserPowerDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Override
	public List<Map<String, Object>> getUserPowerByOpenId(String openId) {
	
//		String sql = "select a.user_name,a.user_phone,d.power_code,d.power_name From user_info a " +
//				" INNER  join group_user_map b on b.user_id=a.user_id " +
//				" INNER JOIN  power_group_map c on c.group_id=b.group_id" +
//				" INNER JOIN  yuanlian.power_info d on d.power_id=c.power_id " +
//				" WHERE  open_id='"+openId+"';";
		String sql="select c.user_name,c.user_phone,a.group_code From group_info a INNER JOIN group_user_map b on b.group_id=a.group_id INNER JOIN user_info c on c.user_id=b.user_id where c.open_id='"+openId+"'; ";

		List<Map<String, Object>> userPowerInfo = jdbcTemplate.queryForList(sql);

		return userPowerInfo;
	}
	@Override
	public List<Map<String, Object>> getUserPowerByUserPhone(String UserPhone) throws Exception {
	
//		String sql = "select a.user_name,a.user_phone,d.power_code,d.power_name From user_info a " +
//				" INNER  join group_user_map b on b.user_id=a.user_id " +
//				" INNER JOIN  power_group_map c on c.group_id=b.group_id" +
//				" INNER JOIN  yuanlian.power_info d on d.power_id=c.power_id " +
//				" WHERE  open_id='"+openId+"';";
		String sql="select c.user_name,c.user_phone,c.manager_phone,a.group_code From group_info a INNER JOIN group_user_map b on b.group_id=a.group_id INNER JOIN user_info c on c.user_id=b.user_id where c.user_phone='"+UserPhone+"'; ";

		List<Map<String, Object>> userPowerInfo = jdbcTemplate.queryForList(sql);

		return userPowerInfo;
	}
	
	@Override
	public String applytoReferee(Map<String,String> map) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

//		 UUID uuid1=UUID.randomUUID();
//	     String userId = uuid1.toString(); 
		 String openId =map.get("openId");
		 String userName =map.get("userName");
		 String userPhone = map.get("userPhone");
		 String ManagerPhone =map.get("ManagerPhone");
		 String userRegTime = Utils.getCurrentDate();
		 String groupId = map.get("groupId");
		 
		 
		 
		 //新增分销商用户
		 String addUserInfoSql ="insert into user_info (open_id,user_name,user_phone,manager_phone,user_reg_time) values(:open_id,:user_name,:user_phone,:manager_phone,:user_reg_time)";
//		 jdbcTemplate.execute(addUserInfoSql);
		 
			paramSource.addValue("open_id", openId);
			paramSource.addValue("user_name", userName);
			paramSource.addValue("user_phone",userPhone );	
			paramSource.addValue("manager_phone", ManagerPhone);
			paramSource.addValue("user_reg_time", Utils.getCurrentDate());

			int addUserResult = namedParameterJdbcTemplate.update(addUserInfoSql, paramSource);
			LOGGER.info("插入分销商addUserResult:"+String.valueOf(addUserResult));
		 
//		 jdbcTemplate.execute(addUserGroupSql);
		MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();
		String GetUserIdSql = "SELECT user_id FROM user_info WHERE user_phone = ? limit 1";

		String rUserId = jdbcTemplate.queryForObject(GetUserIdSql,String.class,
				new Object[] { userPhone });

		 String addUserGroupSql ="insert into group_user_map (group_id,user_id) values(:group_id,:user_id)";
			paramSourceGroup.addValue("user_id", rUserId);
			paramSourceGroup.addValue("group_id", groupId);

		 int addGroupResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("插入分销商addGroupResult:"+String.valueOf(addGroupResult));
		return String.valueOf(addGroupResult);

		 
	}
	/*判断分销商是否为
	 * */
	@Override
	public int isGroupPositionBygroupCode(String reFereeUserPhone,String groupCode) throws Exception {
		String sql = "SELECT COUNT(1) from user_info a INNER JOIN group_user_map b on b.user_id=a.user_id INNER JOIN group_info c on c.group_id=b.group_id  where c.group_code=? and a.user_phone=? ;";


		int isRefereeCount = jdbcTemplate.queryForObject(sql,Integer.class,
				new Object[] { groupCode,reFereeUserPhone });

		return isRefereeCount;

	}
	/*根据手机号，判断用户是否为总监
	 * */
	@Override
	public int isRefereeDicrector(String reFereeUserPhone,String groupCode) throws Exception {
		String sql = "SELECT COUNT(1) from user_info a INNER JOIN group_user_map b on b.user_id=a.user_id INNER JOIN group_info c on c.group_id=b.group_id  where c.group_code=? and a.user_phone=? ;";


		int isRefereeCount = jdbcTemplate.queryForObject(sql,Integer.class,
				new Object[] { groupCode,reFereeUserPhone });

		return isRefereeCount;

	}
	

}
