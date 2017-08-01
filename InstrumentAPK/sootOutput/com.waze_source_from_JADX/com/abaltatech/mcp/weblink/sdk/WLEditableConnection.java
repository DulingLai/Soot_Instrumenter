package com.abaltatech.mcp.weblink.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.TextView;
import com.abaltatech.weblink.service.interfaces.IWLInputConnection.Stub;

public class WLEditableConnection extends Stub {
    private final WLEditableInputConnection m_connection;
    private final Handler m_handler = new Handler(Looper.getMainLooper());

    class C03444 implements Runnable {
        C03444() throws  {
        }

        public void run() throws  {
            WLEditableConnection.this.m_connection.finishComposingText();
        }
    }

    public WLEditableConnection(TextView $r1) throws  {
        this.m_connection = new WLEditableInputConnection($r1);
    }

    public String getTextBeforeCursor(int $i0, int $i1) throws RemoteException {
        return this.m_connection.getTextBeforeCursor($i0, $i1).toString();
    }

    public String getTextAfterCursor(int $i0, int $i1) throws RemoteException {
        return this.m_connection.getTextAfterCursor($i0, $i1).toString();
    }

    public String getSelectedText(int $i0) throws RemoteException {
        return this.m_connection.getSelectedText($i0).toString();
    }

    public int getCursorCapsMode(int $i0) throws RemoteException {
        return this.m_connection.getCursorCapsMode($i0);
    }

    public ExtractedText getExtractedText(ExtractedTextRequest $r1, int $i0) throws RemoteException {
        return this.m_connection.getExtractedText($r1, $i0);
    }

    public boolean deleteSurroundingText(final int $i0, final int $i1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.deleteSurroundingText($i0, $i1);
            }
        });
        return true;
    }

    public boolean setComposingText(final String $r1, final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.setComposingText($r1, $i0);
            }
        });
        return true;
    }

    public boolean setComposingRegion(final int $i0, final int $i1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.setComposingRegion($i0, $i1);
            }
        });
        return true;
    }

    public boolean finishComposingText() throws RemoteException {
        this.m_handler.post(new C03444());
        return true;
    }

    public boolean commitText(final String $r1, final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.commitText($r1, $i0);
            }
        });
        return true;
    }

    public boolean commitCompletion(final CompletionInfo $r1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.commitCompletion($r1);
            }
        });
        return true;
    }

    public boolean commitCorrection(final CorrectionInfo $r1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.commitCorrection($r1);
            }
        });
        return true;
    }

    public boolean setSelection(final int $i0, final int $i1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.setSelection($i0, $i1);
            }
        });
        return true;
    }

    public boolean performEditorAction(final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.performEditorAction($i0);
            }
        });
        return true;
    }

    public boolean performContextMenuAction(final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.performContextMenuAction($i0);
            }
        });
        return true;
    }

    public boolean beginBatchEdit() throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.beginBatchEdit();
            }
        });
        return true;
    }

    public boolean endBatchEdit() throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.endBatchEdit();
            }
        });
        return true;
    }

    public boolean sendKeyEvent(final KeyEvent $r1) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.sendKeyEvent($r1);
            }
        });
        return true;
    }

    public boolean clearMetaKeyStates(final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.clearMetaKeyStates($i0);
            }
        });
        return true;
    }

    public boolean reportFullscreenMode(final boolean $z0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.reportFullscreenMode($z0);
            }
        });
        return true;
    }

    public boolean performPrivateCommand(final String $r1, final Bundle $r2) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.performPrivateCommand($r1, $r2);
            }
        });
        return true;
    }

    @SuppressLint({"NewApi"})
    public boolean requestCursorUpdates(final int $i0) throws RemoteException {
        this.m_handler.post(new Runnable() {
            public void run() throws  {
                WLEditableConnection.this.m_connection.requestCursorUpdates($i0);
            }
        });
        return true;
    }
}
