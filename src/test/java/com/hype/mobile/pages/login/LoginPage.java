package com.hype.mobile.pages.login;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@ScenarioScoped
public class LoginPage extends MobilePage {

    @CacheLookup
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Indirizzo email\")")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Indirizzo email']")
    private WebElement title;

    @CacheLookup
    @AndroidFindBys({
            @AndroidBy(className = "android.widget.EditText"),
            @AndroidBy(xpath = "//*[@password='false']")
    })
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTextField[`label == 'Indirizzo email'`]")
    private WebElement txtEmail;

    @CacheLookup
    @AndroidFindBy(xpath = "//*[@password='true']")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSecureTextField[`label == 'Password'`]")
    private WebElement txtPassword;

    @AndroidFindBy(id = "it.hype.app:id/login_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Accedi']")
    private WebElement btnLogin;

    @AndroidFindBy(id = "it.hype.app:id/error_field")
    @iOSXCUITFindBy(xpath = "//*[contains(@name,'non valida')]")
    private List<WebElement> errorMessages;

    @Inject
    public LoginPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private void submit() {
        waitUntilVisible(btnLogin).click();
    }

    public void login(String username, String password) {
        typeCredentials(username, password);
        submit();
    }

    public void typeCredentials(String username, String password) {
        clearFields(txtEmail, txtPassword);
        type(txtEmail, username);
        type(txtPassword, password);
    }

    public void clearFields() {
        clearFields(txtEmail);
        this.txtEmail.clear();
        this.txtPassword.clear();
    }

    public int errorMessages() {
        return errorMessages.size();
    }


    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

    @Override
    public String getPagename() {
        return "Pagina di Login";
    }

}
