package com.automation.demoqa.pages.UserRegistrationPages;

import com.automation.demoqa.utils.WebPageActions;

public class TransitionToNextPage extends WebPageActions{

	public TransitionToNextPage() {
		
		super(TransitionToNextPage.class.getSimpleName());
		
	}

	public void verifyTransitionToNextPage() {

		String actualTitle = GetTitle();
		String expectedTitle = "Web Table";
		// Assert.assertEquals(actualTitle, expectedTitle);
		
		if (actualTitle.contains(expectedTitle)) {
			
			System.out.println("Successfully landed on next page [" + actualTitle + "]");
			
		} else {
			
			System.out.println("Could not land on next page");
			
		}

	}

	

}
