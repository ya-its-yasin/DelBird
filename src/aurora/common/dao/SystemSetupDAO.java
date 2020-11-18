package aurora.common.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.SystemLanguage;
import aurora.common.model.SystemSetup;


@Repository("systemSetupDAO")
public class SystemSetupDAO {
	
	@Autowired @Qualifier("hibernateTemplate")
	HibernateTemplate hibernateTemplate;

	public SystemSetup getSystemSetup()
	{
		
		
		
		String ssQuery = "select ss.ssKey as ssKey,ss.ssBilingualYN as ssBilingualYN,ss.ssLangP as ssLangP,ss.ssLangS as ssLangS,"
				
				+ " (select sl from SystemLanguage sl where slKey=ss.ssLangP) as slLangP , "
				+ " (select sl from SystemLanguage sl where slKey=ss.ssLangS) as slLangS   "
				+ " from SystemSetup ss";
		
	//	String hqlQuery = "from SystemSetup";
		List<SystemSetup> systemSetupList = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(ssQuery)
				.setResultTransformer(Transformers.aliasToBean(SystemSetup.class))
				.list();
		if(systemSetupList.size()>0)
		{
			return systemSetupList.get(0);
		}
		return null;
	}
	
	public SystemLanguage getSystemLanguageByKey(Long langKey)
	{
		String hqlQuery = "from SystemLanguage where slKey = "+langKey+"";
		List<SystemLanguage> systemLanguageList =(List<SystemLanguage>) hibernateTemplate.find(hqlQuery);
		if(systemLanguageList.size()>0)
		{
			return systemLanguageList.get(0);
		}
		return null;
	}
}
