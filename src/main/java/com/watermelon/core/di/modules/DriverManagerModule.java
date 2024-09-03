package com.watermelon.core.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Make the following objects available for Dependency Injection:
 * {@link DriverManager}, {@link JavascriptExecutor}, {@link WebDriverWait}, {@link Actions}
 *
 * @author AM
 */
public class DriverManagerModule extends AbstractModule {

    public static final Duration DefaultTimeout = Duration.ofSeconds(45);
    public static final Duration DefaultTimeoutInterval = Duration.ofMillis(500);
    private static final Logger logger = LoggerFactory.getLogger(DriverManagerModule.class);

    @Override
    protected void configure() {
        logger.debug("Configuring {}", getClass().getSimpleName());
        bind(AppiumDriver.class).toProvider(DriverManager.class);
        logger.debug("{} bound to provider {}", AppiumDriver.class, DriverManager.class);
    }

    @Provides
    public Actions getActions(AppiumDriver driver) {
        Actions actions = new Actions(driver);
        return actions;
    }

    @Provides
    public WebDriverWait getWait(AppiumDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, DefaultTimeout, DefaultTimeoutInterval);
        return wait;
    }
}
