package org.andy.shop.service;

import java.util.List;
import java.util.Map;

import org.andy.shop.entity.CustomerReportPo;
import org.andy.shop.entity.UserInfoPo;

/**
 * 创建时间：2015-1-30 上午11:31:34
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoService 接口
 */

public interface UserPowerService {

	List<Map<String, Object>> getUserPowerByOpenId(String openId);

	String applytoReferee(Map<String, String> Map);

	int isGroupPositionBygroupCode(String reFereeUserPhone,String groupCode) throws Exception;

	List<Map<String, Object>> getUserPowerByUserPhone(String UserPhone)
			throws Exception;

	int isRefereeDicrector(String reFereeUserPhone, String groupCode)
			throws Exception;

	

}
