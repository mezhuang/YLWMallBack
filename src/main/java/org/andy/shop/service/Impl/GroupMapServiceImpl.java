package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.GroupMapService;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.service.UserPowerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建时间：2015-1-30 上午11:40:19
 * 
 * @author andy
 * @version 2.2 实现接口
 */

@Service("GroupMapService")
public class GroupMapServiceImpl implements GroupMapService {

	private static final Logger LOGGER = Logger
	.getLogger(GroupMapServiceImpl.class);
	@Autowired
	private GroupMapDao groupMapDao;
	
	@Override
	public String addUserToGroupMap(Map<String,String> map)throws Exception
	{
		  return groupMapDao.addUserToGroupMap(map);
	}
	@Override
	public String deleteGroupMapByUserIdAndGroupId(Map<String,String> map)throws Exception
	{
		return groupMapDao.deleteGroupMapByUserIdAndGroupId(map);
	}
	@Override
	public String getByGroupIdByGroupCode(String groupCode)throws Exception{
		return groupMapDao.getByGroupIdByGroupCode(groupCode);
	}


	

}
