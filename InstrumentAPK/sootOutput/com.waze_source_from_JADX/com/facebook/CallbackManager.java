package com.facebook;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

public interface CallbackManager {

    public static class Factory {
        public static CallbackManager create() throws  {
            return new CallbackManagerImpl();
        }
    }

    boolean onActivityResult(int i, int i2, Intent intent) throws ;
}
