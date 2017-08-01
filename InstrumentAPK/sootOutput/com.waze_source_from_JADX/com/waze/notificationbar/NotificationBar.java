package com.waze.notificationbar;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.waze.C1283R;

public class NotificationBar {
    private boolean mBarEnabled = true;
    private int mType = 0;
    private View mView;

    public NotificationBar(View view, Context context) {
        this.mView = view;
    }

    public View getView() {
        return this.mView;
    }

    private void animateIn() {
        this.mView.setVisibility(0);
    }

    private void animateOut() {
        this.mView.setVisibility(8);
    }

    private void hideNow() {
        this.mView.setVisibility(8);
    }

    public void setIsEnabled(boolean isEnabled) {
        if (isEnabled != this.mBarEnabled) {
            this.mBarEnabled = isEnabled;
            if (isEnabled) {
                animateIn();
            } else {
                hideNow();
            }
        }
    }

    private void setMessage(String msg) {
        if (msg != null) {
            ((TextView) this.mView.findViewById(C1283R.id.Text)).setText(msg);
        }
    }

    public int getResourceId(Context context, String image) {
        return context.getResources().getIdentifier("drawable/" + image.replace("-", "_").toLowerCase(), null, context.getPackageName());
    }

    public void showMessage(String str, String iconName, int nType) {
        this.mType = nType;
        setMessage(str);
        if (!this.mBarEnabled) {
            return;
        }
        if (str == null) {
            animateOut();
        } else {
            animateIn();
        }
    }
}
