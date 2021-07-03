package org.date.datecalculation;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * The DataCalculation class is the main class 
 * i.e. Program start point
 * Build tool use Maven, Dependencies fetch from POM.xml like Selenium and apache POI
 */

public class DateCalculation {
	
	/*
	 * This will give the path of current directory and run irrespective of the location
	 */
	public static final String BASEPATH = System.getProperty("user.dir");
	
	/*
	 * In-order to create executable jar, Main method is introduce 
	 * Jar name: RunMe.jar resides parallel to src folder
	 */
	public static void main(String[] ar ) throws Exception {
		WebDriver driver=ConfiguredBrowserInstance.getChromeDriver();
		try {
			//1st ask: To Get the ID value from UI 
			driver.get(BASEPATH+File.separator+"TestFiles"+File.separator+"HTML_sample.html");
			switchToFrameByName("frm1", driver);
			switchToFrameByName("frm2", driver);
			String sID = driver.findElement(By.xpath("//*[@id='usrid']")).getText();
			
			//getting the date value for file for corresponding ID
			String sDate = new ReadExcel().getDateForID(sID.trim());
			
			//2nd ask: calculate the actual date by adding 3 years 
			sDate = getGivenDateInRequiredFromatPlusNoOfYears(sDate, 3);
			
			//3rd ask: Fill the Due/Expire date in the form having nested iframes i.e. only HTML_sample.html has nested Iframes   
			driver.findElement(By.xpath("//*[@id='expiryData']")).sendKeys(sDate);
			
			switchToDefaultContent(driver);
		}finally{
			Thread.sleep(5000); // to wait for 5sec before quieting the application 
			driver.quit();
		}
	}
	
	
	
	public static void switchToFrameByName(String sFrameName,WebDriver driver) {
		driver.switchTo().frame(sFrameName);
	}
	
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	@SuppressWarnings("deprecation")
	public static String getGivenDateInRequiredFromatPlusNoOfYears(String sDate,int iNoOfYrsToBeAdded) throws ParseException {
		
		Date date = new Date(sDate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sDate= sdf.format(date);
	
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(sDate));
		c.add(Calendar.YEAR, iNoOfYrsToBeAdded); // number of days to add.
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		
	    Date finalDate = c.getTime();  
        DateFormat dateFormat = new SimpleDateFormat("EEEEE, dd MMMM yyyy");  
        String strDate = dateFormat.format(finalDate); 
        
        return strDate;
		
	}

}
