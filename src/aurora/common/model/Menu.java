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
@Table(name = "TB_D_menu")
public class Menu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MENU")
	@SequenceGenerator(name = "SEQ_MENU", sequenceName = "SQ_MENU_PK")
	@Column(name = "td_menu_key")
	private long menuKey;

	@Column(name = "td_menu_name_p")
	private String nameP;

	@Column(name = "td_menu_name_s")
	private String nameS;

	@Column(name = "td_menu_desc_p")
	private String descP;

	@Column(name = "td_menu_desc_s")
	private String descS;

	@Column(name = "td_menu_keyword")
	private String keyword;

	@Column(name = "td_menu_url")
	private String url;

	@Column(name = "td_menu_parent_key")
	private Long parentKey;

	@Column(name = "td_menu_status")
	private String activeYN;

	@Column(name = "td_menu_eff_start_date")
	private Date effStartDate;

	@Column(name = "td_menu_eff_end_date")
	private Date effEndDate;

	@Column(name = "td_menu_created_user")
	private Long createdUser;

	@Column(name = "td_menu_created_date", updatable = false)
	private Date createdDateTime;


	@Column(name = "td_menu_updated_user")
	private Long updatedUser;

	@Column(name = "td_menu_updated_date")
	private Date updatedDateTime;

	@Column(name = "td_menu_module_key")
	private Long moduleKey;
	
	
	
	@Column(name="td_menu_cls")
	private String menuClass;
	
	@Column(name="TD_MENU_SEQ_NO")
	private Integer seqNo;
	
	@Transient
	private String subMenu;
	
	@Transient
	private String moduleName;
	
	@Transient
	private String parentMenuName;
	
	
	
	
	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	@Transient
	private String createdUserName;
	@Transient
	private String updatedUserName;
	public long getMenuKey() {
		return menuKey;
	}
	public String getNameP() {
		return nameP;
	}
	public String getNameS() {
		return nameS;
	}
	public String getDescP() {
		return descP;
	}
	public String getDescS() {
		return descS;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getUrl() {
		return url;
	}
	public Long getParentKey() {
		return parentKey;
	}
	public String getActiveYN() {
		return activeYN;
	}
	public Date getEffStartDate() {
		return effStartDate;
	}
	public Date getEffEndDate() {
		return effEndDate;
	}
	public Long getCreatedUser() {
		return createdUser;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public Long getUpdatedUser() {
		return updatedUser;
	}
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public String getCreatedUserName() {
		return createdUserName;
	}
	public String getUpdatedUserName() {
		return updatedUserName;
	}
	public void setMenuKey(long menuKey) {
		this.menuKey = menuKey;
	}
	public void setNameP(String nameP) {
		this.nameP = nameP;
	}
	public void setNameS(String nameS) {
		this.nameS = nameS;
	}
	public void setDescP(String descP) {
		this.descP = descP;
	}
	public void setDescS(String descS) {
		this.descS = descS;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setParentKey(Long parentKey) {
		this.parentKey = parentKey;
	}
	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}
	public void setEffStartDate(Date effStartDate) {
		this.effStartDate = effStartDate;
	}
	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
	}
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public void setUpdatedUser(Long updatedUser) {
		this.updatedUser = updatedUser;
	}
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}

	
	

	public String getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}

	


	
}
