package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import pom.AckoelementsTW;

public class AckoTWcompare {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/bike/?pf=d";
	String excelpath = "C:\\Excelfiles\\Acko_TW\\testdata.xlsx";
	String excelpath_update = "C:\\Excelfiles\\Acko_TW\\Data\\dataentry.xlsx";
	String sheetname = "Base Template";

	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	String resultdata[] = new String[14];
	
	String nodata ="No Data Found";


	@BeforeMethod

	public void setup() {

		try {	
			
			FirefoxOptions options = new FirefoxOptions();
            
			FirefoxProfile profile = new FirefoxProfile();
			        
			        profile.setPreference("webdriver.load.strategy", "unstable");
			        profile.setPreference("network.proxy.type", 1);
			        profile.setPreference("network.proxy.socks", "127.0.0.1");
			        profile.setPreference("network.proxy.socks_port", 9050);
			        profile.setPreference("dom.webnotifications.enabled", false);
			        options.setProfile(profile);
			        options.setBinary("C:\\Tor\\Browser\\firefox.exe");
			        
			        System.setProperty("webdriver.gecko.driver", "C:\\Tor\\geckodriver.exe");
			     
			        
			        driver = new FirefoxDriver();
			       // driver.manage().window().maximize();
			        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			        wait = new WebDriverWait(driver, 10);
			
			FileInputStream fis = new FileInputStream(excelpath);
			workbook = new XSSFWorkbook(fis);
			modelsheet = workbook.getSheetAt(0);

			
			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\Excelfiles\\Acko_TW\\Data\\dataentry_cmt" + localDate11 + ".xlsx";
			SetExcelFile(excelpath_update, sheetname);

			row = modelsheet.getRow(0);

			int colCount = row.getLastCellNum();
			System.out.println("Column Count :- " + colCount);

			rowCount = modelsheet.getLastRowNum() + 1;
			System.out.println("Row Count :- " + rowCount);

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void getDiesel(){
		for(int i =1; i <=500; i++)
		{
			
		
		try{
			driver.get(carurl);
			
			row = modelsheet.getRow(i);
			
			List<String> arrName = new ArrayList<String>();
	        for(int j=0; j<24; j++){
	        	
	        	
	            // Create an object reference of 'Cell' class
	            Cell cell = row.getCell(j);
	            CellType modelcell = cell.getCellTypeEnum();
	            
	            if(modelcell == CellType.STRING){
	            	arrName.add(modelsheet.getRow(i).getCell(j).getRichStringCellValue().toString());
	            }else if(modelcell == CellType.NUMERIC){
	            
	            
	            arrName.add(modelsheet.getRow(i).getCell(j).getRawValue().toString());
	            }
	        }	
	        System.out.println(arrName);
	        System.out.println("Size of the arrayList: "+arrName.size());
	        // Create an iterator to iterate through the arrayList- 'arrName'
	      /*  Iterator<String> itr = arrName.iterator();
	        while(itr.hasNext()){
	            System.out.println("arrayList values: "+itr.next());
	        }*/
	        
	        String make = arrName.get(20);
			System.out.println("the make is " + make);
			
			
			
			Thread.sleep(10000);
		pom.AckoelementsTW.selectbike(driver).click();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.visibilityOf(pom.AckoelementsTW.selectbikeinput(driver)));
		pom.AckoelementsTW.selectbikeinput(driver).sendKeys(make);
		Thread.sleep(7000);
		System.out.println("the bike name is " + pom.AckoelementsTW.selectbikeoption(driver, 1).getText());
		pom.AckoelementsTW.selectbikeoption(driver, 1).click();
		Thread.sleep(2000);
		pom.AckoelementsTW.boughtyear(driver).click();
		
		String Year = arrName.get(7);
		System.out.println("the Year is " + Year);
		
		pom.AckoelementsTW.yearselect(driver, Year).click();
		Thread.sleep(2000);
		pom.AckoelementsTW.ISexpired(driver).click();
		
		pom.AckoelementsTW.expiry(driver, "Expired").click();
		
		pom.AckoelementsTW.viewprices(driver).click();
		
		Thread.sleep(4000);
		
		String defaultidvvalue = pom.AckoelementsTW.defaultidv(driver).getText();
		
		
		defaultidvvalue = defaultidvvalue.replaceAll("[^\\d.]", "");
		
		int dv = Integer.parseInt(defaultidvvalue);
		defaultidvvalue = String.valueOf(dv);
		System.out.println("The default idv value is " + defaultidvvalue);
		String threeyearvalue = pom.AckoelementsTW.threeyearplan(driver).getText();
		
		threeyearvalue = threeyearvalue.replaceAll("[^\\d.]", "");
		
		int tv = Integer.parseInt(threeyearvalue);
		threeyearvalue = String.valueOf(tv);
		System.out.println("The 3 value is " + threeyearvalue);
		
		String twoyearvalue = pom.AckoelementsTW.twoyearplan(driver).getText();
		twoyearvalue = twoyearvalue.replaceAll("[^\\d.]", "");
		
		int tov = Integer.parseInt(twoyearvalue);
		twoyearvalue = String.valueOf(tov);
		System.out.println("The 2 value is " + twoyearvalue);
		
		String oneyearvalue = pom.AckoelementsTW.oneyearplan(driver).getText();
		oneyearvalue = oneyearvalue.replaceAll("[^\\d.]", "");
		
		int ov = Integer.parseInt(oneyearvalue);
		oneyearvalue = String.valueOf(ov);
		System.out.println("The 1 value is " + oneyearvalue);
		
		arrName.add(defaultidvvalue);
		arrName.add(threeyearvalue);
		arrName.add(twoyearvalue);
		arrName.add(oneyearvalue);
		Thread.sleep(10000);
		int x = 10;
		//driver.get("https://www.acko.com/bike/quote/?quote_id=74WmMIzdY1dDzszTC_Ik8g&pf=d");
		int width = pom.AckoelementsTW.slideridv(driver).getSize().getWidth();
		   Actions act= new Actions(driver);
		   act.moveToElement(pom.AckoelementsTW.slideridv(driver), ((width*x)/100), 0).click();
		   act.build().perform();
		
		Thread.sleep(8000);
		String lowidval = pom.AckoelementsTW.defaultidv(driver).getText();
		lowidval = lowidval.replaceAll("[^\\d.]", "");
		
		int dv1 = Integer.parseInt(lowidval);
		lowidval = String.valueOf(dv1);
		System.out.println("The default lowidv value is " + lowidval);
		
		String threeyearvalue2 = pom.AckoelementsTW.threeyearplan(driver).getText();

		threeyearvalue2 = threeyearvalue2.replaceAll("[^\\d.]", "");
		
		int tv2 = Integer.parseInt(threeyearvalue2);
		threeyearvalue2 = String.valueOf(tv2);
		System.out.println("The low3 value is " + threeyearvalue2);
		
		String twoyearvalue2 = pom.AckoelementsTW.twoyearplan(driver).getText();
		twoyearvalue2 = twoyearvalue2.replaceAll("[^\\d.]", "");
		
		int tov2 = Integer.parseInt(twoyearvalue2);
		twoyearvalue = String.valueOf(tov2);
		System.out.println("The low2 value is " + twoyearvalue2);
		
		String oneyearvalue2 = pom.AckoelementsTW.oneyearplan(driver).getText();
		
		
		oneyearvalue2 = oneyearvalue2.replaceAll("[^\\d.]", "");
		
		int ov2 = Integer.parseInt(oneyearvalue2);
		oneyearvalue = String.valueOf(ov2);
		System.out.println("The low1 value is " + oneyearvalue2);
		arrName.add(lowidval);
		arrName.add(threeyearvalue2);
		arrName.add(twoyearvalue2);
		arrName.add(oneyearvalue2);
		
		SetInputData(excelpath_update, sheetname, excelrow,arrName);
		excelrow++;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	}

	public static void SetInputData(String filePath, String sheetName, int row, List<String> data) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet inputSheet = workbook.getSheetAt(0);

		// Retrieve the row and check for null
		XSSFRow row0 = (XSSFRow) inputSheet.getRow(row);
		Cell cell = null;
		if (row0 == null) {
			row0 = (XSSFRow) inputSheet.createRow(row);
		}
		
		Row row1 = inputSheet.createRow(0);

		row1.createCell(0).setCellValue("ProductID");
		row1.createCell(1).setCellValue("LeadID");
		row1.createCell(2).setCellValue("EnquiryId");
		row1.createCell(3).setCellValue("LeadCreatedOn");
		row1.createCell(4).setCellValue("MakeName");
		row1.createCell(5).setCellValue("ModelName");
		row1.createCell(6).setCellValue("Variantname");
		row1.createCell(7).setCellValue("RegistrationYear");
		row1.createCell(8).setCellValue("BookedLead_IDV");
		row1.createCell(9).setCellValue("Booking Lead Total Premium");
		row1.createCell(10).setCellValue("TotalOwnDamagePremium");
		row1.createCell(11).setCellValue("FinalTotalLiabilityPremium");
		row1.createCell(12).setCellValue("TotalAddOnPremium");
		row1.createCell(13).setCellValue("1st Rank Insurer (Comp)");
		row1.createCell(14).setCellValue("1st Rank Plan (Comp)");
		row1.createCell(15).setCellValue("1st Rank IDV (Comp)");
		row1.createCell(16).setCellValue("1st Rank Total Premium (Comp)");
		row1.createCell(17).setCellValue("1stRank Own Damage Premium (Comp)");
		row1.createCell(18).setCellValue("1stRank Total Liability Premium (Comp)");
		row1.createCell(19).setCellValue("Acko Make");
		row1.createCell(20).setCellValue("Acko Model");
		row1.createCell(21).setCellValue("MakeId");
		row1.createCell(22).setCellValue("ModelId");
		row1.createCell(23).setCellValue("VariantId");
		row1.createCell(24).setCellValue("Default IDV");
		row1.createCell(25).setCellValue("3 Year Plan");
		row1.createCell(26).setCellValue("2 Year Plan");
		row1.createCell(27).setCellValue("1 Year Plan");
		row1.createCell(28).setCellValue("Low IDV");
		row1.createCell(29).setCellValue("3 Year Plan");
		row1.createCell(30).setCellValue("2 Year Plan");
		row1.createCell(31).setCellValue("1 Year Plan");
		
		
	
		// Update the value of cell
		for(int i=0;i<data.size();i++){
		cell = row0.getCell(i);
		if (cell == null) {
			cell = row0.createCell(i);
		}
		cell.setCellValue(data.get(i));
		}
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		workbook.close();
	}
	
	
	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File
			XSSFWorkbook wb = new XSSFWorkbook();
			
			XSSFSheet sh = wb.createSheet(sheetName);
			
			 sh = wb.getSheet(sheetName);

			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
            fileOut.close();
            wb.close();
            System.out.println("Your excel file has been generated!");

		} catch (Exception e) {
			throw (e);
		}
		
	}



}
