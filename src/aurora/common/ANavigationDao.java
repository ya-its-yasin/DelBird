package aurora.common;import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import aurora.util.CommonUtil;
import aurora.util.ConstantsMsg;
;


@Component
public abstract class ANavigationDao {

	
	@Autowired@Qualifier("hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	

	
	@SuppressWarnings("unchecked")
	public <T> T getFirst(Class<T>  cls) throws Exception{
		T t = null;

		String name = cls.getSimpleName();
	    String fieldName=CommonUtil.getFieldName(cls);
		
		List<?> list = hibernateTemplate.find("select u from "+name+" u where u."+fieldName+" = (select min("+fieldName+") from "+name+" )");
		if(list.size()>0){
			t = (T)list.get(0);
		}else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return t;
	}
	@SuppressWarnings("unchecked")
	public <T> T  getLast(Class<T>  cls) throws Exception{
		T t = null;

		String name = cls.getSimpleName();
	    String fieldName=CommonUtil.getFieldName(cls);
		
		List<?> list = hibernateTemplate.find("select u from "+name+" u where u."+fieldName+" = (select max("+fieldName+") from "+name+" )");
		if(list.size()>0){
			t = (T)list.get(0);
		}else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return t;
	}
	@SuppressWarnings("unchecked")
	public <T> T getPrev(long currentKey,Class<T>  cls) throws Exception{
		T t = null;

		String name = cls.getSimpleName();
	    String fieldName=CommonUtil.getFieldName(cls);
		
		List<?> list = hibernateTemplate.find("select u from "+name+" u where u."+fieldName+" = (select max("+fieldName+") from "+name+" where "+fieldName+" < "
				+ currentKey + " )");
		if(list.size()>0){
			t = (T)list.get(0);
		}else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return t;
	}
	@SuppressWarnings("unchecked")
	public <T> T getNext(long currentKey,Class<T>  cls) throws Exception{
		T t = null;

		String name = cls.getSimpleName();
	    String fieldName=CommonUtil.getFieldName(cls);
		
		List<?> list = hibernateTemplate.find("select u from "+name+" u where u."+fieldName+" = (select min("+fieldName+") from "+name+" where "+fieldName+" > "
				+ currentKey + " )");
		if(list.size()>0){
			t = (T)list.get(0);
		}else {
			throw new Exception(ConstantsMsg.NO_RECORD_FOUND);
		}
		return t;
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
	
	public Boolean checkDuplicate(String fieldName,Class<?>  cls,String mode,String fieldValue,Long key)	{				String clsName = cls.getSimpleName();			List list = null;		if(mode.equalsIgnoreCase("add"))		{			list = hibernateTemplate.find("from "+clsName+" where upper("+fieldName+")=upper('"+fieldValue+"')");		}		else if(mode.equalsIgnoreCase("edit"))		{			String keyFieldName=CommonUtil.getFieldName(cls);			list = hibernateTemplate.find("from "+clsName+" where upper("+fieldName+")=upper('"+fieldValue+"') and "+keyFieldName+" not in("+key+") ");		}				if(list.size()>0)		{			return true;		}				return false;	}			public Boolean checkDuplicate(String fieldName,String className,String fieldValue)	{			long startTime = System.currentTimeMillis();		List list = null;			list = hibernateTemplate.find("from "+className+" where upper("+fieldName+")=upper('"+fieldValue+"')");				long endTime = System.currentTimeMillis();		System.out.println("DiffTimeA:"+(endTime-startTime));		if(list.size()>0)		{			return true;		}				return false;	}
	
	
	
	
	
}
