package com.waze.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.waze.AppService;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.ProfileCallback;
import com.waze.mywaze.MyWazeNativeManager.ProfileSettings;

public class ProfileLauncher implements ProfileCallback {
    private boolean afterFailure;
    private Context context;

    public static void launch(Context context, boolean afterFailure) {
        MyWazeNativeManager.getInstance().getProfileSettings(new ProfileLauncher(context, afterFailure));
    }

    private ProfileLauncher(Context context, boolean afterFailure) {
        this.context = context;
        this.afterFailure = afterFailure;
    }

    public void onProfile(ProfileSettings profile) {
        Intent intent;
        if (profile.guestUser) {
            intent = new Intent(this.context, LoginActivity.class);
        } else {
            intent = new Intent(this.context, ProfileActivity.class);
            intent.putExtra("com.waze.mywaze.username", profile.userName);
            intent.putExtra("com.waze.mywaze.password", profile.password);
        }
        intent.putExtra("com.waze.mywaze.nickname", profile.nickName);
        intent.putExtra("com.waze.mywaze.pingable", profile.allowPings);
        Activity context = AppService.getActiveActivity();
        if (context != null) {
            context.startActivityForResult(intent, 0);
        }
    }
}
