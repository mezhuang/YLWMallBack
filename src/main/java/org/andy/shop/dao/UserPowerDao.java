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

public interface  UserPowerDao extends Serializable {

	List<Map<String, Object>> getUserPowerByOpenId(String openId);

	void applytoReferee(Map<String, String> Map);

	int isGroupPositionBygroupCode(String reFereeUserPhone,String groupCode) throws Exception;

	List<Map<String, Object>> getUserPowerByUserPhone(String UserPhone) throws Exception;

	int isRefereeDicrector(String reFereeUserPhone, String groupCode)
			throws Exception;


}
