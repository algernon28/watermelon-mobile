package com.hype.mobile.pages.android;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class PreloginPage extends MobilePage {

    @AndroidFindBy(id = "it.hype.app:id/title")
    private WebElement title;
    @AndroidFindBy(id = "it.hype.app:id/spinner")
    private WebElement spnEnvironment;
    @AndroidFindBy(id = "it.hype.app:id/salva")
    private WebElement btnSalva;

    @Inject
    public PreloginPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectEnvironment(Environment environment) {
        By xpath = By.xpath(String.format("//*[@resource-id='android:id/text1' and @index = '%s']", environment.index));
        spnEnvironment.click();
        driver.findElement(xpath).click();
        btnSalva.click();
    }

    /**
     * @return the element identifying the page
     */
    @Override
    public boolean isLoaded() {
        return checkIfLoaded(title);
    }

    @Override
    public String getPagename() {
        return "Pagina di selezione ambiente";
    }

    public enum Environment {
        PRO(0), PRE(1), TEST(2), MOCKED(3);
        public int index;

        Environment(int index) {
            this.index = index;
        }
    }

}
