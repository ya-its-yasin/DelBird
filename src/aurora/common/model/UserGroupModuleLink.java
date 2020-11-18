package aurora.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_D_USERGROUP_MOD_LINK")
public class UserGroupModuleLink {
	
	@Id
	@GeneratedValue
	@Column(name = "TD_UGMDL_KEY")
	private long userGroupModuleLinkKey;

	@Column(name = "TD_UGMDL_UG_KEY")
	private Long userGroupKey;

	@Column(name = "TD_UGMDL_MODULE_KEY")
	private Long moduleKey;

	@Column(name = "TD_UGMDL_APPL_YN")
	private char applicableYN;

	@Column(name = "TD_UGMDL_CREATED_USER", updatable=false)
	private Long createdUser;

	@Column(name = "TD_UGMDL_CREATED_DATE",  updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_UGMDL_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_UGMDL_UPDATED_DATE")
	private Date updatedDateTime;

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	@Transient
	private String moduleName;
	
	public long getUserGroupModuleLinkKey() {
		return userGroupModuleLinkKey;
	}
	public Long getUserGroupKey() {
		return userGroupKey;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public char getApplicableYN() {
		return applicableYN;
	}
	public Long getCreatedUser() {
		return createdUser;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public Long getUpdatedUser() {
		return updatedUser;
	}
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public String getUpdatedUserName() {
		return updatedUserName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setUserGroupModuleLinkKey(long userGroupModuleLinkKey) {
		this.userGroupModuleLinkKey = userGroupModuleLinkKey;
	}
	public void setUserGroupKey(Long userGroupKey) {
		this.userGroupKey = userGroupKey;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public void setApplicableYN(char applicableYN) {
		this.applicableYN = applicableYN;
	}
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public void setUpdatedUser(Long updatedUser) {
		this.updatedUser = updatedUser;
	}
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	
	

	
	
	
	

}
