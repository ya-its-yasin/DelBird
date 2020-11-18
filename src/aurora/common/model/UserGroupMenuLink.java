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
@Table(name = "TB_D_USERGROUP_MENU_LINK")
public class UserGroupMenuLink implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USER_GROUP_MENU_LINK")
	@SequenceGenerator(name = "SEQ_USER_GROUP_MENU_LINK", sequenceName = "SQ_USERGROUP_MENULINK_PK")
	@Column(name = "TD_UGML_KEY")
	private long userGroupMenuLinkKey;

	@Column(name = "TD_UGML_GROUP_KEY")
	private Long groupKey;

	@Column(name = "TD_UGML_MENU_KEY")
	private Long menuKey;

	@Column(name = "TD_UGML_QUERY_YN")
	private String queryYN;

	@Column(name = "TD_UGML_INSERT_YN")
	private String insertYN;

	@Column(name = "TD_UGML_UPDATE_YN")
	private String updateYN;

	@Column(name = "TD_UGML_DELETE_YN")
	private String deleteYN;

	@Column(name = "TD_UGML_PRINT_YN")
	private String printYN;

	@Column(name = "TD_UGML_EXPORT_YN")
	private String exportYN;

	@Column(name = "TD_UGML_CREATED_USER")
	private Long createdUser;

	@Column(name = "TD_UGML_CREATED_DATE",  updatable=false)
	private Date createdDateTime;

	@Column(name = "TD_UGML_UPDATED_USER")
	private Long updatedUser;

	@Column(name = "TD_UGML_UPDATED_DATE")
	private Date updatedDateTime;

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;

	@Transient
	private String menuName;

	public long getUserGroupMenuLinkKey() {
		return userGroupMenuLinkKey;
	}

	public void setUserGroupMenuLinkKey(long userGroupMenuLinkKey) {
		this.userGroupMenuLinkKey = userGroupMenuLinkKey;
	}

	public Long getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(Long groupKey) {
		this.groupKey = groupKey;
	}

	public Long getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(Long menuKey) {
		this.menuKey = menuKey;
	}

	

	public String getQueryYN() {
		return queryYN;
	}

	public void setQueryYN(String queryYN) {
		this.queryYN = queryYN;
	}

	public String getInsertYN() {
		return insertYN;
	}

	public void setInsertYN(String insertYN) {
		this.insertYN = insertYN;
	}

	public String getUpdateYN() {
		return updateYN;
	}

	public void setUpdateYN(String updateYN) {
		this.updateYN = updateYN;
	}

	public String getDeleteYN() {
		return deleteYN;
	}

	public void setDeleteYN(String deleteYN) {
		this.deleteYN = deleteYN;
	}

	public String getPrintYN() {
		return printYN;
	}

	public void setPrintYN(String printYN) {
		this.printYN = printYN;
	}

	public String getExportYN() {
		return exportYN;
	}

	public void setExportYN(String exportYN) {
		this.exportYN = exportYN;
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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

		
}
