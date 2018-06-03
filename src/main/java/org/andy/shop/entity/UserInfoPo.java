package org.andy.shop.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

/**
 * 实现数据表与字段的映射
 * 
 * @author andy
 * 
 */
public class UserInfoPo implements RowMapper<UserInfoPo>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823504831198719837L;


	private String userId;
	private String userName;
	private String userPhone;
	private Date userRegTime;


	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Date getUserRegTime() {
		return userRegTime;
	}

	public void setUserRegTime(Date userRegTime) {
		this.userRegTime = userRegTime;
	}



	@Override
	public UserInfoPo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfoPo userInfo = new UserInfoPo();
		userInfo.setUserId(rs.getString("user_id"));
		userInfo.setUserName( rs.getString("userName"));
		userInfo.setUserRegTime(rs.getDate("userRegTime"));
		return userInfo;
	}

}