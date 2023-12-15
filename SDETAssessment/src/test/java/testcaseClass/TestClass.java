package testcaseClass;

import org.json.simple.JSONArray;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.base.TestBase;
import pomClasses.UIElements;

public class TestClass extends TestBase {
	
	//object for UI POM class
	UIElements ui;

	// initializing the class with properties of the superclass i.e. base class
	public TestClass() {
		super();
	}
	
	//calling test pre-condition
	@BeforeTest
	public void setUp() {
		preCondition();
		ui= new UIElements();
		//setSychronisation(driver);
	}

	//testcase
	@Test
	public void testcase() {

		try {
			
			//loading json data
			JSONArray jsonList=ui.loadJSON();
			
			//checking json length
			int n = jsonList.size();
			
			Wait(1000);
			
			//inputting data in text field on UI
			ui.inputData(jsonList.toString());
			Wait(1000);
			
			//loading data to table using refresh click
			ui.clickRefresh();
			Wait(1000);
			
			//verifying data matches or not
			ui.readAndVerifyDataFromTable(n,jsonList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//implementing teardown to end test and close driver
	@AfterSuite
	public void endTest() {
		tearDown();
	}
}
