package aurora.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;

@SuppressWarnings("unchecked")
@Repository("delUserDAO")
public class DelUserDAO  extends ANavigationDao implements IMasterDao  {
	
	@Autowired @Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public Object add(Object delUser) throws Exception {
		// TODO Auto-generated method stub
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(delUser);
		return delUser;
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
