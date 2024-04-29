package com.sample;

import static io.restassured.RestAssured.*;

import com.sample.listeners.TestListener;
import com.sample.base.Base;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import org.testng.asserts.SoftAssert;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.HashMap;

import com.sample.utils.AutomationTestDataProvider;
import com.sample.utils.CommonUtils;

public class DataDriver extends Base {
//	basic details for acessing the apis
	String baseURL = super.baseURL;


	SoftAssert softAssert;
	public DataDriver() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		softAssert = new SoftAssert();
	}

	@AfterMethod
	public void tearDown() {

	}
//Method to execute the test cases
	@Test(dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void E2ETestCase(ITestContext iTestContext, HashMap<String, String> hashmap) {
		CommonUtils commonUtils = new CommonUtils();
		JSONObject data = new JSONObject();
		String token;
		Response res = null;
		String Execute = hashmap.get("Execute");
		String URI = hashmap.get("URI");
		String requestMethod = hashmap.get("requestMethod");
		String JSONData1 = hashmap.get("JSONData1");
		String JSONData2 = hashmap.get("JSONData2");
		String JSONData3 = hashmap.get("JSONData3");
		String expectedRspCode = hashmap.get("expectedRespCode");

		//Code needs to be extended to handle queryparams in request

		//Data driven approach where the script shall be executed only when 'Yes' has been set in the data driver sheet
		if (Execute.equalsIgnoreCase("Y")||Execute.equalsIgnoreCase("YES"))
		{

			if (JSONData1 != null && !JSONData1.isEmpty() && JSONData1.contains("|")) {
				String[] JSONData1Parts = JSONData1.split("\\|");
				// Access the split parts

				if (commonUtils.isBoolean(JSONData1Parts[1])){
					data.put(JSONData1Parts[0], Boolean.parseBoolean(JSONData1Parts[1]));
				}else if(commonUtils.isInteger(JSONData1Parts[1])){
					data.put(JSONData1Parts[0], Integer.parseInt(JSONData1Parts[1]));
				}else {
					data.put(JSONData1Parts[0], JSONData1Parts[1]);
				}
			}
			if (JSONData2 != null && !JSONData2.isEmpty() && JSONData2.contains("|")) {
				String[] JSONData2Parts = JSONData2.split("\\|");
				// Access the split parts
				if (commonUtils.isBoolean(JSONData2Parts[1])){
					data.put(JSONData2Parts[0], Boolean.parseBoolean(JSONData2Parts[1]));
				}else if(commonUtils.isInteger(JSONData2Parts[1])){
					data.put(JSONData2Parts[0], Integer.parseInt(JSONData2Parts[1]));
				}else {
					data.put(JSONData2Parts[0], JSONData2Parts[1]);
				}

			}
			if (JSONData3 != null && !JSONData3.isEmpty() && JSONData3.contains("|")) {
				String[] JSONData3Parts = JSONData3.split("\\|");
				// Access the split parts
				if (commonUtils.isBoolean(JSONData3Parts[1])){
					data.put(JSONData3Parts[0], Boolean.parseBoolean(JSONData3Parts[1]));
				}else if(commonUtils.isInteger(JSONData3Parts[1])){
					data.put(JSONData3Parts[0], Integer.parseInt(JSONData3Parts[1]));
				}else {
					data.put(JSONData3Parts[0], JSONData3Parts[1]);
				}
			}

			// Check for the html method type
			if (requestMethod.equalsIgnoreCase("POST"))
			{

				if (URI.equalsIgnoreCase("auth/gentoken")) {
					res = given().contentType("application/json").body(data.toString()).when()
							.post(this.baseURL + "auth/gentoken");
				}else {
					res = given().headers("Authorization", iTestContext.getSuite().getAttribute("token").toString()).contentType("application/json")
							.accept("application/json").body(data.toString()).when().post(this.baseURL + URI);
				}
				ResponseBody<?> body = res.getBody();
				System.out.println("Response Body is: " + body.asString());
				if (body.asString().contains("token")) {
					token = res.jsonPath().getString("token");
					iTestContext.getSuite().setAttribute("token", token);
				}
				int statCode = res.getStatusCode();
				System.out.println("Status code for accessToken: " + statCode);
				Assert.assertEquals(String.valueOf(statCode), expectedRspCode);




			} else if (requestMethod.equalsIgnoreCase("PUT")) {
				res = given().headers("Authorization", iTestContext.getSuite().getAttribute("token").toString()).contentType("application/json")
						.accept("application/json").body(data.toString()).when().put(this.baseURL + URI);

			ResponseBody<?> body = res.getBody();
			System.out.println("Response Body is: " + body.asString());
			if (body.asString().contains("token")) {
				token = res.jsonPath().getString("token");
				iTestContext.getSuite().setAttribute("token", token);
			}
			int statCode = res.getStatusCode();
			System.out.println("Status code for accessToken: " + statCode);
			Assert.assertEquals(String.valueOf(statCode), expectedRspCode);

			} else if (requestMethod.equalsIgnoreCase("GET")) {

			}
		}

	}
}
