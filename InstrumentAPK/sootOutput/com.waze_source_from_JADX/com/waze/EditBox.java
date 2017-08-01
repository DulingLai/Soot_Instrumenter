package com.waze;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.internal.NativeProtocol;
import com.waze.SpeechttManagerBase.Callback;

public class EditBox extends EditText {
    public static final int WAZE_EDITBOX_FLAG_PASSWORD = 1;
    public static final int WAZE_EDITBOX_FLAG_SPEECHTT = 2;
    public static String WAZE_EDITBOX_TAG = "@WazeEditBox";
    private EditBoxCallback mCallback = null;
    private MainActivity mContext = null;
    private int mFlags = 0;
    private int mImeAction = 0;
    private boolean mStayOnAction = false;
    private String mValue = null;

    class C11221 implements OnEditorActionListener {
        C11221() throws  {
        }

        public boolean onEditorAction(TextView aView, int $i0, KeyEvent aEvent) throws  {
            if ($i0 != EditBox.this.mImeAction) {
                return false;
            }
            boolean $z0 = EditBox.this.onKeyDown(66, new KeyEvent(0, 66));
            if (EditBox.this.mStayOnAction) {
                return $z0;
            }
            EditBox.this.HideSoftInput();
            return $z0;
        }
    }

    class C11232 implements TextWatcher {
        C11232() throws  {
        }

        public void onTextChanged(CharSequence $r1, int aStart, int aBefore, int aCount) throws  {
            if ($r1.length() == 1) {
                AppService.getNativeManager().EditBoxCheckTypingLock(EditBox.this);
            }
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) throws  {
        }

        public void afterTextChanged(Editable arg0) throws  {
        }
    }

    class C11243 extends Callback {
        C11243() throws  {
        }

        protected void onResults(long aCbContext, String $r1, int $i1) throws  {
            if ($i1 == 1) {
                EditBox.this.setText($r1);
            } else {
                Log.e(Logger.TAG, "Got error result from the speech to text manager");
            }
            EditBox.this.ShowSoftInput();
        }
    }

    public static abstract class EditBoxCallback implements Runnable {
        private boolean calledCallback = false;
        private volatile long mCbContext = 0;
        private volatile boolean mResult = false;
        private volatile String mText = null;

        public abstract void CallbackDone(int i, String str, long j) throws ;

        EditBoxCallback(long $l0) throws  {
            this.mCbContext = $l0;
        }

        public void run() throws  {
            byte $b0 = this.mResult ? (byte) 1 : (byte) 0;
            if (this.calledCallback) {
                Logger.ee("Calling EditBox callback twice?");
                return;
            }
            this.calledCallback = true;
            CallbackDone($b0, this.mText, this.mCbContext);
        }

        protected void Post(boolean $z0, String $r1) throws  {
            this.mText = $r1;
            this.mResult = $z0;
            AppService.getNativeManager().PostRunnable(this);
        }
    }

    public boolean onCheckIsTextEditor() throws  {
        return true;
    }

    public EditBox(Context $r1) throws  {
        super($r1);
        Init($r1);
    }

    public EditBox(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        Init($r1);
    }

    public EditBox(Context $r1, int $i0, boolean $z0, String $r2, EditBoxCallback $r3) throws  {
        super($r1);
        Init($r1);
        setEditBoxAction($i0);
        setEditBoxStayOnAction($z0);
        setEditBoxValue($r2);
        setEditBoxCallback($r3);
    }

    public void setEditBoxAction(int $i0) throws  {
        this.mImeAction = $i0;
        setImeOptions(this.mImeAction | getImeOptions());
    }

    public void setEditBoxFlags(int $i0) throws  {
        this.mFlags = $i0;
        short $s1 = (short) 1;
        if ((this.mFlags & 1) > 0) {
            $s1 = (short) 1 | (short) 128;
        }
        if ((this.mFlags & 2) > 0) {
            PrepareSpeechTTHandler();
        }
        setInputType($s1);
    }

    public void setEditBoxStayOnAction(boolean $z0) throws  {
        this.mStayOnAction = $z0;
    }

    public void setEditBoxValue(String $r1) throws  {
        this.mValue = $r1;
        setText(this.mValue);
    }

    public void setEditBoxCallback(EditBoxCallback $r1) throws  {
        this.mCallback = $r1;
    }

    public void ShowSoftInput() throws  {
        getInputMethodManager().restartInput(this);
        getInputMethodManager().showSoftInput(this, 2);
    }

    public void HideSoftInput() throws  {
        getInputMethodManager().hideSoftInputFromWindow(getWindowToken(), 0);
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        switch ($r1.getKeyCode()) {
            case 66:
                ActionHandler();
                return true;
            default:
                return super.onKeyDown($i0, $r1);
        }
    }

    public boolean dispatchKeyEventPreIme(KeyEvent $r1) throws  {
        if ($r1.getAction() != 0 && $r1.getAction() != 2) {
            return false;
        }
        switch ($r1.getKeyCode()) {
            case 4:
                if (this.mCallback != null) {
                    this.mCallback.Post(false, null);
                }
                if (this.mContext != null) {
                    this.mContext.getLayoutMgr().HideEditBox();
                }
                return true;
            case 66:
                ActionHandler();
                return true;
            default:
                return false;
        }
    }

    protected void Init(Context $r3) throws  {
        if ($r3 instanceof MainActivity) {
            this.mContext = (MainActivity) $r3;
        }
        setSingleLine();
        setFocusableInTouchMode(true);
        setInputType(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY);
        setTag(WAZE_EDITBOX_TAG);
        setOnEditorActionListener(new C11221());
        addTextChangedListener(new C11232());
    }

    protected void ActionHandler() throws  {
        if (this.mCallback != null) {
            this.mCallback.Post(true, getText().toString());
        }
        if (!this.mStayOnAction && this.mContext != null) {
            this.mContext.getLayoutMgr().HideEditBox();
        }
    }

    protected void PrepareSpeechTTHandler() throws  {
        final C11243 $r1 = new C11243();
        C11254 $r2 = new OnClickListener() {
            public void onClick(View aView) throws  {
                SpeechttManagerBase $r3 = AppService.getNativeManager().getSpeechttManager();
                EditBox.this.HideSoftInput();
                Callback $r5 = $r1;
                $r1.getClass();
                $r3.Start($r5, 0, 5, null, null, 0);
            }
        };
        View $r3 = getRootView().findViewById(C1283R.id.VoiceButton);
        if ($r3 != null) {
            $r3.setOnClickListener($r2);
        }
    }

    private InputMethodManager getInputMethodManager() throws  {
        return (InputMethodManager) getContext().getSystemService("input_method");
    }
}
