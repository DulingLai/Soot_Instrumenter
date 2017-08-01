package com.waze.autocomplete;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.tokenautocomplete.TokenCompleteTextView;
import com.tokenautocomplete.TokenCompleteTextView.TokenClickStyle;
import com.waze.utils.EditTextUtils;

public class ContactsCompletionView extends TokenCompleteTextView {
    IGetViewForObject getViewForObject;
    OnTouchListener mKeyboardLockOnTouchListener;
    int maxHeightLimit = -1;
    IOnBackPressed onBackPressed;

    public interface IGetViewForObject {
        View getViewForObject(Object obj) throws ;
    }

    public interface IOnBackPressed {
        void onBackPressed() throws ;
    }

    public ContactsCompletionView(Context $r1) throws  {
        super($r1);
        allowDuplicates(false);
        setTokenClickStyle(TokenClickStyle.SelectDeselect);
        init();
    }

    public ContactsCompletionView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        allowDuplicates(false);
        setTokenClickStyle(TokenClickStyle.SelectDeselect);
        init();
    }

    public ContactsCompletionView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        allowDuplicates(false);
        setTokenClickStyle(TokenClickStyle.SelectDeselect);
        init();
    }

    private void init() throws  {
        this.mKeyboardLockOnTouchListener = EditTextUtils.getKeyboardLockOnTouchListener(getContext(), this, null);
    }

    public void setIGetViewForObject(IGetViewForObject $r1) throws  {
        this.getViewForObject = $r1;
    }

    public void setIOnBackPressed(IOnBackPressed $r1) throws  {
        this.onBackPressed = $r1;
    }

    protected View getViewForObject(Object $r1) throws  {
        return this.getViewForObject == null ? null : this.getViewForObject.getViewForObject($r1);
    }

    protected Object defaultObject(String $r1) throws  {
        int $i0 = $r1.indexOf(64);
        if ($i0 == -1) {
            return new Person($r1, $r1.replace(" ", "") + "@example.com", null);
        }
        return new Person($r1.substring(0, $i0), $r1, null);
    }

    public void setMaxHeightLimit(int $i0) throws  {
        this.maxHeightLimit = $i0;
        setMaxHeight(this.maxHeightLimit);
        if (getHeight() > this.maxHeightLimit) {
            setHeight(this.maxHeightLimit);
        }
    }

    public void onFocusChanged(boolean $z0, int $i0, Rect $r1) throws  {
        super.onFocusChanged($z0, $i0, $r1);
        if (this.maxHeightLimit > 0) {
            setMaxHeight(this.maxHeightLimit);
        }
    }

    public boolean onKeyPreIme(int $i0, KeyEvent $r1) throws  {
        if ($i0 == 4 && $r1.getAction() == 1 && this.onBackPressed != null) {
            this.onBackPressed.onBackPressed();
        }
        return super.onKeyPreIme($i0, $r1);
    }

    protected CharSequence convertSelectionToString(Object $r1) throws  {
        super.convertSelectionToString($r1);
        return $r1.toString();
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        this.mKeyboardLockOnTouchListener.onTouch(this, $r1);
        return super.onTouchEvent($r1);
    }
}
