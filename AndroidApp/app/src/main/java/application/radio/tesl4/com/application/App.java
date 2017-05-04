package application.radio.tesl4.com.application;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import application.radio.tesl4.com.application.httpbase.AppVolley;

/**
 * Created by Tesl4 on 17-5-3.
 */

public class App extends MultiDexApplication
{
    public static boolean bDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        AppVolley.init(this);
    }
}
