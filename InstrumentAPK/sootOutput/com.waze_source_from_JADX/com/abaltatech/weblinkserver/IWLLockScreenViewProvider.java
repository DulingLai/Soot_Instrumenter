package com.abaltatech.weblinkserver;

import android.content.Context;
import android.view.View;

public interface IWLLockScreenViewProvider {
    View getLockScreenView(Context context) throws ;
}
