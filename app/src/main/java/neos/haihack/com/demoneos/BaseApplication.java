package neos.haihack.com.demoneos;

import android.app.Application;


public class BaseApplication extends Application {

    private static BaseApplication instance;
    private String UUID;
    public static final boolean IS_DEBUG_BUILD_TYPE = BuildConfig.BUILD_TYPE.equals("debug");

    public boolean isMainActivityRunning = false;
    public boolean isStartActivityRunning = false;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getUUID() {
        return UUID;
    }

}
