package aurora.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TB_D_SYS_LANG")
public class SystemLanguage {
	
	@Id
	@Column(name = "TD_SL_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SYS_LANG")
	@SequenceGenerator(name = "SYS_LANG", sequenceName = "SQ_SYS_LANG")
	private long slKey;
	
	@Column(name="TD_SL_CODE")
	private String slCode;
	
	@Column(name="TD_SL_DESC")
	private String slDesc;
	
	@Column(name="TD_SL_DIR")
	private String slDir;

	public long getSlKey() {
		return slKey;
	}

	public void setSlKey(long slKey) {
		this.slKey = slKey;
	}

	public String getSlCode() {
		return slCode;
	}

	public void setSlCode(String slCode) {
		this.slCode = slCode;
	}

	public String getSlDesc() {
		return slDesc;
	}

	public void setSlDesc(String slDesc) {
		this.slDesc = slDesc;
	}

	public String getSlDir() {
		return slDir;
	}

	public void setSlDir(String slDir) {
		this.slDir = slDir;
	}
	
	
	

}
