package aurora.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_D_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_USER")
	@SequenceGenerator(name = "SQ_D_USER", sequenceName = "SQ_D_USER",allocationSize=1)
	@Column(name = "TD_USER_KEY")
	private long userKey;

	@Column(name = "TD_USER_NAME_P")
	private String nameP;

	@Column(name = "TD_USER_NAME_S")
	private String nameS;

	@Column(name = "TD_USER_LOGINID")
	private String loginId;

	@Column(name = "TD_USER_PASSWORD")
	private String userPassword;

	@Column(name = "TD_USER_EMAILID")
	private String emailId;

	@Column(name = "TD_USER_CONTACT_NO")
	private String contactNo;

	@Column(name = "TD_USER_USERGROUP")
	private Long userGroup;

	

	@Column(name = "TD_USER_STATUS")
	private String activeYN;

	@Column(name = "TD_USER_EFF_START_DATE")
	private Date effStartDate;

	@Column(name = "TD_USER_EFF_END_DATE")
	private Date effEndDate;

	@Column(name = "TD_USER_CREATED_USER")
	private Long createdUser;

	@Column(name = "TD_USER_CREATED_DATE",  updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_USER_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_USER_UPDATED_DATE")
	private Date updatedDateTime;

	
	
	
	@Column(name="TD_USER_LOCK_YN")
	private Character usrLockYN;
	
	@Column(name="TD_USER_TYPE")
	private Character userType;
	
	@Column(name = "TD_USER_REF_ID")
	private Long refID;


    @Column(name="TD_USER_IMG_EXT")
    private String image;
	
	
    
    
	
	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}







	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
	
	
	
	public Character getUserType() {
		return userType;
	}
	public void setUserType(Character userType) {
		this.userType = userType;
	}


	public Long getRefID() {
		return refID;
	}

	public void setRefID(Long refID) {
		this.refID = refID;
	}






	@Transient
	private String groupName;

	@Transient
	private String entityName;

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	
	@Transient
	private String passwordPolicyResult;
	
	@Transient
	private String userThemeName;
	
	@Transient
	private String userHomePageAttr;
	
	@Transient
	private String userDefaultModule;

	
	
	public String getPasswordPolicyResult() {
		return passwordPolicyResult;
	}
	public void setPasswordPolicyResult(String passwordPolicyResult) {
		this.passwordPolicyResult = passwordPolicyResult;
	}
	public String getUserThemeName() {
		return userThemeName;
	}
	public void setUserThemeName(String userThemeName) {
		this.userThemeName = userThemeName;
	}
	public String getUserHomePageAttr() {
		return userHomePageAttr;
	}
	public void setUserHomePageAttr(String userHomePageAttr) {
		this.userHomePageAttr = userHomePageAttr;
	}
	
	

	public long getUserKey() {
		return userKey;
	}
	public void setUserKey(long userKey) {
		this.userKey = userKey;
	}
	public String getNameP() {
		return nameP;
	}
	public void setNameP(String nameP) {
		this.nameP = nameP;
	}
	public String getNameS() {
		return nameS;
	}
	public void setNameS(String nameS) {
		this.nameS = nameS;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Long getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(Long userGroup) {
		this.userGroup = userGroup;
	}

	public String getActiveYN() {
		return activeYN;
	}
	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}
	public Date getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(Date effStartDate) {
		this.effStartDate = effStartDate;
	}
	public Date getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
	}
	public Long getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Long getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(Long updatedUser) {
		this.updatedUser = updatedUser;
	}
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public String getUpdatedUserName() {
		return updatedUserName;
	}
	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	
	
	public String getUserDefaultModule() {
		return userDefaultModule;
	}
	public void setUserDefaultModule(String userDefaultModule) {
		this.userDefaultModule = userDefaultModule;
	}
	
	public Character getUsrLockYN() {
		return usrLockYN;
	}
	public void setUsrLockYN(Character usrLockYN) {
		this.usrLockYN = usrLockYN;
	}
	
	
	

	
}
