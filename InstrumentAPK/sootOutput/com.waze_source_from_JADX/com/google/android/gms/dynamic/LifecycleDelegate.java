package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: dalvik_source_com.waze.apk */
public interface LifecycleDelegate {
    void onCreate(Bundle bundle) throws ;

    View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws ;

    void onDestroy() throws ;

    void onDestroyView() throws ;

    void onInflate(Activity activity, Bundle bundle, Bundle bundle2) throws ;

    void onLowMemory() throws ;

    void onPause() throws ;

    void onResume() throws ;

    void onSaveInstanceState(Bundle bundle) throws ;

    void onStart() throws ;

    void onStop() throws ;
}
