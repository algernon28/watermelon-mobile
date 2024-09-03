package com.watermelon.core.di.modules;

import lombok.Data;

@Data
public class Configuration {
    private AppiumServer appiumServer;
    private Reporting reporting;

    @Data
    public static class AppiumServer {
        private String protocol;
        private String host;
        private String path;
        private int port;
    }

    @Data
    public static class Reporting {
        private LEVEL screenshotLevel;

        public enum LEVEL {
            ALWAYS, ONLY_FAILED
        }
    }
}
