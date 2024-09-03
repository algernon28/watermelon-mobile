package com.hype.mobile.steps.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "classpath:features/mobile/", glue = {
        "com.watermelon.mobile.hooks",
        "com.hype.mobile.steps"},
        monochrome = true,
        plugin = {"pretty",
                "html:target/TestReports/cucumber/android/report.html",
                "timeline:target/TestReports/cucumber/android/timeline",
                "json:target/TestReports/cucumber/cucumber-android.json",
                "testng:target/TestReports/cucumber/cucumber-android.xml",
                "rerun:target/TestReports/cucumber/cucumber-android-rerun.txt",
                "usage:target/TestReports/cucumber/usage.json"
        }
)
@Slf4j
public class AndroidRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        log.debug("Scenarios");
        return super.scenarios();
    }

}
