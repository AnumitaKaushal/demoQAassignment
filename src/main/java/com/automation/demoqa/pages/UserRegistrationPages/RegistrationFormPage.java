package com.automation.demoqa.pages.UserRegistrationPages;

import org.openqa.selenium.WebElement;
import com.automation.demoqa.utils.WebPageActions;


public class RegistrationFormPage extends WebPageActions {

	public RegistrationFormPage() {
		
		super(RegistrationFormPage.class.getSimpleName());
		
	}

	public void enterFirstName(String firstName) throws Exception {

		EnterValueInTextField("tbx_firstname", firstName);

	}

	public void enterLastName(String lastName) throws Exception {
		
		EnterValueInTextField("tbx_lastname", lastName);
	}

	public void enterAddress(String address) throws Exception {
		
		EnterValueInTextField("tbx_address", address);
	}

	public void enterEmail(String email) throws Exception {
		
		EnterValueInTextField("tbx_email", email);
	}
	
	public void uploadPhoto() throws Exception {
		try {
			HoverToElementAndClick("btn_upload");
			uploadFile("\\src\\test\\resources\\FilesToUpload\\logo.png");
			System.out.println("File uploaded successfully");
		} catch (Exception e) {
			System.out.println("Unable to upload file");
			e.printStackTrace();
		}

	}
	public void enterMobileNo(String phoneNo) throws Exception {
		
		EnterValueInTextField("tbx_phoneno", phoneNo);
		
	}

	public void selectGender() throws Exception {

		try {
			HoverToElementAndClick("rdb_gender");			
			System.out.println("Gender is selected");
			GetAttributeForElement("rdb_gender", "value");						
			
		} catch (Exception e) {
			
			System.out.println("Gender is not selected");
		}

	}

	public void selectHobbies() throws Exception {

		try {
			WebElement hobbies = HoverToElementAndClick("chx_hobbies");
			
			if (hobbies.isSelected() == true) {
				
				System.out.println("Hobby is selected");
				GetAttributeForElement("chx_hobbies", "value");				
				
			} else {
				
				System.out.println("Hobby is not selected");
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();

		}

	}

	public void selectLanguage() throws Exception {

		ClickOnElement("ddb_language");
		WaitABit();
		
		String language = GetAttributeForElement("set_language", "innerHTML");
		
		HoverToElementAndClick("set_language");
		System.out.println("Language selected is [" + language + "]");
		
		ClickOnElement("clk_outside");

	}

	public void selectSkills(String skills) throws Exception {

		SelectFromDropDownByValue(skills, "ddb_skills");

	}

	public void selectCountry(String country) throws Exception {

		SelectFromDropDownByVisibleText(country, "ddb_country");

	}

	public void selectCountryComboBox(String countryCombo) throws Exception {

		SelectFromDropDownByVisibleText(countryCombo, "cbx_country");

	}

	public void selectDateOfBirth(String year, String month, String day) throws Exception {

		SelectFromDropDownByValue(year, "ddb_dobyear");
		SelectFromDropDownByVisibleText(month, "ddb_dobmonth");
		SelectFromDropDownByValue(day, "ddb_dobday");
	}

	public void enterPassword(String password) throws Exception {
		
		showEncryptedValue(password, "tbx_password");
		
	}

	public void enterConfirmPassword(String confirmpassword) throws Exception {
		
		showEncryptedValue(confirmpassword, "tbx_confirmPassword");
		
	}



	public void clickSubmit() throws Exception {
		
		System.out.println("Registration Page is complete");
		ClickOnElement("btn_submit");
		System.out.println("User Registration is successful");
	}

}
