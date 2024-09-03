package com.watermelon.steps;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseSteps {

    @Inject
    protected AppiumDriver driver;

    @Inject
    protected WebDriverWait wait;

    protected BaseSteps() {

    }


}
