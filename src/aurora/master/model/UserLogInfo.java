package aurora.master.model;

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

import org.apache.commons.net.ntp.TimeStamp;

@Entity
@Table(name = "TB_D_USER_LOG")
public class UserLogInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_USER_LOG")
	@SequenceGenerator(name = "SQ_D_USER_LOG", sequenceName = "SQ_D_USER_LOG",allocationSize=1)
	@Column(name = "TD_UL_ID")
	private long ulKey;
	
	@Column(name = "TD_UL_DATE_TIME")
	private Date ulDateTime;
	
	@Column(name = "TD_UL_ACTION")
	private String action;
	
	@Column(name = "TD_UL_USERNAME")
	private String userName;
	
	@Column(name = "TD_UL_USER_KEY")
	private Long userKey;

	public long getUlKey() {
		return ulKey;
	}

	public void setUlKey(long ulKey) {
		this.ulKey = ulKey;
	}



	public Date getUlDateTime() {
		return ulDateTime;
	}

	public void setUlDateTime(Date ulDateTime) {
		this.ulDateTime = ulDateTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	
	
	
	
}

