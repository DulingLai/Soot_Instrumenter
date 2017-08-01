package com.abaltatech.mcp.weblink.sdk;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Editable.Factory;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.util.Log;
import android.util.LogPrinter;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

/* compiled from: WLEditableConnection */
class WLBaseInputConnection implements InputConnection {
    static final Object COMPOSING = new ComposingText();
    private static final boolean DEBUG = true;
    private static final int META_SELECTING = 2048;
    private static final String TAG = "EditableInputConnection";
    private Object[] mDefaultComposingSpans;
    final boolean mDummyMode;
    Editable mEditable;
    KeyCharacterMap mKeyCharacterMap;
    final View mTargetView;

    public boolean beginBatchEdit() throws  {
        return false;
    }

    public boolean commitCompletion(CompletionInfo text) throws  {
        return false;
    }

    public boolean commitCorrection(CorrectionInfo correctionInfo) throws  {
        return false;
    }

    public boolean endBatchEdit() throws  {
        return false;
    }

    public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) throws  {
        return null;
    }

    public boolean performContextMenuAction(int id) throws  {
        return false;
    }

    public boolean performPrivateCommand(String action, Bundle data) throws  {
        return false;
    }

    public boolean reportFullscreenMode(boolean enabled) throws  {
        return true;
    }

    public boolean requestCursorUpdates(int cursorUpdateMode) throws  {
        return false;
    }

    WLBaseInputConnection(boolean $z0) throws  {
        this.mTargetView = null;
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.mDummyMode = $z0;
    }

    public WLBaseInputConnection(View $r1, boolean $z0) throws  {
        this.mTargetView = $r1;
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.mDummyMode = $z0;
    }

    public static final void removeComposingSpans(Spannable $r0) throws  {
        $r0.removeSpan(COMPOSING);
        Object[] $r2 = $r0.getSpans(0, $r0.length(), Object.class);
        if ($r2 != null) {
            for (int $i0 = $r2.length - 1; $i0 >= 0; $i0--) {
                Object $r1 = $r2[$i0];
                if (($r0.getSpanFlags($r1) & 256) != 0) {
                    $r0.removeSpan($r1);
                }
            }
        }
    }

    public static void setComposingSpans(Spannable $r0) throws  {
        setComposingSpans($r0, 0, $r0.length());
    }

    public static void setComposingSpans(Spannable $r0, int $i0, int $i1) throws  {
        Object[] $r2 = $r0.getSpans($i0, $i1, Object.class);
        if ($r2 != null) {
            for (int $i2 = $r2.length - 1; $i2 >= 0; $i2--) {
                Object $r1 = $r2[$i2];
                if ($r1 == COMPOSING) {
                    $r0.removeSpan($r1);
                } else {
                    int $i3 = $r0.getSpanFlags($r1);
                    if (($i3 & 307) != 289) {
                        $r0.setSpan($r1, $r0.getSpanStart($r1), $r0.getSpanEnd($r1), (($i3 & -52) | 256) | 33);
                    }
                }
            }
        }
        $r0.setSpan(COMPOSING, $i0, $i1, 289);
    }

    public static int getComposingSpanStart(Spannable $r0) throws  {
        return $r0.getSpanStart(COMPOSING);
    }

    public static int getComposingSpanEnd(Spannable $r0) throws  {
        return $r0.getSpanEnd(COMPOSING);
    }

    public Editable getEditable() throws  {
        if (this.mEditable == null) {
            this.mEditable = Factory.getInstance().newEditable("");
            Selection.setSelection(this.mEditable, 0);
        }
        return this.mEditable;
    }

    protected void reportFinish() throws  {
    }

    public boolean clearMetaKeyStates(int $i0) throws  {
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return false;
        }
        MetaKeyKeyListener.clearMetaKeyState($r1, $i0);
        return true;
    }

    public boolean commitText(CharSequence $r1, int $i0) throws  {
        Log.v(TAG, "commitText " + $r1);
        replaceText($r1, $i0, false);
        sendCurrentText();
        return true;
    }

    public boolean deleteSurroundingText(int $i0, int $i1) throws  {
        Log.v(TAG, "deleteSurroundingText " + $i0 + " / " + $i1);
        Editable $r3 = getEditable();
        if ($r3 == null) {
            return false;
        }
        beginBatchEdit();
        int $i2 = Selection.getSelectionStart($r3);
        int $i3 = $i2;
        int $i4 = Selection.getSelectionEnd($r3);
        int $i5 = $i4;
        if ($i2 > $i4) {
            $i3 = $i4;
            $i5 = $i2;
        }
        $i2 = getComposingSpanStart($r3);
        $i4 = $i2;
        int $i6 = getComposingSpanEnd($r3);
        int $i7 = $i6;
        if ($i6 < $i2) {
            $i4 = $i6;
            $i7 = $i2;
        }
        if (!($i4 == -1 || $i7 == -1)) {
            if ($i4 < $i3) {
                $i3 = $i4;
            }
            if ($i7 > $i5) {
                $i5 = $i7;
            }
        }
        $i2 = 0;
        if ($i0 > 0) {
            $i0 = $i3 - $i0;
            if ($i0 < 0) {
                $i0 = 0;
            }
            $r3.delete($i0, $i3);
            $i2 = $i3 - $i0;
        }
        if ($i1 > 0) {
            $i0 = $i5 - $i2;
            $i1 = $i0 + $i1;
            if ($i1 > $r3.length()) {
                $i1 = $r3.length();
            }
            $r3.delete($i0, $i1);
        }
        endBatchEdit();
        return true;
    }

    public boolean finishComposingText() throws  {
        Log.v(TAG, "finishComposingText");
        Editable $r1 = getEditable();
        if ($r1 != null) {
            beginBatchEdit();
            removeComposingSpans($r1);
            sendCurrentText();
            endBatchEdit();
        }
        return true;
    }

    public int getCursorCapsMode(int $i0) throws  {
        if (this.mDummyMode) {
            return 0;
        }
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return 0;
        }
        int $i1 = Selection.getSelectionStart($r1);
        int $i2 = $i1;
        int $i3 = Selection.getSelectionEnd($r1);
        if ($i1 > $i3) {
            $i2 = $i3;
        }
        return TextUtils.getCapsMode($r1, $i2, $i0);
    }

    public CharSequence getTextBeforeCursor(int $i1, int $i0) throws  {
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return null;
        }
        int $i2 = Selection.getSelectionStart($r1);
        int $i3 = $i2;
        int $i4 = Selection.getSelectionEnd($r1);
        if ($i2 > $i4) {
            $i3 = $i4;
        }
        if ($i3 <= 0) {
            return "";
        }
        if ($i1 > $i3) {
            $i1 = $i3;
        }
        if (($i0 & 1) != 0) {
            return $r1.subSequence($i3 - $i1, $i3);
        }
        return TextUtils.substring($r1, $i3 - $i1, $i3);
    }

    public CharSequence getSelectedText(int $i0) throws  {
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return null;
        }
        int $i1 = Selection.getSelectionStart($r1);
        int $i2 = $i1;
        int $i3 = Selection.getSelectionEnd($r1);
        int $i4 = $i3;
        if ($i1 > $i3) {
            $i2 = $i3;
            $i4 = $i1;
        }
        if ($i2 == $i4) {
            return null;
        }
        if (($i0 & 1) != 0) {
            return $r1.subSequence($i2, $i4);
        }
        return TextUtils.substring($r1, $i2, $i4);
    }

    public CharSequence getTextAfterCursor(int $i1, int $i0) throws  {
        Editable $r1 = getEditable();
        if ($r1 == null) {
            return null;
        }
        int $i2 = Selection.getSelectionStart($r1);
        int $i3 = Selection.getSelectionEnd($r1);
        int $i4 = $i3;
        if ($i2 > $i3) {
            $i4 = $i2;
        }
        if ($i4 < 0) {
            $i4 = 0;
        }
        if ($i4 + $i1 > $r1.length()) {
            $i1 = $r1.length() - $i4;
        }
        if (($i0 & 1) != 0) {
            return $r1.subSequence($i4, $i4 + $i1);
        }
        return TextUtils.substring($r1, $i4, $i4 + $i1);
    }

    public boolean performEditorAction(int actionCode) throws  {
        long $l1 = SystemClock.uptimeMillis();
        sendKeyEvent(new KeyEvent($l1, $l1, 0, 66, 0, 0, -1, 0, 22));
        sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), $l1, 1, 66, 0, 0, -1, 0, 22));
        return true;
    }

    public boolean setComposingText(CharSequence $r1, int $i0) throws  {
        Log.v(TAG, "setComposingText " + $r1);
        replaceText($r1, $i0, true);
        return true;
    }

    public boolean setComposingRegion(int $i0, int $i1) throws  {
        Editable $r1 = getEditable();
        if ($r1 != null) {
            beginBatchEdit();
            removeComposingSpans($r1);
            int $i2 = $i0;
            int $i3 = $i1;
            if ($i0 > $i1) {
                $i2 = $i1;
                $i3 = $i0;
            }
            $i0 = $r1.length();
            if ($i2 < 0) {
                $i2 = 0;
            }
            if ($i3 < 0) {
                $i3 = 0;
            }
            if ($i2 > $i0) {
                $i2 = $i0;
            }
            if ($i3 > $i0) {
                $i3 = $i0;
            }
            ensureDefaultComposingSpans();
            if (this.mDefaultComposingSpans != null) {
                for (Object $r3 : this.mDefaultComposingSpans) {
                    $r1.setSpan($r3, $i2, $i3, 289);
                }
            }
            $r1.setSpan(COMPOSING, $i2, $i3, 289);
            sendCurrentText();
            endBatchEdit();
        }
        return true;
    }

    public boolean setSelection(int $i0, int $i1) throws  {
        Log.v(TAG, "setSelection " + $i0 + ", " + $i1);
        Editable $r3 = getEditable();
        if ($r3 == null) {
            return false;
        }
        int $i2 = $r3.length();
        if ($i0 > $i2) {
            return true;
        }
        if ($i1 > $i2) {
            return true;
        }
        if ($i0 < 0) {
            return true;
        }
        if ($i1 < 0) {
            return true;
        }
        if ($i0 != $i1 || MetaKeyKeyListener.getMetaState($r3, 2048) == 0) {
            Selection.setSelection($r3, $i0, $i1);
            return true;
        }
        Selection.extendSelection($r3, $i0);
        return true;
    }

    public boolean sendKeyEvent(KeyEvent $r1) throws  {
        if (this.mTargetView != null) {
            this.mTargetView.dispatchKeyEvent($r1);
        }
        return false;
    }

    private void sendCurrentText() throws  {
        if (this.mDummyMode) {
            Editable $r3 = getEditable();
            if ($r3 != null) {
                int $i0 = $r3.length();
                if ($i0 != 0) {
                    if ($i0 == 1) {
                        if (this.mKeyCharacterMap == null) {
                            this.mKeyCharacterMap = KeyCharacterMap.load(-1);
                        }
                        char[] $r2 = new char[1];
                        $r3.getChars(0, 1, $r2, 0);
                        KeyEvent[] $r5 = this.mKeyCharacterMap.getEvents($r2);
                        if ($r5 != null) {
                            for ($i0 = 0; $i0 < $r5.length; $i0++) {
                                Log.v(TAG, "Sending: " + $r5[$i0]);
                                sendKeyEvent($r5[$i0]);
                            }
                            $r3.clear();
                            return;
                        }
                    }
                    sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), $r3.toString(), -1, 0));
                    $r3.clear();
                }
            }
        }
    }

    private void ensureDefaultComposingSpans() throws  {
        if (this.mDefaultComposingSpans != null) {
        }
    }

    private void replaceText(CharSequence $r1, int $i0, boolean $z0) throws  {
        Editable $r2 = getEditable();
        if ($r2 != null) {
            beginBatchEdit();
            int $i1 = getComposingSpanStart($r2);
            int $i2 = $i1;
            int $i3 = getComposingSpanEnd($r2);
            int $i4 = $i3;
            Log.v(TAG, "Composing span: " + $i1 + " to " + $i3);
            if ($i3 < $i1) {
                $i2 = $i3;
                $i4 = $i1;
            }
            if ($i2 == -1 || $i4 == -1) {
                $i1 = Selection.getSelectionStart($r2);
                $i2 = $i1;
                $i3 = Selection.getSelectionEnd($r2);
                $i4 = $i3;
                if ($i1 < 0) {
                    $i2 = 0;
                }
                if ($i3 < 0) {
                    $i4 = 0;
                }
                if ($i4 < $i2) {
                    $i1 = $i2;
                    $i2 = $i4;
                    $i4 = $i1;
                }
            } else {
                removeComposingSpans($r2);
            }
            if ($z0) {
                Spannable $r5;
                if ($r1 instanceof Spannable) {
                    $r5 = (Spannable) $r1;
                } else {
                    $r5 = r11;
                    SpannableStringBuilder r11 = new SpannableStringBuilder($r1);
                    $r1 = $r5;
                    ensureDefaultComposingSpans();
                    if (this.mDefaultComposingSpans != null) {
                        for (Object $r7 : this.mDefaultComposingSpans) {
                            $r5.setSpan($r7, 0, $r5.length(), 289);
                        }
                    }
                }
                setComposingSpans($r5);
            }
            Log.v(TAG, "Replacing from " + $i2 + " to " + $i4 + " with \"" + $r1 + "\", composing=" + $z0 + ", type=" + $r1.getClass().getCanonicalName());
            LogPrinter $r9 = r0;
            LogPrinter logPrinter = new LogPrinter(2, TAG);
            $r9.println("Current text:");
            TextUtils.dumpSpans($r2, $r9, "  ");
            $r9.println("Composing text:");
            TextUtils.dumpSpans($r1, $r9, "  ");
            if ($i0 > 0) {
                $i0 += $i4 - 1;
            } else {
                $i0 += $i2;
            }
            if ($i0 < 0) {
                $i0 = 0;
            }
            if ($i0 > $r2.length()) {
                $i0 = $r2.length();
            }
            Selection.setSelection($r2, $i0);
            $r2.replace($i2, $i4, $r1);
            $r9 = logPrinter;
            logPrinter = new LogPrinter(2, TAG);
            $r9.println("Final text:");
            TextUtils.dumpSpans($r2, $r9, "  ");
            endBatchEdit();
        }
    }
}
