package testCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;



public class BaseClass {
	
	public ResourceBundle rb;// to read config.properties
	 public static  WebDriver driver;
	 public Logger logger; // for logging
	
	@BeforeClass(groups = { "Master", "Sanity", "Regression" }) //Step8 groups added
	@Parameters("browser")   // getting browser parameter from testng.xml
	public void setup(String br)
		{
		rb = ResourceBundle.getBundle("config");// Load config.properties
		logger=LogManager.getLogger(this.getClass());  //this.getclass() fetches the current class
		//WebDriverManager.chromedriver().setup();  // latest version it is skipped if we add dependencies on the pom.xml file
		
		
		if (br.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (br.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(rb.getString("appURL")); // get url from config.properties file,  where app URL is the key
		driver.manage().window().maximize();
		
		
	}
	
	@AfterClass(groups = { "Master", "Sanity", "Regression" }) //Step8 groups added
	public void tearDown()
	{
		
		driver.quit();
		
	}
	
	public String randomeString()
	
	{
		
		String generatedstring=RandomStringUtils.randomAlphabetic(5); // genertaes randon string
		return (generatedstring);
	}
	public String randomeNumber()
		{
		
		String generatedNumber=RandomStringUtils.randomNumeric(10);
		return (generatedNumber);
	}
	public String randomAlphaNumeric()
	{
	
	String str=RandomStringUtils.randomAlphabetic(5);
	String num=RandomStringUtils.randomNumeric(10);
	return (str+"@"+num);
}
	
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
			//FileUtils.copyFile(source,destination);
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}
	
}
