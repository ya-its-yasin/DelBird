package aurora.common.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aurora.common.dao.SystemSetupDAO;
import aurora.common.model.SystemLanguage;
import aurora.common.model.SystemSetup;

@Service
public class SystemSetupService {

	static Logger logger = Logger.getLogger(SystemSetupService.class);

	@Autowired
	private SystemSetupDAO systemSetupDAO;
	
	@Transactional(readOnly=true)
	public SystemSetup getSystemSetup()
	{
		return systemSetupDAO.getSystemSetup();
	}
	
	@Transactional(readOnly=true)
	public SystemLanguage getSystemLanguageByKey(Long langKey)
	{
		return systemSetupDAO.getSystemLanguageByKey(langKey);
	}
}
