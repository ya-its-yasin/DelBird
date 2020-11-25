package aurora.common.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class DelUser implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_DELUSERS")
	@SequenceGenerator(name = "SQ_DELUSERS", sequenceName = "SQ_DELUSERS",allocationSize=1)
	@Column(name= "USERKEY")
	private long userKey;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "USERID")
	private String userId;
	
	@Column(name = "MOBNUM")
	private String mobNum;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "DOB")
	private Date dobDateTime;
	
	@Column(name = "PASSWORD")
	private String password;
	
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobNum() {
		return mobNum;
	}

	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

	public Date getDobDateTime() {
		return dobDateTime;
	}

	public void setDobDateTime(Date dobDateTime) {
		this.dobDateTime = dobDateTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserKey() {
		return userKey;
	}

	public void setUserKey(long userKey) {
		this.userKey = userKey;
	}
	
	
}
