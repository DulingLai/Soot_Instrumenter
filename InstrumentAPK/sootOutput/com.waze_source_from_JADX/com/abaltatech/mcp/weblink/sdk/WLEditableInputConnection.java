package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.TextView;

/* compiled from: WLEditableConnection */
class WLEditableInputConnection extends WLBaseInputConnection {
    private static final boolean DEBUG = false;
    private static final String TAG = "EditableInputConnection";
    private int mBatchEditNesting;
    private final TextView mTextView;

    public WLEditableInputConnection(TextView $r1) throws  {
        super($r1, true);
        this.mTextView = $r1;
    }

    public Editable getEditable() throws  {
        TextView $r1 = this.mTextView;
        if ($r1 != null) {
            return $r1.getEditableText();
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean beginBatchEdit() throws  {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.mBatchEditNesting;	 Catch:{ Throwable -> 0x0016 }
        if (r0 < 0) goto L_0x0013;
    L_0x0005:
        r1 = r4.mTextView;	 Catch:{ Throwable -> 0x0016 }
        r1.beginBatchEdit();	 Catch:{ Throwable -> 0x0016 }
        r0 = r4.mBatchEditNesting;	 Catch:{ Throwable -> 0x0016 }
        r0 = r0 + 1;
        r4.mBatchEditNesting = r0;	 Catch:{ Throwable -> 0x0016 }
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        r2 = 1;
        return r2;
    L_0x0013:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        r2 = 0;
        return r2;
    L_0x0016:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLEditableInputConnection.beginBatchEdit():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean endBatchEdit() throws  {
        /*
        r4 = this;
        monitor-enter(r4);
        r0 = r4.mBatchEditNesting;	 Catch:{ Throwable -> 0x0016 }
        if (r0 <= 0) goto L_0x0013;
    L_0x0005:
        r1 = r4.mTextView;	 Catch:{ Throwable -> 0x0016 }
        r1.endBatchEdit();	 Catch:{ Throwable -> 0x0016 }
        r0 = r4.mBatchEditNesting;	 Catch:{ Throwable -> 0x0016 }
        r0 = r0 + -1;
        r4.mBatchEditNesting = r0;	 Catch:{ Throwable -> 0x0016 }
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        r2 = 1;
        return r2;
    L_0x0013:
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        r2 = 0;
        return r2;
    L_0x0016:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ Throwable -> 0x0016 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.WLEditableInputConnection.endBatchEdit():boolean");
    }

    protected void reportFinish() throws  {
        super.reportFinish();
        synchronized (this) {
            while (this.mBatchEditNesting > 0) {
                endBatchEdit();
            }
            this.mBatchEditNesting = -1;
        }
    }

    public boolean clearMetaKeyStates(int $i0) throws  {
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return false;
        }
        KeyListener $r3 = this.mTextView.getKeyListener();
        if ($r3 != null) {
            try {
                $r3.clearMetaKeyState(this.mTextView, $r1, $i0);
            } catch (AbstractMethodError e) {
            }
        }
        return true;
    }

    public boolean commitCompletion(CompletionInfo $r1) throws  {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCompletion($r1);
        this.mTextView.endBatchEdit();
        return true;
    }

    public boolean commitCorrection(CorrectionInfo $r1) throws  {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCorrection($r1);
        this.mTextView.endBatchEdit();
        return true;
    }

    public boolean performEditorAction(int $i0) throws  {
        this.mTextView.onEditorAction($i0);
        return true;
    }

    public boolean performContextMenuAction(int $i0) throws  {
        this.mTextView.beginBatchEdit();
        this.mTextView.onTextContextMenuItem($i0);
        this.mTextView.endBatchEdit();
        return true;
    }

    public ExtractedText getExtractedText(ExtractedTextRequest $r1, int flags) throws  {
        if (this.mTextView != null) {
            ExtractedText $r3 = new ExtractedText();
            if (this.mTextView.extractText($r1, $r3)) {
                return $r3;
            }
        }
        return null;
    }

    public boolean performPrivateCommand(String $r1, Bundle $r2) throws  {
        this.mTextView.onPrivateIMECommand($r1, $r2);
        return true;
    }

    public boolean commitText(CharSequence $r1, int $i0) throws  {
        return super.commitText($r1, $i0);
    }

    @SuppressLint({"InlinedApi"})
    public boolean requestCursorUpdates(int $i0) throws  {
        return ($i0 & -4) != 0 ? false : false;
    }
}
