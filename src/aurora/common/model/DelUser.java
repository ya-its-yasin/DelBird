package aurora.common.model;

import java.io.Serializable;

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
	
	@Column(name = "USERNAME")
	private long userName;
	
	@Column(name = "USERID")
	private long userId;
	
	@Column(name = "MOBNUM")
	private long mobNum;
	
	@Column(name = "GENDER")
	private long gender;
	
	@Column(name = "DOB")
	private long DOB;
	
	@Column(name = "PASSWORD")
	private long password;
	
	public long getUserName() {
		return userName;
	}

	public void setUserName(long userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMobNum() {
		return mobNum;
	}

	public void setMobNum(long mobNum) {
		this.mobNum = mobNum;
	}

	public long getGender() {
		return gender;
	}

	public void setGender(long gender) {
		this.gender = gender;
	}

	public long getDOB() {
		return DOB;
	}

	public void setDOB(long dOB) {
		DOB = dOB;
	}

	public long getPassword() {
		return password;
	}

	public void setPassword(long password) {
		this.password = password;
	}
	
	
}
