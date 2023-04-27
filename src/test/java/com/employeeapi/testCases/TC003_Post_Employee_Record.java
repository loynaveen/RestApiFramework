package com.employeeapi.testCases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC003_Post_Employee_Record extends TestBase {

	RequestSpecification httpRequest;
	Response response;

	String empName = RestUtils.empName();
	String empSalary = RestUtils.empSal();
	String empAge = RestUtils.empAge();

	@BeforeClass
	void createEmployee() throws InterruptedException {
		logger.info("************* Started TC003_Post_Employee_Record ************");

		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("salary", empSalary);
		requestParams.put("age", empAge);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/create");

		Thread.sleep(5000);
	}

	@Test
	void checkResponseBody() {
		logger.info("************* Checking Response Body ************");

		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(empSalary), true);
		Assert.assertEquals(responseBody.contains(empAge), true);
	}

	@Test
	void checkStatusCode() {
		logger.info("************* Checking Status Code ************");

		int statusCode = response.getStatusCode();
		logger.info("Status Code is ==>" + statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	void checkResponseTime() {
		logger.info("************* Checking Response Time ************");

		long responseTime = response.getTime();
		logger.info("Response Time is ==>" + responseTime);
		if (responseTime > 6000)
			logger.warn("Response Time is greater than 2000");

		Assert.assertTrue(responseTime < 6000);

	}

	@Test
	void checkStatusLine() {
		logger.info("************* Checking Status Line ************");
		String statusLine = response.getStatusLine();
		logger.info("Status Line is ==>" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test
	void checkContentType() {
		logger.info("************* Checking Content Type ************");

		String contentType = response.getContentType();
		logger.info("Content Type is ==>" + contentType);
		Assert.assertEquals(contentType, "application/json");
	}

	@Test
	void checkServerType() {
		logger.info("************* Checking Server Type ************");
		String serverType = response.header("Server");
		logger.info("Server Type is ==>" + serverType);
		Assert.assertEquals(serverType, "Apache");

	}

	@Test
	void checkContentLength() {
		logger.info("************* Checking Content Length ************");
		String contentLength = response.header("Content-Length");
		logger.info("Content Length is ==>" + contentLength);

		if (Integer.parseInt(contentLength) < 200)
			logger.warn("Content Length is less than 200");
		Assert.assertTrue(Integer.parseInt(contentLength) < 200);
	}

	@AfterClass
	void tearDown() {
		logger.info("************* Finished TC003_Post_Employee_Record ************");

	}

}
