package aurora.common.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.common.model.DelUser;

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
	
	public List<DelUser> getDelUsers(String TloginId, String Tpassword) throws Exception{
	
	/*	String query = "select u.userKey as userKey,u.nameP as nameP,u.nameS as nameS,u.loginId as loginId,u.userPassword as userPassword,u.createdUser as createdUser,u.updatedUser as updatedUser,"
				+ " u.emailId as emailId, u.contactNo as contactNo,u.userGroup as userGroup,u.usrLockYN as usrLockYN,"
				+ " (select nameP from UserGroup where userGroupKey=u.userGroup) as groupName , "
				+ "(case when u.activeYN = 'A' and u.effStartDate <= sysdate and (u.effEndDate >=sysdate or  u.effEndDate "
				+ " IS NULL) then 'A' else 'I' end) as activeYN,u.effStartDate as effStartDate,u.effEndDate as effEndDate,"
				+ " (select nameP from User where userKey=u.createdUser) as createdUserName,"
				+ " u.createdDateTime as createdDateTime, "
				+ "(select nameP from User where userKey=u.updatedUser) as updatedUserName,"
				+ "u.updatedDateTime as updatedDateTime from User u where u.loginId='"+loginId+"'";
		*/
		
		String query="select userId,password from users where userId='"+TloginId+"' and password='"+Tpassword+"'";
		
		List<DelUser> list =null;

		list = hibernateTemplate.getSessionFactory().getCurrentSession()
				
				.createQuery(query)
				
				.setResultTransformer(Transformers.aliasToBean(DelUser.class))
				.list();
		
		return list;
	}
	
	

}
