package com.automation.tests.UserRegistrationTests;

import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.demoqa.pages.UserRegistrationPages.LandingPage;
import com.automation.demoqa.pages.UserRegistrationPages.RegistrationFormPage;
import com.automation.demoqa.pages.UserRegistrationPages.TransitionToNextPage;
import com.automation.demoqa.utils.WebPageActions;
import com.automation.demoqa.utils.DataProviderUtils;

public class VerifyUserRegistrationTests {

	@BeforeTest
	public void initBrowser() {

		System.out.println("Test Execution Started on: " + new Date());
	}

	@Test(priority = 1)
	public void LandOnWebsite() {

		LandingPage lndPageObj = new LandingPage();

		lndPageObj.launchWebSite();
	}

	@Test(priority = 2, dataProvider = "basicDataProvider", dataProviderClass = DataProviderUtils.class)
	public void VerifyUserRegistration(String firstName, String lastName, String address, String email, String phoneNo,
			String skills, String country, String countryCombo, String year, String month, String day, String password,
			String confirmpassword) throws Exception {

		RegistrationFormPage regPageObj = new RegistrationFormPage();
		System.out.println(firstName + " "+lastName+" "+ address+" "+ email+""+ phoneNo+""+ skills+""+ country+""+ countryCombo+""+ year+""+ month+""+
				day+ " "+ password+" "+ confirmpassword);
		/*
		 * regPageObj.enterFirstName(firstName);
		 * 
		 * regPageObj.enterLastName(lastName);
		 * 
		 * regPageObj.enterAddress(address);
		 * 
		 * regPageObj.enterEmail(email);
		 * 
		 * regPageObj.uploadPhoto();
		 * 
		 * regPageObj.enterMobileNo(phoneNo);
		 * 
		 * regPageObj.selectGender();
		 * 
		 * regPageObj.selectHobbies();
		 * 
		 * regPageObj.selectLanguage();
		 * 
		 * regPageObj.selectSkills(skills);
		 * 
		 * regPageObj.selectCountry(country);
		 * 
		 * regPageObj.selectCountryComboBox(countryCombo);
		 * 
		 * regPageObj.selectDateOfBirth(year, month, day);
		 * 
		 * regPageObj.enterPassword(password);
		 * 
		 * regPageObj.enterConfirmPassword(confirmpassword);
		 * 
		 * regPageObj.clickSubmit();
		 * 
		 * WebPageActions.WaitABit();
		 */
	}

	@Test(priority = 3)
	public void LandOnNextPage() {

		TransitionToNextPage nextPageObj = new TransitionToNextPage();

		nextPageObj.verifyTransitionToNextPage();
	}

	@AfterTest
	public void tearDown() {

		WebPageActions.closeBrowser();

		System.out.println("Test Execution Finished on: " + new Date());
	}

}
