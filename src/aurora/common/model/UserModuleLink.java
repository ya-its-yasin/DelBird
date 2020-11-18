package aurora.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_D_USER_MOD_LINK")
public class UserModuleLink implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "TD_UMDL_KEY")
	private long userModuleLinkKey;

	@Column(name = "TD_UMDL_USER_KEY")
	private Long userKey;

	@Column(name = "TD_UMDL_MODULE_KEY")
	private Long moduleKey;

	@Column(name = "TD_UMDL_APPL_YN")
	private char applicableYN;

	@Column(name = "TD_UMDL_CREATED_USER", updatable=false)
	private Long createdUser;

	@Column(name = "TD_UMDL_CREATED_DATE", updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_UMDL_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_UMDL_UPDATED_DATE")
	private Date updatedDateTime;

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	@Transient
	private String moduleName;
	public long getUserModuleLinkKey() {
		return userModuleLinkKey;
	}
	public void setUserModuleLinkKey(long userModuleLinkKey) {
		this.userModuleLinkKey = userModuleLinkKey;
	}
	public Long getUserKey() {
		return userKey;
	}
	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public char getApplicableYN() {
		return applicableYN;
	}
	public void setApplicableYN(char applicableYN) {
		this.applicableYN = applicableYN;
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
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
