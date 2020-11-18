package aurora.common.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.CustomCombo;
import aurora.common.model.UserGroup;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@SuppressWarnings("unchecked")
@Repository("userGroupDAO")
public class UserGroupDAO extends ANavigationDao implements IMasterDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<?> getAll(String lang) throws Exception {
		List<UserGroup> list;

		String userQuery = "";

		if (lang.equalsIgnoreCase("EN")) {

			/*userQuery = "select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,ut.nameP as userTypeName,"
			        
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ " u.updatedDateTime  as updatedDateTime from UserGroup u, UserType ut where u.userType=ut.userTypeKey";
*/
			userQuery = "select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
			        
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ " u.updatedDateTime  as updatedDateTime from UserGroup u";
		} else {

			/*userQuery = "select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,ut.nameP as userTypeName,"
			        
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ " u.updatedDateTime  as updatedDateTime from UserGroup u, UserType ut where u.userType=ut.userTypeKey";*/
					
					userQuery = "select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
							+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or u.effEndDate "
							+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					        
							+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
							+ "  u.createdDateTime  as createdDateTime, "
							+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
							+ " u.updatedDateTime  as updatedDateTime from UserGroup u";
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

	@SuppressWarnings("deprecation")
	@Override
	public List<?> getList(String lang) throws Exception {
		List<CustomCombo> list;
		String userQuery = "";
		
		if (lang.equalsIgnoreCase("en")) {
			userQuery = "select g.userGroupKey as key,g.nameP as value from UserGroup g where g.activeYN = 'A'  and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";
		} else {
			userQuery = "select g.userGroupKey as key,g.nameS as value from UserGroup g where g.activeYN = 'A'  and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";
		}
		/*if (lang.equalsIgnoreCase("en")) {
			userQuery = "select g.userGroupKey as key,g.nameP as value from UserGroup g where g.activeYN = 'A' and g.effStartDate < sysdate and (g.effEndDate > curdate() or g.effEndDate is null)";
		}
		else {
			userQuery = "select g.userGroupKey as key,g.nameS as value from UserGroup g where g.activeYN = 'A' and g.eff_start_date < sysdate and (g.effEndDate > curdate() or g.effEndDate is null)";
		}*/
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}

	@Override
	public Object add(Object userGroup) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(userGroup);
		return userGroup;
	}

	@Override
	public Object update(Object userGroup) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.saveOrUpdate(userGroup);
		return userGroup;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		String query = /*"select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS," +
				"ut.nameP as userTypeName,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN, u.effStartDate  as effStartDate, u.effEndDate  as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.createdUser as createdUser,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.updatedUser as updatedUser,"
					+ "  u.updatedDateTime  as updatedDateTime from UserGroup u, UserType ut where u.userType=ut.userTypeKey and u.userGroupKey = "
				+ key;*/
				"select u.userGroupKey as userGroupKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS," 
				
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN, u.effStartDate  as effStartDate, u.effEndDate  as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.createdUser as createdUser,"
					+ "  u.createdDateTime  as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.updatedUser as updatedUser,"
					+ "  u.updatedDateTime  as updatedDateTime from UserGroup u where u.userGroupKey = "
				+ key;
				

		List<UserGroup> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(UserGroup.class))
				.list();

		return list.get(0);
	}

	@Override
	public Object delete(long key) throws Exception {
		UserGroup obj;
		DetachedCriteria criteria = DetachedCriteria.forClass(UserGroup.class);
		criteria.add(Restrictions.eq("userGroupKey", key));
		List<UserGroup> gr = (List<UserGroup>)hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			obj = gr.get(0);
			hibernateTemplate.delete(obj);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return obj;
	}

	

}
