package aurora.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aurora.common.dao.MenuDAO;
import aurora.common.model.CustomCombo;
import aurora.common.model.Menu;
import aurora.common.model.Module;
import aurora.common.model.User;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class MenuService {

	static Logger logger = Logger.getLogger(MenuService.class);

	@Autowired
	private MenuDAO menuDAO;
	
	@Autowired
	UserMenuLinkService userMenuLinkService;
	@Autowired
	MessageSource messageSource;

	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getMenuList(HttpServletRequest request) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<Menu> list = (ArrayList<Menu>) menuDAO.getAll(lang);
			for(Menu menu:list){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(menu));
			}
		} catch (Exception e) {
			logger.error("Transaction Failed in getMenuList Method >>", e);
		}
		return ar;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getListForCombo(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) menuDAO
					.getParentMenuByModuleKey(lang, Long.parseLong(request.getParameter("moduleKey").toString()));
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getListForComboForParentMenu(HttpServletRequest request,Long menuKey) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) menuDAO
					.getParentMenuByModuleKey(lang, menuKey);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getMenus(HttpServletRequest request,Long menuKey) {
		 
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
		List<Menu> list = (ArrayList<Menu>) menuDAO.getMenus(lang, menuKey);
		for(Menu menu:list){
			ar.add(JsonReaderWriter.getJSONObjectFromClassObject(menu));
		}
			//ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getParentMenusByModuleKey(HttpServletRequest request,Long moduleKey) {
		 
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
		List<Menu> list = (ArrayList<Menu>) menuDAO.getParentMenusByModuleKey(lang, moduleKey);
		for(Menu menu:list){
			ar.add(JsonReaderWriter.getJSONObjectFromClassObject(menu));
		}
			//ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	
	@Transactional
	public Map<String, Object> getMenuByKey(HttpServletRequest request) {

		Menu menu = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			menu = (Menu) menuDAO.getObjectByKey(key);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(menu);
		} catch (Exception e) {
			msg = ConstantsMsg.SUCCESS;
			lMsg = "messages.fetch.error";
			logger.error("Transaction Failed in getMenuByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, (lMsg == null ? ""
				: messageSource.getMessage(lMsg, null, request.getLocale())));
	}

	@Transactional
	public Map<String, Object> saveMenu(Menu menu, HttpServletRequest request) {
		Menu m = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.menuSave.success";
		try {
			menu.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			menu.setCreatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			menu.setSeqNo(menuDAO.getMaxSeqNo(Menu.class));
			m = (Menu) menuDAO.add(menu);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(m);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = "messages.menuSave.error";
			logger.error("Transaction Failed in Save Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,
				messageSource.getMessage(lMsg, null, request.getLocale()));

	}

	@Transactional
	public Map<String, Object> updateMenu(Menu menu, HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		JSONObject jsonObject=null;
		String lMsg = "messages.menuUpdate.success";
		try {
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menu.getMenuKey());
			String flag = request.getParameter("flag");
			boolean dataIntrCheck = UserDateFormat.getDISByDate(menu.getUpdatedDateTime(), dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true
					|| (flag != null && flag.equalsIgnoreCase("Overwrite"))) {
				menu.setUpdatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				menu.setUpdatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
				menu.setSeqNo(menu.getSeqNo()==0?null:menu.getSeqNo());
				menu = (Menu) menuDAO.update(menu);
				
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.menuUpdate.error";
				menu = dbObject;
			}
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(menu);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = "messages.menuSave.error";
			logger.error("Transaction Failed in Update Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,
				messageSource.getMessage(lMsg, null, request.getLocale()));
	}

	@Transactional
	public Map<String, Object> deleteMenu(HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.menuDelete.success";
		Menu menu = null;
		JSONObject jsonObject=null;
		try {
			String flag = request.getParameter("flag");
			String updatedDate = request.getParameter("updatedDate");
			Long menuKey = Long.parseLong(request.getParameter("key"));
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menuKey);
			Date dt =(updatedDate==null?null:UserDateFormat.convertStringToDate(updatedDate, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			boolean dataIntrCheck = UserDateFormat.getDISByDate(dt, dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true || flag.equalsIgnoreCase("Delete")) {
				menu = (Menu) menuDAO.delete(menuKey);
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.deleteModified.success";
			}
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(menu);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = "messages.menuDelete.error";
			logger.error("Transaction Failed in Delete Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,
				messageSource.getMessage(lMsg, null, request.getLocale()));

	}

	@Transactional
	public Map<String, Object> getFirstMenu(HttpServletRequest request) {
		Menu menu = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.FIRST;
		try {
			menu =  menuDAO.getFirst(Menu.class);
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menu
					.getMenuKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getFirstMenu Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getLastMenu(HttpServletRequest request) {
		Menu menu = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.LAST;
		try {
			menu =  menuDAO.getLast(Menu.class);
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menu
					.getMenuKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getLastMenu Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getNextMenu(HttpServletRequest request) {
		Menu menu = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			menu =  menuDAO.getNext(key,Menu.class);
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menu
					.getMenuKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_FORWARD;
			logger.error("Transaction Failed in getNextMenu Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getPreviousMenu(HttpServletRequest request) {
		Menu menu = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			menu =  menuDAO.getPrev(key,Menu.class);
			Menu dbObject = (Menu) menuDAO.getObjectByKey(menu
					.getMenuKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_BACKWARD;
			logger.error("Transaction Failed in getPreviousMenu Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}
	@Transactional
	public List<Menu> getMenuListForAccordion(Long userKey) {
		return menuDAO.getMenuListForAccordion(userKey);
	}
	@Transactional
	public List<Module>getModuleListForAccordian(Long userKey)
	{
		return menuDAO.getModuleListForAccordian(userKey);
	}

	@Transactional
	public List<Menu> getListByParentKey(Long modulekey, Long userKey) 
	{
		
		return menuDAO.getListByParentKey(modulekey, userKey);
	}
	
	@Transactional
	public List<Menu> getModulePanel(Long modulekey, Long userKey)
	{
		return menuDAO.getModulePanel(modulekey, userKey);
	}

	@Transactional
	public List<Menu> getModulePanel1(Long modulekey, Long userKey)
	{
		return menuDAO.getModulePanel1(modulekey, userKey);
	}
	
	@Transactional
	public List<Menu> getMenuPanel(Long menuKey, Long userKey)
	{
		return menuDAO.getMenuPanel(menuKey, userKey);
	}

	@Transactional
	public Boolean swapMenuHeadSeqNO(Integer currentSeqNO,Integer prevSeqNo) throws Exception {
	
		
		return menuDAO.swapRecord(currentSeqNO, prevSeqNo,Menu.class);
	}
}
