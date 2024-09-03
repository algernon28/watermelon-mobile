package com.watermelon.core.di.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.watermelon.core.UnsupportedBrowserException;
import com.watermelon.core.Utils;
import com.watermelon.core.di.modules.Configuration.AppiumServer;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.guice.ScenarioScoped;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service Provider: provides a thread-safe instance of {@link WebDriver} for
 * Dependency Injection
 *
 * @author AM
 * @see Provider
 * @see WebDriver
 */
@ScenarioScoped
public class DriverManager implements Provider<AppiumDriver> {
    public static final String CAPABILITIES_PATH = "src/test/resources/capabilities";
    public static final String CAPABILITIES_PROPERTY = "caps";
    private static final ThreadLocal<AppiumDriver> DRIVERPOOL = new ThreadLocal<>();
    private final Configuration config;

    @Inject
    public DriverManager(Configuration config) throws UnsupportedBrowserException, IOException {
        this.config = config;
        AppiumDriver driver;
        String platform = Utils.lookupParameter("platform").get();
        driver = resolveAppiumDriver(platform);
        DRIVERPOOL.set(driver);
    }

    /**
     * Remove current driver from the threadpool. Delegate method to comply with
     * Sonar {@code java:S5164} rule.
     */
    public static void removeDriver() {
        DRIVERPOOL.remove();
    }

    private AppiumDriver resolveAppiumDriver(String platform) throws IOException {
        AppiumServer appium = config.getAppiumServer();
        //URL url = new URL(appium.getProtocol(), appium.getHost(), appium.getPort(), appium.getPath());
        String path = Optional.ofNullable(appium.getPath()).orElse("");
        String urlString = String.format("%s://%s:%d%s", appium.getProtocol(), appium.getHost(), appium.getPort()
                , path);
        URL url = new URL(urlString);
        Path capsPath = Paths.get(CAPABILITIES_PATH, Utils.lookupParameter(CAPABILITIES_PROPERTY).get());
        Capabilities caps = loadCapabilities(capsPath);
        Optional<String> browserVersion = Optional.ofNullable(System.getProperty((MobileCapabilityType.BROWSER_VERSION)));
        WebDriverManager wdm;

        switch (platform.toLowerCase()) {
            case "android" -> {
                wdm = WebDriverManager.chromedriver();
                //If a specific cromedriver version was passed through command line, use it, otherwise rely on appium autodownload
                if (browserVersion.isPresent()) {
                    wdm.browserVersion(browserVersion.get());
                    wdm.setup();
                    Map chromeMap = new HashMap();
                    chromeMap.put(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, wdm.getDownloadedDriverPath());
                    Capabilities chromeCaps = new DesiredCapabilities(chromeMap);
                    caps.merge(chromeCaps);
                }
                return new AndroidDriver(url, caps);
            }

            case "ios" -> {
                return new IOSDriver(url, caps);
            }
            default -> throw new IllegalArgumentException("Unexpected platform: " + platform);
        }
    }

    private Capabilities loadCapabilities(Path jsonPath) throws IOException {
        URL fileURL = jsonPath.toAbsolutePath().toUri().toURL();
        @SuppressWarnings("unchecked")
        Map<String, ?> map = new ObjectMapper().readValue(fileURL, HashMap.class);
        return new DesiredCapabilities(map);
    }

    /**
     * @return the Selenium {@link WebDriver} instance
     */
    @Override
    public AppiumDriver get() {
        return DRIVERPOOL.get();
    }

}
