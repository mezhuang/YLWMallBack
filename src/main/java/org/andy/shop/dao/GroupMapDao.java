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

public interface  GroupMapDao extends Serializable {


	String addUserToGroup(Map<String, String> Map) throws Exception;


}
