package aurora.common.model;

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
@Table(name = "TB_L_user")
public class UserLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_L_USER")
	@SequenceGenerator(name = "SQ_L_USER", sequenceName = "SQ_L_USER",allocationSize=1)
	@Column(name = "td_ul_key")
	private long userLogKey;

	@Column(name = "td_ul_user")
	private Long user;

	@Column(name = "td_ul_activity_flag")
	private String activityFlag;

	@Column(name = "td_ul_log_time", columnDefinition = "date default sysdate", insertable = true)
	private Date logDateTime;

	
	
	@Column(name = "TD_UL_REMARK")
	private String remark;

	
	
	@Column(name = "td_ul_created_user")
	private Long createdUser;

	@Column(name = "td_ul_created_date", columnDefinition = "date default sysdate", insertable = true)
	private Date createdDateTime;

	@Transient
	private String userName;

	@Transient
	private String changedUserName;

	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getUserLogKey() {
		return userLogKey;
	}

	public void setUserLogKey(long userLogKey) {
		this.userLogKey = userLogKey;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public String getActivityFlag() {
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}



	public Date getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChangedUserName() {
		return changedUserName;
	}

	public void setChangedUserName(String changedUserName) {
		this.changedUserName = changedUserName;
	}

		
	
	
}
