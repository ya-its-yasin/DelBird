package aurora.common.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.CustomCombo;
import aurora.common.model.User;
import aurora.common.model.UserLog;
import aurora.common.model.UserPrefrence;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;

import aurora.util.ConstantsMsg;
import aurora.util.DateUtil;
import aurora.util.UserDateFormat;

@SuppressWarnings("unchecked")
@Repository("userDAO")
public class UserDao extends ANavigationDao implements IMasterDao {
	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	

	@Override
	public Object add(Object user) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(user);
		return user;
	}
	
	
	@Override
	public Object update(Object user) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.update(user);
		return user;
	}

	@Override
	public Object delete(long key) throws Exception {
		User user =null;
		try
		{
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("userKey", key));
		List<User> gr =(List<User>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			user = gr.get(0);
			hibernateTemplate.delete(user);
			
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}

	public List<?> getAllUsersByType(String lang,String type,Long projectkey) {
		String userQuery = "";
		if (type.equalsIgnoreCase("A")) {
			userQuery = "select u.userKey as key, u.nameP as value from User u where u.userKey NOT IN(select p.userKey as userKey from ProjectUsers p where p.projectKey="+projectkey+") and u.userType='"+type+"'";

		} 
		if (type.equalsIgnoreCase("C")) {
			userQuery = "select u.userKey as key, u.nameP as value from User u,Project m where u.userKey NOT IN(select p.userKey as userKey from ProjectUsers p where p.projectKey="+projectkey+") and u.refID=m.prjClientKey and u.userType='"+type+"' and m.prjKey="+projectkey;

		} 
		if (type.equalsIgnoreCase("T")) {
			userQuery = "select u.userKey as key, u.nameP as value from User u,Project m where u.userKey NOT IN(select p.userKey as userKey from ProjectUsers p where  p.projectKey="+projectkey+") and u.refID=m.prjContractorKey and u.userType='"+type+"' and m.prjKey="+projectkey;

		}
		
		List<CustomCombo> list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();

		return list;

	}
	@Override
	public Object getObjectByKey(long key) throws Exception {
		String query = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.userPassword as userPassword,u.createdUser as createdUser,u.updatedUser as updatedUser,"
				+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.usrLockYN as usrLockYN,"
				+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
				+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
				+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
				
				+ " u.createdDateTime as createdDateTime, "

				+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
				+ "u.updatedDateTime as updatedDateTime from User u where u.userKey="+key;

		List<User> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();

		return list.get(0);
	}

	public List<?> getAllActiveUser(String lang) {
		String userQuery = "";
		if (lang.equalsIgnoreCase("en")) {
			userQuery = "select u.userKey as key, u.nameP as value from User u where u.activeYN = 'A'";

		} else {
			userQuery = "select u.userKey as key, u.nameP as value from User u where u.activeYN = 'A'";

		}
		List<CustomCombo> list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();

		return list;

	}
	public List<?> getAllActiveDeveloper(Long id,String lang) {
		String userQuery = "";
		if (lang.equalsIgnoreCase("en")) {
			userQuery = "select u.userKey as key, u.nameP as value from ProjectUsers p,User u where p.userKey=u.userKey and u.activeYN = 'A' and u.userType='A' and p.projectKey="+id;

		} else {
			userQuery = "select u.userKey as key, u.nameP as value from ProjectUsers p,User u where p.userKey=u.userKey and u.activeYN = 'A' and u.userType='A' and p.projectKey="+id;

		}
		List<CustomCombo> list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();

		return list;

	}
    public Object getUserEmailByClick(long key) throws Exception {
		
		Object object=null;
		String userQuery =
			"select u.emailId as emailId from User u where  u.userKey="+key;

		List<User> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();
		if(list.size()>0)
			object= list.get(0);
		return object;
	}
    
    public Object getAssignedUserEmail(long key) throws Exception {
		
		Object object=null;
		String userQuery =
			"select u.emailId as emailId from User u where  u.userKey="+key;

		List<User> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();
		if(list.size()>0)
			object= list.get(0);
		return object;
	}
	public Object getObjectByName(String loginId) throws Exception {
		String query = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.userPassword as userPassword,u.createdUser as createdUser,u.updatedUser as updatedUser,"
				+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.usrLockYN as usrLockYN,"
				+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
				+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
				+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
				
				+ " u.createdDateTime as createdDateTime, "

				+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
				+ "u.updatedDateTime as updatedDateTime from User u where u.loginId='"+loginId+"'";

		List<User> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();

		return list.get(0);
	}
	@Override
	public List<?> getAll(String lang) throws Exception {
		List<User> list;
		String userQuery = "";
		
		list =(List<User>)hibernateTemplate.find(" from User");
		/*String query = "";

		if (lang.equalsIgnoreCase("EN")) {

			query = "select * from Site a";

		} else {

			query = "select a.siteKey as siteKey, a.siteNameP as siteNameP, a.siteNameS as siteNameS, a.siteDescP as siteDescP, a.siteDescS as siteDescS, "
					+ "(select nameP from User where userKey=a.createdUser) as createdUserName, a.createdUser as createdUser, "
					+ "a.createdDateTime as createdDateTime, (select nameP from User where userKey=a.updatedUser) as updatedUserName, a.updatedUser as updatedUser, "
					+ "a.updatedDateTime as updatedDateTime, a.activeYN as activeYN,a.effStartDate as effStartDate,a.effEndDate as effEndDate from Site a";

		}

		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(Site.class)).list();*/
		return list;
		/*if (lang.equalsIgnoreCase("EN")) {

			userQuery = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.usrLockYN as usrLockYN,"
					+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.divisionKey as divisionKey,"
					+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
					+ " (select nameP from Entities where entityKey=u.entityKey) as entityName , "
					
					+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.usrCompanyKey as usrCompanyKey,"
					+ " u.usrAbbr as usrAbbr, "
					+ " u.usrCode as usrCode, "
					+ " u.salesManYN as salesManYN, "
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ "u.updatedDateTime as updatedDateTime from User u";
		} else {

			userQuery = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.usrLockYN as usrLockYN,"
					+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.divisionKey as divisionKey ,"
					+ " (select nameS from UserGroup where userGroupKey=u.userGroup) as groupName , "
					+ " (select nameS from Entities where entityKey=u.entityKey) as entityName , "
					
					+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,u.usrCompanyKey as usrCompanyKey,"
					
					+ " (select nameS from User where userKey=u.createdUser) as createdUserName,"
					+ " u.usrAbbr as usrAbbr, "
					+ " u.usrCode as usrCode, "
					+ " u.salesManYN as salesManYN, "
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameS from User where userKey=u.updatedUser) as updatedUserName,"
					+ "u.updatedDateTime as updatedDateTime from User u";
		}

		list = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();
		return list;*/
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		List<CustomCombo> list;
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			
		
			
			query = "select u.userKey as key, u.nameP as value from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) and u.usrLockYN!='Y' order by u.nameP asc ";
		} else {
			query = "select u.userKey as key, u.nameS as value  from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) and u.usrLockYN!='Y' order by u.nameS asc";
		}
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}
	
	public List<?> getLockedUserList(String lang) throws Exception {
		List<CustomCombo> list;
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			
		
			
			query = "select u.userKey as key, u.nameP as value  from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) and u.usrLockYN='Y' order by u.nameP asc";
		} else {
			query = "select u.userKey as key, u.nameS as value from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) and u.usrLockYN='Y' order by u.nameS asc" ;
		}
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}
	
	public List<?> getListByUserGroup(String lang,Long userGroupKey) throws Exception {
		List<CustomCombo> list;
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			query = "select m.userKey as key, m.nameP as value from User m where m.activeYN = 'A' and userGroup = "+userGroupKey+"";
		} else {
			query = "select m.userKey as key, m.nameS as value from User m where m.activeYN = 'A' and userGroup = "+userGroupKey+"";
		}
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}
	
	public List<User> getUserByUserGroup(long ugkey) throws Exception {
		User user;
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("userGroup", ugkey));
		List<User> gr =(List<User>) hibernateTemplate.findByCriteria(criteria);
		
		return gr;
	}
	public String getUserPeference(long userKey, String prefKeyword) {
		String returnValue = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(UserPrefrence.class);
		criteria.add(Restrictions.eq("userKey", userKey));
		criteria.add(Restrictions.eq("preferenceKeyword", prefKeyword));
		List<UserPrefrence> theme =(List<UserPrefrence>) hibernateTemplate
				.findByCriteria(criteria);
		if (theme.size() > 0) {
			returnValue = theme.get(0).getPreferenceValue();
		} else if(prefKeyword.equals("THEME")){
			returnValue = "default";
		} else if(prefKeyword.equals("HOMEPAGE")){
			returnValue = "application";
		} else {
			returnValue = null;
		}
		return returnValue;
	}

	public String getLastLogOnTime(long userKey) throws Exception {
		String hql = "select ul from UserLog ul where ul.userLogKey =(select max(userLogKey) from UserLog where user="+userKey+" and activityFlag='LI')";
		List<UserLog> logs = (List<UserLog>)hibernateTemplate.find(hql);
		if (logs.size() > 0) {
			Date logdate=logs.get(logs.size() - 1).getLogDateTime();
			
			return UserDateFormat.convertDateToString(logdate, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A);
		}
		return DateUtil.presentDateTime();
	}

	public void enterUserLog(UserLog userLog) {
		//userLog.setCreatedDateTime(();
		
		
		hibernateTemplate.save(userLog);
	}

	public UserPrefrence saveUserPreference(UserPrefrence themePrefrence) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(UserPrefrence.class);
		criteria.add(Restrictions.eq("userKey", themePrefrence.getUserKey()));
		criteria.add(Restrictions.eq("preferenceKeyword", themePrefrence.getPreferenceKeyword()));
		List<UserPrefrence> theme = (List<UserPrefrence>)hibernateTemplate
				.findByCriteria(criteria);
		hibernateTemplate.clear();
		if (theme.size() > 0) {
			themePrefrence.setUserThemePrefrenceKey(theme.get(0)
					.getUserThemePrefrenceKey());
			hibernateTemplate.saveOrUpdate(themePrefrence);
		} else {
			hibernateTemplate.save(themePrefrence);
		}
		hibernateTemplate.flush();
		
		return themePrefrence;
	}
	
	
	public List<UserPrefrence> getUserPrefrenceByUserKey(Long userKey) {
		
		List<UserPrefrence> list=null;
		list=(List<UserPrefrence>)hibernateTemplate.find(" from UserPrefrence  where userKey="+userKey);
		return list;
	}


	


	
	
	public List<User> getUsers(String loginId, String password) throws Exception{
		List<User> list =null;
	
		String encPassword=getEncryptPassword(password);
		String userQuery = "";

		

			userQuery = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId," 
					+   " u.userPassword as userPassword,u.createdUser as createdUser,u.updatedUser as updatedUser," 
					+   " u.createdDateTime as createdDateTime,u.updatedDateTime as updatedDateTime,u.usrLockYN as usrLockYN,"
					+ 	" u.emailId as emailId,u.contactNo as contactNo,u.userGroup as userGroup," 
					+   " u.activeYN as activeYN,u.userType as userType,u.refID as refID," 
					+	" u.effStartDate as effStartDate,u.effEndDate as effEndDate"
					
					//+ 	" (select preferenceValue from UserPrefrence where userKey=u.userKey and preferenceKeyword='THEME') as userThemeName , "
					//+ 	" (select preferenceValue from UserPrefrence where userKey=u.userKey and preferenceKeyword='HOMEPAGE') as userHomePageAttr , "
					
					//+ 	" (select preferenceValue from UserPrefrence where userKey=u.userKey and preferenceKeyword='MODULE') as userDefaultModule "
					//,u.salesManYN as salesManYN "
					
			/*		+ " (select p from CompanyPasswordPolicyLink cpl, PasswordPolicy p where cpl.compKey=u.usrCompanyKey and sysdate between cpl.effStartDate and cpl.effEndDate and cpl.passwordPolicyKey=p.passPolicyKey) as passwordPolicy  "*/
					+   " from User u where UPPER(RTRIM(u.loginId))='"+loginId.toUpperCase()+"' and u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate IS NULL)";
		
			
		list = hibernateTemplate.getSessionFactory().getCurrentSession()
				
				.createQuery(userQuery)
				
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();
		
		return list;
		
	}
	
	public String getEncryptPassword(String password) {
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
	
	
	
/*	public String encryptDecryptPassword(String password,String flag) {
		 EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		 StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		
		 config.setPasswordEnvName("TEMP");
		  config.setAlgorithm("PBEWithMD5AndDES");
		  encryptor.setConfig(config);
		  if(flag.equalsIgnoreCase("E")){
			  return encryptor.encrypt(password);
		  }else {
			return encryptor.decrypt(password);
		}
		  
		
	}*/
	
	
	

	public List<UserLog> getUserLogDetailByActivityFlag(
			String lang, Long userKey,String activityFlag) {
/*		DetachedCriteria criteria=DetachedCriteria.forClass(UserLog.class);
		criteria.add(Restrictions.eq("user", userKey));
		criteria.add(Restrictions.eq("activityFlag", "PW_RESET"));
		
		List<UserLog> userLogs=hibernateTemplate.findByCriteria(criteria);
		
		return userLogs;*/
		
		String userQuery="";
		if(lang.equalsIgnoreCase("en"))
		{
		 userQuery="select distinct (u.userLogKey) as userLogKey,u.user as user,u.activityFlag as activityFlag," +
		 		" u.logDateTime as logDateTime,u.createdDateTime as createdDateTime,u.remark as remark,"+
				" (select nameP from User where userKey=u.user) as userName," +
				"(select nameP from User where userKey=u.createdUser) as changedUserName" +
				" from UserLog u where activityFlag='"+activityFlag+"' and u.user="+userKey+"";
		}else{
			 userQuery="select distinct (u.userLogKey) as userLogKey,u.user as user,u.activityFlag as activityFlag," +
				 		" u.logDateTime as logDateTime,u.createdDateTime as createdDateTime,u.remark as remark,"+
						" (select nameS from User where userKey=u.user) as userName," +
						"(select nameS from User where userKey=u.createdUser) as changedUserName" +
						" from UserLog u where activityFlag='"+activityFlag+"' and u.user="+userKey+"";
		}
				
		
		List<UserLog> list= hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(userQuery).setResultTransformer(Transformers.aliasToBean(UserLog.class)).list();
		
		return list;
		
	}
	
	
	
	
	
    public String getUserEmailId(long userKey) throws Exception{
	DetachedCriteria criteria = DetachedCriteria
		.forClass(User.class);
	criteria.add(Restrictions.eq("userKey", userKey));
	List<User> userlist =(List<User>) hibernateTemplate.findByCriteria(criteria);
	
	hibernateTemplate.clear();
	hibernateTemplate.flush();
	return userlist.size()>0?userlist.get(0).getEmailId():null;
    }
	
    
 

public List<UserLog> getUserLoginLogListbyFlag(Long userKey, String flag,long loginAttempts) {
	
	List<UserLog> list=null;
	List<UserLog> list2= new ArrayList<UserLog>();
	int lgAtm=(int)loginAttempts;
	DetachedCriteria criteria = DetachedCriteria.forClass(UserLog.class);
	criteria.add(Restrictions.eq("user", userKey));
	criteria.addOrder(Order.desc("userLogKey"));
	if(flag.equalsIgnoreCase("LI")){
		criteria.add(Restrictions.eq("activityFlag", flag));
		list=(List<UserLog>)hibernateTemplate.findByCriteria(criteria);
		list2=list;
	}else if(flag.equalsIgnoreCase("LF")){
		list=(List<UserLog>)hibernateTemplate.findByCriteria(criteria, 0, lgAtm);
		for (UserLog userLog:list) {
			
			if(userLog.getActivityFlag().equals("LF")){
				list2.add(userLog);
			}
		}
	}else if(flag.equalsIgnoreCase("LAST_REC")){
		criteria.add(Restrictions.eq("activityFlag", "PW_RESET"));
		list=(List<UserLog>)hibernateTemplate.findByCriteria(criteria, 0, 1);
		list2=list;
	}
	
	/*
	 * 

	 * 
	 * String query="";
	if(flag.equalsIgnoreCase("LI")){
	query="select ul from UserLog ul where ul.user ="+userKey+" and ul.activityFlag='"+flag+"'";
	}else if(flag.equalsIgnoreCase("LF"))
	{
		query="select ul from UserLog ul where ul.activityFlag='"+flag+"' and ul.userLogKey in (select ul1.userLogKey from UserLog ul1 where ul1.user ="+userKey+"  and ROWNUM<="+loginAttempts+" ORDER BY ul.logTime DESC)";
	}
	List<UserLog> list= hibernateTemplate.find(query);*/
	
	return list2;
}

public Long getExpiredDaysfromUserLog(Long userKey) {
	
	
	
	// TODO Auto-generated method stub
	return null;
}

public List<?> getUserListForResetPwd(String lang) throws Exception {
	List<CustomCombo> list;
	String query = "";
	if (lang.equalsIgnoreCase("en")) {
		
	
		
		query = "select u.userKey as key, u.nameP as value  from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL) ";
		
	} else {
		query = "select u.userKey as key, u.nameS as value  from User u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL)";
		
	}
	list = hibernateTemplate
			.getSessionFactory()
			.getCurrentSession()
			.createQuery(query)
			.setResultTransformer(
					Transformers.aliasToBean(CustomCombo.class)).list();
	return list;
}


   
  



  
}
