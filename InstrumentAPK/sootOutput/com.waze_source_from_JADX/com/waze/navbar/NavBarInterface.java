package com.waze.navbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class NavBarInterface {

    interface INavBarViewCallbacks {
        void onDone(INavBarViewEvents iNavBarViewEvents) throws ;

        void onReady(INavBarViewEvents iNavBarViewEvents) throws ;
    }

    interface INavBarViewEvents {
        View getView() throws ;

        void onActivityResult(Activity activity, int i, int i2, Intent intent) throws ;

        void onHide() throws ;

        void onOpen() throws ;

        void onShow() throws ;

        void onUpdate() throws ;

        void setSkin(boolean z) throws ;
    }
}
