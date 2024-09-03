package com.watermelon.mobile.hooks;

import com.google.inject.Inject;
import com.watermelon.core.di.modules.DriverManager;
import com.watermelon.core.di.modules.MapConfiguration;
import com.watermelon.mobile.MobileConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.appmanagement.AndroidInstallApplicationOptions;
import io.appium.java_client.appmanagement.BaseInstallApplicationOptions;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.net.MediaType.PNG;

@Slf4j
public class MobileHooks extends AbstractTestNGCucumberTests {
    @Inject
    Locale locale;
    @Inject
    private AppiumDriver driver;
    @Inject
    private MapConfiguration<String, Object> config;

    //@Before(order = 1)
    public void startUp() {
        InteractsWithApps iwa = ((InteractsWithApps) driver);
        String appFile = System.getProperty(MobileConstants.OPTION_APPFILE);
        String appID = System.getProperty(MobileConstants.OPTION_APPID);
        if (driver.getCapabilities().getCapability("app") != null)
            return;
        if (!iwa.isAppInstalled(appID)) {
            log.debug("Did not find {}: installing it...", appID);
            installApp(Paths.get(appFile).toAbsolutePath().toString(), driver.getCapabilities().getPlatformName(), iwa);
        } else {
            log.debug("App {} found: terminating it...", appID);
            //driver.manage().deleteAllCookies();
            //driver.terminateApp(appID);
            log.debug("Removing {}...", appID);
            iwa.removeApp(appID);
            log.debug("App {} fully cleared and removed: now installing it...", appID);
            installApp(Paths.get(appFile).toAbsolutePath().toString(), driver.getCapabilities().getPlatformName(), iwa);
            log.debug("App {} successfully installed", appID);
        }

    }

    private static void installApp(String appPath, Platform platform, InteractsWithApps iwa){
        switch (platform){
            case ANDROID -> {
                //io.appium.java_client.android.appmanagement
                BaseInstallApplicationOptions options = new AndroidInstallApplicationOptions().withGrantPermissionsEnabled().withAllowTestPackagesEnabled();
                iwa.installApp(appPath, options);
            }
            case IOS -> {
                iwa.installApp(appPath);
            }
        }
    }

    @After
    public void screenshot(Scenario scenario) {
        LEVEL configLevel = LEVEL.valueOf((String) config.getMap("reporting").get("screenshotLevel"));
        byte[] payload = takeScreenshot();
        // String payload = takeBase64Screenshot();

        String name = String.format("%s: %s", scenario.getId(), UUID.randomUUID());
        if (configLevel == LEVEL.ALWAYS) {
            scenario.attach(payload, "image/png", name);
        } else if (configLevel == LEVEL.ONLY_FAILED && scenario.isFailed()) {
            scenario.attach(payload, "image/png", name.concat(": FAIL"));
        }
    }

    private byte[] readImage(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("img/" + fileName);
        return is.readAllBytes();
    }

    @Before(order = 1000)
    public void attachData(Scenario scenario) throws IOException {
        RemoteWebDriver rd = driver;
        String platform = rd.getCapabilities().getPlatformName().name();
        String version = rd.getCapabilities().getBrowserVersion();
        String message = String.format("Platform: %s [%s], Driver: %s", platform, version,
                rd.getClass().getSimpleName());
        String imageName = platform.toLowerCase().concat(".png");
        scenario.attach(readImage(imageName), PNG.toString(), message);
    }

    private byte[] takeScreenshot() {
        return driver.getScreenshotAs(OutputType.BYTES);

    }

    private String takeBase64Screenshot() {
        return driver.getScreenshotAs(OutputType.BASE64);

    }

    private File takeFileScreenshot() {
        return driver.getScreenshotAs(OutputType.FILE);

    }

    @After(order = 0)
    public void tearDown() {
        String appID = System.getProperty(MobileConstants.OPTION_APPID);
        if (Optional.ofNullable(driver).isPresent()) {
            if (driver instanceof InteractsWithApps iwa) {
//                log.debug("Terminating {}...", appID);
//                iwa.terminateApp(appID);
//                log.debug("Removing {}...", appID);
//                iwa.removeApp(appID);
//                log.debug("App {} fully cleared and removed", appID);
                driver.manage().deleteAllCookies();
            }

            driver.quit();
            log.debug("quitting..");
            DriverManager.removeDriver();
        }
    }

    public enum LEVEL {
        ALWAYS, ONLY_FAILED
    }

}
