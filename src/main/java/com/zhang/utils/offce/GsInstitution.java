package com.zhang.utils.offce;

/**
 * 员工账户
 * 
 * @author zhangshaolong
 *
 */
public class GsInstitution {

	/**
	 * 机构编码
	 */
	private String institutionId;

	/**
	 * 机构名称
	 */
	private String institutionName;

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	@Override
	public String toString() {
		return "GsInstitution [institutionId=" + institutionId + ", institutionName=" + institutionName + "]";
	}

}
