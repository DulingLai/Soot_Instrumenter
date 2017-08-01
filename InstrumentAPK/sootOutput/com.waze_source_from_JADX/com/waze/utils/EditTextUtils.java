package com.waze.utils;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.view.text.TypingLockListener;

public class EditTextUtils {

    public interface OnTouchListenerDoneListener {
        void onDone();
    }

    public static OnTouchListener getKeyboardLockOnTouchListener(final Context context, final EditText editText, final OnTouchListenerDoneListener listener) {
        return new OnTouchListener() {

            class C29401 implements TypingLockListener {
                C29401() {
                }

                public void shouldLock() {
                    ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    AppService.getNativeManager().checkTypingLock(new C29401());
                    if (listener != null) {
                        listener.onDone();
                    }
                }
                return false;
            }
        };
    }

    public static void checkTypeLockOnCreate(final Context context, final EditText editText) {
        AppService.getNativeManager().checkTypingLock(new TypingLockListener() {
            public void shouldLock() {
                ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });
    }

    public static void closeKeyboard(Activity a, View v) {
        ((InputMethodManager) a.getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void openKeyboard(Activity a, View v) {
        ((InputMethodManager) a.getSystemService("input_method")).showSoftInput(v, 0);
    }

    public static SpannableString underlineSpan(int ds) {
        String text = NativeManager.getInstance().getLanguageString(ds);
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return content;
    }
}
