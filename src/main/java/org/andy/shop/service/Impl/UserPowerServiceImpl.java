package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
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

@Service("userPowerService")
public class UserPowerServiceImpl implements UserPowerService {

	private static final Logger LOGGER = Logger
	.getLogger(UserPowerServiceImpl.class);
	@Autowired
	private UserPowerDao userPowerDao;
	
	@Override
	public List<Map<String, Object>> getUserPowerByOpenId(String openId){
		return userPowerDao.getUserPowerByOpenId(openId);
	}
	@Override
	public String applytoReferee(Map<String, String> Map){
		 return userPowerDao.applytoReferee(Map);
	}
	
	@Override
	public int isGroupPositionBygroupCode(String reFereeUserPhone,String groupCode) throws Exception{
		return userPowerDao.isGroupPositionBygroupCode(reFereeUserPhone, groupCode);
	}
	
	@Override	
	public List<Map<String, Object>> getUserPowerByUserPhone(String UserPhone) throws Exception{
		return userPowerDao.getUserPowerByUserPhone(UserPhone);
	}
	
	
	@Override
	public int isRefereeDicrector(String reFereeUserPhone, String groupCode)
	throws Exception{
		return userPowerDao.isRefereeDicrector(reFereeUserPhone, groupCode);
		
	}
	

}
