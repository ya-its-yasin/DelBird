package aurora.master.service;

import java.text.SimpleDateFormat;
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


import aurora.common.model.CustomCombo;
import aurora.master.model.UserLogInfo;
import aurora.master.service.UserLogService;
import aurora.master.dao.UserLogDAO;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class UserLogService {
	
	static Logger logger = Logger.getLogger(UserLogService.class);

	@Autowired
	private UserLogDAO userLogDAO;
	
	@Autowired
	MessageSource messageSource;
	
	
	@Transactional
	public Map<String, Object> saveUserLog(UserLogInfo userLogInfo,
			HttpServletRequest request) {
		UserLogInfo s= null;
		String userName = "" , action = "";
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countrySave.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			userName = (String) request.getParameter("userName");
			action = (String) request.getParameter("action");
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss a").format(new Date());
			userLogInfo.setUlDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			userLogInfo.setUserName(userName);
			userLogInfo.setAction(action);
			s= (UserLogInfo) userLogDAO.add(userLogInfo);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(s);
		} 
		catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}
	
	@Transactional
	public Map<String, Object> saveUserLogWithUserKey(UserLogInfo userLogInfo,
			HttpServletRequest request) {
		UserLogInfo s= null;
		String userName = "" , action = "";
		Long userKey=null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countrySave.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			userName = (String) request.getParameter("userName");
			userKey = Long.parseLong(request.getParameter("userKey").toString());
			action = (String) request.getParameter("action");
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss a").format(new Date());
			userLogInfo.setUlDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			userLogInfo.setUserName(userName);
			userLogInfo.setUserKey(userKey);
			userLogInfo.setAction(action);
			s= (UserLogInfo) userLogDAO.add(userLogInfo);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(s);
		} 
		catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}
	@Transactional
	public Map<String, Object> saveUserLogoutWithUserKey(UserLogInfo userLogInfo,
			HttpServletRequest request) {
		UserLogInfo s= null;
		String userName = "" , action = "";
		Long userKey=null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countrySave.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			userName = (String) request.getParameter("userName");
			userKey = Long.parseLong(request.getParameter("userKey").toString());
			action = (String) request.getParameter("action");
			String timeStamp = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss a").format(new Date());
			userLogInfo.setUlDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			userLogInfo.setUserName(userName);
			userLogInfo.setUserKey(userKey);
			userLogInfo.setAction(action);
			s= (UserLogInfo) userLogDAO.add(userLogInfo);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(s);
		} 
		catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}
	@Transactional
	public Map<String, Object> getClientLoginInfo(long userid) {

		UserLogInfo userLogInfo = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			userLogInfo = (UserLogInfo) userLogDAO.getClientLoginInfo(userid);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(userLogInfo);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getClientByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}
	@Transactional
	public Map<String, Object> getClientLogoutInfo(long userid) {

		UserLogInfo userLogInfo = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			userLogInfo = (UserLogInfo) userLogDAO.getClientLogoutInfo(userid);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(userLogInfo);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getClientByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}

	@Transactional
	public Map<String, Object> getClientCurrentLoginInfo(long userid) {

		UserLogInfo userLogInfo = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			userLogInfo = (UserLogInfo) userLogDAO.getClientCurrentLoginInfo(userid);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(userLogInfo);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getClientByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}

	
	

}
