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
@Table(name = "TB_D_USERGROUP")
public class UserGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_USERGROUP")
	@SequenceGenerator(name = "SQ_D_USERGROUP", sequenceName = "SQ_D_USERGROUP",allocationSize=1)
	@Column(name = "TD_UG_KEY")
	private long userGroupKey;

	
	@Column(name = "TD_UG_KEYWORD")
	private String keyword;
	
	@Column(name = "TD_UG_NAME_P")
	private String nameP;

	@Column(name = "TD_UG_NAME_S")
	private String nameS;

	/*@Column(name = "TD_UG_USER_TYPE")
	private Long userType;*/

	@Column(name = "TD_UG_STATUS")
	private String activeYN;

	@Column(name = "TD_UG_EFF_START_DATE")
	private Date effStartDate;

	@Column(name = "TD_UG_EFF_END_DATE")
	private Date effEndDate;

	@Column(name = "TD_UG_CREATED_USER")
	private Long createdUser;

	@Column(name = "TD_UG_CREATED_DATE",  updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_UG_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_UG_UPDATED_DATE")
	private Date updatedDateTime;

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	
	@Transient
	private String userTypeName;
	
	
	
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public long getUserGroupKey() {
		return userGroupKey;
	}
	public void setUserGroupKey(long userGroupKey) {
		this.userGroupKey = userGroupKey;
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
	
	
	/*public Long getUserType() {
		return userType;
	}
	public void setUserType(Long userType) {
		this.userType = userType;
	}*/
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
	
	
	
}