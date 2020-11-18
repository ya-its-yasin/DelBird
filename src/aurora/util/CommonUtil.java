package aurora.util;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Id;

public class CommonUtil {

	public static String getFieldName(Class<?>  clss){
		String fieldName="";
		Field[] fields = clss.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			
			Id id = fields[i].getAnnotation(Id.class);
			if(id!=null){
				fieldName =fields[i].getName();
				break;
			}
		}
		return fieldName;
	}
	
	public static String buildDynamicSearchStringByFields(Class<?>  clss,String fieldsArr,String value){
		String fieldName="";
		Field[] fields = clss.getDeclaredFields();
		String likeCondition="";
		for(int i=0;i<fields.length;i++){
			
			Column column = fields[i].getAnnotation(Column.class);
			if(column!=null){
				fieldName =fields[i].getName();
				String fieldType = fields[i].getType().getSimpleName();
				if(fieldsArr.indexOf(fieldName)!=-1){
					if(fieldType.equalsIgnoreCase("Date") && fieldName.endsWith("Date") ){
						likeCondition+= "UPPER(TO_CHAR("+column.name()+",'DD-Mon-rrrr hh:mi:ss')) LIKE UPPER('"+value+"%') OR ";
					}else  if(fieldType.equalsIgnoreCase("Date") && (fieldName.endsWith("DateTime") )){
						likeCondition+= "UPPER(TO_CHAR("+column.name()+",'DD-Mon-rrrr hh:mi:ss am')) LIKE UPPER('"+value+"%') OR ";
					}else{
						likeCondition+= "UPPER("+column.name()+") LIKE UPPER('"+value+"%') OR ";
					}
				}
				
			}
		}
		likeCondition=likeCondition.trim();
		if(likeCondition.contains("OR")){
			likeCondition=likeCondition.substring(0,likeCondition.length()-2);
			likeCondition="("+likeCondition+")";
		}
		
		return likeCondition;
	}
}
