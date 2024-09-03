package com.watermelon.mobile;

public abstract class MobileConstants {

    //Either .apk(Android) or .isa(IOS). It can be an URL
    public static final String OPTION_APPFILE = "appFile";

    //Either appPackage(Android) or bundleID(IOS)
    public static final String OPTION_APPID = "appID";

    private MobileConstants() {

    }
}
