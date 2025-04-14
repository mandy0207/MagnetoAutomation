package com.Jobsity.pageInstanceFactory;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import com.Jobsity.Interfaces.PageActionsHelper;
import com.Jobsity.driver.factory.DriverManager;

public class PageInstancesFactory {

    /**
     * Get an instance of a page object class
     *
     * @param <T>  The Page Object class type
     * @param type The class of the page object
     * @return an instance of the page class
     */

    public static <T extends PageActionsHelper> T getInstance(Class<T> type) {
        try {
            return type.getConstructor(WebDriver.class).newInstance(DriverManager.getDriver());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
