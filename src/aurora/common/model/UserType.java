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
@Table(name = "TB_D_USERTYPE")
public class UserType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_USERTYPE")
	@SequenceGenerator(name = "SQ_D_USERTYPE", sequenceName = "SQ_D_USERTYPE")
	@Column(name = "TD_UT_KEY")
	private long userTypeKey;

	@Column(name = "TD_UT_KEYWORD")
	private String keyword;
	
	@Column(name = "TD_UT_NAME_P")
	private String nameP;

	@Column(name = "TD_UT_NAME_S")
	private String nameS;

	@Column(name = "TD_UT_STATUS")
	private String activeYN;

	@Column(name = "TD_UT_EFF_START_DATE")
	private Date effStartDate;

	@Column(name = "TD_UT_EFF_END_DATE")
	private Date effEndDate;

	@Column(name = "TD_UT_CREATED_USER")
	private Long createdUser;

	@Column(name = "TD_UT_CREATED_DATE", updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_UT_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_UT_UPDATED_DATE")
	private Date updatedDateTime;

	@Column(name="TD_UT_SEQNO")
	private Integer seqNo;
	
	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	
	
	
	
	
	
	
	
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public long getUserTypeKey() {
		return userTypeKey;
	}
	public void setUserTypeKey(long userTypeKey) {
		this.userTypeKey = userTypeKey;
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
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
		
	
}
