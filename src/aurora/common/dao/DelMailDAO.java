package aurora.common.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.common.model.DelMail;


@SuppressWarnings("unchecked")
@Repository("delMailDAO")
public class DelMailDAO extends ANavigationDao implements IMasterDao {

	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public Object add(Object delMail) throws Exception {
		// TODO Auto-generated method stub
				hibernateTemplate.clear();
				hibernateTemplate.flush();
				hibernateTemplate.save(delMail);
				return delMail;
	}

	@Override
	public Object update(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object delete(long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getAll(String lang) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
