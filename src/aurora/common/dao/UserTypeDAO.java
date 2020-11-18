package aurora.common.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.CustomCombo;
import aurora.common.model.UserType;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@SuppressWarnings("unchecked")
@Repository("userTypeDAO")
public class UserTypeDAO extends ANavigationDao  implements IMasterDao{

	@Autowired
	@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;


	@Override
	public Object add(Object ut) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(ut);
		return ut;
	}

	@Override
	public Object update(Object ut) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.update(ut);
		return ut;
	}

	@Override
	public Object delete(long key) throws Exception {
		UserType ut;
		DetachedCriteria criteria = DetachedCriteria.forClass(UserType.class);
		criteria.add(Restrictions.eq("userTypeKey", key));

		List<UserType> gr =(List<UserType>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			ut = gr.get(0);
			hibernateTemplate.delete(ut);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return ut;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		UserType userType = null;
		String query = "select u.userTypeKey as userTypeKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.createdUser as createdUser,"
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.updatedUser as updatedUser,"
					+ " u.updatedDateTime as updatedDateTime,u.seqNo as seqNo from UserType u where u.userTypeKey = "
				+ key;

		List<UserType> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(UserType.class))
				.list();
		
		if(list.size()>0)
			userType=list.get(0);
		
		return userType;
	}

	@Override
	public List<?> getAll(String lang) throws Exception {
		String userQuery = "";
		if (lang.equalsIgnoreCase("EN")) {
			userQuery = "select u.userTypeKey as userTypeKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.seqNo as seqNo,"
					+ " u.updatedDateTime as updatedDateTime from UserType u order by seqNo asc";
		} else {
			userQuery = "select u.userTypeKey as userTypeKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+ " (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >= sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ " u.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.seqNo as seqNo,"
					+ " u.updatedDateTime as updatedDateTime from UserType u order by seqNo asc";
		}

		return hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(UserType.class))
				.list();
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			query = "select u.userTypeKey as key, u.nameP as value from UserType u where u.activeYN = 'A' and u.effStartDate < sysdate and (u.effEndDate > sysdate or u.effEndDate is null)";

		} else {
			query = "select u.userTypeKey as key, u.nameS as value from UserType u where u.activeYN = 'A' and u.effStartDate < sysdate and (u.effEndDate > sysdate or u.effEndDate is null)";

		}
		List<CustomCombo> list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}
	
	

}
