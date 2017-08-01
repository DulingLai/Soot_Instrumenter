package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.C0496R;
import com.facebook.login.LoginClient.OnCompletedListener;
import com.facebook.login.LoginClient.Request;
import com.facebook.login.LoginClient.Result;

public class LoginFragment extends Fragment {
    private static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginFragment";
    private String callingPackage;
    private LoginClient loginClient;
    private Request request;

    class C05521 implements OnCompletedListener {
        C05521() throws  {
        }

        public void onCompleted(Result $r1) throws  {
            LoginFragment.this.onLoginClientCompleted($r1);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if ($r1 != null) {
            this.loginClient = (LoginClient) $r1.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = new LoginClient((Fragment) this);
        }
        this.loginClient.setOnCompletedListener(new C05521());
        FragmentActivity $r5 = getActivity();
        if ($r5 != null) {
            initializeCallingPackage($r5);
            if ($r5.getIntent() != null) {
                this.request = (Request) $r5.getIntent().getParcelableExtra("request");
            }
        }
    }

    public void onDestroy() throws  {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater $r1, @Nullable ViewGroup $r2, @Nullable Bundle savedInstanceState) throws  {
        final View $r4 = $r1.inflate(C0496R.layout.com_facebook_login_fragment, $r2, false);
        this.loginClient.setBackgroundProcessingListener(new BackgroundProcessingListener() {
            public void onBackgroundProcessingStarted() throws  {
                $r4.findViewById(C0496R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
            }

            public void onBackgroundProcessingStopped() throws  {
                $r4.findViewById(C0496R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
            }
        });
        return $r4;
    }

    private void onLoginClientCompleted(Result $r1) throws  {
        this.request = null;
        byte $b0 = $r1.code == Code.CANCEL ? (byte) 0 : (byte) -1;
        Bundle $r2 = new Bundle();
        $r2.putParcelable(RESULT_KEY, $r1);
        Intent $r3 = new Intent();
        $r3.putExtras($r2);
        if (isAdded()) {
            getActivity().setResult($b0, $r3);
            getActivity().finish();
        }
    }

    public void onResume() throws  {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onPause() throws  {
        super.onPause();
        getActivity().findViewById(C0496R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
        this.loginClient.onActivityResult($i0, $i1, $r1);
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putParcelable(SAVED_LOGIN_CLIENT, this.loginClient);
    }

    private void initializeCallingPackage(Activity $r1) throws  {
        ComponentName $r2 = $r1.getCallingActivity();
        if ($r2 != null) {
            this.callingPackage = $r2.getPackageName();
        }
    }

    static Bundle populateIntentExtras(Request $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putParcelable("request", $r0);
        return $r1;
    }
}
