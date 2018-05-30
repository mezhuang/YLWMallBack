package org.andy.shop.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * 实现数据表与字段的映射
 * 
 * @author andy
 * 
 */
public class CustomerReportPo implements RowMapper<CustomerReportPo>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823504831198719837L;


//	guide
//	report_time
//	remark
	private String    customerPhone;
	private String    visitTime;
	private String    taskPhone;
	private String 	  isTask;
	private String    guidePhone;
	private String    reportTime;
	private String 	  remark;
	private String 	  openId;
	private String    startIndex;//查询开始索引
	private String    endIndex;//查询结束索引
	
	//用户信息列表
	private List<UserInfoPo> userInfoList;
	
	
	public String getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}
	public String getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(String endIndex) {
		this.endIndex = endIndex;
	}
	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public List<UserInfoPo> getUserInfoList() {
		return userInfoList;
	}


	public void setUserInfoList(List<UserInfoPo> userInfoList) {
		this.userInfoList = userInfoList;
	}


	
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getIsTask() {
		return isTask;
	}


	public void setIsTask(String isTask) {
		this.isTask = isTask;
	}


	

	public String getReportTime() {
		return reportTime;
	}


	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}


	public String getVisitTime() {
		return visitTime;
	}


	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	
	@Override
	public CustomerReportPo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerReportPo userInfo = new CustomerReportPo();
//		customer_id
//		customer_phone
//		visit_time
//		referee_phone
//		is_task
//		guide
//		report_time
//		remark
//		userInfo.setUserId(rs.getInt("user_id"));

		userInfo.setIsTask(rs.getString("isTask"));
		userInfo.setReportTime(rs.getString("reportTime"));
		userInfo.setRemark(rs.getString("remark"));
		
		return userInfo;
	}


	


	public String getCustomerPhone() {
		return customerPhone;
	}


	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}


	


	public String getTaskPhone() {
		return taskPhone;
	}


	public void setTaskPhone(String taskPhone) {
		this.taskPhone = taskPhone;
	}


	public String getGuidePhone() {
		return guidePhone;
	}


	public void setGuidePhone(String guidePhone) {
		this.guidePhone = guidePhone;
	}


	public String getRemark() {
		return remark;
	}

}