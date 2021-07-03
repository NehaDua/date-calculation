package org.date.datecalculation;
import java.io.File;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ConfiguredBrowserInstance {
	public static final String BASEPATH = System.getProperty("user.dir");

	public static WebDriver getChromeDriver()
	{
		WebDriver driver=null;
	
		try {
			
			System.setProperty("webdriver.chrome.driver", BASEPATH + File.separator+"chromedriver"+File.separator+"chromedriver.exe");
		
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			ChromeOptions options = new ChromeOptions();
			//options.setBinary(BASEPATH + File.separator+"Chrome"+File.separator+"chrome.exe");
                         options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("start-maximized"); 
			options.addArguments("--disable-extensions");
			driver = new ChromeDriver(options); 			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

}
