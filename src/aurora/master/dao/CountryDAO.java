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
import aurora.master.model.Country;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.ConstantsMsg;

@SuppressWarnings("unchecked")
@Repository("countryDAO")
public class CountryDAO extends ANavigationDao implements IMasterDao {

	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<?> getAll(String lang) throws Exception {

		String userQuery = "";
		if (lang.equalsIgnoreCase("EN")) {

			userQuery = "select u.cntKey as cntKey,u.nameP as nameP,u.nameS as nameS,u.status as status,u.createdUser as createdUser,u.createdDateTime as createdDateTime,"+
			"u.updatedUser as updatedUser,u.updatedDateTime as updatedDateTime from Country u";

		} else {

			userQuery = "select u.cntKey as cntKey,u.nameP as nameP,u.nameS as nameS,u.status as status,u.createdUser as createdUser,u.createdDateTime as createdDateTime,"+
			"u.updatedUser as updatedUser,u.updatedDateTime as updatedDateTime from Country u";

		}

		List<Country> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(Country.class))
				.list();

		return list;

	}

	public List<?> getAllCountries(String lang) {
		String userQuery = "";
		if (lang.equalsIgnoreCase("en")) {
			userQuery = "select u.cntKey as key, u.nameS as value from Country u order by u.nameS asc ";

		} else {
			userQuery = "select u.cntKey as key, u.nameS as value from Country u order by u.nameS asc ";

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
	public Object add(Object country) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(country);
		return country;
	}

	@Override
	public Object update(Object country) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.saveOrUpdate(country);
		return country;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		
		Object object=null;
		
		String userQuery ="select u.cntKey as cntKey,u.nameP as nameP,u.nameS as nameS,u.status as status,u.createdUser as createdUser,u.createdDateTime as createdDateTime,"+
				"u.updatedUser as updatedUser,u.updatedDateTime as updatedDateTime from Country u where u.cntKey = "
				 + key;
				
		List<Country> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(userQuery)
				.setResultTransformer(Transformers.aliasToBean(Country.class))
				.list();
		if(list.size()>0)
			object= list.get(0);
		return object;
	}

	@Override
	public Object delete(long key) throws Exception {
		Country obj;
		DetachedCriteria criteria = DetachedCriteria.forClass(Country.class);
		criteria.add(Restrictions.eq("cntKey", key));

		List<Country> gr =(List<Country>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			obj = gr.get(0);
			hibernateTemplate.delete(obj);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return obj;
	}

	

}
