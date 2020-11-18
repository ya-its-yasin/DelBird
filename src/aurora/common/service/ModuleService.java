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

import aurora.common.dao.ModuleDAO;
import aurora.common.model.CustomCombo;
import aurora.common.model.Module;
import aurora.util.ConstantsMsg;
import aurora.util.DBConstants.Align;
import aurora.util.DBConstants.ColumnType;
import aurora.util.JsonReaderWriter;
import aurora.util.UserDateFormat;

@Service
public class ModuleService {

	static Logger logger = Logger.getLogger(ModuleService.class);

	@Autowired
	private ModuleDAO moduleDAO;
	@Autowired
	UserMenuLinkService userMenuLinkService;
	@Autowired
	MessageSource messageSource;

	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JSONObject> getModuleList(HttpServletRequest request) {

		List<JSONObject> ar = new ArrayList<JSONObject>();
		try {
			String lang = request.getSession().getAttribute("lang") != null ? request
					.getSession().getAttribute("lang").toString()
					: "en";
			List<Module> list = (ArrayList<Module>) moduleDAO.getAll(lang);
			for(Module module:list){
				ar.add(JsonReaderWriter.getJSONObjectFromClassObject(module));
			}
		} catch (Exception e) {
			logger.error("Transaction Failed in getModuleList Method >>", e);
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
			List<CustomCombo> list = (ArrayList<CustomCombo>) moduleDAO
					.getList(lang);
			ar = JsonReaderWriter.getJSONList(list);
		} catch (Exception e) {
			logger.error("Transaction Failed in getListForCombo Method >>", e);
		}
		return ar;
	}

	@Transactional
	public Map<String, Object> getModuleByKey(HttpServletRequest request) {

		Module module = null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = null;
		JSONObject jsonObject=null;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			module = (Module) moduleDAO.getObjectByKey(key);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(module);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			lMsg = e.getMessage();
			logger.error("Transaction Failed in getModuleByKey Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg,lMsg);
	}

	@Transactional
	public Map<String, Object> saveModule(Module module,
			HttpServletRequest request) {
		Module m = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.moduleSave.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			if(moduleDAO.checkDuplicate("keyword", Module.class, "add", module.getKeyword(),null))
			{
				//duplicateFlag = true;
				throw new Exception("Duplication error");
			}
			module.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			module.setCreatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			m = (Module) moduleDAO.add(module);
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(m);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Save Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}

	@Transactional
	public Map<String, Object> updateModule(Module module,
			HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.moduleUpdate.success";
		JSONObject jsonObject=null;
		String fMsg=null;
		try {
			Module dbObject = (Module) moduleDAO.getObjectByKey(module
					.getModuleKey());
			String flag = request.getParameter("flag");
			boolean dataIntrCheck = UserDateFormat.getDISByDate(module.getUpdatedDateTime(), dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true
					|| (flag != null && flag.equalsIgnoreCase("Overwrite"))) {
				module.setUpdatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
				module.setUpdatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
				module = (Module) moduleDAO.update(module);
			} else {
				msg = ConstantsMsg.MODIFIED;
				lMsg = "messages.moduleUpdate.error";
				module = dbObject;
			}
			fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(module);
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
	public Map<String, Object> deleteModule(HttpServletRequest request) {
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.moduleDelete.success";
		Module module = null;
		JSONObject jsonObject=null; 
		String fMsg=null;
		try {
			String flag = request.getParameter("flag");
			String updatedDate = request.getParameter("updatedDate");
			Long moduleKey = Long.parseLong(request.getParameter("key"));
			Module dbObject = (Module) moduleDAO.getObjectByKey(moduleKey);
			Date dt =(updatedDate==null?null:UserDateFormat.convertStringToDate(updatedDate, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			boolean dataIntrCheck = UserDateFormat.getDISByDate(dt, dbObject.getUpdatedDateTime());
			if (dataIntrCheck == true || flag.equalsIgnoreCase("Delete")) {
				module = (Module) moduleDAO.delete(moduleKey);
				jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(module);
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

		return JsonReaderWriter.mapOK(jsonObject, msg,
				fMsg);

	}

	@Transactional
	public Map<String, Object> getFirstModule(HttpServletRequest request) {
		Module module = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.FIRST;
		try {
			module = (Module) moduleDAO.getFirst(Module.class);
			Module dbObject = (Module) moduleDAO.getObjectByKey(module
					.getModuleKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getFirstModule Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getLastModule(HttpServletRequest request) {
		Module module = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.LAST;
		try {
			module =  moduleDAO.getLast(Module.class);
			Module dbObject = (Module) moduleDAO.getObjectByKey(module
					.getModuleKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			logger.error("Transaction Failed in getLastModule Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getNextModule(HttpServletRequest request) {
		Module module = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			module =  moduleDAO.getNext(key,Module.class);
			Module dbObject = (Module) moduleDAO.getObjectByKey(module
					.getModuleKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_FORWARD;
			logger.error("Transaction Failed in getNextModule Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

	@Transactional
	public Map<String, Object> getPreviousModule(HttpServletRequest request) {
		Module module = null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		try {
			Long key = Long.parseLong(request.getParameter("key"));
			module =  moduleDAO.getPrev(key,Module.class);
			Module dbObject = (Module) moduleDAO.getObjectByKey(module
					.getModuleKey());
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(dbObject);
		} catch (Exception e) {
			msg = ConstantsMsg.CANNOT_MOVE_BACKWARD;
			logger.error("Transaction Failed in getPreviousModule Method >>", e);
		}
		return JsonReaderWriter.mapOK(jsonObject, msg, "");
	}

}
