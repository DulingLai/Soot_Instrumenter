package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

abstract class BaseFragmentActivityDonut extends Activity {
    abstract View dispatchFragmentsOnCreateView(View view, String str, Context context, AttributeSet attributeSet) throws ;

    BaseFragmentActivityDonut() throws  {
    }

    protected void onCreate(Bundle $r1) throws  {
        if (VERSION.SDK_INT < 11 && getLayoutInflater().getFactory() == null) {
            getLayoutInflater().setFactory(this);
        }
        super.onCreate($r1);
    }

    public View onCreateView(String $r1, Context $r2, AttributeSet $r3) throws  {
        View $r4 = dispatchFragmentsOnCreateView(null, $r1, $r2, $r3);
        if ($r4 == null) {
            return super.onCreateView($r1, $r2, $r3);
        }
        return $r4;
    }

    void onBackPressedNotHandled() throws  {
        finish();
    }
}
