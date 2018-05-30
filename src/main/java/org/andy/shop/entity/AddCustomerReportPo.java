package org.andy.shop.entity;

public class AddCustomerReportPo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8823504831198719837L;

	private CustomerReportPo customerReportPo;
	private UserInfoPo    UserInfoPo;
	public CustomerReportPo getCustomerReportPo() {
		return customerReportPo;
	}
	public void setCustomerReportPo(CustomerReportPo customerReportPo) {
		this.customerReportPo = customerReportPo;
	}
	public UserInfoPo getUserInfoPo() {
		return UserInfoPo;
	}
	public void setUserInfoPo(UserInfoPo userInfoPo) {
		UserInfoPo = userInfoPo;
	}
	
}
