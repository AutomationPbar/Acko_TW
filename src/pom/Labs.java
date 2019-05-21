package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Labs {
	
WebDriver driver;
	
	public static WebElement searchbox(WebDriver driver){
		
		WebElement sm =driver.findElement(By.id("search-tests-input"));
		
		return sm;
	}
	
	
public static WebElement firsttest(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='search-test-dropdown']/div[1]/div[2]/div"));
		
		return sm;
	}
	

}
