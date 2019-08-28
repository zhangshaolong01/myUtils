package com.utils.offce;

import java.util.List;

/**
 * @author zhangshaolong
 *
 */
public class GsEmployee {

	/**
	 * 员工编号
	 */
	private String employeeNo;
	/**
	 * 员工姓名
	 */
	private String employeeName;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 所在部门
	 */
	private String departId;
	/**
	 * 操作动作
	 */
	private String operateStatus;
	/**
	 * 用户登录账号
	 */
	private String useraccount;

	/**
	 * 机构列表
	 */
	private List<GsInstitution> institutionIds;

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}

	public List<GsInstitution> getInstitutionIds() {
		return institutionIds;
	}

	public void setInstitutionIds(List<GsInstitution> institutionIds) {
		this.institutionIds = institutionIds;
	}

	@Override
	public String toString() {
		return "GsEmployee [employeeNo=" + employeeNo + ", employeeName=" + employeeName + ", sex=" + sex
				+ ", departId=" + departId + ", operateStatus=" + operateStatus + ", useraccount=" + useraccount
				+ ", institutionIds=" + institutionIds + "]";
	}

}
