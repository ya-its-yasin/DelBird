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
import aurora.common.model.User;
import aurora.common.model.UserGroupMenuLink;
import aurora.common.model.UserMenuLink;
import aurora.common.ANavigationDao;
import aurora.common.ILinkDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;
import oracle.sql.DATE;

@SuppressWarnings("unchecked")
@Repository("userMenuLinkDAO")
public class UserMenuLinkDAO extends ANavigationDao implements IMasterDao,ILinkDao {

	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;


	@Override
	public Object add(Object uml) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(uml);
		return uml;
	}

	@Override
	public Object update(Object uml) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.saveOrUpdate(uml);
		hibernateTemplate.flush();
		return uml;
	}

	@Override
	public Object delete(long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		UserMenuLink uml;
		DetachedCriteria criteria = DetachedCriteria.forClass(UserMenuLink.class);
		criteria.add(Restrictions.eq("userMenuLinkKey", key));
		List<UserMenuLink> gr =(List<UserMenuLink>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			uml = gr.get(0);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return uml;
	}
	
	public Object updateFavourite(long userkey,long menukey, String favouriteYN) throws Exception {
		UserMenuLink uml=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(UserMenuLink.class);
		criteria.add(Restrictions.eq("userKey", userkey));
		criteria.add(Restrictions.eq("menuKey", menukey));
		
		List<UserMenuLink> gr = (List<UserMenuLink>)hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			uml = gr.get(0);
			uml.setFavouriteYN(favouriteYN);
			hibernateTemplate.saveOrUpdate(uml);
		} 
		return uml;
	}
	@Override
	public List<?> getAll(String lang) throws Exception {
		List<User> list;
		String userQuery = "";

		if (lang.equalsIgnoreCase("EN")) {

			userQuery = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.userPassword as userPassword,"
					+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.divisionKey as divisionKey ,"
					+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
					+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.usrCompanyKey as usrCompanyKey,"
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ "u.updatedDateTime as updatedDateTime from User u where u.activeYN='A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or u.effEndDate IS NULL)";
		} else {

			userQuery = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.userPassword as userPassword,"
					+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.divisionKey as divisionKey ,"
					+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
					+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.usrCompanyKey as usrCompanyKey,"
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ "u.updatedDateTime as updatedDateTime from User u where u.activeYN='A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or u.effEndDate IS NULL)";
		}

		list = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(User.class))
				.list();
		return list;
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserMenuLink>userAccess(String loginKey,String menuKey,String lang) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
			
			sqlQuery="select uml.queryYN as queryYN,uml.insertYN as insertYN,uml.updateYN as updateYN,uml.deleteYN as deleteYN from UserMenuLink uml  where  uml.userKey="+loginKey+" and uml.menuKey="+menuKey;
		}
		else
		{
			sqlQuery="select cg.queryYN as queryYN,cg.insertYN as insertYN,cg.updateYN as updateYN,cg.deleteYN as deleteYN,from UserMenuLink cg where  cg.userKey="+loginKey+" and cg.menuKey="+menuKey;
			
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserMenuLink.class));
		List<UserMenuLink> list= query.list();
		return list;
		
	}
	
	public List<UserMenuLink> getUserMenuLinkListByParentUserKey(Long userKey,String lang,Long parentMenuKey) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
	
			
			sqlQuery="select  cg.userMenuLinkKey as userMenuLinkKey," +
					"cg.userKey as userKey," +
					"cg.menuKey as menuKey," +
					"cg.queryYN as queryYN, " +
					"cg.insertYN as insertYN," +
					"cg.updateYN as updateYN," +
					"cg.deleteYN as deleteYN," +
					"cg.printYN as printYN," +
					"cg.exportYN as exportYN,"+
					" menu.nameP as menuName " +
					" from UserMenuLink cg ,User u1,Menu menu " +
					" where cg.userKey=u1.userKey  " +
					" and cg.menuKey=menu.menuKey and u1.userKey="+userKey+" and menu.parentKey="+parentMenuKey+" ";
		}
		else
		{
			sqlQuery="select  cg.userMenuLinkKey as userMenuLinkKey," +
					"cg.userKey as userKey," +
					"cg.menuKey as menuKey," +
					"cg.queryYN as queryYN, " +
					"cg.insertYN as insertYN," +
					"cg.updateYN as updateYN," +
					"cg.deleteYN as deleteYN," +
					"cg.printYN as printYN," +
					"cg.exportYN as exportYN,"+
					" menu.nameS as menuName " +
					" from UserMenuLink cg ,User u1,Menu menu " +
					" where cg.userKey=u1.userKey  " +
					" and cg.menuKey=menu.menuKey and u1.userKey="+userKey+" and menu.parentKey="+parentMenuKey+" ";
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserMenuLink.class));
		List<UserMenuLink> list= query.list();
		return list;
		
	}
	
	public List<UserMenuLink> getUserMenuLinkListByUserKey1(Long userKey,String lang,Long moduleKey) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
	
			
			sqlQuery="select  cg.userMenuLinkKey as userMenuLinkKey," +
					"cg.userKey as userKey," +
					"cg.menuKey as menuKey," +
					"cg.queryYN as queryYN, " +
					"cg.insertYN as insertYN," +
					"cg.updateYN as updateYN," +
					"cg.deleteYN as deleteYN," +
					"cg.printYN as printYN," +
					"cg.exportYN as exportYN,"+
					" menu.nameP as menuName, menu.parentKey as parentKey" +
					" from UserMenuLink cg ,User u1,Menu menu " +
					" where cg.createdUser=u1.userKey  " +
					" and cg.menuKey=menu.menuKey and cg.userKey="+userKey+" and menu.moduleKey = "+moduleKey+"";
		}
		else
		{
			sqlQuery="select  cg.userMenuLinkKey as userMenuLinkKey," +
					"cg.userKey as userKey," +
					"cg.menuKey as menuKey," +
					"cg.queryYN as queryYN, " +
					"cg.insertYN as insertYN," +
					"cg.updateYN as updateYN," +
					"cg.deleteYN as deleteYN," +
					"cg.printYN as printYN," +
					"cg.exportYN as exportYN,"+
					" menu.nameS as menuName,menu.parentKey as parentKey" +
					" from UserMenuLink cg ,User u1,Menu menu " +
					" where cg.createdUser=u1.userKey  " +
					" and cg.menuKey=menu.menuKey and cg.userKey="+userKey+" and menu.moduleKey = "+moduleKey+"";
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserMenuLink.class));
		List<UserMenuLink> list= query.list();
		return list;
		
	}
	

	
	
	
	
	
	public List<Menu> getUserModuleLinkListByUserKey(Long userKey,String lang) {
		String sqlQuery="";
		if(lang.equalsIgnoreCase("EN"))
		{
			
	
			sqlQuery="select DISTINCT " +
					"m.moduleKey as moduleKey," +
					" m.nameP as moduleName " +
					" from Module m,Menu mu ,UserMenuLink uml,  User u " +
					" where  m.moduleKey=mu.moduleKey and mu.menuKey= uml.menuKey and uml.userKey= u.userKey and u.userKey="+userKey+" order by m.nameP asc";
		}
		else
		{
			sqlQuery="select DISTINCT " +
					"m.moduleKey as moduleKey," +
					" m.nameS as moduleName " +
					" from Module m,Menu mu ,UserMenuLink uml,  User u " +
					" where  m.moduleKey=mu.moduleKey and mu.menuKey= uml.menuKey and uml.userKey= u.userKey and u.userKey="+userKey+" order by m.nameS asc";
		}
		Query query=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(Menu.class));
		List<Menu> list= query.list();
		return list;
		
	}
	
	
	
	
	
	
	
	public UserMenuLink getUserRights(String keyword, int userKey) 
	{
		List<UserMenuLink> UserMenuList = (List<UserMenuLink>)hibernateTemplate
				.find("select uml from UserMenuLink uml, Menu m where uml.userKey="+ userKey	+ "and uml.menuKey=m.menuKey and m.keyword='"+ keyword +"'");
		return UserMenuList.get(0);
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

	@Override
	public List<?> saveOrUpdateList(List<?> list) throws Exception {
		// TODO Auto-generated method stub
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		//hibernateTemplate.saveOrUpdateAll(list);
for(int i=0; i<list.size();i++) {
	UserMenuLink copy=new UserMenuLink();
	copy=(UserMenuLink) list.get(i);
	UserMenuLink obj=(UserMenuLink) getObjectByKey(copy.getUserMenuLinkKey());
	obj.setDeleteYN(copy.getDeleteYN());
	obj.setExportYN(copy.getExportYN());
	obj.setFavouriteYN(copy.getFavouriteYN());
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
		
		return list;
	}

	@Override
	public List<?> deleteList(List<?> list) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


public List<Menu> getFavouritesFromUserMenuLink(long userkey){

	/*DetachedCriteria criteria = DetachedCriteria.forClass(UserMenuLink.class);
	
	criteria.add(Restrictions.eq("userKey",userkey));
	criteria.add(Restrictions.eq("favouriteYN", 'Y'));*/
	//return hibernateTemplate.findByCriteria(criteria);

	return (List<Menu>)hibernateTemplate.find("select m from Menu m ,UserMenuLink uml where uml.userKey="+userkey+" " +
			" and uml.favouriteYN='Y' and m.menuKey=uml.menuKey order by m.nameP asc");
	
}
public List<Menu> searchMenu(long userkey,String searchText){	
	
	return (List<Menu>)hibernateTemplate.find("select m from Menu m ,UserMenuLink uml where uml.userKey="+userkey+" " +
			"  and m.menuKey=uml.menuKey and uml.queryYN='Y' and (upper(m.nameP) like upper('%"+searchText+"%') or "+
			"  upper(m.nameS) like upper('%"+searchText+"%') or "+
			"  upper(m.descP) like upper('%"+searchText+"%') or "+
			"  upper(m.descS) like upper('%"+searchText+"%')  ) and m.parentKey is not null");
	
}


public User getFirstUserMenuLink(String lang) {
	
	
	List<User> list=(List<User>)hibernateTemplate.find("select g from User g where userKey=(select min(userKey) from User where activeYN='A')");

if(list.size()>0){
	return list.get(0);
	
}else{
	return null;
}

}

public User getLastUserMenuLink(String lang) {
	
	
	List<User> list=(List<User>)hibernateTemplate.find("select g from User g where userKey=(select max(userKey) from User where activeYN='A')");

if(list.size()>0){
	return list.get(0);
	
}else{
	return null;
}

}


public User getNextUserMenuLink(long presentKey,String lang) {
	
	
	List<User> list=(List<User>)hibernateTemplate.find("select g from User g where userKey=(select min(userKey) from User where userKey>"+presentKey+" and activeYN='A')");

if(list.size()>0){
	return list.get(0);
	
}else{
	return null;
}

}

public User getPreviousUserMenuLink(long presentKey,String lang) {
	
	
	List<User> list=(List<User>)hibernateTemplate.find("select g from User g where userKey=(select max(userKey) from User where userKey<"+presentKey+" and activeYN='A')");

if(list.size()>0){
	return list.get(0);
	
}else{
	return null;
}

}

public UserMenuLink getParentByChildKey(Long userKey,Long menuKey)
{
	String query = "from UserMenuLink where menuKey = (select menuKey  from Menu where menuKey = (select parentKey from Menu where menuKey = "+menuKey+")) and userKey = "+userKey+"";
	List<UserMenuLink> list =(List<UserMenuLink>) hibernateTemplate.find(query);
	if(list.size()>0)
	{
		return list.get(0);
	}
	return null;
}




}
