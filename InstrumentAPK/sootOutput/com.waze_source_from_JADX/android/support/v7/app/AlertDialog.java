package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertController.AlertParams;
import android.support.v7.appcompat.C0192R;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AlertDialog extends AppCompatDialog implements DialogInterface {
    static final int LAYOUT_HINT_NONE = 0;
    static final int LAYOUT_HINT_SIDE = 1;
    private AlertController mAlert;

    public static class Builder {
        private final AlertParams f5P;
        private int mTheme;

        public Builder(Context $r1) throws  {
            this($r1, AlertDialog.resolveDialogTheme($r1, 0));
        }

        public Builder(Context $r1, int $i0) throws  {
            this.f5P = new AlertParams(new ContextThemeWrapper($r1, AlertDialog.resolveDialogTheme($r1, $i0)));
            this.mTheme = $i0;
        }

        public Context getContext() throws  {
            return this.f5P.mContext;
        }

        public Builder setTitle(int $i0) throws  {
            this.f5P.mTitle = this.f5P.mContext.getText($i0);
            return this;
        }

        public Builder setTitle(CharSequence $r1) throws  {
            this.f5P.mTitle = $r1;
            return this;
        }

        public Builder setCustomTitle(View $r1) throws  {
            this.f5P.mCustomTitleView = $r1;
            return this;
        }

        public Builder setMessage(int $i0) throws  {
            this.f5P.mMessage = this.f5P.mContext.getText($i0);
            return this;
        }

        public Builder setMessage(CharSequence $r1) throws  {
            this.f5P.mMessage = $r1;
            return this;
        }

        public Builder setIcon(int $i0) throws  {
            this.f5P.mIconId = $i0;
            return this;
        }

        public Builder setIcon(Drawable $r1) throws  {
            this.f5P.mIcon = $r1;
            return this;
        }

        public Builder setIconAttribute(int $i0) throws  {
            TypedValue $r1 = new TypedValue();
            this.f5P.mContext.getTheme().resolveAttribute($i0, $r1, true);
            this.f5P.mIconId = $r1.resourceId;
            return this;
        }

        public Builder setPositiveButton(int $i0, OnClickListener $r1) throws  {
            this.f5P.mPositiveButtonText = this.f5P.mContext.getText($i0);
            this.f5P.mPositiveButtonListener = $r1;
            return this;
        }

        public Builder setPositiveButton(CharSequence $r1, OnClickListener $r2) throws  {
            this.f5P.mPositiveButtonText = $r1;
            this.f5P.mPositiveButtonListener = $r2;
            return this;
        }

        public Builder setNegativeButton(int $i0, OnClickListener $r1) throws  {
            this.f5P.mNegativeButtonText = this.f5P.mContext.getText($i0);
            this.f5P.mNegativeButtonListener = $r1;
            return this;
        }

        public Builder setNegativeButton(CharSequence $r1, OnClickListener $r2) throws  {
            this.f5P.mNegativeButtonText = $r1;
            this.f5P.mNegativeButtonListener = $r2;
            return this;
        }

        public Builder setNeutralButton(int $i0, OnClickListener $r1) throws  {
            this.f5P.mNeutralButtonText = this.f5P.mContext.getText($i0);
            this.f5P.mNeutralButtonListener = $r1;
            return this;
        }

        public Builder setNeutralButton(CharSequence $r1, OnClickListener $r2) throws  {
            this.f5P.mNeutralButtonText = $r1;
            this.f5P.mNeutralButtonListener = $r2;
            return this;
        }

        public Builder setCancelable(boolean $z0) throws  {
            this.f5P.mCancelable = $z0;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener $r1) throws  {
            this.f5P.mOnCancelListener = $r1;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener $r1) throws  {
            this.f5P.mOnDismissListener = $r1;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener $r1) throws  {
            this.f5P.mOnKeyListener = $r1;
            return this;
        }

        public Builder setItems(int $i0, OnClickListener $r1) throws  {
            this.f5P.mItems = this.f5P.mContext.getResources().getTextArray($i0);
            this.f5P.mOnClickListener = $r1;
            return this;
        }

        public Builder setItems(CharSequence[] $r1, OnClickListener $r2) throws  {
            this.f5P.mItems = $r1;
            this.f5P.mOnClickListener = $r2;
            return this;
        }

        public Builder setAdapter(ListAdapter $r1, OnClickListener $r2) throws  {
            this.f5P.mAdapter = $r1;
            this.f5P.mOnClickListener = $r2;
            return this;
        }

        public Builder setCursor(Cursor $r1, OnClickListener $r2, String $r3) throws  {
            this.f5P.mCursor = $r1;
            this.f5P.mLabelColumn = $r3;
            this.f5P.mOnClickListener = $r2;
            return this;
        }

        public Builder setMultiChoiceItems(int $i0, boolean[] $r1, OnMultiChoiceClickListener $r2) throws  {
            this.f5P.mItems = this.f5P.mContext.getResources().getTextArray($i0);
            this.f5P.mOnCheckboxClickListener = $r2;
            this.f5P.mCheckedItems = $r1;
            this.f5P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] $r1, boolean[] $r2, OnMultiChoiceClickListener $r3) throws  {
            this.f5P.mItems = $r1;
            this.f5P.mOnCheckboxClickListener = $r3;
            this.f5P.mCheckedItems = $r2;
            this.f5P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor $r1, String $r2, String $r3, OnMultiChoiceClickListener $r4) throws  {
            this.f5P.mCursor = $r1;
            this.f5P.mOnCheckboxClickListener = $r4;
            this.f5P.mIsCheckedColumn = $r2;
            this.f5P.mLabelColumn = $r3;
            this.f5P.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(int $i0, int $i1, OnClickListener $r1) throws  {
            this.f5P.mItems = this.f5P.mContext.getResources().getTextArray($i0);
            this.f5P.mOnClickListener = $r1;
            this.f5P.mCheckedItem = $i1;
            this.f5P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor $r1, int $i0, String $r2, OnClickListener $r3) throws  {
            this.f5P.mCursor = $r1;
            this.f5P.mOnClickListener = $r3;
            this.f5P.mCheckedItem = $i0;
            this.f5P.mLabelColumn = $r2;
            this.f5P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] $r1, int $i0, OnClickListener $r2) throws  {
            this.f5P.mItems = $r1;
            this.f5P.mOnClickListener = $r2;
            this.f5P.mCheckedItem = $i0;
            this.f5P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter $r1, int $i0, OnClickListener $r2) throws  {
            this.f5P.mAdapter = $r1;
            this.f5P.mOnClickListener = $r2;
            this.f5P.mCheckedItem = $i0;
            this.f5P.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemSelectedListener $r1) throws  {
            this.f5P.mOnItemSelectedListener = $r1;
            return this;
        }

        public Builder setView(int $i0) throws  {
            this.f5P.mView = null;
            this.f5P.mViewLayoutResId = $i0;
            this.f5P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View $r1) throws  {
            this.f5P.mView = $r1;
            this.f5P.mViewLayoutResId = 0;
            this.f5P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
            this.f5P.mView = $r1;
            this.f5P.mViewLayoutResId = 0;
            this.f5P.mViewSpacingSpecified = true;
            this.f5P.mViewSpacingLeft = $i0;
            this.f5P.mViewSpacingTop = $i1;
            this.f5P.mViewSpacingRight = $i2;
            this.f5P.mViewSpacingBottom = $i3;
            return this;
        }

        public Builder setInverseBackgroundForced(boolean $z0) throws  {
            this.f5P.mForceInverseBackground = $z0;
            return this;
        }

        public Builder setRecycleOnMeasureEnabled(boolean $z0) throws  {
            this.f5P.mRecycleOnMeasure = $z0;
            return this;
        }

        public AlertDialog create() throws  {
            AlertDialog $r1 = new AlertDialog(this.f5P.mContext, this.mTheme, false);
            this.f5P.apply($r1.mAlert);
            $r1.setCancelable(this.f5P.mCancelable);
            if (this.f5P.mCancelable) {
                $r1.setCanceledOnTouchOutside(true);
            }
            $r1.setOnCancelListener(this.f5P.mOnCancelListener);
            $r1.setOnDismissListener(this.f5P.mOnDismissListener);
            if (this.f5P.mOnKeyListener == null) {
                return $r1;
            }
            $r1.setOnKeyListener(this.f5P.mOnKeyListener);
            return $r1;
        }

        public AlertDialog show() throws  {
            AlertDialog $r1 = create();
            $r1.show();
            return $r1;
        }
    }

    protected AlertDialog(Context $r1) throws  {
        this($r1, resolveDialogTheme($r1, 0), true);
    }

    protected AlertDialog(Context $r1, int $i0) throws  {
        this($r1, $i0, true);
    }

    AlertDialog(Context $r1, int $i0, boolean createThemeContextWrapper) throws  {
        super($r1, resolveDialogTheme($r1, $i0));
        this.mAlert = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog(Context $r1, boolean $z0, OnCancelListener $r2) throws  {
        super($r1, resolveDialogTheme($r1, 0));
        setCancelable($z0);
        setOnCancelListener($r2);
        this.mAlert = new AlertController($r1, this, getWindow());
    }

    static int resolveDialogTheme(Context $r0, int $i0) throws  {
        if ($i0 >= ViewCompat.MEASURED_STATE_TOO_SMALL) {
            return $i0;
        }
        TypedValue $r1 = new TypedValue();
        $r0.getTheme().resolveAttribute(C0192R.attr.alertDialogTheme, $r1, true);
        return $r1.resourceId;
    }

    public Button getButton(int $i0) throws  {
        return this.mAlert.getButton($i0);
    }

    public ListView getListView() throws  {
        return this.mAlert.getListView();
    }

    public void setTitle(CharSequence $r1) throws  {
        super.setTitle($r1);
        this.mAlert.setTitle($r1);
    }

    public void setCustomTitle(View $r1) throws  {
        this.mAlert.setCustomTitle($r1);
    }

    public void setMessage(CharSequence $r1) throws  {
        this.mAlert.setMessage($r1);
    }

    public void setView(View $r1) throws  {
        this.mAlert.setView($r1);
    }

    public void setView(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        this.mAlert.setView($r1, $i0, $i1, $i2, $i3);
    }

    void setButtonPanelLayoutHint(int $i0) throws  {
        this.mAlert.setButtonPanelLayoutHint($i0);
    }

    public void setButton(int $i0, CharSequence $r1, Message $r2) throws  {
        this.mAlert.setButton($i0, $r1, null, $r2);
    }

    public void setButton(int $i0, CharSequence $r1, OnClickListener $r2) throws  {
        this.mAlert.setButton($i0, $r1, $r2, null);
    }

    public void setIcon(int $i0) throws  {
        this.mAlert.setIcon($i0);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mAlert.setIcon($r1);
    }

    public void setIconAttribute(int $i0) throws  {
        TypedValue $r1 = new TypedValue();
        getContext().getTheme().resolveAttribute($i0, $r1, true);
        this.mAlert.setIcon($r1.resourceId);
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        return this.mAlert.onKeyDown($i0, $r1) ? true : super.onKeyDown($i0, $r1);
    }

    public boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
        return this.mAlert.onKeyUp($i0, $r1) ? true : super.onKeyUp($i0, $r1);
    }
}
