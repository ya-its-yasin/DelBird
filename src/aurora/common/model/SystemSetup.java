package aurora.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TB_D_SYS_SETUP")
public class SystemSetup {
	
	@Id
	@Column(name="TD_SS_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SYS_SETUP")
	@SequenceGenerator(name = "SYS_SETUP", sequenceName = "SQ_SYS_SETUP")
	private long ssKey;
	
	@Column(name="TD_SS_BILINGUAL_YN")
	private Character ssBilingualYN;
	
	@Column(name="TD_SS_PLANG_SL_KEY")
	private Long ssLangP;
	
	@Column(name="TD_SS_SLANG_SL_KEY")
	private Long ssLangS;
	
	

	@Transient
	private String ssLangpName;
	
	@Transient
	private String ssLangsName;
	
	
	
	
	
	@Transient
	private SystemLanguage slLangP;
	
	@Transient
	private SystemLanguage slLangS;
	
	
	
	
	public SystemLanguage getSlLangP() {
		return slLangP;
	}

	public void setSlLangP(SystemLanguage slLangP) {
		this.slLangP = slLangP;
	}

	public SystemLanguage getSlLangS() {
		return slLangS;
	}

	public void setSlLangS(SystemLanguage slLangS) {
		this.slLangS = slLangS;
	}

	

	public long getSsKey() {
		return ssKey;
	}

	public void setSsKey(long ssKey) {
		this.ssKey = ssKey;
	}

	public Character getSsBilingualYN() {
		return ssBilingualYN;
	}

	public void setSsBilingualYN(Character ssBilingualYN) {
		this.ssBilingualYN = ssBilingualYN;
	}

	public Long getSsLangP() {
		return ssLangP;
	}

	public void setSsLangP(Long ssLangP) {
		this.ssLangP = ssLangP;
	}

	public Long getSsLangS() {
		return ssLangS;
	}

	public void setSsLangS(Long ssLangS) {
		this.ssLangS = ssLangS;
	}

	
	public String getSsLangpName() {
		return ssLangpName;
	}

	public void setSsLangpName(String ssLangpName) {
		this.ssLangpName = ssLangpName;
	}

	public String getSsLangsName() {
		return ssLangsName;
	}

	public void setSsLangsName(String ssLangsName) {
		this.ssLangsName = ssLangsName;
	}
	
	
	

}
