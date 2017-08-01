package com.waze.ifs.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.WazeApplication;
import com.waze.view.map.ProgressAnimation;

public class ProgressBarDialog extends Dialog {
    ProgressAnimation mAnimation;
    private Context mContext;
    ImageView mImage;
    private String mImageName = null;
    boolean mShowing = false;
    private String mText = null;
    TextView mTextView;

    class C17481 implements Runnable {
        C17481() throws  {
        }

        public void run() throws  {
            if (ProgressBarDialog.this.mShowing) {
                ProgressBarDialog.this.mShowing = false;
                if (ProgressBarDialog.this.mImageName == null) {
                    ProgressBarDialog.this.mAnimation.stop();
                }
                if (ProgressBarDialog.this.mContext != null && (ProgressBarDialog.this.mContext instanceof ActivityBase) && ((ActivityBase) ProgressBarDialog.this.mContext).isAlive()) {
                    super.dismiss();
                }
            }
        }
    }

    public ProgressBarDialog(Context $r1, String $r2, String $r3) throws  {
        super($r1, C1283R.style.ProgressBarDialog);
        init($r1, $r2, $r3);
    }

    public ProgressBarDialog(Context $r1) throws  {
        super($r1, C1283R.style.ProgressBarDialog);
        init($r1, null, null);
    }

    public void show() throws  {
        super.show();
        if (this.mImageName != null) {
            this.mImage.setVisibility(0);
            this.mAnimation.setVisibility(8);
            this.mImage.setImageResource(ResManager.GetDrawableId(this.mImageName));
        } else {
            this.mImage.setVisibility(8);
            this.mAnimation.setVisibility(0);
            this.mAnimation.start();
        }
        this.mShowing = true;
    }

    public void cancel() throws  {
        _dismiss();
    }

    public void dismiss() throws  {
        _dismiss();
    }

    private void _dismiss() throws  {
        C17481 $r1 = new C17481();
        if (WazeApplication.isUIThread()) {
            $r1.run();
        } else if (this.mContext != null && (this.mContext instanceof ActivityBase)) {
            ((ActivityBase) this.mContext).runOnUiThread($r1);
        }
    }

    private void init(Context $r1, String $r2, String $r3) throws  {
        if ($r2 != null) {
            this.mText = NativeManager.getInstance().getLanguageString($r2);
        } else {
            this.mText = null;
        }
        setContentView(C1283R.layout.progress_bar_dlg);
        this.mTextView = (TextView) findViewById(C1283R.id.progress_bar_dlg_text);
        this.mAnimation = (ProgressAnimation) findViewById(C1283R.id.progress_bar_dlg_animation);
        this.mImage = (ImageView) findViewById(C1283R.id.progress_bar_icon);
        if ($r3 != null) {
            this.mImageName = $r3;
        } else {
            this.mImageName = null;
        }
        if (this.mText != null) {
            this.mTextView.setText(this.mText);
        } else {
            this.mImage.setVisibility(8);
            this.mAnimation.setVisibility(0);
            this.mAnimation.setGravity(17);
        }
        this.mContext = $r1;
        getWindow().addFlags(32);
    }
}
