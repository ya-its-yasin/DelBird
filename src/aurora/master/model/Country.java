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

@Entity
@Table(name = "TB_D_COUNTRY")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_COUNTRY")
	@SequenceGenerator(name = "SQ_D_COUNTRY", sequenceName = "SQ_D_COUNTRY",allocationSize=1)
	@Column(name = "TD_CNT_KEY")
	private long cntKey;
	
	@Column(name = "TD_CNT_NAME_P")
	private String nameP;
	
	@Column(name = "TD_CNT_NAME_S")
	private String nameS;
	
	@Column(name = "TD_CNT_STATUS")
	private char status;
	
	@Column(name = "TD_CNT_CREATED_USER")
	private Long createdUser;

	@Column(name = "TD_CNT_CREATED_DATE",  updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_CNT_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_CNT_UPDATED_DATE")
	private Date updatedDateTime;

	public long getCntKey() {
		return cntKey;
	}

	public void setCntKey(long cntKey) {
		this.cntKey = cntKey;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
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
	
	
	
}
