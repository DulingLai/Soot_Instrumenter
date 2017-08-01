package com.abaltatech.weblink.service.interfaces;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;

public interface IWLInputConnection extends IInterface {

    public static abstract class Stub extends Binder implements IWLInputConnection {
        private static final String DESCRIPTOR = "com.abaltatech.weblink.service.interfaces.IWLInputConnection";
        static final int TRANSACTION_beginBatchEdit = 16;
        static final int TRANSACTION_clearMetaKeyStates = 19;
        static final int TRANSACTION_commitCompletion = 11;
        static final int TRANSACTION_commitCorrection = 12;
        static final int TRANSACTION_commitText = 10;
        static final int TRANSACTION_deleteSurroundingText = 6;
        static final int TRANSACTION_endBatchEdit = 17;
        static final int TRANSACTION_finishComposingText = 9;
        static final int TRANSACTION_getCursorCapsMode = 4;
        static final int TRANSACTION_getExtractedText = 5;
        static final int TRANSACTION_getSelectedText = 3;
        static final int TRANSACTION_getTextAfterCursor = 2;
        static final int TRANSACTION_getTextBeforeCursor = 1;
        static final int TRANSACTION_performContextMenuAction = 15;
        static final int TRANSACTION_performEditorAction = 14;
        static final int TRANSACTION_performPrivateCommand = 21;
        static final int TRANSACTION_reportFullscreenMode = 20;
        static final int TRANSACTION_requestCursorUpdates = 22;
        static final int TRANSACTION_sendKeyEvent = 18;
        static final int TRANSACTION_setComposingRegion = 8;
        static final int TRANSACTION_setComposingText = 7;
        static final int TRANSACTION_setSelection = 13;

        private static class Proxy implements IWLInputConnection {
            private IBinder mRemote;

            public String getInterfaceDescriptor() throws  {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder $r1) throws  {
                this.mRemote = $r1;
            }

            public IBinder asBinder() throws  {
                return this.mRemote;
            }

            public String getTextBeforeCursor(int $i0, int $i1) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    this.mRemote.transact(1, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getTextAfterCursor(int $i0, int $i1) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    this.mRemote.transact(2, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public String getSelectedText(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(3, $r1, $r2, 0);
                    $r2.readException();
                    String $r4 = $r2.readString();
                    return $r4;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public int getCursorCapsMode(int $i0) throws RemoteException {
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(4, $r1, $r2, 0);
                    $r2.readException();
                    $i0 = $r2.readInt();
                    return $i0;
                } finally {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public ExtractedText getExtractedText(ExtractedTextRequest $r1, int $i0) throws RemoteException {
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    ExtractedText $r7;
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    $r2.writeInt($i0);
                    this.mRemote.transact(5, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $r7 = (ExtractedText) ExtractedText.CREATOR.createFromParcel($r3);
                    } else {
                        $r7 = null;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $r7;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean deleteSurroundingText(int $i0, int $i1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    this.mRemote.transact(6, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean setComposingText(String $r1, int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    $r2.writeInt($i0);
                    this.mRemote.transact(7, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $z0 = true;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean setComposingRegion(int $i0, int $i1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    this.mRemote.transact(8, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean finishComposingText() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean commitText(String $r1, int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r2.writeString($r1);
                    $r2.writeInt($i0);
                    this.mRemote.transact(10, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() != 0) {
                        $z0 = true;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean commitCompletion(CompletionInfo $r1) throws RemoteException {
                boolean $z0 = true;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(11, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z0 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean commitCorrection(CorrectionInfo $r1) throws RemoteException {
                boolean $z0 = true;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(12, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z0 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean setSelection(int $i0, int $i1) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    $r1.writeInt($i1);
                    this.mRemote.transact(13, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean performEditorAction(int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(14, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean performContextMenuAction(int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(15, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean beginBatchEdit() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean endBatchEdit() throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean sendKeyEvent(KeyEvent $r1) throws RemoteException {
                boolean $z0 = true;
                Parcel $r2 = Parcel.obtain();
                Parcel $r3 = Parcel.obtain();
                try {
                    $r2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($r1 != null) {
                        $r2.writeInt(1);
                        $r1.writeToParcel($r2, 0);
                    } else {
                        $r2.writeInt(0);
                    }
                    this.mRemote.transact(18, $r2, $r3, 0);
                    $r3.readException();
                    if ($r3.readInt() == 0) {
                        $z0 = false;
                    }
                    $r3.recycle();
                    $r2.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r3.recycle();
                    $r2.recycle();
                }
            }

            public boolean clearMetaKeyStates(int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(19, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean reportFullscreenMode(boolean $z0) throws RemoteException {
                boolean $z1 = true;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    byte $b0;
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    if ($z0) {
                        $b0 = (byte) 1;
                    } else {
                        $b0 = (byte) 0;
                    }
                    $r1.writeInt($b0);
                    this.mRemote.transact(20, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() == 0) {
                        $z1 = false;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z1;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }

            public boolean performPrivateCommand(String $r1, Bundle $r2) throws RemoteException {
                boolean $z0 = true;
                Parcel $r3 = Parcel.obtain();
                Parcel $r4 = Parcel.obtain();
                try {
                    $r3.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r3.writeString($r1);
                    if ($r2 != null) {
                        $r3.writeInt(1);
                        $r2.writeToParcel($r3, 0);
                    } else {
                        $r3.writeInt(0);
                    }
                    this.mRemote.transact(21, $r3, $r4, 0);
                    $r4.readException();
                    if ($r4.readInt() == 0) {
                        $z0 = false;
                    }
                    $r4.recycle();
                    $r3.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r4.recycle();
                    $r3.recycle();
                }
            }

            public boolean requestCursorUpdates(int $i0) throws RemoteException {
                boolean $z0 = false;
                Parcel $r1 = Parcel.obtain();
                Parcel $r2 = Parcel.obtain();
                try {
                    $r1.writeInterfaceToken(Stub.DESCRIPTOR);
                    $r1.writeInt($i0);
                    this.mRemote.transact(22, $r1, $r2, 0);
                    $r2.readException();
                    if ($r2.readInt() != 0) {
                        $z0 = true;
                    }
                    $r2.recycle();
                    $r1.recycle();
                    return $z0;
                } catch (Throwable th) {
                    $r2.recycle();
                    $r1.recycle();
                }
            }
        }

        public Stub() throws  {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWLInputConnection asInterface(IBinder $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            IInterface $r1 = $r0.queryLocalInterface(DESCRIPTOR);
            if ($r1 == null || !($r1 instanceof IWLInputConnection)) {
                return new Proxy($r0);
            }
            return (IWLInputConnection) $r1;
        }

        public IBinder asBinder() throws  {
            return this;
        }

        public boolean onTransact(int $i0, Parcel $r1, Parcel $r2, int $i1) throws RemoteException {
            byte $b2 = (byte) 0;
            String $r3;
            boolean $z0;
            switch ($i0) {
                case 1:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getTextBeforeCursor($r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 2:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getTextAfterCursor($r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 3:
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = getSelectedText($r1.readInt());
                    $r2.writeNoException();
                    $r2.writeString($r3);
                    return true;
                case 4:
                    $r1.enforceInterface(DESCRIPTOR);
                    $i0 = getCursorCapsMode($r1.readInt());
                    $r2.writeNoException();
                    $r2.writeInt($i0);
                    return true;
                case 5:
                    ExtractedTextRequest $r6;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r6 = (ExtractedTextRequest) ExtractedTextRequest.CREATOR.createFromParcel($r1);
                    } else {
                        $r6 = null;
                    }
                    ExtractedText $r7 = getExtractedText($r6, $r1.readInt());
                    $r2.writeNoException();
                    if ($r7 != null) {
                        $r2.writeInt(1);
                        $r7.writeToParcel($r2, 1);
                        return true;
                    }
                    $r2.writeInt(0);
                    return true;
                case 6:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = deleteSurroundingText($r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 7:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = setComposingText($r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 8:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = setComposingRegion($r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 9:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = finishComposingText();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 10:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = commitText($r1.readString(), $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 11:
                    CompletionInfo $r8;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r8 = (CompletionInfo) CompletionInfo.CREATOR.createFromParcel($r1);
                    } else {
                        $r8 = null;
                    }
                    $z0 = commitCompletion($r8);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 12:
                    CorrectionInfo $r9;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r9 = (CorrectionInfo) CorrectionInfo.CREATOR.createFromParcel($r1);
                    } else {
                        $r9 = null;
                    }
                    $z0 = commitCorrection($r9);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 13:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = setSelection($r1.readInt(), $r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 14:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = performEditorAction($r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 15:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = performContextMenuAction($r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 16:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = beginBatchEdit();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 17:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = endBatchEdit();
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 18:
                    KeyEvent $r10;
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $r10 = (KeyEvent) KeyEvent.CREATOR.createFromParcel($r1);
                    } else {
                        $r10 = null;
                    }
                    $z0 = sendKeyEvent($r10);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 19:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = clearMetaKeyStates($r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 20:
                    $r1.enforceInterface(DESCRIPTOR);
                    if ($r1.readInt() != 0) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    $z0 = reportFullscreenMode($z0);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 21:
                    Bundle $r11;
                    $r1.enforceInterface(DESCRIPTOR);
                    $r3 = $r1.readString();
                    if ($r1.readInt() != 0) {
                        $r11 = (Bundle) Bundle.CREATOR.createFromParcel($r1);
                    } else {
                        $r11 = null;
                    }
                    $z0 = performPrivateCommand($r3, $r11);
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 22:
                    $r1.enforceInterface(DESCRIPTOR);
                    $z0 = requestCursorUpdates($r1.readInt());
                    $r2.writeNoException();
                    if ($z0) {
                        $b2 = (byte) 1;
                    }
                    $r2.writeInt($b2);
                    return true;
                case 1598968902:
                    $r2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact($i0, $r1, $r2, $i1);
            }
        }
    }

    boolean beginBatchEdit() throws RemoteException;

    boolean clearMetaKeyStates(int i) throws RemoteException;

    boolean commitCompletion(CompletionInfo completionInfo) throws RemoteException;

    boolean commitCorrection(CorrectionInfo correctionInfo) throws RemoteException;

    boolean commitText(String str, int i) throws RemoteException;

    boolean deleteSurroundingText(int i, int i2) throws RemoteException;

    boolean endBatchEdit() throws RemoteException;

    boolean finishComposingText() throws RemoteException;

    int getCursorCapsMode(int i) throws RemoteException;

    ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) throws RemoteException;

    String getSelectedText(int i) throws RemoteException;

    String getTextAfterCursor(int i, int i2) throws RemoteException;

    String getTextBeforeCursor(int i, int i2) throws RemoteException;

    boolean performContextMenuAction(int i) throws RemoteException;

    boolean performEditorAction(int i) throws RemoteException;

    boolean performPrivateCommand(String str, Bundle bundle) throws RemoteException;

    boolean reportFullscreenMode(boolean z) throws RemoteException;

    boolean requestCursorUpdates(int i) throws RemoteException;

    boolean sendKeyEvent(KeyEvent keyEvent) throws RemoteException;

    boolean setComposingRegion(int i, int i2) throws RemoteException;

    boolean setComposingText(String str, int i) throws RemoteException;

    boolean setSelection(int i, int i2) throws RemoteException;
}
