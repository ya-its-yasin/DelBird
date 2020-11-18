package aurora.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import aurora.common.model.CustomCombo;
import aurora.common.model.Menu;
import aurora.common.model.Module;
import aurora.common.ANavigationDao;
import aurora.common.IMasterDao;
import aurora.util.CommonUtil;
import aurora.util.ConstantsMsg;


@SuppressWarnings("unchecked")
@Repository("menuDAO")
public class MenuDAO extends ANavigationDao implements IMasterDao {

	@Autowired@Qualifier("hibernateTemplate")
	HibernateTemplate hibernateTemplate;

	

	@Override
	public Object add(Object menu) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.save(menu);hibernateTemplate.flush();
		return menu;
	}

	@Override
	public Object update(Object menu) throws Exception {
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		hibernateTemplate.update(menu);hibernateTemplate.flush();
		return menu;
	}

	@Override
	public Object delete(long key) throws Exception {
		Menu menu;
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
		criteria.add(Restrictions.eq("menuKey", key));
		List<Menu> gr =(List<Menu>) hibernateTemplate.findByCriteria(criteria);
		if (gr.size() > 0) {
			menu = gr.get(0);
			hibernateTemplate.delete(menu);
		} else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return menu;
	}

	@Override
	public Object getObjectByKey(long key) throws Exception {
		String query = "select m.menuKey as menuKey, m.seqNo as seqNo,m.nameP as nameP,m.nameS as nameS, m.descP as descP, m.descS as descS,m.keyword as keyword,m.url as url,m.menuClass as menuClass,"
				+ " (case when m.activeYN = 'A' and m.effStartDate <= sysdate and (m.effEndDate >= sysdate or  m.effEndDate "
				+ " IS NULL) then 'A' else 'I' end) as activeYN,m.effStartDate as effStartDate,m.effEndDate as effEndDate,m.parentKey as parentKey,m.moduleKey as moduleKey,"
				+ " (select nameP from User where userKey=m.createdUser) as createdUserName,m.createdUser as createdUser,"
				+ "  m.createdDateTime as createdDateTime, "
				+ "(select nameP from User where userKey=m.updatedUser) as updatedUserName,m.updatedUser as updatedUser,"
				+ "  m.updatedDateTime as updatedDateTime from Menu m where m.menuKey = "
				+ key;

		List<Menu> list = hibernateTemplate.getSessionFactory()
				.getCurrentSession().createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(Menu.class))
				.list();

		return list.get(0);
	}

	@Override
	public List<?> getAll(String lang) throws Exception {
		List<Menu> list;

		String query = "";

		if (lang.equalsIgnoreCase("EN")) {

			query = "select m.menuKey as menuKey,m.seqNo as seqNo, m.nameP as nameP,m.nameS as nameS, m.descP as descP, m.descS as descS,m.keyword as keyword,m.url as url,m.menuClass as menuClass,"
					+ " (case when m.activeYN = 'A' and m.effStartDate <= sysdate and (m.effEndDate >= sysdate or  m.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,m.effStartDate as effStartDate,m.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=m.createdUser) as createdUserName,"
					+ " (select nameP from Module where moduleKey=m.moduleKey) as moduleName,"
					+ " (select nameP from Menu where menuKey=m.parentKey) as parentMenuName,"
					+ "  m.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=m.updatedUser) as updatedUserName,"
					+ "  m.updatedDateTime  as updatedDateTime from Menu m order by seqNo asc";

		} else {

			query = "select m.menuKey as menuKey,m.seqNo as seqNo, m.nameP as nameP,m.nameS as nameS, m.descP as descP, m.descS as descS,m.keyword as keyword,m.url as url,m.menuClass as menuClass,"
					+ " (case when m.activeYN = 'A' and m.effStartDate <= sysdate and (m.effEndDate >= sysdate or  m.effEndDate "
					+ " IS NULL) then 'A' else 'I' end) as activeYN,m.effStartDate as effStartDate,m.effEndDate as effEndDate,"
					+ " (select nameP from User where userKey=m.createdUser) as createdUserName,"
					+ " (select nameP from Module where moduleKey=m.moduleKey) as moduleName,"
					+ " (select nameP from Menu where menuKey=m.parentKey) as parentMenuName,"
					+ "  m.createdDateTime as createdDateTime, "
					+ "(select nameP from User where userKey=m.updatedUser) as updatedUserName,"
					+ "  m.updatedDateTime  as updatedDateTime from Menu m order by seqNo asc";

		}

		list = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(query)
				.setResultTransformer(Transformers.aliasToBean(Menu.class))
				.list();
		return list;
	}

	@Override
	public List<?> getList(String lang) throws Exception {
		List<CustomCombo> list;
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			query = "select g.menuKey as key,g.nameP as value from Menu g where g.activeYN = 'A' and g.parentKey is null and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";
		} else {
			query = "select g.menuKey as key,g.nameS as value from Menu g where g.activeYN = 'A' and g.parentKey is null and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null)";
		}
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}

	public List<Menu> getMenuListForAccordion(Long userKey) {
		List<Menu> menuList = (List<Menu>)hibernateTemplate
				.find("select g from Menu g where menuKey in (select menuKey from UserMenuLink where userKey="
						+ userKey
						+ "and queryYN='Y' ) and parentKey is null order by menuKey asc");
	
		return menuList;
	}
	
	public List<Module>getModuleListForAccordian(Long userKey)
	{
		
		//List<Module> modulesList=hibernateTemplate.find("select m from Module m where m.moduleKey in (select menu.moduleKey from Menu menu, UserMenuLink uml where menu.moduleKey=m.moduleKey and  menu.parentKey is not null and uml.userKey="+userKey+" and uml.menuKey=menu.menuKey and uml.queryYN='Y') order by m.nameP asc ");
		List<Module> modulesList=(List<Module>)hibernateTemplate.find("select m from Module m,UserModuleLink uml where uml.userKey="+userKey+" and uml.applicableYN='Y' and m.moduleKey=uml.moduleKey order by m.moduleSeqNo desc");
		return modulesList;
	}

	public List<Menu> getListByParentKey(Long modulekey, Long userKey) 
	{
		List<Menu> menuList =(List<Menu>) hibernateTemplate
				.find("select m from Menu m where menuKey in (select menuKey from UserMenuLink where userKey="+ userKey	+ "and queryYN='Y' ) "
						+"and (select count(sm) from Menu sm where sm.parentKey=m.menuKey)>0 and moduleKey="+ modulekey +" and parentKey is null order by menuKey asc");
		return menuList;
	}
	
	public List<Menu> getModulePanel(Long modulekey, Long userKey) 
	{
		String hql=" " +
				"select m from Menu m where menuKey in (select menuKey from UserMenuLink where userKey="+ userKey	+ "and queryYN='Y' )  and moduleKey="+ modulekey +" and m.parentKey is not null  order by seqNo asc,nameP asc ";
		String parentMenu=" select m from Menu m where menuKey in( select distinct m.parentKey from Menu m where menuKey in (select menuKey from UserMenuLink where userKey="+ userKey	+ "and queryYN='Y' )  and moduleKey="+ modulekey +" )"
				+"  order by seqNo asc,nameP asc ";
				
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.addAll((List<Menu>) hibernateTemplate.find(parentMenu));
		menuList.addAll( (List<Menu>)hibernateTemplate.find(hql));
		return menuList;
	}
	
	public List<Menu> getModulePanel1(Long modulekey, Long userKey) 
	{
		String hql=" " +
				"select m from Menu m where menuKey in (select menuKey from UserMenuLink where userKey="+ userKey	+ "and queryYN='Y' )  and moduleKey="+ modulekey +" and m.parentKey is not null  order by seqNo asc,nameP asc ";
		String parentMenu=" select m from Menu m where menuKey in( select distinct m.parentKey from Menu m where menuKey in (select menuKey from UserMenuLink where userKey="+ userKey	+ "and queryYN='Y' )  and moduleKey="+ modulekey +" )"
				+"  order by seqNo asc,nameP asc ";
				
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.addAll((List<Menu>) hibernateTemplate.find(parentMenu));
		menuList.addAll( (List<Menu>)hibernateTemplate.find(hql));
		return menuList;
	}
	
	public List<Menu> getMenuPanel(Long menuKey, Long userKey) 
	{
		
		List<Menu> menuList = (List<Menu>)hibernateTemplate
				.find("select m from Menu m ,UserMenuLink uml where m.parentKey="+ menuKey +" and m.menuKey=uml.menuKey and uml.userKey="+ userKey+ " and uml.queryYN='Y'  order by m.nameP asc");
		return menuList;
	}
	
	/**  getParentMenuByModuleKey  */
	public List<CustomCombo> getParentMenuByModuleKey(String lang, Long moduleKey) 
	{
		
		List<CustomCombo> list;
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			
			query = "select g.menuKey as key,g.nameP as value from Menu g where g.activeYN = 'A' and g.parentKey is null and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null) and g.moduleKey="+moduleKey+" order by g.nameP asc";
		
		} else {
		
			query = "select g.menuKey as key,g.nameS as value from Menu g where g.activeYN = 'A' and g.parentKey is null and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null) and g.moduleKey="+moduleKey+" order by g.nameS asc";
		
		}
		list = hibernateTemplate
				.getSessionFactory()
				.getCurrentSession()
				.createQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(CustomCombo.class)).list();
		return list;
	}
	
	public List<Menu> getParentMenusByModuleKey(String lang, Long moduleKey) 
	{
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			query = "select distinct   pm.menuKey as menuKey,pm.nameP as parentMenuName from Menu pm ,Menu cm  where cm.parentKey=pm.menuKey and pm.activeYN = 'A' and pm.parentKey is null  and pm.moduleKey="+moduleKey+" order by pm.nameP asc";
		}
		else
		{
			query = "select distinct  pm.menuKey as menuKey,pm.nameS as parentMenuName from Menu pm ,Menu cm  where cm.parentKey=pm.menuKey and pm.activeYN = 'A' and pm.parentKey is null  and pm.moduleKey="+moduleKey+" order by pm.nameS asc";
		}
		List ls=hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(query).setResultTransformer(Transformers.aliasToBean(Menu.class)).list();
		return ls;
	}
	
	
	
	public List<Menu> getMenus(String lang, Long moduleKey) 
	{
		
		
		String query = "";
		if (lang.equalsIgnoreCase("en")) {
			query = "from Menu g where g.activeYN = 'A'  and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null) and g.moduleKey="+moduleKey;
		}
		else
		{
			query = "from Menu g where g.activeYN = 'A' and g.effStartDate < sysdate and (g.effEndDate > sysdate or g.effEndDate is null) and g.moduleKey="+moduleKey;
		}
		
		return (List<Menu>)hibernateTemplate.find(query);
	}
	
	public Integer  getMaxSeqNo(Class<?>  cls) throws Exception{
		String clsName = cls.getSimpleName();
		Integer seqNo=null;
		
		try
		{
			seqNo =  DataAccessUtils.intResult(hibernateTemplate.find("select max(seqNo)+1 from "+clsName+""));
		}
		catch(Exception e)
		{
			seqNo = null;
		}
		
		return seqNo ;
	}
	public Boolean swapRecord(Integer curSeqNo, Integer prevSeqNo,Class<?>  cls) throws Exception{
		Boolean flag = false;
		String clsName = cls.getSimpleName();
		
		 String fieldName=CommonUtil.getFieldName(cls);
		
		try
		{
			Long currentKey =  DataAccessUtils.longResult(hibernateTemplate.find("select "+fieldName+" from "+clsName+" where seqNo="+curSeqNo+""));
			Long previousKey = DataAccessUtils.longResult(hibernateTemplate.find("select "+fieldName+"  from "+clsName+" where seqNo="+prevSeqNo+""));
			
			String updateCurr ="update "+clsName+" set seqNo="+prevSeqNo+" where "+fieldName+"="+currentKey;
			
			String updatePrev ="update "+clsName+" set seqNo="+curSeqNo+" where "+fieldName+"="+previousKey;
			
			
			hibernateTemplate.bulkUpdate(updateCurr);
			hibernateTemplate.bulkUpdate(updatePrev);
			flag = true;
		}
		catch(Exception e)
		{
			flag = false;
		}
		
		return flag ;
	}
}
