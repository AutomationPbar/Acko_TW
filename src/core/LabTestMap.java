package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.DBManager;

public class LabTestMap {
	
	
	private WebDriver driver;
	WebDriverWait wait;


	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	String tableName = "Automation.Practo1MG";
	
	String baseUrl = "https://www.1mg.com/labs?utm_source=1mg&utm_medium=jewel&utm_campaign=labsgrowth";
	
	String excelpath = "C:\\docprime\\LabTestsScraper.xlsx";
	String excelpath_update = "C:\\docprime\\datamap.xlsx";
	String sheetname = "Base Template";
	
	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;

	DBManager dbm = new DBManager();
	
	String resultdata[] = new String[14];

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C://eclipse//chromedriver.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);

		wait = new WebDriverWait(driver, 10);

		//dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
		
		FileInputStream fis = new FileInputStream(excelpath);
		workbook = new XSSFWorkbook(fis);
		modelsheet = workbook.getSheetAt(0);

		
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		Date datedd = new Date();
		System.out.println(formatter.format(datedd));
		String localDate11 = formatter.format(datedd).toString();
		excelpath_update = "C:\\docprime\\datamap" + localDate11 + ".xlsx";
		SetExcelFile(excelpath_update, sheetname);

		row = modelsheet.getRow(0);

		int colCount = row.getLastCellNum();
		System.out.println("Column Count :- " + colCount);

		rowCount = modelsheet.getLastRowNum() + 1;
		System.out.println("Row Count :- " + rowCount);
	}
	
	
	@Test 
	public void gettestnames(){
		
		
		

		try {
			driver.get(baseUrl);
			driver.manage().window().maximize();
			
			Thread.sleep(5000);
			
			for(int i = 1; i<=20;i++){
				
				try{
				
					driver.navigate().refresh();
					
					Thread.sleep(6000);
					
					row = modelsheet.getRow(i);
			        // Create an object reference of 'Cell' class
						Cell cell1=  row.getCell(0);
			            Cell cell = row.getCell(1);
			            CellType modelcell = cell1.getCellTypeEnum();
			            String docid= null;
			            
			            
			           if(modelcell == CellType.NUMERIC){
			            
			           
			            docid=modelsheet.getRow(i).getCell(0).getRawValue().toString();
			            System.out.println("doc id is " + docid);
			            }
			            String testname = cell.getStringCellValue();
			            
			            System.out.println("the test name is " + testname);
			            
			            String newtestname = testname.substring(0, testname.indexOf(' '));
			            
			            System.out.println("the new test name is " + newtestname);
			            
			            pom.Labs.searchbox(driver).click();
			            
			            Thread.sleep(3000);
				 
			            pom.Labs.searchbox(driver).sendKeys(testname);
			            
			            Thread.sleep(10000);
			            String mgtestname = null;
			            mgtestname= pom.Labs.firsttest(driver).getText();
			            
			            
			            System.out.println("1 mg testname is " + mgtestname);
			            
			            resultdata[0] = docid;
						resultdata[1] = testname;
						resultdata[2] = mgtestname;
			            
			            SetInputData(excelpath_update, sheetname, excelrow,resultdata);
						excelrow++;
				}catch(Exception e){
					Alert alert = null;
					try{
					alert = driver.switchTo().alert();
					if (alert != null) {
					 System.out.println("Alert present");
						alert.dismiss();
					}
					}catch(Exception ex){
						//ex.printStackTrace();
					}
				}
			}
			
			
			
		} catch (Exception e) {
			
			
			e.printStackTrace();
		}

		
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
	
	
	public static void SetInputData(String filePath, String sheetName, int row, String[] result) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet inputSheet = workbook.getSheetAt(0);

		// Retrieve the row and check for null
		XSSFRow row0 = (XSSFRow) inputSheet.getRow(row);
		Cell cell = null;
	
		
		if (row == 1) {
			Row row1 = inputSheet.createRow(0);


		row1.createCell(0).setCellValue("DocprimeID");
		row1.createCell(1).setCellValue("Docprime Name");
		row1.createCell(2).setCellValue("1 MG Name");
		
		}

		// Update the value of cell
		Row row2 = inputSheet.createRow(row);
		
		System.out.println("Row Created :" + (row));
		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			row2.createCell(i).setCellValue(result[i]);

		}

	
		
		
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		workbook.close();
	}
	


}
