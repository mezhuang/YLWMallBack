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

	
	public String addGoodsImage(String goodsImageUrl,String goodsId,String displayPosition )throws Exception;
	
	String deleteGoodsInfo(Map<String, String> map) throws Exception;
	String deleteGoodsMap(Map<String, String> map) throws Exception;
	String deleteGoodsImage(Map<String, String> map) throws Exception;
	public String addGoodsFormatAndPrice(String goodsId,String formatName,String orgPrice,String currPrice )throws Exception;
	String deleteGoodsFormatPrice(Map<String, String> map) throws Exception;
	List<Map<String, Object>> getGoodsRecordList(String startIndex,String indexSize)throws Exception;

	public List<Map<String, Object>> getGoodsRecordDetail(String goodsId) throws Exception;
	List<Map<String, Object>> getGoodsOnelevelClassList(Map<String, String> map)
	throws Exception;
	List<Map<String, Object>> getGoodsClassList(Map<String, String> map)
			throws Exception;
	List<Map<String, Object>> getGoodsInfoBytwolevelCode(Map<String, String> map)
			throws Exception;
	
	

}
