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


import aurora.common.model.CustomCombo;
import aurora.common.model.DelUser;
import aurora.common.model.User;
import aurora.master.model.Country;
import aurora.master.service.CountryService;
import aurora.common.dao.DelUserDAO;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;


@Service
public class DelUserService {
	
	
	static Logger logger = Logger.getLogger(DelUserService.class);

	@Autowired
	private static DelUserDAO delUserDAO;
	
	@Autowired
	MessageSource messageSource;
	
	@Transactional
	public Map<String, Object> addDelUser(DelUser delUser,
			HttpServletRequest request) {
		DelUser s= null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userAdd.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			
			/*
			 * country.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			country.setCreatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			s= (Country) countryDAO.add(country);
			*/
			
			s= (DelUser) delUserDAO.add(delUser);		
			
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(s);
		} 
		catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Add Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}
	
	@Transactional
	public static List<DelUser> getDelUserServ(String loginId, String password) throws Exception{
		List<DelUser> DelUsers=null;
		DelUsers= delUserDAO.getDelUsers(loginId, password);
		return DelUsers;
	}
	
	
}


