package aurora.common;

import java.util.List;


public interface IMasterDao {

	//CRUD methods
	public Object add(Object obj) throws Exception;	
	public Object update(Object obj) throws Exception;
	public Object delete(long key) throws Exception;
	public Object getObjectByKey(long key) throws Exception;
	public List<?>  getAll(String lang) throws Exception;
	//for list
	public List<?> getList(String lang) throws Exception;
	
	

	
}
