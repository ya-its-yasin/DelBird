package aurora.master.service;

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

import aurora.master.model.Country;
import aurora.master.service.CountryService;
import aurora.master.dao.CountryDAO;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class CountryService {
	
	static Logger logger = Logger.getLogger(CountryService.class);

	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	MessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getCountryList(HttpServletRequest request) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<Country> list = (ArrayList<Country>) countryDAO.getAll(lang);
			for(Country country:list){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(country));
			}
		} catch (Exception e) {
			logger.error("Transaction Failed in getModuleList Method >>", e);
		}
		return ar;
	}

	@Transactional
	public Map<String, Object> getCountryByKey(long key) {

		Country country = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			//Long key = Long.parseLong(request.getParameter("key"));
			country = (Country) countryDAO.getObjectByKey(key);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(country);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getStudentByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}
	
	@Transactional
	public List<JSONObject> getAllCountries(HttpServletRequest request) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
					
			List<CustomCombo> list = (ArrayList<CustomCombo>) countryDAO.getAllCountries(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getModuleList Method >>", e);
		}
		return ar;
	}
	
	@Transactional
	public Map<String, Object> saveCountry(Country country,
			HttpServletRequest request) {
		Country s= null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countrySave.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			
			country.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			country.setCreatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			s= (Country) countryDAO.add(country);
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
	public Map<String, Object> updateCountry(Country country,
			HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countryUpdate.success";
		JSONObject jsonObject=null;
		String fMsg=null;
		try {
			Country dbObject = (Country) countryDAO.getObjectByKey(country
					.getCntKey());
			String flag = request.getParameter("flag");
			boolean dataIntrCheck = UserDateFormat.getDISByDate(country.getUpdatedDateTime(), dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true
					|| (flag != null && flag.equalsIgnoreCase("Overwrite"))) {
				country.setUpdatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				country.setUpdatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
				country = (Country) countryDAO.update(country);
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.countryUpdate.error";
				country = dbObject;
			}
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(country);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Update Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,
				fMsg);
	}
	
	@Transactional
	public Map<String, Object> deleteCountry(HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.countryDelete.success";
		Country country = null;
		JSONObject jsonObject=null; 
		String fMsg=null;
		try {
			String flag = request.getParameter("flag");
			String updatedDate = request.getParameter("updatedDate");
			Long cntKey = Long.parseLong(request.getParameter("key"));
			Country dbObject = (Country) countryDAO.getObjectByKey(cntKey);
			Date dt =(updatedDate==null?null:UserDateFormat.convertStringToDate(updatedDate, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			boolean dataIntrCheck = UserDateFormat.getDISByDate(dt, dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true || flag.equalsIgnoreCase("Delete")) {
				country = (Country) countryDAO.delete(cntKey);
				jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(country);
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.countryDelete.error";
			}
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Delete Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,
				fMsg);

	}

}
