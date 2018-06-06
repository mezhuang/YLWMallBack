package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.CommissioinDao;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.SaleInfoDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.CommissionService;
import org.andy.shop.service.GroupMapService;
import org.andy.shop.service.SaleInfoService;
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

@Service("CommissioinService")
public class CommissionServiceImpl implements CommissionService {

	private static final Logger LOGGER = Logger
	.getLogger(CommissionServiceImpl.class);
	@Autowired
	private CommissioinDao CommissioinDao;
	
	@Override
	public String addCommissioin(Map<String, String> Map) throws Exception{
		return CommissioinDao.addCommissioin(Map);
	}

	@Override
	public List<Map<String, Object>> getCommissioinInfo(String openId)throws Exception{
	return CommissioinDao.getCommissioinInfo(openId);
}


	

}
