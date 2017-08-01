package com.abaltatech.weblinkserver;

import android.view.KeyEvent;

interface WLKeyboardEventHandler {
    void onKeyEvent(KeyEvent keyEvent) throws ;

    void onKeyboardHidden() throws ;

    void onKeyboardShown() throws ;
}
