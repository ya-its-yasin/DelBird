package aurora.util;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;






@Component
public class JsonReaderWriter {

	
	public static Map<String,Object> mapOK(List<?> list){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", list.size());
		modelMap.put("data", list);
		modelMap.put("success", true);
		
		return modelMap;
	}
	public static Map<String,Object> mapOK(List<?> list, int total){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		modelMap.put("total", total);
		modelMap.put("data", list);
		modelMap.put("success", true);
		
		return modelMap;
	}
	public static Map<String,Object> mapOK(Object obj, String keyword,String msg){
		
		Map<String,Object> modelMap = new HashMap<String,Object>(3);		
		modelMap.put("data", obj);
		modelMap.put("keyword", keyword);
		modelMap.put("message", msg);		
		return modelMap;
	}

	public static Map<String,Object> mapList(List<JSONObject> obj, String keyword,String msg){
	
		Map<String,Object> modelMap = new HashMap<String,Object>(3);		
		modelMap.put("data", obj);
		modelMap.put("keyword", keyword);
		modelMap.put("message", msg);		
		return modelMap;
	}
	public static Map<String,Object> mapOK(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", true);

		return modelMap;
	} 
	public static Map<String,Object> mapError(String msg){

		Map<String,Object> modelMap = new HashMap<String,Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);

		return modelMap;
	} 
	
	public static List<?> getClassList(Object data, Class class1){
		
		List list = new ArrayList();
		
		if(data.toString().contains("[")){
			
			JSONArray jsonArray = JSONArray.fromObject(data);
			list = JSONArray.toList(jsonArray,class1);
		}
		else{
			JSONObject jsonObject = JSONObject.fromObject(data);
			list.add( JSONObject.toBean(jsonObject, class1) );
		}
		
		return list;
	}
	
	public static List<JSONObject> getJSONClassList(Object data){
		
		List<JSONObject>  listJsonObjects = new ArrayList<JSONObject>();
		//List list = new ArrayList();
		
		if(data.toString().contains("[")){
			
		   JSONArray	 jsonArray = JSONArray.fromObject(data);
		   
		   Object[] jsonObject = jsonArray.toArray();
		   for(int i=0;i<jsonObject.length;i++)
			   listJsonObjects.add(getJSONObject(jsonObject[i]));
		   
		 
		}
		else{
			  listJsonObjects.add(getJSONObject(data));
		}
		return listJsonObjects;
	}
	public static List<JSONObject> getJSONList(List<?> list){
		
			List<JSONObject>  listJsonObjects = new ArrayList<JSONObject>();
			if(list.size() >0){
				JSONArray	 jsonArray = JSONArray.fromObject(list);
				Object[] jsonObject = jsonArray.toArray();
				for(int i=0;i<jsonObject.length;i++){
					   listJsonObjects.add(getJSONObject(jsonObject[i]));
				}
			}
		return listJsonObjects;
	}
	
	public static JSONObject getJSONObject(Object data){
		try
		{
			return JSONObject.fromObject(data);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public static boolean getDataIntegrityStatus(String updateDate, String dbUpdateDate){
		
		 //n
		    boolean dataIntegrityStatus = false;
		   // checking null condition,intially records save with null values in updatedDate and updatedBy columns.
		   String date=updateDate==null?null:updateDate;
		 
		   String dbDate=dbUpdateDate==null?null:dbUpdateDate;
		     if( (date==null || date.length()==0) &&  (dbDate==null||dbDate.length()==0))
		    	 dataIntegrityStatus=true;
		     
		     if(dbDate!=null && dbDate.contains("."))
		    	 dbDate = dbDate.substring(0,dbDate.indexOf("."));
		     if(date!=null && date.contains("."))
		    	 date = date.substring(0,date.indexOf("."));
		     
		    if (dbDate!=null && dbDate.equals(date))
		       dataIntegrityStatus=true;
		     
		     return dataIntegrityStatus;
	    }
	public static boolean getDateStatus(Date updateDate, Date dbUpdateDate){
		
		 //n
		    boolean dataIntegrityStatus = false;
		   // checking null condition,intially records save with null values in updatedDate and updatedBy columns.
		   Date date=updateDate==null?null:updateDate;
		 
		   Date dbDate=dbUpdateDate==null?null:dbUpdateDate;
		    if (date.equals(dbDate))
		       dataIntegrityStatus=true;
		     
		     return dataIntegrityStatus;
	    }
	
	
    public static List<? extends Object> getBeanFromRequest(Object data,Class classtype) {
	     
		List<Object> list;
	
	   	if (data.toString().indexOf('[') > -1) {

	   	    list = getListBeanFromJSON(data,classtype);

	   	} else {

	   	 Object obj = getBeanFromJSON(data,classtype);

	   	    list = new ArrayList();
	   	    list.add(obj);
	   	}

	   	return list;
	    }
    
    public static Object getBeanFromJSON(Object data, Class classtype) {
    	JSONObject jsonObject = JSONObject.fromObject(data);
    	Object newObj = (Object) JSONObject.toBean(jsonObject, classtype);
	
    	return newObj;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> getListBeanFromJSON(Object data,Class classType) {
    	JSONArray jsonArray = JSONArray.fromObject(data);
    	List<Object> obj = (List<Object>) JSONArray.toCollection(jsonArray,classType);
    	return obj;
    }
    
    public static JSONObject getJSONObjectFromClassObject(Object object){
		JSONObject jsonObject = getJSONClassList(object).get(0);
		try{
			Field[] fields = object.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				
				
				Column column = fields[i].getAnnotation(Column.class);
				Transient transient1=fields[i].getAnnotation(Transient.class);
				Field field = fields[i];
				if(column!=null || transient1!=null){
					
					String fieldType = field.getType().getSimpleName();
					String fieldName=field.getName();
					String jsonString=  jsonObject.getString(fieldName);
					
					//foreign keys condition check added to set null when value is "0"
					if(fieldType.equals("Long")){
						 if(jsonString==null || jsonString.equalsIgnoreCase("0") || jsonString.trim().length() ==0){
								jsonObject.put(fieldName, "");
							}
					 }
					// time stamp
					 if(fieldType.equalsIgnoreCase("Date") && (fieldName.endsWith("DateTime") )){
						
						if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
							jsonObject.put(fieldName, "");
						}else{
							Method methodName= object.getClass().getDeclaredMethod("get"+(fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1,fieldName.length())));
							Date dt= (Date) methodName.invoke(object, null);
							jsonObject.put(fieldName, UserDateFormat.convertDateToString(dt, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
						}
						
					}// only date
					else if(fieldType.equalsIgnoreCase("Date") && (fieldName.endsWith("Date") ) ){
					
						if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
							jsonObject.put(fieldName, "");
						}else{
							Method methodName= object.getClass().getDeclaredMethod("get"+(fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1,fieldName.length())));
							Date dt= (Date) methodName.invoke(object, null);
							jsonObject.put(fieldName, UserDateFormat.convertDateToString(dt, ConstantsMsg.DD_MON_YYYY));
						}
						
					}
					
				}
				
			}// end for loop;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return jsonObject;
	}
	
	public static Object getClassObjectFromJSONObject(Object object,JSONObject jsonObject){
		Object rtnObject=null;
		
		String className= object.getClass().getName();
		try{
		rtnObject=Class.forName(className).newInstance();
		Field[] fields = object.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			
			
			Column column = fields[i].getAnnotation(Column.class);
			Field field = fields[i];
			if(column!=null){
				
				String fieldType = field.getType().getSimpleName();
				String fieldName=field.getName();
				if(jsonObject.containsKey(fieldName) == false )
				{
					continue;
				}
				String jsonString=  jsonObject.getString(fieldName);
				
				if(fieldType.equalsIgnoreCase("long")){
					
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0 ){
						if(fieldType.equals("Long"))PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, jsonObject.getLong(fieldName));
					}
				}
				else if(fieldType.equalsIgnoreCase("int")){
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						//PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, jsonObject.getInt(fieldName));
					}
				}
				else if(fieldType.equalsIgnoreCase("Integer")){
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, Integer.parseInt(jsonObject.get(fieldName).toString()));
					}
				}
				else if(fieldType.equalsIgnoreCase("Double")){
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, jsonObject.getDouble(fieldName));
					}
					
				}
				// time stamp
				else if(fieldType.equalsIgnoreCase("Date") && (fieldName.endsWith("DateTime")) ){
					
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, UserDateFormat.convertStringToDate(jsonObject.getString(fieldName),ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
					}
					
				}
				else if(fieldType.equalsIgnoreCase("String")){
					PropertyUtils.setProperty(rtnObject,fieldName, jsonObject.getString(fieldName));
				}
				else if(fieldType.equalsIgnoreCase("Boolean")){
					PropertyUtils.setProperty(rtnObject,fieldName, jsonObject.getBoolean(fieldName));
				}
				else if(fieldType.equalsIgnoreCase("Character") || fieldType.equalsIgnoreCase("char")){
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						PropertyUtils.setProperty(rtnObject,fieldName, "");
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, jsonObject.getString(fieldName).charAt(0));
					}
				}
			

				else if(fieldType.equalsIgnoreCase("Date") && fieldName.endsWith("Date") ){
					
					if(jsonString==null || jsonString.equalsIgnoreCase("null") || jsonString.trim().length() ==0){
						PropertyUtils.setProperty(rtnObject,fieldName, null);
					}else{
						PropertyUtils.setProperty(rtnObject, fieldName, UserDateFormat.convertStringToDate(jsonObject.getString(fieldName),ConstantsMsg.DD_MON_YYYY));
					}
					
				}
				
			}
			
		}// end for loop;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return rtnObject;
	}
	public static boolean isInt(String s)
	{
	 try
	  { 
		 int i = Integer.parseInt(s); return true; 
	  }
	 catch(NumberFormatException er)
	  { return false; }
	}
	public static Map<String, Object> mapOKp(Object obj, Long supportKey, String keyword,String msg) {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);		
		modelMap.put("data", obj);
		modelMap.put("supportKey", supportKey);
		modelMap.put("keyword", keyword);
		modelMap.put("message", msg);		
		return modelMap;
	}
}
