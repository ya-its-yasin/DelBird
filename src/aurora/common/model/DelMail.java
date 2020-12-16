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
@Table(name = "ALLMAILS")
public class DelMail implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_DELMAILS")
	@SequenceGenerator(name = "SQ_DELUSERS", sequenceName = "SQ_DELMAILS",allocationSize=1)
	@Column(name= "MAILKEY")
	private long mailKey;
	
	@Column(name="MTO")
	private String mTo;
	
	@Column(name="MSUBJECT")
	private String mSubject;
	
	@Column(name="MCONTENT")
	private String mContent;
	
	@Column(name="MTIME")
	private String mTime;
	
	@Column(name="MSTATUS")
	private String mStatus;

	public long getMailKey() {
		return mailKey;
	}

	public void setMailKey(long mailKey) {
		this.mailKey = mailKey;
	}

	public String getmTo() {
		return mTo;
	}

	public void setmTo(String mTo) {
		this.mTo = mTo;
	}

	public String getmSubject() {
		return mSubject;
	}

	public void setmSubject(String mSubject) {
		this.mSubject = mSubject;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	
	
}
