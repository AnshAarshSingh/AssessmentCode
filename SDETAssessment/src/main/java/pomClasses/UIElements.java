package pomClasses;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class UIElements extends TestBase {

	// Page Elements
	@FindBy(how = How.XPATH, using = "//*[@id='dynamictable']/caption")
	WebElement dynamicTableTitle;

	@FindBy(how = How.XPATH, using = "//*[@id='dynamictable']")
	WebElement dynamicTable;

	@FindBy(how = How.XPATH, using = "//*[@id='dynamictable']")
	WebElement table;

	@FindBy(how = How.XPATH, using = "//*[@id='jsondata']")
	WebElement jsonDataField;

	@FindBy(how = How.XPATH, using = "//*[@id='refreshtable']")
	WebElement refreshButton;

	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div[3]/details")
	WebElement tableDataAccess;

	@FindBy(how = How.XPATH, using = "/html/body/div[1]/h1")
	WebElement header;

	//
	public UIElements() {
		PageFactory.initElements(driver, this);
	}

	// extract JSON list in string
	public String extractJSONinString(JSONArray usersList) {
		try {
			return usersList.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// load JSON & return
	public JSONArray loadJSON() {
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader("details.json");

			// Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray usersList = (JSONArray) obj;

			return usersList;

			// inputData(usersList.toString());
			// clickRefresh();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// read name from JSON file
	public String readNameValues(JSONArray usersList, int i) {
		JSONObject users = (JSONObject) usersList.get(i);
		String name = users.get("name").toString();
		System.out.println("name: " + name);

		return name;
	}

	// read age from JSON file
	public String readAgeValues(JSONArray usersList, int i) {
		JSONObject users = (JSONObject) usersList.get(i);
		String age = users.get("age").toString();
		System.out.println("Age: " + age);

		return age;
	}

	// send retrieved json data to text field after clearing the default data
	public void inputData(String input) {
		try {
			if (tableDataAccess.isDisplayed()) {
				tableDataAccess.click();
				jsonDataField.clear();
				jsonDataField.click();
				jsonDataField.sendKeys(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Table not displayed");
		}
	}

	// click on refresh table button
	public void clickRefresh() {
		try {
			if (refreshButton.isDisplayed()) {
				refreshButton.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Refresh Button not displayed");
		}
	}

	// read data from table and verify expected vs actual data
	public void readAndVerifyDataFromTable(int n, JSONArray usersList) {
		try {
			if (table.isDisplayed()) {
				int j;
				for (int i = 0; i < n; i++) {
					j=i+2;
					String nameActual = driver.findElement(By.xpath("//*[@id='dynamictable']/tr[" + j + "]/td[1]"))
							.getText();
					String ageActual = driver.findElement(By.xpath("//*[@id='dynamictable']/tr[" + j + "]/td[2]"))
							.getText();

					String nameExpected = readNameValues(usersList, i);
					String ageExpected = readAgeValues(usersList, i);

					
					matchValue(nameExpected, nameActual);
					matchValue(ageExpected, ageActual);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Table not displayed");
		}
	}
	

	//verify expected vs actual
	public void matchValue(String expectedValue, String actualValue) {
		if (expectedValue.equalsIgnoreCase(actualValue)) {
			System.out.println("Testcase passed");
			System.out.println("Expected Value: " + expectedValue);
			System.out.println("Actual Value: " + actualValue);
		}

		else {
			System.out.println("Testcase failed");
			System.out.println("Expected Value: " + expectedValue);
			System.out.println("Actual Value: " + actualValue);
			
		}
	}

}
