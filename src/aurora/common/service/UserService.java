package aurora.common.service;

import java.math.BigInteger;
import java.security.MessageDigest;

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

import aurora.common.dao.UserDao;

import aurora.common.model.CustomCombo;
import aurora.common.model.Module;
import aurora.common.model.User;
import aurora.common.model.UserLog;
import aurora.common.model.UserPrefrence;
import aurora.master.model.Country;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.DateUtil;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class UserService {
	static Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDAO;
	
	
	
	@Autowired
	UserMenuLinkService userMenuLinkService;
	
	@Autowired
	MessageSource messageSource;


	
	
	 
	

	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getUserList(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();;
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<User> list = (ArrayList<User>) userDAO.getAll(lang);
			for (User u : list) {
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(u));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Transaction Failed in getUserList Method >>", e);
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
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getList(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getLockedUserList(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getLockedUserList(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getListForComboByUserGroupKey(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
					Long userGroupKey = Long.parseLong(request.getParameter("userGroupKey"));
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getListByUserGroup(lang, userGroupKey);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}

	@Transactional
	public Map<String, Object> getUserByKey(HttpServletRequest request) {

	
		User user = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String fMsg = null;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			user = (User) userDAO.getObjectByKey(key);
			//user.setUserPassword(userDAO.encryptDecryptPassword(user.getUserPassword(),"D"));
			jsonObject = JsonReaderWriter.getJSONObjectFromClassObject(user);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantsMsg.SUCCESS;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in getUserByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, fMsg );
	}
	
	
	@Transactional
	public List<JSONObject> getAllUsersByType(HttpServletRequest request,String type) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";	
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO.getAllUsersByType(lang,type,Long.parseLong(request.getParameter("ProjectKey")));
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getModuleList Method >>", e);
		}
		return ar;
	}
	

	@Transactional
	public Map<String, Object> saveUser(User user, HttpServletRequest request) {
		User u = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userSave.success";
		String fMsg=null;
	
		try {
			user.setCreatedDateTime(UserDateFormat
					.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			user.setCreatedUser(Long.parseLong(request.getSession()
					.getAttribute("currentUserKey").toString()));
			user.setUsrLockYN('N');
			//user.setSalesManYN(user.getSalesManYN()!=null?user.getSalesManYN():'N');
			if(userDAO.checkDuplicate("loginId", User.class, "add", user.getLoginId(),null))
			{
				throw new Exception("Login Id Duplication error");
			}
			
			/*if(userDAO.checkDuplicate("usrCode", User.class, "add", user.getUsrCode(),null))
			{
				throw new Exception("User Code Duplication error");
			}
			
			
			if(userDAO.checkDuplicate("usrAbbr", User.class, "add", user.getUsrAbbr(),null))
			{
				throw new Exception("User Abbrevation Duplication error");
			}*/
			
			
			if(user.getActiveYN().equals("A")){
				
				user.setUserPassword(user.getUserPassword().length()>0?userDAO.getEncryptPassword(user.getUserPassword()):null);
				}
			
			String[] loginID=user.getLoginId().split(",");
			for (int i = 0; i < loginID.length; i++) {
				if(loginID[i].length()>0)
				user.setLoginId(loginID[i]);
			}
			u = (User) userDAO.add(user);
			jsonObject = JsonReaderWriter.getJSONObjectFromClassObject(u);
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);
	}
	
	
	@Transactional
	public Map<String, Object> updateUser(User user, HttpServletRequest request) {
		
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userUpdate.success";
		String fMsg=null;
		
	
		try {
			User dbObject = (User) userDAO.getObjectByKey(user.getUserKey());
			String flag = request.getParameter("flag");
			boolean dataIntrCheck = UserDateFormat.getDISByDate(
					user.getUpdatedDateTime(),
					dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true
					|| (flag != null && flag.equalsIgnoreCase("Overwrite"))) {
				//user.setSalesManYN(user.getSalesManYN()!=null?user.getSalesManYN():'N');
				user.setUpdatedUser(Long.parseLong(request.getSession()
						.getAttribute("currentUserKey").toString()));
				user.setUpdatedDateTime(UserDateFormat
						.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
		if(user.getUserPassword().length()>0){
				if(user.getActiveYN().equals("A")){
				
					if(dbObject.getUserPassword()!=null)
					user.setUserPassword(dbObject.getUserPassword().equals(user.getUserPassword())?user.getUserPassword():userDAO.getEncryptPassword(user.getUserPassword()));
					else{
						user.setUserPassword(getEncryptPassword(user.getUserPassword()));
					}
				}
		}else{
			user.setUserPassword(null);
		}
				user = (User) userDAO.update(user);

			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.userUpdate.error";
				user = dbObject;
			}
			
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
			
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg =e.getMessage();
			logger.error("Transaction Failed in Update Method >>", e);
		}
		jsonObject = JsonReaderWriter
				.getJSONObjectFromClassObject(user);
		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);
	}

	@Transactional
	public List<JSONObject> getAllActiveUser(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getAllActiveUser(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}
	
	@Transactional
	public List<JSONObject> getAllActiveDeveloper(Long id,HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getAllActiveDeveloper(id,lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}

	@Transactional
	public Map<String, Object> getUserEmailByClick(long key) {

		User user = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			user = (User) userDAO.getUserEmailByClick(key);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(user);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getClientByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}
	
	@Transactional
	public Map<String, Object> getAssignedUserEmail(long key) {

		User user = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			user = (User) userDAO.getAssignedUserEmail(key);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(user);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getClientByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}
	
	
	@Transactional
	public Map<String, Object> updateMyProfile(User user, HttpServletRequest request) {
		
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userUpdate.success";
		String fMsg=null;
		
	
		try {
			User dbUser = (User) userDAO.getObjectByKey(user.getUserKey());
			dbUser.setNameS(user.getNameS());
			dbUser.setEmailId(user.getEmailId());
			dbUser.setContactNo(user.getContactNo());
			dbUser.setUpdatedDateTime(user.getUpdatedDateTime());
			dbUser.setUpdatedUser(user.getUpdatedUser());
			 userDAO.update(dbUser);
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
			
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg =e.getMessage();
			logger.error("Transaction Failed in Update Method >>", e);
		}
		jsonObject = JsonReaderWriter
				.getJSONObjectFromClassObject(user);
		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);
	}

	@Transactional
	public Map<String, Object> deleteUser(HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userDelete.success";
		User user = null;
		JSONObject jsonObject = null;
		String fMsg=null;
		try {
			String flag = request.getParameter("flag");
			String updatedDate = request.getParameter("updatedDate");
			Long userKey = Long.parseLong(request.getParameter("key"));
			User dbObject = (User) userDAO.getObjectByKey(userKey);
			Date dt = (updatedDate == null ? null : UserDateFormat
					.convertStringToDate(updatedDate,
							ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			boolean dataIntrCheck = UserDateFormat.getDISByDate(dt,
					dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true || flag.equalsIgnoreCase("Delete")) {
				user = (User) userDAO.delete(userKey);
				jsonObject = JsonReaderWriter
						.getJSONObjectFromClassObject(user);
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.deleteModified.success";
			}
			
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Delete Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);
	}

	@Transactional
	public Map<String, Object> getFirstUser(HttpServletRequest request) {
		User user = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.FIRST;
		try {
			user =  userDAO.getFirst(User.class);
			User dbObject = (User) userDAO.getObjectByKey(user
					.getUserKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getFirstUser Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getLastUser(HttpServletRequest request) {
		User user = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.LAST;
		try {
			user =  userDAO.getLast(User.class);
			User dbObject = (User) userDAO.getObjectByKey(user
					.getUserKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getLastUser Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getNextUser(HttpServletRequest request) {
		User user = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			user = userDAO.getNext(key,User.class);
			User dbObject = (User) userDAO.getObjectByKey(user
					.getUserKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_FORWARD;
			logger.error("Transaction Failed in getNextUser Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getPreviousUser(HttpServletRequest request) {
		User user = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			user =  userDAO.getPrev(key,User.class);
			User dbObject = (User) userDAO.getObjectByKey(user
					.getUserKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_BACKWARD;
			logger.error("Transaction Failed in getPreviousUser Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public String getUserPeference(long userKey, String prefKeyword) {
		return userDAO.getUserPeference(userKey, prefKeyword);
	}

	@Transactional
	public String getLastLogOnTime(long userKey) {
		try {
			return userDAO.getLastLogOnTime(userKey);
		} catch (Exception e) {

			
			e.printStackTrace();
			
			return "";
		}
		
	}

	@Transactional
	public void enterUserLog(UserLog userLog)throws Exception {
		
		userLog.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
		userLog.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
		userDAO.enterUserLog(userLog);
		
	}


	 
	@Transactional
	public Map<String, Object> saveUserPreference(UserPrefrence themePrefrence, HttpServletRequest request) {
		UserPrefrence userPrefrence = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userPrefAdd.success";
		String fMsg=null;
		fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
		
			 userPrefrence = userDAO.saveUserPreference(themePrefrence);
			 
			 List<UserPrefrence> list=new ArrayList<UserPrefrence>(); 
			 
			 list=userDAO.getUserPrefrenceByUserKey(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			 
			 for(UserPrefrence up:list){
				 
				 if(userPrefrence.getPreferenceKeyword().equalsIgnoreCase("THEME")){
					 
					 request.getSession().setAttribute("themeName",userPrefrence.getPreferenceValue());
				
				 }else if(userPrefrence.getPreferenceKeyword().equalsIgnoreCase("MODULE")){
					
					 request.getSession().setAttribute("userDefaultModule",userPrefrence.getPreferenceValue());
					 
				 }else if(userPrefrence.getPreferenceKeyword().equalsIgnoreCase("HOMEPAGE")){
					
					 request.getSession().setAttribute("userHomePageAttr",userPrefrence.getPreferenceValue());
					 
				 }
				 
			 }
			 
			 /*String pk=userPrefrence.getPreferenceKeyword();
				if(pk.equalsIgnoreCase(""))
				{
					request.getSession().setAttribute("themeName",userPrefrence.getPreferenceValue());
				}
				if(pk.equalsIgnoreCase(""))
				{
					request.getSession().setAttribute("themeName",userPrefrence.getPreferenceValue());
				}
				if(pk.equalsIgnoreCase("HOMEPAGE"))
				{
					request.getSession().setAttribute("userHomePageAttr",userPrefrence.getPreferenceValue());
				}*/
			 
				
				
			 jsonObject = JsonReaderWriter.getJSONObjectFromClassObject(userPrefrence);
				
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);
	}
	
	
	
	
	
	
	

	@Transactional
	public List<User> getUsers(String loginId, String password) throws Exception{
		List<User> users=null;
		users= userDAO.getUsers(loginId, password);
		return users;
	}
	
	
	
	
	
	
	

	
	
	@Transactional
	public Map<String, Object> validateDuplication(HttpServletRequest request,String fieldName,String cls,String fieldValue) {
		Boolean result = null;
		JSONObject jsonObject = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.fetch.success";
		try {
			
			result=userDAO.checkDuplicate(fieldName, cls,fieldValue);
				if(result)
				{
					 msg = ConstantsMsg.SUCCESS;
					
				}else{
					
					msg = ConstantsMsg.FAILURE;
					
				}
			 
			
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = "messages.fetch.error";
			logger.error("Transaction Failed in Save Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,
				messageSource.getMessage(lMsg, null, request.getLocale()));
	}
	
	
	@Transactional
	public String checkUserPassword(HttpServletRequest request){
		String result="";
	try {
		Long userKey=Long.parseLong(request.getParameter("userKey").toString());
		String password=request.getParameter("password").toString();
		String newPassword=request.getParameter("newPassword").toString();
		String flag=request.getParameter("flag").toString();
		
		String remarks=request.getParameter("remarks")!=null?request.getParameter("remarks").toString():"";
		
		User user = (User) userDAO.getObjectByKey(userKey);
		
		if(flag.equalsIgnoreCase("UNLOCK")){
		
			user.setUsrLockYN('N');
			if(password.equalsIgnoreCase("null")){
				
				user.setUserPassword(getEncryptPassword(newPassword));
			
	    		
			}
			
			User user2=(User) userDAO.update(user);
			
			
			if(user2!=null)
    		{
				UserLog log = new UserLog();
    			log.setActivityFlag("UNLOCK");
    			log.setUser(Long.parseLong(user.getUserKey() + ""));
    			log.setCreatedUser(Long.parseLong(request.getSession()
						.getAttribute("currentUserKey").toString()));
    			log.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    			log.setRemark(remarks);
    			enterUserLog(log);
    			
    			if(password.equalsIgnoreCase("null"))
    			{
    				UserLog log1 = new UserLog();
    				log1.setActivityFlag("PW_RESET");
    				log1.setUser(Long.parseLong(user.getUserKey() + ""));
        			log1.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    				log1.setCreatedUser(Long.parseLong(request.getSession()
    						.getAttribute("currentUserKey").toString()));
    				log1.setRemark(remarks);
        			enterUserLog(log1);
    			}
    			result="UNLOCK";
    		}else{
    			result="UNLOCK_FAILURE";
    		}
		}else{
		
		if(password.equalsIgnoreCase("null")){
			
			user.setUserPassword(getEncryptPassword(newPassword));
		

			
    		if(userDAO.update(user)!=null)
    		{
    			//------------Save user log--------
    			UserLog log = new UserLog();
    			log.setActivityFlag("PW_RESET");
    			log.setUser(Long.parseLong(user.getUserKey() + ""));
    			log.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    			log.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    			log.setCreatedUser(Long.parseLong(request.getSession()
						.getAttribute("currentUserKey").toString()));
    			log.setRemark(remarks);
    			enterUserLog(log);

    			//-------------------------------
    			
    			result="PASSWORD_CHANGED";
    		}else{
    			result="PASSWORD_CHANGE_FAILURE";
    		}
    		
		}else{
			String oldPassword=getEncryptPassword(password);    	
		if(oldPassword.equals(user.getUserPassword())){
    		user.setUserPassword(getEncryptPassword(newPassword));
    		if(userDAO.update(user)!=null)
    		{
    			UserLog log1 = new UserLog();
    			log1.setActivityFlag("PW_RESET");
    			log1.setUser(Long.parseLong(user.getUserKey() + ""));
    			log1.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    			log1.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
    			log1.setCreatedUser(Long.parseLong(request.getSession()
						.getAttribute("currentUserKey").toString()));
    			log1.setRemark(remarks);
    			enterUserLog(log1);
    			
    			if(flag.equalsIgnoreCase("FLC")){
    				
    				UserLog log = new UserLog();
        			log.setActivityFlag("LI");
        			log.setLogDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
        			log.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
        			log.setUser(Long.parseLong(user.getUserKey() + ""));
        			log.setCreatedUser(Long.parseLong(request.getSession()
    						.getAttribute("currentUserKey").toString()));
        			log.setRemark(remarks);
        			enterUserLog(log);
    				
    			}
    			
    			result="PASSWORD_CHANGED";
    		}else{
    			result="PASSWORD_CHANGE_FAILURE";
    		}
    		
    	}else{
    		result="PASSWORD_MISMATCH";
    	}
		}
		}
	} catch (Exception e) {
		result="FAILURE";
		// TODO Auto-generated catch block
		
	}
		
		
	return result;
	}
	
	
	//////
	
	@Transactional
	public String checkUserResetPassword(HttpServletRequest request){
		String result="";
	try {
		String loginId=request.getParameter("loginId").toString();
	
		String confirmPassword=request.getParameter("confirmPassword").toString();
		//String confirmPassword=request.getParameter("confirmPassword").toString();
		User user = (User) userDAO.getObjectByName(loginId);
		
		
			//if(confirmPassword.equalsIgnoreCase("null")){
				
				user.setUserPassword(getEncryptPassword(confirmPassword));
			
				if(userDAO.update(user)!=null)
	    		{
	    		
	    			result="PASSWORD_CHANGED";
	    		}else{
	    			result="PASSWORD_CHANGE_FAILURE";
	    		}
			//}
			
	} catch (Exception e) {
		result="FAILURE";
		// TODO Auto-generated catch block
		
	}
		
		
	return result;
	}
	
	
	
	///////
	@Transactional
	public List<JSONObject> getUserLogDetailByActivityFlag(HttpServletRequest request) {
		
		List<JSONObject> ar = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
					Long userKey = Long.parseLong(request.getParameter("userkey"));
					String activityFlag = request.getParameter("activityFlag");
					
			List<UserLog> list = userDAO.getUserLogDetailByActivityFlag(lang, userKey,activityFlag);
			for(UserLog userLog:list){
				jsonObject = JsonReaderWriter
						.getJSONObjectFromClassObject(userLog);
				ar.add(jsonObject);
			}
				
		} catch (Exception e) {
			logger.error("Transaction Failed in getResetPasswordLogDetails Method >>", e);
		}
		return ar;
		
	}
	
	
	
	
	
	
	
	
	public String getEncryptPassword(String password){
		String encryptedHashCode = null;
		try {
			MessageDigest msgDig = MessageDigest.getInstance("MD5");
			msgDig.update(password.getBytes(), 0, password.length());
			encryptedHashCode = new BigInteger(1, msgDig.digest()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedHashCode;
	}
	
	
	
	@Transactional
	public List<UserLog> getUserLoginLogListbyFlag(Long userKey,String falg,long loginAttempts) {
		
		List<UserLog> userLogs=null;
		try {
			
			userLogs =  userDAO.getUserLoginLogListbyFlag(userKey,falg,loginAttempts);
			
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Transaction Failed in getGridDetails Method >>", e);
		}
		return userLogs;
		
	}
	
	@Transactional
	public void updateUserOnLoginFal(User user) {
		try {
			userDAO.update(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Transactional
	public Boolean booleanPwdExpiredDet(Long userKey,Long pwdExpDays) {
		try {
			 List<UserLog> userLogs=userDAO.getUserLoginLogListbyFlag(userKey, "LAST_REC", 1);
			 
			 Date oldDate=userLogs.get(0).getLogDateTime();
			 Date currentDateTime = new Date();
		    
			 long daybtn=(long)DateUtil.numOfDaysToExpire(currentDateTime, oldDate);
			 
			 long expiredResult=pwdExpDays-daybtn;
			 
			 
			 if(expiredResult>0){
				 return false;
			 }else{
				 
				 return true;
			 }
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getUserListForResetPwd(HttpServletRequest request) {
		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<CustomCombo> list = (ArrayList<CustomCombo>) userDAO
					.getUserListForResetPwd(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}

	
}
