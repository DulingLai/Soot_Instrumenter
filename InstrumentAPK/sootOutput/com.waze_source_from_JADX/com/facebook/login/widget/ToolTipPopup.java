package com.facebook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.C0496R;
import java.lang.ref.WeakReference;

public class ToolTipPopup {
    public static final long DEFAULT_POPUP_DISPLAY_TIME = 6000;
    private final WeakReference<View> mAnchorViewRef;
    private final Context mContext;
    private long mNuxDisplayTime = DEFAULT_POPUP_DISPLAY_TIME;
    private PopupContentView mPopupContent;
    private PopupWindow mPopupWindow;
    private final OnScrollChangedListener mScrollListener = new C05651();
    private Style mStyle = Style.BLUE;
    private final String mText;

    class C05651 implements OnScrollChangedListener {
        C05651() throws  {
        }

        public void onScrollChanged() throws  {
            if (ToolTipPopup.this.mAnchorViewRef.get() != null && ToolTipPopup.this.mPopupWindow != null && ToolTipPopup.this.mPopupWindow.isShowing()) {
                if (ToolTipPopup.this.mPopupWindow.isAboveAnchor()) {
                    ToolTipPopup.this.mPopupContent.showBottomArrow();
                } else {
                    ToolTipPopup.this.mPopupContent.showTopArrow();
                }
            }
        }
    }

    class C05662 implements Runnable {
        C05662() throws  {
        }

        public void run() throws  {
            ToolTipPopup.this.dismiss();
        }
    }

    class C05673 implements OnClickListener {
        C05673() throws  {
        }

        public void onClick(View v) throws  {
            ToolTipPopup.this.dismiss();
        }
    }

    private class PopupContentView extends FrameLayout {
        private View bodyFrame;
        private ImageView bottomArrow;
        private ImageView topArrow;
        private ImageView xOut;

        public PopupContentView(Context $r2) throws  {
            super($r2);
            init();
        }

        private void init() throws  {
            LayoutInflater.from(getContext()).inflate(C0496R.layout.com_facebook_tooltip_bubble, this);
            this.topArrow = (ImageView) findViewById(C0496R.id.com_facebook_tooltip_bubble_view_top_pointer);
            this.bottomArrow = (ImageView) findViewById(C0496R.id.com_facebook_tooltip_bubble_view_bottom_pointer);
            this.bodyFrame = findViewById(C0496R.id.com_facebook_body_frame);
            this.xOut = (ImageView) findViewById(C0496R.id.com_facebook_button_xout);
        }

        public void showTopArrow() throws  {
            this.topArrow.setVisibility(0);
            this.bottomArrow.setVisibility(4);
        }

        public void showBottomArrow() throws  {
            this.topArrow.setVisibility(4);
            this.bottomArrow.setVisibility(0);
        }
    }

    public enum Style {
        BLUE,
        BLACK
    }

    public ToolTipPopup(String $r1, View $r2) throws  {
        this.mText = $r1;
        this.mAnchorViewRef = new WeakReference($r2);
        this.mContext = $r2.getContext();
    }

    public void setStyle(Style $r1) throws  {
        this.mStyle = $r1;
    }

    public void show() throws  {
        if (this.mAnchorViewRef.get() != null) {
            this.mPopupContent = new PopupContentView(this.mContext);
            ((TextView) this.mPopupContent.findViewById(C0496R.id.com_facebook_tooltip_bubble_view_text_body)).setText(this.mText);
            if (this.mStyle == Style.BLUE) {
                this.mPopupContent.bodyFrame.setBackgroundResource(C0496R.drawable.com_facebook_tooltip_blue_background);
                this.mPopupContent.bottomArrow.setImageResource(C0496R.drawable.com_facebook_tooltip_blue_bottomnub);
                this.mPopupContent.topArrow.setImageResource(C0496R.drawable.com_facebook_tooltip_blue_topnub);
                this.mPopupContent.xOut.setImageResource(C0496R.drawable.com_facebook_tooltip_blue_xout);
            } else {
                this.mPopupContent.bodyFrame.setBackgroundResource(C0496R.drawable.com_facebook_tooltip_black_background);
                this.mPopupContent.bottomArrow.setImageResource(C0496R.drawable.com_facebook_tooltip_black_bottomnub);
                this.mPopupContent.topArrow.setImageResource(C0496R.drawable.com_facebook_tooltip_black_topnub);
                this.mPopupContent.xOut.setImageResource(C0496R.drawable.com_facebook_tooltip_black_xout);
            }
            View $r5 = ((Activity) this.mContext).getWindow().getDecorView();
            int $i1 = $r5.getWidth();
            int $i0 = $r5.getHeight();
            registerObserver();
            this.mPopupContent.measure(MeasureSpec.makeMeasureSpec($i1, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec($i0, Integer.MIN_VALUE));
            PopupContentView $r3 = this.mPopupContent;
            PopupContentView $r14 = this.mPopupContent;
            $i0 = $r14.getMeasuredWidth();
            $r14 = this.mPopupContent;
            this.mPopupWindow = new PopupWindow($r3, $i0, $r14.getMeasuredHeight());
            this.mPopupWindow.showAsDropDown((View) this.mAnchorViewRef.get());
            updateArrows();
            if (this.mNuxDisplayTime > 0) {
                this.mPopupContent.postDelayed(new C05662(), this.mNuxDisplayTime);
            }
            this.mPopupWindow.setTouchable(true);
            this.mPopupContent.setOnClickListener(new C05673());
        }
    }

    public void setNuxDisplayTime(long $l0) throws  {
        this.mNuxDisplayTime = $l0;
    }

    private void updateArrows() throws  {
        if (this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
            if (this.mPopupWindow.isAboveAnchor()) {
                this.mPopupContent.showBottomArrow();
            } else {
                this.mPopupContent.showTopArrow();
            }
        }
    }

    public void dismiss() throws  {
        unregisterObserver();
        if (this.mPopupWindow != null) {
            this.mPopupWindow.dismiss();
        }
    }

    private void registerObserver() throws  {
        unregisterObserver();
        if (this.mAnchorViewRef.get() != null) {
            ((View) this.mAnchorViewRef.get()).getViewTreeObserver().addOnScrollChangedListener(this.mScrollListener);
        }
    }

    private void unregisterObserver() throws  {
        if (this.mAnchorViewRef.get() != null) {
            ((View) this.mAnchorViewRef.get()).getViewTreeObserver().removeOnScrollChangedListener(this.mScrollListener);
        }
    }
}
