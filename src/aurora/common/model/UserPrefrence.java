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
@Table(name = "TB_D_user_preference")
public class UserPrefrence implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_D_USER_PREFERENCE")
	@SequenceGenerator(name = "SQ_D_USER_PREFERENCE", sequenceName = "SQ_D_USER_PREFERENCE")
	@Column(name = "td_up_key")
	private long userThemePrefrenceKey;
	@Column(name = "td_up_userkey")
	private Long userKey;
	@Column(name = "TD_UP_PREFERENCE_KEYWORD")
	private String preferenceKeyword;
	@Column(name = "TD_UP_PREFERENCE_VALUE")
	private String preferenceValue;

	public long getUserThemePrefrenceKey() {
		return userThemePrefrenceKey;
	}

	public void setUserThemePrefrenceKey(long userThemePrefrenceKey) {
		this.userThemePrefrenceKey = userThemePrefrenceKey;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	public String getPreferenceKeyword() {
		return preferenceKeyword;
	}

	public void setPreferenceKeyword(String preferenceKeyword) {
		this.preferenceKeyword = preferenceKeyword;
	}

	public String getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(String preferenceValue) {
		this.preferenceValue = preferenceValue;
	}

}
