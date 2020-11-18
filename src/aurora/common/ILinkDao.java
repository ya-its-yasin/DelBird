package aurora.common;

import java.util.List;

public interface ILinkDao {

	//for Link tables
		public Object deleteAllByHeadKey(long headKey);
		public List<?> getAllLinksByHeadKey(String lang,long headkey);
		public List<?> getListOfLinksByHeadKey(String lang,long headkey);
		
		List<?> saveOrUpdateList(List<?> list) throws Exception;
		List<?> deleteList(List<?> list) throws Exception;
		
		
		
}
