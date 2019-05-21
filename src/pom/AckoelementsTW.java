package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AckoelementsTW {
	
	
	public static WebElement selectbike(WebDriver driver){
		
		WebElement selectmake = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div"));
		return selectmake;
		
	}
	
	public static WebElement selectbikeoption(WebDriver driver,int m){
		
		WebElement selectmodel = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[2]/a["+m+"]"));
		return selectmodel;
		
	}
	
public static List<WebElement> variants(WebDriver driver){
		
		List<WebElement> variant = driver.findElements(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[2]/a"));
		return variant;
		
	}
	public static WebElement selectbikedropdown(WebDriver driver){
		
		WebElement selectmodel = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[2]"));
		return selectmodel;
		
	}

public static WebElement selectbikeinput(WebDriver driver){
		
		WebElement selectmodel = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/div[2]/div[1]/input"));
		return selectmodel;
		
	}

	public static WebElement boughtyear(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[2]/div/div/span"));
		return value;
		
	}

	public static WebElement yearselect(WebDriver driver,String year){
	
	WebElement value = driver.findElement(By.linkText(year));
	return value;
	
	}
	
	public static WebElement expiry(WebDriver driver,String isexpired){
		
		WebElement value = driver.findElement(By.linkText(isexpired));
		return value;
		
		}
	public static WebElement ISexpired(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[3]/div/div/span"));
		return value;
		
	}
	
public static WebElement defaultidv(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[1]/div[2]/div[1]/span[3]"));
		return value;
		
	}
	
	

	
	public static WebElement viewprices(WebDriver driver){
		
		WebElement value = driver.findElement(By.linkText("View Prices"));
		return value;
		
	}
	


	
public static WebElement threeyearplan(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/keyboardavoidingview/div/div[2]/div[2]/span[1]"));
		return value;
		
	}

public static WebElement twoyearplan(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[2]/keyboardavoidingview/div/div[2]/div[2]/span[1]"));
	return value;
	
}
public static WebElement oneyearplan(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[2]/div[3]/keyboardavoidingview/div/div[2]/div[2]/span[1]"));
	return value;
	
}
public static WebElement lowidv(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/span[1]"));
	return value;
	
}

public static WebElement idvscroll(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//*[@id='root']/div/keyboardavoidingview/div[2]/div/div/div/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[3]/span[1]"));
	return value;
	
}
public static WebElement slideridv(WebDriver driver){
	
	WebElement value = driver.findElement(By.className("sliderIDV"));
	return value;
	
}

}
