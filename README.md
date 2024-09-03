
<img src="src/main/resources/img/watermelon.png" width="200"/>

# Watermelon-mobile

### _A lightweight Selenium/Cucumber Framework_
This is a very lightweight test automation framework, thought to make writing Cucumber tests as painless as possible.

### How to script it
- Configuration
	- Static configuration: the config-<dev|preprod|prod>.yaml files are binded to an instance of _Configuration_, which is essentially a javabean representing the current structure of the yaml files. It is convenient but it only works with that structure.
	- Dynamic configuration: Watermelon also dynamically binds whatever yaml you pass in the parameters as "stage" into a _MapConfiguration_, which is little more than a subclass of LinkedHashMap. This way you don't need to worry about creating new beans, but you need to remember the keys.
- Pages: 
  - All you need to do is to extend WebPage, and implement a constructor with Webdriver and WebdriverWait as parameters, both injected (see below)**.
  - The method _isLoaded()_ is necessary to the framework in order to correctly determine if the page was correctly loaded.

```Java
	import com.watermelon.core.WebPage;

	public class MyPage extends WebPage {
		@CacheLookup //Cache the element so it won't be looked up multiple times. Only use when you don't expect them to change
		@AndroidFindBy(uiAutomator = "new UiSelector().text(\"Indirizzo email\")") //Android UiAutomation selector
		@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Indirizzo email']") //XCode automation selector
		private WebElement title;
        
		@Inject
		public MyPage(WebDriver driver, WebDriverWait wait) {
			super(driver, wait); //Call parent's constructor
		}

		@Override
		public boolean isLoaded() {
			return checkIfLoaded(title); //This page is uniquely identified by title WebElement
		}
	}
```
- Dependencies: don't use "new" anymore! Declare your dependencies with @Inject, either as field or in your constructor: If the class has empty constructor it will be instantiated and passed to your class by Guice. In the example below, Guice will create "page" as instance of MyPage and _inject_ it***.

```
	@Inject
	private MyPage myPage; // This page will be instantiated as soon as it is used
```
- Steps (glue): extend BaseSteps. Beside that and what we said about injection, just write them as usual.

```Java
import com.watermelon.steps.BaseSteps;
public class MyPageSteps extends BaseSteps { 
	@Inject 
	private MyPage page;
    
	public MyPageSteps() {
        page.doSomething(); // MyPage will be instantiated here
    }
}
```

- Standard TestNG unit tests (no Gherkin) are also easily implemented (see WebTable test)

** This is necessary to make PageFactory.initElements() work in the parent class.

*** Note that MyPage doesn't strictly have a constructor "without parameters", however since both WebDriver and WebDriverWait are injected it will still work.

## Nuts & Bolts

### Libraries
Watermelon is a  testing framework based on Cucumber and Java, with TestNG as underlying test engine and Selenium 4 for the browser automation.
The framework makes heavy use of Dependency Injection, using Guice.

- Java 17+
- Guice
- TestNG
- Cucumber
- Assertj
- Selenium 4
- Appium 2
- Android SDK
- XCode

### Build

- Bare compilation: mvn clean install -DskipTests
- Run Tests with Cluecumber reporting: mvn clean test cluecumber-report:reporting *parameters
> _Mandatory parameters_
>-Dstage=config-dev.yaml|config-preprod.yaml|config-prod.yaml  *
>-DtestSuite=test-suites/testng.yaml  **

* configuration files by environment
  ** TestNG suite file to be run. Three suite files (and relative runners) are included in this project, for cross-browser testing purposes: one for Chrome, one for Firefox and one for Edge. One more meta-suite file (testng.yaml) is also included to runn all three together (see suiteThreads parameter for running them in parallel).
> _Optional parameters_
> -DgithubToken=[github token] ***
> -DsuiteThreads=[# threads] ****

*** github token, mandatory for FireFox browser with WebDriverManager
**** number of threads, used for multibrowsing (multiple runners in parallel). Not adviced when scenarios are already run in parallel

### Components
#### Configuration
There are two main configuration objects:
- _Configuration:_ bean mapped on config-[env].yaml. It contains mainly information about the website's URL being tested and screenshot level (when to take the screenshots:
	- ALWAYS: take screenshots regardless of the outcome
	- ONLY_FAILED: only take screenshot when the step/test is failed
- _DriverManager:_ reads the browser requirements from the suite config files and instantiates the relative webdriver (this is thread-safe). This class implements the Provider interface and it provides such instances for use with dependency injection.
>DriverManager works internally with WebDriverManager. downloading the webdriver implementation straight from the internet.

### _Dependency Injection_
##### Modules
_ConfigurationModule:_ provides a Configuration, a java.util.Locale and a ResourceBundle containing properties taken from **message.properties**
> Note that this is designed to work with i18n (internationalization). Providing a different properties for each language (e.g. message_en_GB.properties, messages_it_IT.properties...), changing Locale the correct properties will be automatically picked. Without locale suffix the file will be intended as default.

_DriverManagerModule:_ provides a webdriver, using the DriverManager provider, a JavascriptExecutor and a WebDriverWait.

_GuiceModuleFactory:_ loads the aforementioned modules and creates the relative injector.
>Important: this factory is declared as implementation of io.cucumber.core.backend.ObjectFactory in META-IF/services as for Java Service Loading specification.
>Note that because how Cucumber is implemented, also the relative entry _cucumber.object-factory_ in cucumber.properties had to be set for disambiguation.



## Usage
### with Maven: 
```
clean install cluecumber-report:reporting 
    -Dstage={config file yaml} -DtestSuite={testsuite yaml} -Dcucumber.filter.tags="{tags}" 
    -Dlog.dir={log directory} 
    -DappID={package name}
    --add-opens java.base/java.lang=ALL-UNNAMED
	
example: 
clean install cluecumber-report:reporting
	-Dstage=config-dev.yaml
	-Dcucumber.filter.tags=@login
	-DtestSuite=test-suites/Test-Android.yaml 
	-Dlog.dir=/temp 
	-DappID=it.hype.app
	--add-opens java.base/java.lang=ALL-UNNAMED
```
naming convention:  config-dev.yaml| config-pre.yaml | config-pro.yaml

### With IDE TestNG runner
suite file: (example> test-suites/Test-Android.yaml 

VM arguments: 
```
	-Dstage=config-dev.yaml
	-Dcucumber.filter.tags="@login"
	-DappFile=apps/Android-MyDemoAppRN.1.3.0.build-244.apk
	-DappID=com.saucelabs.mydemoapp.rn
	--add-opens java.base/java.lang=ALL-UNNAMED
```