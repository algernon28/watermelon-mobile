package com.watermelon.core;

import com.watermelon.core.di.modules.DriverManagerModule;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class MobilePage {

    protected AppiumDriver driver;

    protected WebDriverWait wait;

    protected MobilePage(AppiumDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    /**
     * @return if the page was loaded. The check is made on the page title. Every
     * MobilePage Object must implement it.
     */
    public abstract boolean isLoaded();

    public String getPagename() {
        return this.getClass().getSimpleName();
    }

    public void verify() {
        String name = Optional.ofNullable(getPagename()).orElse(getClass().getSimpleName());
        log.debug("Checking if {} is loaded...", name);
        if (!this.isLoaded()) {
            String msg = String.format("%s non caricata correttamente", name);
            throw new PageNotLoadedException(msg);
        }
        log.debug("{} is correctly loaded", name);
    }

    /**
     * Convenience method
     *
     * @param pageTitle the title of the page
     * @return true if the page is loaded, false otherwise
     */
    protected boolean checkIfLoaded(WebElement pageTitle) {
        return checkIfLoaded(pageTitle, DriverManagerModule.DefaultTimeout);
    }

    protected boolean checkIfLoaded(WebElement pageTitle, Duration duration) {
        try {
            wait.withTimeout(duration).until(ExpectedConditions.visibilityOf(pageTitle));
            log.info("page title {} found: page is loaded", pageTitle);
            return true;
        } catch (Exception e) {
            log.info("page title not found: {} could not be loaded", pageTitle);
            return false;
        }
    }

    /**
     * Wait {@link DriverManagerModule#DefaultTimeout} or until the element is
     * visible. Will throw an unchecked {@link TimeoutException} if the element is
     * not visible by the timeout.
     *
     * @param element to be checked
     * @return the element itself
     */
    public WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    /**
     * Click an element if visible
     *
     * @param element the element to be clicked
     * @see #waitUntilVisible(WebElement)
     */
    protected void click(WebElement element) {
//        log.debug("Aspetto che {} sia 'clickable'", element);
        waitUntilClickable(element);
        log.debug("{} cliccabile: procedo al click...", element);
        element.click();
        log.debug("{} cliccato...", element);
    }


    /**
     * Clear a text element if visible. It only works for text and password
     * elements.
     *
     * @param element the element to be cleared
     * @see #waitUntilVisible(WebElement)
     */
    protected void clear(WebElement element) {
        waitUntilVisible(element).clear();
    }

    /**
     * Clear all the elements. It only works for text and password elements.
     *
     * @param elements the {@link List} of elements to be cleared
     * @see #clear(WebElement)
     */
    protected void clearFields(List<WebElement> elements) {
        elements.forEach(WebElement::clear);
    }

    /**
     * @param elements an array of elements to be cleared
     * @see #clearFields(List)
     */
    protected void clearFields(WebElement... elements) {
        clearFields(Arrays.asList(elements));
    }

    /**
     * Clear the element and type in the text value. It only works for text and
     * password elements.
     *
     * @param element the element where to type the text
     * @param value   the text to be typed in
     */
    protected void type(WebElement element, String value) {
        waitUntilVisible(element);
        clear(element);
        if (element == null) {
            return;
        }
        element.sendKeys(value);
    }

    /**
     * Append text into an element
     *
     * @param element the element where to append the text
     * @param value   the text to be appended
     * @see #type(WebElement, String)
     */
    protected void addText(WebElement element, String value) {
        waitUntilVisible(element).sendKeys(value);
    }

    protected void acceptAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            //no alerts, just ignore
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            if (element.isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException e) {
            //The element is not present, therefore is not displayed
            return false;
        }
        //Any other condition means it's not displayed
        return false;
    }

    public boolean isPresent(By elementLocator) {
        return driver.findElements(elementLocator).size() >= 1;
    }

}



