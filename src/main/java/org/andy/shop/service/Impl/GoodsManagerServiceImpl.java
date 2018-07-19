package org.andy.shop.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

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
	public String addGoodsRecord(Map<String, String> map) throws Exception{
		return goodsManagerDao.addGoodsRecord(map);
	}
	@Override
	public String updateGoodsRecord(Map<String, String> map) throws Exception{
		return goodsManagerDao.updateGoodsRecord(map);
	}
	@Override
	public String deleteGoodsInfo(Map<String, String> map) throws Exception{
		return goodsManagerDao.deleteGoodsInfo(map);
	}
	@Override
	public String deleteGoodsFormatPrice(Map<String, String> map) throws Exception{
		return goodsManagerDao.deleteGoodsFormatPrice(map);
	}
	@Override
	public String deleteGoodsMap(Map<String, String> map) throws Exception{
		return goodsManagerDao.deleteGoodsMap(map);
	}
	@Override 
	public String deleteGoodsImage(Map<String, String> map) throws Exception{
		return goodsManagerDao.deleteGoodsImage(map);
	}
	@Override
	public
	List<Map<String, Object>> getGoodsRecordList(String startIndex,String indexSize)throws Exception{
		return goodsManagerDao.getGoodsRecordList(startIndex, indexSize);
	}
	@Override
	public String addGoodsImage(String goodsImageServer,String goodsImageUrl,String goodsImageUrlSl,String goodsId,String displayPosition )throws Exception{
		
		return goodsManagerDao.addGoodsImage( goodsImageServer,goodsImageUrl,goodsImageUrlSl, goodsId,displayPosition);
	}
	@Override
	public List<Map<String, Object>> getGoodsRecordDetail(String goodsId)
			throws Exception {
		return goodsManagerDao.getGoodsRecordDetail(goodsId);
	}
	@Override
	public String addGoodsFormatAndPrice(Map<String,String> map)throws Exception{
		String goodsId=null;
		String formatName=null;
		String formatCode=null;
		String orgPrice=null;
		String currPrice=null;
		String retSult="";
		//map.get("goodsFormat0"+)
		
		int formatAndPriceNO =Integer.parseInt(map.get("formatAndPriceNO"));
		for(int i=1;i<=formatAndPriceNO;i++)
		{
//			formatCode = map.get("formatCode0"+String.valueOf(i));
			formatName = map.get("formatName"+String.valueOf(i));
			orgPrice = map.get("orgPrice"+String.valueOf(i));
			currPrice = map.get("currPrice"+String.valueOf(i));
			goodsId=map.get("goodsId");
			retSult=goodsManagerDao.addGoodsFormatAndPrice(goodsId, formatName, orgPrice, currPrice);
			
		}
		
		return retSult;
	}
	
	@Override
	public String updateGoodsFormatAndPrice(Map<String,String> map)throws Exception{
		String goodsId=null;
		String formatName=null;
		String formatCode=null;
		String orgPrice=null;
		String currPrice=null;
		String retSult="";
		//map.get("goodsFormat0"+)
		
		int formatAndPriceNO =Integer.parseInt(map.get("formatAndPriceNO"));
		for(int i=1;i<=formatAndPriceNO;i++)
		{
//			formatCode = map.get("formatCode0"+String.valueOf(i));
			formatName = map.get("formatName"+String.valueOf(i));
			orgPrice = map.get("orgPrice"+String.valueOf(i));
			currPrice = map.get("currPrice"+String.valueOf(i));
			goodsId=map.get("goodsId");
			retSult=goodsManagerDao.updateGoodsFormatAndPrice(goodsId, formatName, orgPrice, currPrice);
			
		}
		
		return retSult;
	}

	@Override
	public List<Map<String, Object>> getGoodsClassList(Map<String, String> map)throws Exception{
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> oneLevelClass =goodsManagerDao.getGoodsOnelevelClassList(map);
		
		

		for(Map<String,Object> fMap:oneLevelClass)
		{ 
			String onelevelCode = (String) fMap.get("onelevel_code");
			String onelevelName = (String) fMap.get("onelevel_name");

			Map<String, String> twoLevelClass = new HashMap<String, String>();
			twoLevelClass.put("onelevelCode",onelevelCode );
			List<Map<String, Object>>  twoClassList = goodsManagerDao.getGoodsClassList(twoLevelClass);
			Map<String, Object> reMap = new HashMap<String, Object>();
			reMap.put("onelevel_code",onelevelCode);
			reMap.put("onelevel_name",onelevelName);
			if(twoClassList.size()>0)
			{
				reMap.put("isHaveTwoCalss", true);
			}
			else
			{
				reMap.put("isHaveTwoCalss", false);
			}
			reMap.put("twoClassList", twoClassList);
			reList.add(reMap);
		}
		return reList;
	}
	@Override
	public List<Map<String, Object>> getGoodsInfoBytwolevelCode(Map<String, String> map) throws Exception{
		return goodsManagerDao.getGoodsInfoBytwolevelCode(map);
		 
	}
    /**
    * @param standardImgPath 原图片path
    * @param thumName 缩略图path
    */
	@Override
    public String storeThumbnail(String standardImgPath, String thumName) {
        File file = new File(standardImgPath);
        if(file!=null&&file.isFile()){
            try {
                File outFIle = new File(thumName);
                Thumbnails.of(file).size(100, 150).toFile(outFIle);
                return outFIle.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



	

}
