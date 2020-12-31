package com.automation.demoqa.pages.UserRegistrationPages;

import com.automation.demoqa.utils.WebPageActions;

public class LandingPage extends WebPageActions {

	public LandingPage() {
		
		super(LandingPage.class.getSimpleName());
		
	}

	public void launchWebSite() {
		
		launchUrl(URL);
		
	}

}
