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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import pom.AckoelementsTW;

public class GetVariants {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/bike/?pf=d";
	String excelpath = "C:\\Excelfiles\\Acko_TW\\makename.xlsx";
	String excelpath_update = "C:\\Excelfiles\\Acko_TW\\dataentry.xlsx";
	String sheetname = "Base Template";

	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	String resultdata[] = new String[14];
	
	String makemodelv = "";
	
	String nodata ="No Data Found";


	@BeforeMethod

	public void setup() {

		try {	
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, 10);
			
			FileInputStream fis = new FileInputStream(excelpath);
			workbook = new XSSFWorkbook(fis);
			modelsheet = workbook.getSheetAt(3);

			
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
		
		try{
			for(int k = 1;k<=rowCount;k++){
			driver.get(carurl);
		row = modelsheet.getRow(k);
		String make = row.getCell(0).getStringCellValue();
		
		String model = row.getCell(1).getStringCellValue();
		model = model.substring(0,1).toUpperCase();
		make = make+" "+model;
		System.out.println("the make is " + make);
		pom.AckoelementsTW.selectbike(driver).click();
		Thread.sleep(4000);
		pom.AckoelementsTW.selectbikeinput(driver).sendKeys(make);
		Thread.sleep(2000);
		int size = pom.AckoelementsTW.variants(driver).size();
		System.out.println("size of models is " + size);
		for(int j=1;j<=size;j++){
			
			makemodelv=pom.AckoelementsTW.selectbikeoption(driver, j).getText();
			Thread.sleep(2000);
			System.out.println("The make model variant is " + makemodelv);
			resultdata[0]=make;
			resultdata[1]=makemodelv;
			SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
			excelrow++;
		}
			}
			
			
		/*pom.Ackoelements.boughtyear(driver).click();
		
		pom.Ackoelements.yearselect(driver, "2018").click();
		Thread.sleep(2000);
		pom.Ackoelements.ISexpired(driver).click();
		
		pom.Ackoelements.expiry(driver, "Expired").click();
		
		pom.Ackoelements.viewprices(driver).click();
		
		Thread.sleep(4000);
		
		String defaultidvvalue = pom.Ackoelements.defaultidv(driver).getText();
		System.out.println("The default idv value is " + defaultidvvalue);
		
		String threeyearvalue = pom.Ackoelements.threeyearplan(driver).getText();
		System.out.println("The 3 value is " + threeyearvalue);
		
		String twoyearvalue = pom.Ackoelements.twoyearplan(driver).getText();
		System.out.println("The 2 value is " + twoyearvalue);
		
		String oneyearvalue = pom.Ackoelements.oneyearplan(driver).getText();
		System.out.println("The 1 value is " + oneyearvalue);
		int x = 10;
		//driver.get("https://www.acko.com/bike/quote/?quote_id=74WmMIzdY1dDzszTC_Ik8g&pf=d");
		int width = pom.Ackoelements.slideridv(driver).getSize().getWidth();
		   Actions act= new Actions(driver);
		   act.moveToElement(pom.Ackoelements.slideridv(driver), ((width*x)/100), 0).click();
		   act.build().perform();
		
		Thread.sleep(4000);
		String lowidval = pom.Ackoelements.defaultidv(driver).getText();
		System.out.println("The default lowidv value is " + lowidval);
		
		String threeyearvalue2 = pom.Ackoelements.threeyearplan(driver).getText();
		System.out.println("The low3 value is " + threeyearvalue2);
		
		String twoyearvalue2 = pom.Ackoelements.twoyearplan(driver).getText();
		System.out.println("The low2 value is " + twoyearvalue2);
		
		String oneyearvalue2 = pom.Ackoelements.oneyearplan(driver).getText();
		System.out.println("The low1 value is " + oneyearvalue2);*/
		
		}catch(Exception e){
			e.printStackTrace();
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

		row1.createCell(0).setCellValue("LeadID");
		row1.createCell(1).setCellValue("MakeName");
		row1.createCell(2).setCellValue("ModelName");
		row1.createCell(3).setCellValue("Variantname");
		row1.createCell(4).setCellValue("RegistrationYear");
		row1.createCell(5).setCellValue("NCB");
		row1.createCell(6).setCellValue("FuleType");
		row1.createCell(7).setCellValue("RegistrationPostCode");
		row1.createCell(8).setCellValue("RegisteredCityName");
		row1.createCell(9).setCellValue("RegisteredStateName");
		row1.createCell(10).setCellValue("Zone");
		row1.createCell(11).setCellValue("City Tier");
		row1.createCell(12).setCellValue("CoverTypedetail");
		row1.createCell(13).setCellValue("PlanAddOns");
		row1.createCell(14).setCellValue("BookedLead_IDV");
		row1.createCell(15).setCellValue("Booking Lead Total Premium");
		row1.createCell(16).setCellValue("TotalOwnDamagePremium");
		row1.createCell(17).setCellValue("FinalTotalLiabilityPremium");
		row1.createCell(18).setCellValue("TotalAddOnPremium");
		row1.createCell(19).setCellValue("OD Discount %");
		row1.createCell(20).setCellValue("1st Rank Insurer (Comp)");
		row1.createCell(21).setCellValue("1st Rank IDV (Comp)");
		row1.createCell(22).setCellValue("1st Rank Total Premium (Comp)");
		row1.createCell(23).setCellValue("1stRank Own Damage Premium (Comp)");
		row1.createCell(24).setCellValue("1stRank Total Liability Premium (Comp)");
		row1.createCell(25).setCellValue("OD Discount %(Comp)");
		row1.createCell(26).setCellValue("Acko Make");
		row1.createCell(27).setCellValue("Acko Model");
		row1.createCell(28).setCellValue("Acko Variant");
		row1.createCell(29).setCellValue("Premium");
		row1.createCell(30).setCellValue("IDV");
		row1.createCell(31).setCellValue("Base Value");
		row1.createCell(32).setCellValue("Zero Dep");
		row1.createCell(33).setCellValue("PB/Acko");
		
		
	
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

	public static void SetCellData1(String filePath, String sheetName, String[] result, int row) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(filePath);

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFile);

		XSSFSheet resultSheet = wb.getSheet(sheetName);

		System.out.println("Row Passed : " + row);

		if (row == 1) {
			Row row0 = resultSheet.createRow(0);

			row0.createCell(0).setCellValue("S.No.");
			row0.createCell(1).setCellValue("Make");
			row0.createCell(2).setCellValue("Model");
			row0.createCell(3).setCellValue("Sub Model");
			row0.createCell(4).setCellValue("Fuel");
			row0.createCell(5).setCellValue("Pin Code");
			row0.createCell(6).setCellValue("Age");
			row0.createCell(7).setCellValue("Claim");
			row0.createCell(8).setCellValue("Premium");
			row0.createCell(9).setCellValue("IDV");
			row0.createCell(10).setCellValue("Base Value");
			row0.createCell(11).setCellValue("Date");
			row0.createCell(12).setCellValue("Time");

		}
		Row row2 = resultSheet.createRow(row);
		row2.createCell(0).setCellValue(row);
		System.out.println("Row Created :" + (row));
		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			row2.createCell(i + 1).setCellValue(result[i]);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		wb.close();

	}


}
