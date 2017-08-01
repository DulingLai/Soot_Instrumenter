package com.android.volley;

import android.content.Intent;

public class AuthFailureError extends VolleyError {
    private Intent mResolutionIntent;

    public AuthFailureError(Intent $r1) throws  {
        this.mResolutionIntent = $r1;
    }

    public AuthFailureError(NetworkResponse $r1) throws  {
        super($r1);
    }

    public AuthFailureError(String $r1) throws  {
        super($r1);
    }

    public AuthFailureError(String $r1, Exception $r2) throws  {
        super($r1, $r2);
    }

    public Intent getResolutionIntent() throws  {
        return this.mResolutionIntent;
    }

    public String getMessage() throws  {
        return this.mResolutionIntent != null ? "User needs to (re)enter credentials." : super.getMessage();
    }
}
