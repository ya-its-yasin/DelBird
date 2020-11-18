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
import aurora.common.model.Module;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@SuppressWarnings("unchecked")
@Repository("moduleDAO")
public class ModuleDAO extends ANavigationDao implements IMasterDao {

	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<?> getAll(String lang) throws Exception {

		String userQuery = "";
		if (lang.equalsIgnoreCase("EN")) {

			userQuery = "select u.moduleKey as moduleKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS, (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, u.moduleCls as moduleCls,"
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ "  u.updatedDateTime as updatedDateTime from Module u";

		} else {

			userQuery = "select u.moduleKey as moduleKey,u.keyword as keyword,u.nameP as nameP,u.nameS as nameS,"
					+

					" (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
					+ "  u.createdDateTime  as createdDateTime, u.moduleCls as moduleCls,"
					+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
					+ "  u.updatedDateTime  as updatedDateTime from Module u";

		}

		List<Module> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(Module.class))
				.list();

		return list;

	}

	@Override
	public List<?> getList(String lang) {
		String userQuery = "";
		if (lang.equalsIgnoreCase("en")) {
			userQuery = "select g.moduleKey as key,g.nameP as value from Module g where g.activeYN = 'A' and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";

		} else {
			userQuery = "select g.moduleKey as key,g.nameS as value from Module g where g.activeYN = 'A' and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";

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
	public Object add(Object module) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(module);
		return module;
	}

	@Override
	public Object update(Object module) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.saveOrUpdate(module);
		return module;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		
		Object object=null;
		
		String userQuery = "select moduleKey as moduleKey, u.keyword as keyword, u.nameP as nameP,u.nameS as nameS,u.moduleCls as moduleCls," +
				
				" (case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL) then 'A' else 'I' end) as activeYN, u.effStartDate  as effStartDate, u.effEndDate  as effEndDate,"
				+ " (select nameP from User where userKey=u.createdUser) as createdUserName,u.createdUser as createdUser,"
				+ "  u.createdDateTime  as createdDateTime, "
				+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,u.updatedUser as updatedUser,"
				+ "  u.updatedDateTime  as updatedDateTime from Module u where u.moduleKey = "
				+ key;

		List<Module> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(Module.class))
				.list();
		if(list.size()>0)
			object= list.get(0);
		return object;
	}

	@Override
	public Object delete(long key) throws Exception {
		Module obj;
		DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
		criteria.add(Restrictions.eq("moduleKey", key));

		List<Module> gr =(List<Module>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			obj = gr.get(0);
			hibernateTemplate.delete(obj);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return obj;
	}

	

}
