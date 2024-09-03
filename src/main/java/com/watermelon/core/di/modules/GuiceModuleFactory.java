package com.watermelon.core.di.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.guice.CucumberModules;
import io.cucumber.guice.ScenarioScope;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuiceModuleFactory implements ObjectFactory {
    private final Injector injector;

    public GuiceModuleFactory() {
        this.injector = Guice.createInjector(Stage.DEVELOPMENT, CucumberModules.createScenarioModule(),
                new DriverManagerModule(), new ConfigurationModule());
        log.debug("Injector configured: {}", injector);
    }

    @Override
    public boolean addClass(Class<?> glueClass) {
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> glueClass) {
        T instance = this.injector.getInstance(glueClass);
        log.debug("Glue class instance: {}", instance);
        return instance;
    }

    @Override
    public void start() {
        ScenarioScope scenarioScope = injector.getInstance(ScenarioScope.class);
        log.debug("ScenarioScope: {} -> entering scope", scenarioScope);
        scenarioScope.enterScope();
    }

    @Override
    public void stop() {
        ScenarioScope scenarioScope = injector.getInstance(ScenarioScope.class);
        log.debug("ScenarioScope: {} -> exiting scope", scenarioScope);
        scenarioScope.exitScope();
    }

}
