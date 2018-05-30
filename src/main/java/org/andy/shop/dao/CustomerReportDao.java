package org.andy.shop.dao;

import java.util.List;
import java.util.Map;

import org.andy.shop.entity.CustomerReportPo;

/**
 * 创建时间：2015-1-30 上午11:22:37
 * 
 * @author andy
 * @version 2.2
 * 
 * UserInfoDao
 */

public interface CustomerReportDao extends GenericDao<CustomerReportPo, Integer> {

	public List<Map<String, Object>> getAllReportcustomerList(String openId,String startIndex,String indexSize) ;

}
