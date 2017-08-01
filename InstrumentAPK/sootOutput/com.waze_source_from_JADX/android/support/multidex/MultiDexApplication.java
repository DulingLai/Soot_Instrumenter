package android.support.multidex;

import android.app.Application;
import android.content.Context;

public class MultiDexApplication extends Application {
    protected void attachBaseContext(Context $r1) throws  {
        super.attachBaseContext($r1);
        MultiDex.install(this);
    }
}
