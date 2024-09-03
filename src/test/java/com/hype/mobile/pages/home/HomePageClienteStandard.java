package com.hype.mobile.pages.home;

import com.google.inject.Inject;
import com.hype.mobile.pages.home.navbar.StandardNavbar;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ScenarioScoped
public class HomePageClienteStandard extends HomePageGeneric {

    @Inject
    private StandardNavbar navbar;

    @CacheLookup
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='it.hype.app:id/badge_tiles_block']" +
            "/child::android.widget.TextView[@resource-id='it.hype.app:id/section_title' and @text='Andamento']")
    private WebElement title;

    @Inject
    public HomePageClienteStandard(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return (checkIfLoaded(title));
    }

    public void toBadge(Badge badge) {
        String scrollable = String.format("new UiScrollable(new UiSelector().resourceId(\"it.hype.app:id/badge_list\"))" +
                ".setAsHorizontalList().scrollIntoView(new UiSelector().textContains(\"%s\"))", badge.label);
        driver.findElement(AppiumBy.androidUIAutomator(scrollable));
        String badgeLocator = String.format("//*[@text='%s']", badge.label);
        WebElement badgeElement = driver.findElement(By.xpath(badgeLocator));
        log.debug("Badge {} found", badgeElement.getText());
        click(badgeElement);
    }

    public enum Badge {
        STATISTICHE("hai speso"), RISPARMI("Hai risparmiato"), CASHBACK("Cashback");
        private static final Map<String, Badge> LOOKUP = Arrays.stream(Badge.values())
                .collect(Collectors.toMap(b -> b.label, Function.identity()));
        public final String label;

        Badge(String label) {
            this.label = label;
        }

        public static Badge fromLabel(String label) {
            return LOOKUP.get(label);
        }
    }
}
