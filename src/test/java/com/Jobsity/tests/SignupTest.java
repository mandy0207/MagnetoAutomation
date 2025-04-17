package com.Jobsity.tests;

import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Jobsity.models.MagnetoUser.MagnetoUser;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class SignupTest extends WebBaseTest {

	@Test(dataProvider="getData")
	public void verifySignupFormErrorMsgs(LinkedHashMap<String, MagnetoUser>  map) {
		LaunchMagneto();
		webHomePage.navigateToSignupPage();
		map.forEach((key, value)->{
			String actualError=webAccountUserPage.getErrorMsg(value);
			System.out.println("Actual Error: "+actualError);
			webHomePage.verifyEquals(actualError, value.errorMsg, "Verified that "+key+" is displayinng correct error Msg", key +" failed");
		});
		
	}
	
	
	
	@DataProvider
	public static Object[][] getData() {

		FixtureFactoryLoader.loadTemplates("com.Jobsity");
		MagnetoUser emptyFirstName = Fixture.from(MagnetoUser.class).gimme("emptyFirstName");
		MagnetoUser emptyLastName = Fixture.from(MagnetoUser.class).gimme("emptyLastName");
		MagnetoUser emptyEmail = Fixture.from(MagnetoUser.class).gimme("emptyEmail");
		MagnetoUser invalidEmail = Fixture.from(MagnetoUser.class).gimme("invalidEmail");
		MagnetoUser emptyPassword = Fixture.from(MagnetoUser.class).gimme("emptyPassword");
		MagnetoUser emptyConfirmPassword = Fixture.from(MagnetoUser.class).gimme("emptyConfirmPassword");
		MagnetoUser minimumLengthPassword = Fixture.from(MagnetoUser.class).gimme("minimumLengthPassword");
		MagnetoUser alphaNumericPassword = Fixture.from(MagnetoUser.class).gimme("alphaNumericPassword");
		

		LinkedHashMap<String, MagnetoUser> userMap = new LinkedHashMap<>();
		userMap.put("empty-FirstName ", emptyFirstName);
		userMap.put("empty-LastName", emptyLastName);
		userMap.put("empty-Email", emptyEmail);
		userMap.put("invalid-Email", invalidEmail);
		userMap.put("empty-Password", emptyPassword);
		userMap.put("empty-ConfirmPassword",emptyConfirmPassword);
		userMap.put("minimum-LengthPassword", minimumLengthPassword);
		userMap.put("alphaNumeric-Password", alphaNumericPassword);
		


		return new Object[][] { { userMap } };
	}
	
}
