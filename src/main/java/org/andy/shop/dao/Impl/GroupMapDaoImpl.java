package org.andy.shop.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.andy.shop.common.Utils;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.common.Constant;
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
@Repository("GroupMapDao")
public class GroupMapDaoImpl implements GroupMapDao {
	private static final Logger LOGGER = Logger
	.getLogger(CustomerReportController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	
	@Override
	public String addUserToGroup(Map<String,String> map)throws Exception{
			
			MapSqlParameterSource paramSourceGroup = new MapSqlParameterSource();

		 String addUserGroupSql ="insert into group_user_map (group_id,user_id) values(:group_id,:user_id)";
			paramSourceGroup.addValue("user_id", map.get("userId"));
			paramSourceGroup.addValue("group_id", map.get("groupId"));
			paramSourceGroup.addValue("group_code", map.get("groupCode"));
			

		 int addGroupResult = namedParameterJdbcTemplate.update(addUserGroupSql, paramSourceGroup);
			LOGGER.info("增加用户至某个分组adduserToGroupResult:"+String.valueOf(addGroupResult));

		 return String.valueOf(addGroupResult);
	}
	

}
