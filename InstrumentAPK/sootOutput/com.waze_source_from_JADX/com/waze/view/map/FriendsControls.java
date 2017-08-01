package com.waze.view.map;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.FriendsActivity;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.view.text.WazeTextView;

public class FriendsControls extends FrameLayout {
    private LayoutInflater inflater;
    WazeTextView mFriends;

    class C30021 implements OnClickListener {
        C30021() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_ON_MAP_CLICKED).send();
            AppService.getActiveActivity().startActivity(new Intent(AppService.getAppContext(), FriendsActivity.class));
        }
    }

    public FriendsControls(Context context) {
        super(context);
        init(context);
    }

    public FriendsControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FriendsControls(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mFriends = (WazeTextView) this.inflater.inflate(C1283R.layout.friends_controls, this).findViewById(C1283R.id.friendsControl);
        this.mFriends.setOnClickListener(new C30021());
        this.mFriends.setText("");
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            int friendsOnline = MyWazeNativeManager.getInstance().getNumberOfFriendsOnline();
            if (friendsOnline > 0) {
                this.mFriends.setText(String.valueOf(friendsOnline));
            } else {
                this.mFriends.setText("");
            }
        }
    }
}
