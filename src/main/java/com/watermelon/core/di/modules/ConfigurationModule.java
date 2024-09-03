package com.watermelon.core.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.watermelon.core.Utils;
import io.cucumber.guice.ScenarioScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.watermelon.core.Utils.DEFAULT_COUNTRY;
import static com.watermelon.core.Utils.DEFAULT_LANG;

/**
 * Make the following objects available for Dependency Injection:
 * {@link Configuration}, {@link Locale}, {@link ResourceBundle}
 *
 * @author AM
 */

public class ConfigurationModule extends AbstractModule {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationModule.class);
    private static final String CONFIGFILE_PARAM = "stage";
    private final String stage = Optional.ofNullable(System.getenv(CONFIGFILE_PARAM))
            .orElse(System.getProperty(CONFIGFILE_PARAM));

    @Override
    protected void configure() {
        log.debug("Configuring {}", getClass().getSimpleName());
    }

    @Provides
    public Configuration getConfiguration() {
        Configuration config = Utils.loadYaml(Configuration.class, stage);
        log.debug("Configuration loaded: {}", config);
        return config;
    }

    @Provides
    public MapConfiguration<String, Object> getConfigurationBean() {
        MapConfiguration<String, Object> mapConfiguration = Utils.fromYaml(stage);
        log.debug("MapConfiguration loaded: {}", mapConfiguration);
        return mapConfiguration;
    }

    @Provides
    @ScenarioScoped
    public Locale getLocale() {
        Locale.setDefault(Locale.ENGLISH);
        String language = Utils.lookupProperty("language").orElse(DEFAULT_LANG);
        String country = Utils.lookupProperty("country").orElse(DEFAULT_COUNTRY);
        Locale result = new Locale(language, country);
        log.debug("Locale configured: {}", result);
        return result;
    }

    @Provides
    @ScenarioScoped
    public ResourceBundle getBundle(Locale locale) {
        ResourceBundle result = ResourceBundle.getBundle("messages", locale);
        log.debug("Locale configured: {}", result);
        return result;
    }

}
