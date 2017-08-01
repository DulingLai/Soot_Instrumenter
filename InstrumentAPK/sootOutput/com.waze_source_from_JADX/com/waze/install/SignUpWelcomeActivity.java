package com.waze.install;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import java.util.Locale;

public class SignUpWelcomeActivity extends ActivityBase {

    class C18201 implements OnClickListener {
        C18201() throws  {
        }

        public void onClick(View v) throws  {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_WELCOME_LOGIN, null, null, true);
            SignUpWelcomeActivity.this.openTermsOnUse(true);
        }
    }

    class C18212 implements OnClickListener {
        C18212() throws  {
        }

        public void onClick(View v) throws  {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_WELCOME_GET_STARTED, null, null, true);
            SignUpWelcomeActivity.this.openTermsOnUse(false);
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.welcome_layout);
        ((TextView) findViewById(C1283R.id.lblWelcomeTitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_WELCOME_BEYOND_NAVIGATION_TITLE));
        ((TextView) findViewById(C1283R.id.lblBenefitOne)).setText(DisplayStrings.displayString(DisplayStrings.DS_WELCOME_BEYOND_NAVIGATION_BULLET_1));
        ((TextView) findViewById(C1283R.id.lblBenefitTwo)).setText(DisplayStrings.displayString(DisplayStrings.DS_WELCOME_BEYOND_NAVIGATION_BULLET_2));
        ((TextView) findViewById(C1283R.id.lblBenefitThree)).setText(DisplayStrings.displayString(DisplayStrings.DS_WELCOME_BEYOND_NAVIGATION_BULLET_3));
        ((TextView) findViewById(C1283R.id.lblNext)).setText(DisplayStrings.displayString(DisplayStrings.DS_WELCOME_GET_STARTED));
        TextView $r3 = (TextView) findViewById(C1283R.id.btnHaveAccount);
        $r3.setText(Html.fromHtml(String.format(Locale.US, "<u>%s</u>", new Object[]{DisplayStrings.displayString(DisplayStrings.DS_WELCOME_LOGIN)})));
        $r3.setOnClickListener(new C18201());
        findViewById(C1283R.id.btnNext).setOnClickListener(new C18212());
        SmartLockManager.getInstance().initialize(getApplicationContext());
    }

    private void openTermsOnUse(boolean $z0) throws  {
        Intent $r1 = new Intent(this, TermsOfUseActivity.class);
        $r1.putExtra(TermsOfUseActivity.EXTRA_LEAD_TO_SIGNUP, $z0);
        startActivity($r1);
        finish();
    }

    public void onBackPressed() throws  {
    }
}
