package aurora.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil 
{
	
	public static String presentDate()
	{
		Date entry_date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
		return fmt.format(entry_date);
		 
	}
	public static Date getPrevDate(Date myDate)
	{
		
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        // Use the date formatter to produce a formatted date string
        Date previousDate = calendar.getTime();
		return previousDate;
	}
	public static String convertDateTimeToOraDbFormat(Date inDate) throws Exception {
		
SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String outDate = outFormat.format(inDate);
				
		return outDate;
		
		/*String outDate=null;
		if(inDate==null || inDate.endsWith("null") || inDate.trim().length()==0){
			outDate= null;
		}
		else{
		SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(inDate.contains(".")){
			inDate = inDate.substring(0,inDate.indexOf("."));
		}
		Date toDateFormat = inFormat.parse(inDate);
		   Timestamp timeStampDate = new Timestamp(toDateFormat.getTime());
		 outDate = timeStampDate.toString();
		outDate=inDate;
		}	
		return outDate;	*/	
	}
	public static String presentDateTime()
	{
		Date entry_date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(entry_date);
		 
	}
	
	public static String getDatewithFormat(String format)
	{
		Date entry_date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(entry_date);
	}
	public static String getDbFormatDate(String strDate) throws ParseException 
	{	/*String strDate = "06-Jan-2012";*/
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date dateStr = formatter.parse(strDate);
		String formattedDate = formatter.format(dateStr);
		
		Date date1 = formatter.parse(formattedDate);

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		formattedDate = formatter.format(date1);
		return formattedDate;
		
	}
	
	public static String convertDateTime(String strDateTime) throws ParseException
	{
		/*added by Manjunath on Dec-03-2012
		 *
		 *o/p format : Dec-24-2012 12:49:07 PM
		 */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStr = formatter.parse(strDateTime);
		String formattedDate = formatter.format(dateStr);
		
		Date date1 = formatter.parse(formattedDate);

		formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		formattedDate = formatter.format(date1);
		
		return formattedDate;
	}
	
	
	public static String convertDateTimeToDDMMYY(String strDateTime) throws ParseException
	{
		/*added by Manjunath on Dec-03-2012 */
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date dateStr = formatter.parse(strDateTime);
		String formattedDate = formatter.format(dateStr);
		
		Date date1 = formatter.parse(formattedDate);

		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		formattedDate = formatter.format(date1);
		return formattedDate;
	}
	
	
	public static String convertJqueryDateTimeToDbFormat(String inDate) throws Exception {
		/*
		29-Dec-2012 12:45:40*/
		SimpleDateFormat inFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date toDateFormat = inFormat.parse(inDate);
		String outDate = outFormat.format(toDateFormat);
				
		return outDate;		
	}
	
	public static String convertJqueryDateToDbFormat(String inDate) throws Exception {
		SimpleDateFormat inFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date toDateFormat = inFormat.parse(inDate);
		String outDate = outFormat.format(toDateFormat);
				
		return outDate;		
	}
	
	public static String convertJqueryDateTimeToDbFormat(Date inDate) throws Exception {
		/*
		13-May-2013 12:45:40*/
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String outDate = outFormat.format(inDate);
				
		return outDate;		
	}
	
	public static String convertJqueryDateTimeToDbFormat1(Date inDate) throws Exception {
		/*
		13-May-2013 12:45:40*/
		SimpleDateFormat outFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		String outDate = outFormat.format(inDate);
				
		return outDate;		
	}
	
	public static int numOfDaysToExpire(Date ExpiryDate,Date PresentDate) throws ParseException
	{
		Date expr=ExpiryDate;
		Date prsnt=PresentDate;
		
			Calendar expr1 = Calendar.getInstance();
	       Calendar prsnt2 = Calendar.getInstance();
	         
	       expr1.set(expr.getYear(), expr.getMonth(), expr.getDate());
	       prsnt2.set(prsnt.getYear(), prsnt.getMonth(), prsnt.getDate());
	 
	        long milis1 = expr1.getTimeInMillis();
	        long milis2 = prsnt2.getTimeInMillis();

	        long d = milis1 - milis2;
	         
	        
	        long noOfDayToExpire = d / (24 * 60 * 60 * 1000);
	         
	        
		
		return (int)noOfDayToExpire;
	}
	
	}