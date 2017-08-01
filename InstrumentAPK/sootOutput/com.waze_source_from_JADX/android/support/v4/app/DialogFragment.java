package android.support.v4.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;

public class DialogFragment extends Fragment implements OnCancelListener, OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    int mBackStackId = -1;
    boolean mCancelable = true;
    Dialog mDialog;
    boolean mDismissed;
    boolean mShownByMe;
    boolean mShowsDialog = true;
    int mStyle = 0;
    int mTheme = 0;
    boolean mViewDestroyed;

    public void setStyle(int $i0, @StyleRes int $i1) throws  {
        this.mStyle = $i0;
        if (this.mStyle == 2 || this.mStyle == 3) {
            this.mTheme = 16973913;
        }
        if ($i1 != 0) {
            this.mTheme = $i1;
        }
    }

    public void show(FragmentManager $r1, String $r2) throws  {
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction $r3 = $r1.beginTransaction();
        $r3.add((Fragment) this, $r2);
        $r3.commit();
    }

    public int show(FragmentTransaction $r1, String $r2) throws  {
        this.mDismissed = false;
        this.mShownByMe = true;
        $r1.add((Fragment) this, $r2);
        this.mViewDestroyed = false;
        this.mBackStackId = $r1.commit();
        return this.mBackStackId;
    }

    public void dismiss() throws  {
        dismissInternal(false);
    }

    public void dismissAllowingStateLoss() throws  {
        dismissInternal(true);
    }

    void dismissInternal(boolean $z0) throws  {
        if (!this.mDismissed) {
            this.mDismissed = true;
            this.mShownByMe = false;
            if (this.mDialog != null) {
                this.mDialog.dismiss();
                this.mDialog = null;
            }
            this.mViewDestroyed = true;
            if (this.mBackStackId >= 0) {
                getFragmentManager().popBackStack(this.mBackStackId, 1);
                this.mBackStackId = -1;
                return;
            }
            FragmentTransaction $r3 = getFragmentManager().beginTransaction();
            $r3.remove(this);
            if ($z0) {
                $r3.commitAllowingStateLoss();
            } else {
                $r3.commit();
            }
        }
    }

    public Dialog getDialog() throws  {
        return this.mDialog;
    }

    @StyleRes
    public int getTheme() throws  {
        return this.mTheme;
    }

    public void setCancelable(boolean $z0) throws  {
        this.mCancelable = $z0;
        if (this.mDialog != null) {
            this.mDialog.setCancelable($z0);
        }
    }

    public boolean isCancelable() throws  {
        return this.mCancelable;
    }

    public void setShowsDialog(boolean $z0) throws  {
        this.mShowsDialog = $z0;
    }

    public boolean getShowsDialog() throws  {
        return this.mShowsDialog;
    }

    public void onAttach(Activity $r1) throws  {
        super.onAttach($r1);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    public void onDetach() throws  {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
    }

    public void onCreate(@Nullable Bundle $r1) throws  {
        super.onCreate($r1);
        this.mShowsDialog = this.mContainerId == 0;
        if ($r1 != null) {
            this.mStyle = $r1.getInt(SAVED_STYLE, 0);
            this.mTheme = $r1.getInt(SAVED_THEME, 0);
            this.mCancelable = $r1.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = $r1.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = $r1.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    public LayoutInflater getLayoutInflater(Bundle $r1) throws  {
        if (!this.mShowsDialog) {
            return super.getLayoutInflater($r1);
        }
        this.mDialog = onCreateDialog($r1);
        if (this.mDialog == null) {
            return (LayoutInflater) this.mHost.getContext().getSystemService("layout_inflater");
        }
        setupDialog(this.mDialog, this.mStyle);
        return (LayoutInflater) this.mDialog.getContext().getSystemService("layout_inflater");
    }

    public void setupDialog(Dialog $r1, int $i0) throws  {
        switch ($i0) {
            case 1:
            case 2:
                break;
            case 3:
                $r1.getWindow().addFlags(24);
                break;
            default:
                return;
        }
        $r1.requestWindowFeature(1);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) throws  {
        return new Dialog(getActivity(), getTheme());
    }

    public void onCancel(DialogInterface dialog) throws  {
    }

    public void onDismiss(DialogInterface dialog) throws  {
        if (!this.mViewDestroyed) {
            dismissInternal(true);
        }
    }

    public void onActivityCreated(Bundle $r1) throws  {
        super.onActivityCreated($r1);
        if (this.mShowsDialog) {
            View $r2 = getView();
            if ($r2 != null) {
                if ($r2.getParent() != null) {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                }
                this.mDialog.setContentView($r2);
            }
            this.mDialog.setOwnerActivity(getActivity());
            this.mDialog.setCancelable(this.mCancelable);
            this.mDialog.setOnCancelListener(this);
            this.mDialog.setOnDismissListener(this);
            if ($r1 != null) {
                $r1 = $r1.getBundle(SAVED_DIALOG_STATE_TAG);
                if ($r1 != null) {
                    this.mDialog.onRestoreInstanceState($r1);
                }
            }
        }
    }

    public void onStart() throws  {
        super.onStart();
        if (this.mDialog != null) {
            this.mViewDestroyed = false;
            this.mDialog.show();
        }
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        if (this.mDialog != null) {
            Bundle $r2 = this.mDialog.onSaveInstanceState();
            if ($r2 != null) {
                $r1.putBundle(SAVED_DIALOG_STATE_TAG, $r2);
            }
        }
        if (this.mStyle != 0) {
            $r1.putInt(SAVED_STYLE, this.mStyle);
        }
        if (this.mTheme != 0) {
            $r1.putInt(SAVED_THEME, this.mTheme);
        }
        if (!this.mCancelable) {
            $r1.putBoolean(SAVED_CANCELABLE, this.mCancelable);
        }
        if (!this.mShowsDialog) {
            $r1.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
        }
        if (this.mBackStackId != -1) {
            $r1.putInt(SAVED_BACK_STACK_ID, this.mBackStackId);
        }
    }

    public void onStop() throws  {
        super.onStop();
        if (this.mDialog != null) {
            this.mDialog.hide();
        }
    }

    public void onDestroyView() throws  {
        super.onDestroyView();
        if (this.mDialog != null) {
            this.mViewDestroyed = true;
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }
}
