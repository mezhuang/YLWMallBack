package org.andy.shop.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.andy.shop.entity.UserInfoPo;

/**
 * 创建时间：2015-1-30 上午11:22:37
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoDao
 */

public interface  GoodsManagerDao extends Serializable {


	String addGoodsRecord(Map<String, String> map) throws Exception;
	String updateGoodsRecord(Map<String, String> map) throws Exception;
	List<Map<String, Object>> getGoodsRecordList(String startIndex,String indexSize)throws Exception;

	public Map<String, Object> getGoodsRecordDetail(String goodsId) throws Exception;
	
	public String addGoodsImage(String goodsImageUrl,String goodsId )throws Exception;
	
	String deleteGoodsInfo(Map<String, String> map) throws Exception;
	String deleteGoodsMap(Map<String, String> map) throws Exception;
	String deleteGoodsImage(Map<String, String> map) throws Exception;

}
