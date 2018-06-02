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
import org.andy.shop.common.Constant;
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

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Override
	public List<Map<String, Object>> getUserPowerByOpenId(String openId) {
	
		String sql = "select a.user_name,a.user_phone,d.power_code,d.power_name From user_info a " +
				" INNER  join group_user_map b on b.user_id=a.user_id " +
				" INNER JOIN  power_group_map c on c.group_id=b.group_id" +
				" INNER JOIN  yuanlian.power_info d on d.power_id=c.power_id " +
				" WHERE  open_id='"+openId+"';";

		List<Map<String, Object>> userPowerInfo = jdbcTemplate.queryForList(sql);

		return userPowerInfo;
	}
	
	@Override
	public void applytoReferee(Map<String,String> map) {
	
		 UUID uuid1=UUID.randomUUID();
	     String userId = uuid1.toString(); 
		 String openId =map.get("openId");
		 String userName =map.get("user_name");
		 String userPhone = map.get("userPhone");
		 String userRegTime = Utils.getCurrentDate();
		 
		 
		 
		 //新增分销商用户
		 String addUserInfoSql ="insert into user_info (user_id,open_id,user_name,user_phone,user_reg_time) values('"+userId+"','"+openId+"','"+userName+"','"+userPhone+"','"+userRegTime+"')";
		 jdbcTemplate.execute(addUserInfoSql);
		 //新增用户分组
		 String group_id=Constant.REFEREE_GROUP_ID;//分销商组
		 String addUserGroupSql ="insert into group_user_map (group_id,user_id) values('"+group_id+"','"+userId+"')";
		 jdbcTemplate.execute(addUserGroupSql);
		 
	}
	

}
