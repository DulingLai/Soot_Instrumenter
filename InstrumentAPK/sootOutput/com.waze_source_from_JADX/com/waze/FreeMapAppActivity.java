package com.waze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.waze.utils.NotificationsActionsBuilder;

public final class FreeMapAppActivity extends Activity {
    private static final int LOCATION_PERMISSION_REQUEST = 1002;

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        carryOn();
    }

    private void carryOn() throws  {
        if (!AppService.IsAppRunning() || AppService.getMainActivity() == null) {
            Intent $r2 = getIntent();
            String $r3 = $r2.getStringExtra("QuestionID");
            String $r4 = $r2.getStringExtra("PushClicked");
            String $r5 = $r2.getStringExtra("AnalyticsType");
            Intent $r6 = new Intent(this, MainActivity.class);
            $r6.setData($r2.getData());
            if (!($r3 == null || $r3.isEmpty())) {
                $r6.putExtra("QuestionID", $r3);
            }
            if (!($r4 == null || $r4.isEmpty())) {
                $r6.putExtra("PushClicked", $r4);
            }
            if (!($r5 == null || $r5.isEmpty())) {
                $r6.putExtra("AnalyticsType", $r5);
            }
            $r3 = $r2.getStringExtra(NotificationsActionsBuilder.EXTRA_BUTTON_NAME);
            if ($r3 != null) {
                $r6.putExtra(NotificationsActionsBuilder.EXTRA_BUTTON_NAME, $r3);
                $r6.putExtra(NotificationsActionsBuilder.EXTRA_NOTIFICATION_TYPE, $r2.getStringExtra(NotificationsActionsBuilder.EXTRA_NOTIFICATION_TYPE));
            }
            if (AppService.IsAppRunning() && AppService.getMainActivity() == null) {
                $r6.putExtra(MainActivity.EXTRA_REFRESH_ORIENTATION, true);
            }
            startActivity($r6);
            finish();
            return;
        }
        IntentManager.HandleIntent(this, true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
