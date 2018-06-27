package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.GoodsManagerDao;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.GoodsManagerService;
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

@Service("GoodsManagerService")
public class GoodsManagerServiceImpl implements GoodsManagerService {

	private static final Logger LOGGER = Logger
	.getLogger(GoodsManagerServiceImpl.class);
	@Autowired
	private GroupMapDao groupMapDao;
	
	@Autowired
	private GoodsManagerDao goodsManagerDao;
	
	@Override
	public
	String addGoodsRecord(Map<String, String> map) throws Exception{
		return goodsManagerDao.addGoodsRecord(map);
	}
	@Override
	public
	String updateGoodsRecord(Map<String, String> map) throws Exception{
		return goodsManagerDao.updateGoodsRecord(map);
	}
	@Override
	public
	String deleteGoodsReCord(Map<String, String> map) throws Exception{
		return goodsManagerDao.deleteGoodsReCord(map);
	}
	@Override
	public
	List<Map<String, Object>> getGoodsRecordList(Map<String, String> map)throws Exception{
		return goodsManagerDao.getGoodsRecordList(map);
	}



	

}
