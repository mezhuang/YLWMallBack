package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
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
@Repository("userInfoDao")
public class UserInfoDaoImpl implements UserInfoDao {

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
	
		String sql = "select  d.user_id,d.user_name,d.user_phone from group_info a " +
				"INNER JOIN group_user_map b on a.group_id=b.group_id " +
				"INNER JOIN user_info d on d.user_id=b.user_id where a.group_code='"+groupCode+"'  ";

		List<Map<String, Object>> userinfoinfo = jdbcTemplate.queryForList(sql);

		return userinfoinfo;
	}
	
	@Override
	public List<Map<String, Object>> getRefereeInfobyCustomerPhone(String customerPhone) throws Exception{
	
		String sql = "select c.user_name,c.user_phone,c.employee_code,b.report_time From sales_info a INNER JOIN customer_report b ON b.customer_phone=a.customer_phone INNER JOIN user_info c on c.open_id=b.open_id where a.customer_phone='"+customerPhone+"';";

		List<Map<String, Object>> userinfo = jdbcTemplate.queryForList(sql);

		return userinfo;
	}
	@Override
	public List<Map<String, Object>> getRefereeInfobyEmployeeCode(String employeeCode) throws Exception{
	
		String sql = "select c.user_name,c.user_phone,c.employee_code From sales_info a INNER JOIN customer_report b ON b.customer_phone=a.customer_phone INNER JOIN user_info c on c.open_id=b.open_id where employee_ode='"+employeeCode+"';";

		List<Map<String, Object>> userinfo = jdbcTemplate.queryForList(sql);

		return userinfo;
	}

}
