package com.hype.mobile.navigation.android;

import com.google.inject.Inject;
import com.hype.mobile.pages.android.PreloginPage;
import com.hype.mobile.pages.intro.IntroWalkthroughPage;
import com.watermelon.core.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PreLogin {
    @Inject
    AppiumDriver driver;
    @Inject
    PreloginPage environmentPage;
    @Inject
    IntroWalkthroughPage introWalkthroughPage;

    public void deepLink() {
        String platform = driver.getCapabilities().getPlatformName().name().toLowerCase();
        String deepLink = Utils.lookupParameter("appURL").get();
        log.debug("Platform found: {}", platform);
        switch (platform) {
            case "android" -> {
                AndroidDriver ad = ((AndroidDriver) driver);
                //ad.startActivity(new Activity("com.android.chrome", "com.google.android.apps.chrome.Main"));
                //ad.get(deepLink);
                ((AndroidDriver) driver).context("NATIVE_APP");
                driver.get(deepLink);
                assertThat(environmentPage).as("Pagina di selezione ambienti non caricata").returns(true, PreloginPage::isLoaded);
                environmentPage.selectEnvironment(PreloginPage.Environment.PRE);
            }
            case "ios" -> throw new NotImplementedException("iOS not yet implemented");
            default -> throw new NotImplementedException("platform not recognised");
        }
    }

    public void skipWalkthru(){
        introWalkthroughPage.verify();
        introWalkthroughPage.skipAll();
    }

}
