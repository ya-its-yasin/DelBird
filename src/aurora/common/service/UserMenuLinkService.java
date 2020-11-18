package aurora.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aurora.common.dao.UserMenuLinkDAO;
import aurora.common.model.Menu;
import aurora.common.model.User;
import aurora.common.model.UserGroup;
import aurora.common.model.UserGroupMenuLink;
import aurora.common.model.UserMenuLink;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class UserMenuLinkService {
	
	static Logger logger = Logger.getLogger(UserMenuLinkService.class);

	@Autowired 
	private UserMenuLinkDAO userMenuLinkDAO;
/*	@Autowired
	UserMenuLinkService userMenuLinkService;*/
	@Autowired
	MessageSource messageSource;

	@Autowired
	private MenuService menuService;

	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> userAccess(String loginKey,String menuKey,HttpServletRequest request) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<UserMenuLink> list = (ArrayList<UserMenuLink>) userMenuLinkDAO.userAccess(loginKey,menuKey,lang);
			for(UserMenuLink userMenuLink:list){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(userMenuLink));
			}
		} catch (Exception e) {
			logger.error("Transaction Failed in getModuleList Method >>", e);
		}
		return ar;
	}
	
	


	@Transactional
	public List<JSONObject> getUserList(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();;
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<User> list = (ArrayList<User>) userMenuLinkDAO.getAll(lang);
			for (User u : list) {
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(u));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Transaction Failed in getUserList Method >>", e);
		}
		return ar;
	}

	@Transactional
	public List<JSONObject> getUserModuleLinkByUserKey(HttpServletRequest request) {

		List<JSONObject> finalList = new ArrayList<JSONObject>();
		try {
			String lang=request.getSession().getAttribute("lang")!=null?request.getSession().getAttribute("lang").toString():"en";
			Long userKey = Long.parseLong(request.getParameter("userKey"));
			List<JSONObject> ar= new ArrayList<JSONObject>();
			// get the module names.
			List<Menu> moduleList =userMenuLinkDAO.getUserModuleLinkListByUserKey(userKey,lang);
			for(Menu mod:moduleList){
				JSONObject moObject= JsonReaderWriter.getJSONObjectFromClassObject(mod);
				// get parent menus
				List<JSONObject> parentMenus = menuService.getParentMenusByModuleKey(request,mod.getModuleKey());
				for(int i=0;i<parentMenus.size();i++){
					JSONObject pjsonObject = parentMenus.get(i);
					Long parentKey=Long.parseLong(pjsonObject.get("menuKey").toString());
					// get child menus and to parent json object
					List<JSONObject> childMenus = getUserMenuLinkListByParentUserKey(userKey ,lang,parentKey,request);
					pjsonObject.put("child2",childMenus);
					parentMenus.get(i).put(i,pjsonObject);
					
				}
				// add parent menu object to module wise..
				moObject.put("child1", parentMenus);
				ar.add(moObject);
			}
			
		
			return ar;
		
		} catch (Exception e) {
			logger.error("Transaction Failed in getUserModuleLinkByUserKey Method >>", e);
		}
		return finalList;
	}
	

	@Transactional
	public List<JSONObject>  getUserMenuLinkListByParentUserKey(Long userKey,String lang,Long parentMenuKey,HttpServletRequest request){
		List<JSONObject> ar = new ArrayList<JSONObject>();
	
		try {
			List<UserMenuLink> umlList = userMenuLinkDAO.getUserMenuLinkListByParentUserKey(userKey,lang,parentMenuKey);
			for(UserMenuLink userMenuLink:umlList){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(userMenuLink));
			}
		} catch (Exception e) {
			
			logger.error("Transaction Failed in getUserMenuLinkListByUserKey Method >>", e);
		}
		return ar;
		
		//return userMenuLinkDAO.getUserMenuLinkListByUserKey(userKey,lang,parentMenuKey);
	}
	
	@Transactional
	public List<JSONObject>  getUserMenuLinkListByUserKey1(Long userKey,Long moduleKey,HttpServletRequest request){
		List<JSONObject> ar = new ArrayList<JSONObject>();
		String lang = request.getSession().getAttribute("lang") != null ? request
				.getSession().getAttribute("lang").toString()
				: "en";
		try {
			List<UserMenuLink> umlList = userMenuLinkDAO.getUserMenuLinkListByUserKey1(userKey,lang,moduleKey);
			for(UserMenuLink userMenuLink:umlList){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(userMenuLink));
			}
		} catch (Exception e) {
			
			logger.error("Transaction Failed in getUserMenuLinkListByUserKey Method >>", e);
		}
		return ar;
		
		//return userMenuLinkDAO.getUserMenuLinkListByUserKey(userKey,lang,parentMenuKey);
	}
	
	@Transactional
	public Map<String, Object>  getUserRights(String keyword, int userKey) {
		UserMenuLink uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			uml =  userMenuLinkDAO.getUserRights(keyword, userKey);
			if(keyword.equalsIgnoreCase("UserMenuLink") || keyword.equalsIgnoreCase("UserGroupMenuLink")){
			uml.setInsertYN("N");
			uml.setDeleteYN("N");
			}
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(uml);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getUserRights Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}
	@Transactional
	public Map<String, Object>  updateFavourites(long userkey,long menukey, String favouriteYN) {
		UserMenuLink uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String message="";
		try {
			uml =  (UserMenuLink) userMenuLinkDAO.updateFavourite(userkey, menukey,  favouriteYN);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(uml);
			if(favouriteYN=="Y")
			{
				message="Menu added to your favourites.";
			}
			else
			{
				message="Menu removed from your favourites.";
			}
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getUserRights Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, message);
	}
	@Transactional
	public Map<String, Object> updateUserMenuLink(Object obj,
			HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userMenuLinksUpdate.success";
		try {
			
			List<UserMenuLink>  userMenuLinksList = new ArrayList<UserMenuLink>();
			UserMenuLink groupMenuLink = new UserMenuLink();
			List<JSONObject> jsonObjectList= (ArrayList<JSONObject>) JsonReaderWriter.getJSONClassList(obj);
			Long userKey=Long.parseLong(request.getSession().getAttribute("currentUserKey").toString());
			/*if(jsonObjectList.size()>0)
			{
				Long menuKey = Long.parseLong(jsonObjectList.get(0).get("menuKey").toString());
				Long umlUserKey = Long.parseLong(jsonObjectList.get(0).get("userKey").toString());
				UserMenuLink uml = userMenuLinkDAO.getParentByChildKey(umlUserKey,menuKey);
				uml.setQueryYN('Y');
				uml.setUpdatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				uml.setUpdatedUser(userKey);
				userMenuLinkDAO.update(uml);
			}*/
			for(JSONObject jsonObject:jsonObjectList){
				
				UserMenuLink  userMenuLink = (UserMenuLink)JsonReaderWriter.getClassObjectFromJSONObject(groupMenuLink,jsonObject);
				
				userMenuLink.setUpdatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				userMenuLink.setUpdatedUser(userKey);
				userMenuLink.setCreatedUser(userKey);
				userMenuLink.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				//UserMenuLink userMenuLink1= (UserMenuLink)userMenuLinkDAO.update(userMenuLink);
				userMenuLinksList.add(userMenuLink);
			}
			userMenuLinkDAO.saveOrUpdateList(userMenuLinksList);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = "messages.userMenuLinkUpdate.error";
			logger.error("Transaction Failed in Update Method >>", e);
			JsonReaderWriter.mapError(messageSource.getMessage(lMsg, null, request.getLocale()));
		}
		return JsonReaderWriter.mapOK(messageSource.getMessage(lMsg, null, request.getLocale()));
	}



	@Transactional
	public Map<String, Object> getFirstUserMenuLink(HttpServletRequest request) {
		User uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.FIRST;
		String lang = request.getSession().getAttribute("lang") != null ? request
				.getSession().getAttribute("lang").toString()
				: "en";
		try {
			uml =  userMenuLinkDAO.getFirstUserMenuLink(lang);
			User dbObject = (User) userMenuLinkDAO.getObjectByKey(uml
					.getUserKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getFirstUserMenuLink Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getLastUserMenuLink(HttpServletRequest request) {
		User uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.LAST;
		String lang = request.getSession().getAttribute("lang") != null ? request
				.getSession().getAttribute("lang").toString()
				: "en";
		try {
			uml = userMenuLinkDAO.getLastUserMenuLink(lang);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(uml);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getLastUserMenuLink Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getNextUserMenuLink(HttpServletRequest request) {
		User uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lang = request.getSession().getAttribute("lang") != null ? request
				.getSession().getAttribute("lang").toString()
				: "en";
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			uml =  userMenuLinkDAO.getNextUserMenuLink(key,lang);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(uml);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_FORWARD;
			logger.error("Transaction Failed in getNextUserMenuLink Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getPreviousUserMenuLink(HttpServletRequest request) {
		User uml = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lang = request.getSession().getAttribute("lang") != null ? request
				.getSession().getAttribute("lang").toString()
				: "en";
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			uml =  userMenuLinkDAO.getPreviousUserMenuLink(key,lang);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(uml);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_BACKWARD;
			logger.error("Transaction Failed in getPreviousUserMenuLink Method >>",
					e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}
	
	
	
	@Transactional
	public  List<Menu> getFavouritesFromUserMenuLink(HttpServletRequest request)
	{
		
		
		return userMenuLinkDAO.getFavouritesFromUserMenuLink(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			
	}
	@Transactional
	public  List<Menu> searchMenu(HttpServletRequest request)
	{
			
			return userMenuLinkDAO.searchMenu(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()),request.getParameter("value"));
			
	}
	
	
	
	
}
