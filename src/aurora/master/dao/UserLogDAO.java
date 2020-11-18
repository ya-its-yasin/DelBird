package aurora.master.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.CustomCombo;
import aurora.master.model.UserLogInfo;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@SuppressWarnings("unchecked")
@Repository("userLogDAO")
public class UserLogDAO  {

	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;


	public Object add(Object userLog) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(userLog);
		return userLog;
	}
	 public Object getClientLoginInfo(long userid) throws Exception {
			Object object=null;
			String userQuery =
				"select u.ulDateTime as ulDateTime from UserLogInfo u where u.ulKey NOT IN (select max(ulKey) from UserLogInfo  where userKey="+userid+") and u.userKey="+userid+" and u.action='L1' order by u.ulKey desc";

			List<UserLogInfo> list = hibernateTemplate.getSessionFactory()
					.getCurrentSession().createQuery(userQuery)
					.setResultTransformer(Transformers.aliasToBean(UserLogInfo.class))
					.list();
			if(list.size()>0)
				object= list.get(0);
			return object;
		}
	 public Object getClientLogoutInfo(long userid) throws Exception {
			Object object=null;
			String userQuery =
				"select u.ulDateTime as ulDateTime from UserLogInfo u where u.ulKey NOT IN (select max(ulKey) from UserLogInfo  where userKey="+userid+") and u.userKey="+userid+" and u.action='L0' order by u.ulKey desc";

			List<UserLogInfo> list = hibernateTemplate.getSessionFactory()
					.getCurrentSession().createQuery(userQuery)
					.setResultTransformer(Transformers.aliasToBean(UserLogInfo.class))
					.list();
			if(list.size()>0)
				object= list.get(0);
			return object;
		}

	 public Object getClientCurrentLoginInfo(long userid) throws Exception {
			Object object=null;
			String userQuery =
				"select u.ulDateTime as ulDateTime from UserLogInfo u where u.userKey="+userid+"  order by u.ulKey desc";

			List<UserLogInfo> list = hibernateTemplate.getSessionFactory()
					.getCurrentSession().createQuery(userQuery)
					.setResultTransformer(Transformers.aliasToBean(UserLogInfo.class))
					.list();
			if(list.size()>0)
				object= list.get(0);
			return object;
		}
	

}
