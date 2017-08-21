package com.cy.core.alumni.entity;

import java.io.Serializable;
import java.util.Date;

public class Alumni implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long  alumniId;
	private String alumniName;
	private String region;
	private String mainId;
	private String mainType;	//lixun 2016-7-12 分会主类型
	private String xueyuanId;	//lixun 2016-7-12 学院ID
	private String industryCode;	//lixun 2016-7-12 行业编码
	private Long parentId;		//lixun 2016-7-12 父ID
	private String delState;	//lixun 2016-7-12 删除标记
	private String isUsed;		//lixun 2016-7-12 是否开通
	private Long level;		//lixun 级别
	private String sequence;	//lixun 序列
	private String parent;	//上级机构
	private String admin;	//管理员表
	private String industry;
	private String hobby;
	private String introduction;
	private Integer status;
	private Long createById;
	private Long accountNum;
	private Date createTime;
	private Integer checkFlag;
	private Long userId;

	private boolean isLeader;// 是否为总会

	static public long g_lXueYuanFenHui = 0;		//lixun 学院分会ID,新增学院分会时使用
	static public long g_lDiQuFenHui = 0;			//地区分会ID,新增地区分会时使用
	static public long g_lHangYeFenHui = 0;			//行业分会ID,新增行业分会时使用

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public Integer getUserFlag() {
		return userFlag;
	}

	public Long getuRoleId() {
		return uRoleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleSystemAdmin() {
		return roleSystemAdmin;
	}

	public Integer getRoleFlag() {
		return roleFlag;
	}
	/*2016-07-16*/
	private String userName;
	private String userAccount;
	private String userPassword;
	private String userEmail;
	private String userTelephone;
	private String userDeptId;
	private Integer userFlag;
	private Long uRoleId;
	private Long roleId;
	private String roleName;
	private String roleSystemAdmin;
	private Integer roleFlag;
	/*2016-07-17*/
	private String diFullName;

	public String getAcademyName() {
		return academyName;
	}

	public void setAcademyName(String academyName) {
		this.academyName = academyName;
	}

	/*2016-07-30*/
	private String academyName;

	private Long id;
	private String name;
	private String memberCount;
	private String presidentName;
	private String telephone;
	private String address;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long   getAlumniId() {
		return alumniId;
	}

	public void setAlumniId(Long  alumniId) {
		this.alumniId = alumniId;
	}

	public String getAlumniName() {
		return alumniName;
	}

	public void setAlumniName(String alumniName) {
		this.alumniName = alumniName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateById() {
		return createById;
	}

	public void setCreateById(Long createById) {
		this.createById = createById;
	}

	public Long getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(Long accountNum) {
		this.accountNum = accountNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/*lixun 2016-7-12*/
	
	public String getMainType() {
		return mainType;
	}
	public void setMainType(String MainType) {
		mainType = MainType;
	}
	
	public String getXueyuanId() {
		return xueyuanId;
	}
	public void setXueyuanId(String XueyuanId) {
		xueyuanId = XueyuanId;
	}
	
	public String getIndustryCode() {
		return industryCode;
	}
	public void getIndustryCode(String IndustryCode) {
		industryCode = IndustryCode;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long ParentId) {
		parentId = ParentId;
	}
	
	public String getDelState() {
		return delState;
	}
	public void getDelState(String DelState) {
		delState = DelState;
	}
	
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String IsUsed) {
		isUsed = IsUsed;
	}
	
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long Level) {
		level = Level;
	}
	
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String Sequence) {
		sequence = Sequence;
	}
	
	public String getParent() {
		return parent;
	}
	public void getParent(String Parent) {
		parent = Parent;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	/*end lixun*/

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public boolean isLeader() {
		if ("99".equals(this.getMainId()) && 0 == this.getParentId()) {
			this.setLeader(true);
		}
		else {
			this.setLeader(false);
		}
		return isLeader;
	}

	public void setLeader(boolean leader) {
		isLeader = leader;
	}
}
