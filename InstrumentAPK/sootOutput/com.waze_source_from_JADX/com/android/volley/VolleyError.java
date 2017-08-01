package com.android.volley;

public class VolleyError extends Exception {
    public final NetworkResponse networkResponse;

    public VolleyError() throws  {
        this.networkResponse = null;
    }

    public VolleyError(NetworkResponse $r1) throws  {
        this.networkResponse = $r1;
    }

    public VolleyError(String $r1) throws  {
        super($r1);
        this.networkResponse = null;
    }

    public VolleyError(String $r1, Throwable $r2) throws  {
        super($r1, $r2);
        this.networkResponse = null;
    }

    public VolleyError(Throwable $r1) throws  {
        super($r1);
        this.networkResponse = null;
    }
}
