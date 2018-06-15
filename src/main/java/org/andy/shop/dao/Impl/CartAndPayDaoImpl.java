package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.CartAndPayDao;
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
 * cartAndPayDao实现类
 */
@Repository("cartAndPayDao")
public class CartAndPayDaoImpl implements CartAndPayDao {

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

	

	

}
