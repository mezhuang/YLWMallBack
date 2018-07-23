package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.GroupMapDao;
import org.andy.shop.dao.SaleInfoDao;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.dao.UserPowerDao;
import org.andy.shop.entity.UserInfoPo;
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

@Service("saleInfoService")
public class SaleInfoServiceImpl implements SaleInfoService {

	private static final Logger LOGGER = Logger
	.getLogger(SaleInfoServiceImpl.class);
	@Autowired
	private SaleInfoDao saleInfoDao;
	
	public String addSaleInfo(Map<String, String> map) throws Exception{
	
		  return saleInfoDao.addSaleInfo(map);
	}
	@Override
	public List<Map<String, Object>> getSaleInfoList(String openId, String startIndex,
			String indexSize)throws Exception{
		return saleInfoDao.getSaleInfoList( openId,  startIndex, indexSize);
		
	}
	@Override
	public String addGoodsOnlineOrder(Map<String, String> map) throws Exception{
		return saleInfoDao.addGoodsOnlineOrder(map);
	}
	@Override
	public String addGoodsdetailList(Map<String, String> map) throws Exception{
		return saleInfoDao.addGoodsdetailList(map);
	}
	@Override
	public List<Map<String, Object>> getGoodsOrderListByOpenId(Map<String, String> map)
	throws Exception{
		return saleInfoDao.getGoodsOrderListByOpenId(map);
	}
	@Override
	public String updateOrderStatus(Map<String, String> map)
	throws Exception{
		return saleInfoDao.updateOrderStatus(map);
	}

	

}
