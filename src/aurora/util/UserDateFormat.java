package aurora.util;

import java.text.SimpleDateFormat;
import java.util.Date;



public class UserDateFormat {

	static SimpleDateFormat simpleDateFormat ;
	
	public static boolean getDISByDateString(String updateDate, String dbUpdateDate){
		
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
	public static boolean getDISByDate(Date updateDate, Date dbUpdateDate){
		
		 //n
		    boolean dataIntegrityStatus = false;
		   // checking null condition,intially records save with null values in updatedDate and updatedBy columns.
		    if(updateDate==null && dbUpdateDate==null )
		    	dataIntegrityStatus = true;
		    else if(updateDate==null && dbUpdateDate!=null )
		    	dataIntegrityStatus = true;
		    else if(updateDate!=null && dbUpdateDate==null )
		    	dataIntegrityStatus = true;
		    else if(updateDate.compareTo(dbUpdateDate) ==0 )
		    	dataIntegrityStatus = true;
		    else
		    	dataIntegrityStatus = false;
		    
		     return dataIntegrityStatus;
	    }
	
	public static Date getNewDateTimeFormat(String format) throws Exception{
		String udt=convertDateToString(new Date(),format);
		return convertStringToDate(udt, ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A);
	}
	
	public static Date convertStringToDate(String date,String format) throws Exception{
		if(date.trim().isEmpty())
			return null;
		simpleDateFormat = new SimpleDateFormat(format);
		Date dt = (Date)simpleDateFormat.parse(date);
		return dt;
	}
	
	public static String convertDateToString(Date date,String format) throws Exception{
		simpleDateFormat = new SimpleDateFormat(format);
		String dateString = simpleDateFormat.format(date);
		return dateString;
	}
	
	public static Date getDateFormat(String dateString){
		
		 String date[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		 String str= dateString.replace("-", "/");
		 String dateArr[] = str.split(" ");
		 String dateArrDD[] = dateArr[0].split("/");
		 Date returnDate;
		  
		 String stringDate= dateArrDD[2]+"/"+dateArrDD[1]+"/"+dateArrDD[0];
		 if(dateArr.length>=2)
			 stringDate=stringDate+" "+dateArr[1];
		
		 Date dt =new Date(stringDate);
		// System.out.println(dt+"<<<<<<<<dateString>>>>>>"+dateString+"<<<<<<<<<<>>>>>>"+stringDate);
		return dt ;
		    
	}
}
