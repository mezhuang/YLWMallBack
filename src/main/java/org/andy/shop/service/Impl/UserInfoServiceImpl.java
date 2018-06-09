package org.andy.shop.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.andy.shop.common.YLConstant;
import org.andy.shop.controller.UserInfoController;
import org.andy.shop.dao.UserInfoDao;
import org.andy.shop.entity.UserInfoPo;
import org.andy.shop.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建时间：2015-1-30 上午11:40:19
 * 
 * @author andy
 * @version 2.2 实现接口
 */

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	private static final Logger LOGGER = Logger
	.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public UserInfoPo getById(Integer id) {
		return userInfoDao.getById(id);
	}

	@Override
	public List<Map<String, Object>> getUserListByGroupCode(String groupCode)throws Exception {
		return userInfoDao.getUserListByGroupCode(groupCode);
	}

	@Override
	public Integer save(UserInfoPo userInfo) {
		return userInfoDao.save(userInfo);
	}
	@Override
	public Integer getByCustomerPhone(String customerPhone) {
		return userInfoDao.getByCustomerPhone(customerPhone);
	}
	@Override
	public List<String> findAllGuideInfo()
	{
		List<String > guideList= new ArrayList<String >();

		try {
			List<Map<String, Object>> GuidemapList = userInfoDao.getUserListByGroupCode(YLConstant.GROUP_CODE_GUIDE);
		
			
			for(Map<String, Object> guideMap:GuidemapList)
			{
				String guideName = (String)guideMap.get("user_name");
				String guidePhone = (String)guideMap.get("user_phone");
				String reGuideInfo =guideName+guidePhone;
				LOGGER.info("reGuideInfo："+reGuideInfo);
				guideList.add(reGuideInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return guideList;
	}
	@Override
	public	List<Map<String, Object>> getRefereeInfobyCustomerPhone(String customerPhone)throws Exception{
	return userInfoDao.getRefereeInfobyCustomerPhone(customerPhone);	
	}
	@Override
	public List<Map<String, Object>> getRefereeInfobyEmployeeCode(String employeeCode)throws Exception{
		return getRefereeInfobyEmployeeCode( employeeCode);
	}

}
