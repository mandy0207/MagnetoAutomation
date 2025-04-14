package com.Jobsity.tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.Jobsity.enums.Locators;
import com.Jobsity.models.Address.OrderReturn;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

public class OrderReturnTest extends WebBaseTest {

    @Test
    public void verifyOrderReturnFormMsgs() {
        LaunchMagneto();
        webHomePage.navigateToURL(config.orderReturnPage());

        getAllCases().forEach((locator, testCases) -> {
            webOrderReturnPage.chooseDropdown(locator);
            testCases.forEach((key, value) -> {
                String actualError = webOrderReturnPage.getError(locator, value);
                System.out.println("[" + locator + "][" + key + "] => " + actualError);
                webHomePage.verifyEquals(
                    actualError,
                    value.errorMsg,
                    "Verified that [" + locator + "][" + key + "] => " + actualError + " is present",
                    "Error message not present for " + key
                );
            });
        });
    }

    private Map<Locators, LinkedHashMap<String, OrderReturn>> getAllCases() {
        FixtureFactoryLoader.loadTemplates("com.Jobsity");

        Map<Locators, LinkedHashMap<String, OrderReturn>> allCases = new LinkedHashMap<>();

        LinkedHashMap<String, OrderReturn> emailCases = new LinkedHashMap<>();
        emailCases.put("empty-OrderID", Fixture.from(OrderReturn.class).gimme("emptyOrderID"));
        emailCases.put("empty-BillingLastName", Fixture.from(OrderReturn.class).gimme("emptyBillingLastName"));
        emailCases.put("empty-Email", Fixture.from(OrderReturn.class).gimme("emptyEmail"));
        emailCases.put("invalid-Email", Fixture.from(OrderReturn.class).gimme("invalidEmail"));

        LinkedHashMap<String, OrderReturn> zipCases = new LinkedHashMap<>();
        zipCases.put("empty-OrderID", Fixture.from(OrderReturn.class).gimme("emptyOrderID"));
        zipCases.put("empty-BillingLastName", Fixture.from(OrderReturn.class).gimme("emptyBillingLastName"));
        zipCases.put("empty-ZipCode", Fixture.from(OrderReturn.class).gimme("emptyZipCode"));

        allCases.put(Locators.email, emailCases);
        allCases.put(Locators.zipCode, zipCases);

        return allCases;
    }
}
