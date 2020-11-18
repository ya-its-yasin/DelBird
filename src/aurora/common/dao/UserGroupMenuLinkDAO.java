package aurora.common.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.Menu;
import aurora.common.model.UserGroup;
import aurora.common.model.UserGroupMenuLink;
import aurora.common.model.UserMenuLink;
import aurora.common.ANavigationDao;
import aurora.common.ILinkDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@Repository
public class UserGroupMenuLinkDAO extends ANavigationDao implements IMasterDao,ILinkDao {

	@Autowired@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	
	public List<Menu> getUserGroupModuleLinkListByUserKey(Long userGroupKey,String lang) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
	
			sqlQuery="select DISTINCT " +
					"m.moduleKey as moduleKey," +
					" m.nameP as moduleName " +
					" from Module m,Menu mu ,UserGroupMenuLink uml,  UserGroup u " +
					" where  m.moduleKey=mu.moduleKey and mu.menuKey= uml.menuKey and uml.groupKey= u.userGroupKey and u.userGroupKey="+userGroupKey+" order by m.nameP";
		}
		else
		{
			sqlQuery="select DISTINCT " +
					"m.moduleKey as moduleKey," +
					" m.nameS as moduleName " +
					" from Module m,Menu mu ,UserGroupMenuLink uml,  UserGroup u " +
					" where  m.moduleKey=mu.moduleKey and mu.menuKey= uml.menuKey and uml.groupKey= u.userGroupKey and u.userGroupKey="+userGroupKey+" order by m.nameS";
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(Menu.class));
		List<Menu> list= query.list();
		return list;
		
	}

	public List<UserGroupMenuLink> getUserGroupMenuLinkListByUserKey(Long userGroupKey,String lang,Long parentMenuKey) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
			
			sqlQuery="select  ug.userGroupMenuLinkKey as userGroupMenuLinkKey," +
					"ug.groupKey as groupKey," +
					"ug.menuKey as menuKey," +
					"ug.queryYN as queryYN, " +
					"ug.insertYN as insertYN," +
					"ug.updateYN as updateYN," +
					"ug.deleteYN as deleteYN," +
					"ug.printYN as printYN," +
					"ug.exportYN as exportYN,"+
					" menu.nameP as menuName " +
					" from UserGroupMenuLink ug ,UserGroup u1,Menu menu " +
					" where ug.groupKey=u1.userGroupKey  " +
					" and ug.menuKey=menu.menuKey and u1.userGroupKey="+userGroupKey+" and menu.parentKey="+parentMenuKey+" ";
		}
		else
		{
			sqlQuery="select  cg.userGroupMenuLinkKey as userGroupMenuLinkKey," +
					"cg.groupKey as groupKey," +
					"cg.menuKey as menuKey," +
					"cg.queryYN as queryYN, " +
					"cg.insertYN as insertYN," +
					"cg.updateYN as updateYN," +
					"cg.deleteYN as deleteYN," +
					"cg.printYN as printYN," +
					"cg.exportYN as exportYN,"+
					" menu.nameS as menuName " +
					" from UserGroupMenuLink cg ,UserGroup u1,Menu menu " +
					" where cg.groupKey=u1.userGroupKey  " +
					" and cg.menuKey=menu.menuKey and u1.userGroupKey="+userGroupKey+" and menu.parentKey="+parentMenuKey+" ";
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserGroupMenuLink.class));
		List<UserGroupMenuLink> list= query.list();
		return list;
		
	}


	

	@SuppressWarnings("unchecked")
	public UserGroupMenuLink getUserGroupMenuLinkByKey(long key) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(UserGroupMenuLink.class);
		criteria.add(Restrictions.eq("userGroupMenuLinkKey", key));
		List<UserGroupMenuLink> list = (List<UserGroupMenuLink>)hibernateTemplate
				.findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	
	@Override
	public Object update(Object ugml) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.update(ugml);
		return ugml;
	}
	
	
	
	@Override
	public Object add(Object ugml) throws Exception {
		// TODO Auto-generated method stub
				return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object delete(long key) throws Exception {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		UserGroupMenuLink uml;
		DetachedCriteria criteria = DetachedCriteria.forClass(UserGroupMenuLink.class);
		criteria.add(Restrictions.eq("userGroupMenuLinkKey", key));
		List<UserGroupMenuLink> list = (List<UserGroupMenuLink>)hibernateTemplate.findByCriteria(criteria);
		if (list.size() > 0) {
			uml = list.get(0);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return uml;
	}

	@Override
	public List<?> getAll(String lang) throws Exception {
		List<UserGroup> list;

		String userQuery = "";

		if (lang.equalsIgnoreCase("EN")) {

			userQuery = "select u.userGroupKey as userGroupKey,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from UserType where userTypeKey=u.userTypeKey) as userTypeName,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ " u.updatedDateTime  as updatedDateTime from UserGroup u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL)";

		} else {

			userQuery = "select u.userGroupKey as userGroupKey,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from UserType where userTypeKey=u.userTypeKey) as userTypeName,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ " u.updatedDateTime  as updatedDateTime from UserGroup u where u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL)";
		}

		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(UserGroup.class))
				.list();
		// resultObject.setResultData(list);

		return list;
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<?> saveOrUpdateList(List<?> list) throws Exception {
		// TODO Auto-generated method stub
		try
		{
			hibernateTemplate.clear();
			hibernateTemplate.flush();
			//hibernateTemplate.saveOrUpdateAll(list);
			for(int i=0; i<list.size();i++) {
				UserGroupMenuLink copy=new UserGroupMenuLink();
				copy=(UserGroupMenuLink) list.get(i);
				UserGroupMenuLink obj=(UserGroupMenuLink) getObjectByKey(copy.getUserGroupMenuLinkKey());
				obj.setDeleteYN(copy.getDeleteYN());
				obj.setExportYN(copy.getExportYN());			
				obj.setInsertYN(copy.getInsertYN());
				obj.setPrintYN(copy.getPrintYN());
				obj.setQueryYN(copy.getQueryYN());
				obj.setUpdatedDateTime(copy.getUpdatedDateTime());
				obj.setUpdatedUser(copy.getUpdatedUser());
				obj.setUpdateYN(copy.getUpdateYN());
				
						hibernateTemplate.update(obj);
						//hibernateTemplate.clear();
						//hibernateTemplate.flush();
				
			}
			
		}
		catch(Exception e)
		{}
		
		
		return list;
	}




	@Override
	public List<?> deleteList(List<?> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Object deleteAllByHeadKey(long headKey) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<?> getAllLinksByHeadKey(String lang, long headkey) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<?> getListOfLinksByHeadKey(String lang, long headkey) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	public UserGroup getFirstUserGroupMenuLink() {
		
		
		List<UserGroup> list=(List<UserGroup>)hibernateTemplate.find("select g from UserGroup g where userGroupKey=(select min(userGroupKey) from UserGroup where activeYN='A' and effStartDate <= sysdate and (effEndDate >= sysdate or effEndDate IS NULL))");

	if(list.size()>0){
		return list.get(0);
		
	}else{
		return null;
	}

	}

	public UserGroup getLastUserGroupMenuLink() {
		
		
		List<UserGroup> list=(List<UserGroup>)hibernateTemplate.find("select g from UserGroup g where userGroupKey=(select max(userGroupKey) from UserGroup where activeYN='A' and effStartDate <= sysdate and (effEndDate >= sysdate or effEndDate IS NULL))");

	if(list.size()>0){
		return list.get(0);
		
	}else{
		return null;
	}

	}


	public UserGroup getNextUserGroupMenuLink(long presentKey) {
		
		
		List<UserGroup> list=(List<UserGroup>)hibernateTemplate.find("select g from UserGroup g where userGroupKey=(select min(userGroupKey) from UserGroup where userGroupKey>"+presentKey+" and activeYN='A' and effStartDate <= sysdate and (effEndDate >= sysdate or effEndDate IS NULL))");

	if(list.size()>0){
		return list.get(0);
		
	}else{
		return null;
	}

	}

	public UserGroup getPreviousUserGroupMenuLink(long presentKey) {
		
		
		List<UserGroup> list=(List<UserGroup>)hibernateTemplate.find("select g from UserGroup g where userGroupKey=(select max(userGroupKey) from UserGroup where userGroupKey<"+presentKey+" and activeYN='A' and effStartDate <= sysdate and (effEndDate >= sysdate or effEndDate IS NULL))");

	if(list.size()>0){
		return list.get(0);
		
	}else{
		return null;
	}

	}
	
	
	
	
	
	
	
	
}
