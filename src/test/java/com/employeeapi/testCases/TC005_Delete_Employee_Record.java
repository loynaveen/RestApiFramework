package com.employeeapi.testCases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC005_Delete_Employee_Record extends TestBase {

	RequestSpecification httpRequest;
	Response response;

	@BeforeClass
	void deleteEmploye() throws InterruptedException {
		logger.info("************* Started TC005_Delete_Employee_Record ************");

		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();

		response = httpRequest.request(Method.GET, "/employees");

		JsonPath jsonPathEvaluator = response.jsonPath();

		// Map<String, String> map = jsonPathEvaluator.getMap("data");

		String empID = jsonPathEvaluator.get("[0].id");
		response = httpRequest.request(Method.DELETE, "/delete/" + empID);

		Thread.sleep(5000);
	}

	@Test
	void checkResponseBody() {
		logger.info("************* Checking Response Body ************");

		String responseBody = response.getBody().asString();
		logger.info("Response Body==>" + responseBody);
		Assert.assertEquals(responseBody.contains("successfully! deleted Records"), true);
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
		logger.info("************* Finished TC004_Put_Employee_Record ************");

	}

}
