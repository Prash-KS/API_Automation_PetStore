package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class UserTests {
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String userName, String firstName, String lastName, String userEmail,
			String password, String phone) {

		logger.info("********** Creating user  ***************");
		
		User userPayload = new User();

		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);

		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("**********User is creatged  ***************");
	}

	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testGetUserByName(String userName) {
		
		logger.info("********** Reading User Info ***************");

		Response response = UserEndPoints.readUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("**********User info  is displayed ***************");
	}

	@Test(priority = 3, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testUpdateUserByName(String userID, String userName, String firstName, String lastName,
			String userEmail, String password, String phone) {
		
		logger.info("********** Updating User ***************");

		User userPayload = new User();

		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);

		Response response = UserEndPoints.updateUser(userName, userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********** User updated ***************");
	}

	@Test(priority = 4, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {
		
		logger.info("**********   Deleting User  ***************");
		
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********** User deleted ***************");
	}

}
