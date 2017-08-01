package com.waze;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.button.PillButton;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.timer.TimerBar;

public final class MsgBox {
    private static final int CONFIRM_DIALOG_ABORT = 6;
    private static final int CONFIRM_DIALOG_CANCEL = 2;
    private static final int CONFIRM_DIALOG_CLOSE = 0;
    private static final int CONFIRM_DIALOG_COMMIT = 5;
    private static final int CONFIRM_DIALOG_IGNORE = 8;
    private static final int CONFIRM_DIALOG_NO = 4;
    private static final int CONFIRM_DIALOG_OK = 1;
    private static final int CONFIRM_DIALOG_RETRY = 7;
    private static final int CONFIRM_DIALOG_YES = 3;
    public static final int WAZE_MSG_BOX_RES_CANCEL = 0;
    public static final int WAZE_MSG_BOX_RES_OK = 1;
    private static MsgBox mInstance = null;
    private ActivityBase mHijackingDialogActivity;
    private volatile boolean mIsBlocking = false;
    private boolean mTripSuggestButtonPressed = false;
    private String mTripTypeStr;
    private Dialog tripDialog = null;

    static class AnonymousClass32 implements OnClickListener {
        final /* synthetic */ DialogInterface.OnClickListener val$callback;
        final /* synthetic */ Dialog val$dialog;
        final /* synthetic */ boolean val$highlightSecondary;

        AnonymousClass32(Dialog $r1, DialogInterface.OnClickListener $r2, boolean $z0) throws  {
            this.val$dialog = $r1;
            this.val$callback = $r2;
            this.val$highlightSecondary = $z0;
        }

        public void onClick(View v) throws  {
            ((TimerBar) this.val$dialog.findViewById(C1283R.id.confirmSendTimer)).stop();
            ((TimerBar) this.val$dialog.findViewById(C1283R.id.confirmCloseTimer)).stop();
            try {
                this.val$dialog.setOnCancelListener(null);
                this.val$dialog.cancel();
                this.val$dialog.dismiss();
            } catch (Exception e) {
            }
            if (this.val$callback != null) {
                this.val$callback.onClick(this.val$dialog, this.val$highlightSecondary ? (byte) 1 : (byte) 0);
            }
        }
    }

    static class AnonymousClass33 implements OnClickListener {
        final /* synthetic */ DialogInterface.OnClickListener val$callback;
        final /* synthetic */ Dialog val$dialog;
        final /* synthetic */ boolean val$highlightSecondary;

        AnonymousClass33(Dialog $r1, DialogInterface.OnClickListener $r2, boolean $z0) throws  {
            this.val$dialog = $r1;
            this.val$callback = $r2;
            this.val$highlightSecondary = $z0;
        }

        public void onClick(View v) throws  {
            try {
                this.val$dialog.setOnCancelListener(null);
                this.val$dialog.cancel();
                this.val$dialog.dismiss();
            } catch (Exception e) {
            }
            if (this.val$callback != null) {
                this.val$callback.onClick(this.val$dialog, this.val$highlightSecondary ? (byte) 0 : (byte) 1);
            }
        }
    }

    class C12033 implements Runnable {
        C12033() throws  {
        }

        public void run() throws  {
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().openVoicePopup();
            }
        }
    }

    class C12044 implements Runnable {
        C12044() throws  {
        }

        public void run() throws  {
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().closeVoicePopup();
            }
        }
    }

    private class MsgBoxOnClick implements DialogInterface.OnClickListener {
        private volatile long mCbContext;
        private volatile int mCbRes;

        class C12141 implements Runnable {
            C12141() throws  {
            }

            public void run() throws  {
                MsgBox.this.SpecialMsgBoxCallbackNTV(MsgBoxOnClick.this.mCbRes, MsgBoxOnClick.this.mCbContext);
            }
        }

        public MsgBoxOnClick(int $i0, long $l1) throws  {
            this.mCbRes = $i0;
            this.mCbContext = $l1;
        }

        public void onClick(DialogInterface $r1, int which) throws  {
            $r1.cancel();
            AppService.getNativeManager().PostRunnable(new C12141());
            if (MsgBox.this.mIsBlocking) {
                synchronized (MsgBox.mInstance) {
                    MsgBox.mInstance.notify();
                }
            }
        }
    }

    private native void ConfirmDialogCallbackNTV(int i, long j, long j2, long j3) throws ;

    private native void InitMsgBoxNTV() throws ;

    private native void MsgBoxCallbackNTV(long j) throws ;

    private native void SpecialMsgBoxCallbackNTV(int i, long j) throws ;

    public static android.app.Dialog openConfirmDialogJavaCallback(java.lang.String r34, java.lang.String r35, boolean r36, android.content.DialogInterface.OnClickListener r37, java.lang.String r38, java.lang.String r39, int r40, java.lang.String r41, android.content.DialogInterface.OnCancelListener r42, boolean r43, boolean r44, boolean r45, android.view.View r46, android.widget.FrameLayout.LayoutParams r47) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:29:0x013b
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = com.waze.ifs.ui.ActivityBase.getRunningCount();
        r4 = 1;
        if (r3 < r4) goto L_0x000d;
    L_0x0007:
        r5 = com.waze.AppService.getActiveActivity();
        if (r5 != 0) goto L_0x000f;
    L_0x000d:
        r6 = 0;
        return r6;
    L_0x000f:
        if (r43 == 0) goto L_0x013f;
    L_0x0011:
        r7 = r39;
    L_0x0013:
        if (r43 == 0) goto L_0x0146;
    L_0x0015:
        com.waze.AppService.getNativeManager();
        if (r45 == 0) goto L_0x014d;
    L_0x001a:
        r5 = com.waze.AppService.getMainActivity();
    L_0x001e:
        r8 = new android.app.Dialog;
        r4 = 2131427580; // 0x7f0b00fc float:1.847678E38 double:1.053065144E-314;
        r8.<init>(r5, r4);
        r5.setDialog(r8);
        if (r44 == 0) goto L_0x0156;
    L_0x002b:
        r4 = 2130903156; // 0x7f030074 float:1.7413122E38 double:1.052806044E-314;
        r8.setContentView(r4);
    L_0x0031:
        if (r38 == 0) goto L_0x015d;
    L_0x0033:
        r4 = 2131690316; // 0x7f0f034c float:1.9009672E38 double:1.0531949527E-314;
        r9 = r8.findViewById(r4);
        r11 = r9;
        r11 = (android.widget.TextView) r11;
        r10 = r11;
        r0 = r38;
        r10.setText(r0);
    L_0x0045:
        if (r40 > 0) goto L_0x016e;
    L_0x0047:
        r4 = 2131690320; // 0x7f0f0350 float:1.900968E38 double:1.0531949547E-314;
        r9 = r8.findViewById(r4);
        r13 = r9;
        r13 = (com.waze.view.timer.TimerBar) r13;
        r12 = r13;
        r12.stop();
        r4 = 2131690317; // 0x7f0f034d float:1.9009674E38 double:1.053194953E-314;
        r9 = r8.findViewById(r4);
        r14 = r9;
        r14 = (com.waze.view.timer.TimerBar) r14;
        r12 = r14;
        r12.stop();
    L_0x0067:
        r4 = 2131690319; // 0x7f0f034f float:1.9009678E38 double:1.053194954E-314;
        r9 = r8.findViewById(r4);
        r15 = r9;
        r15 = (android.widget.TextView) r15;
        r10 = r15;
        r10.setText(r7);
        r4 = 2131690325; // 0x7f0f0355 float:1.900969E38 double:1.053194957E-314;
        r9 = r8.findViewById(r4);
        r16 = r9;
        r16 = (android.widget.TextView) r16;
        r10 = r16;
        r0 = r34;
        r10.setText(r0);
        r4 = 2131690326; // 0x7f0f0356 float:1.9009692E38 double:1.0531949576E-314;
        r9 = r8.findViewById(r4);
        r17 = r9;
        r17 = (android.widget.TextView) r17;
        r10 = r17;
        r18 = android.text.method.LinkMovementMethod.getInstance();
        r0 = r18;
        r10.setMovementMethod(r0);
        r0 = r35;
        r19 = android.text.Html.fromHtml(r0);
        r0 = r19;
        r10.setText(r0);
        if (r41 == 0) goto L_0x00df;
    L_0x00ac:
        r0 = r41;
        r40 = r0.length();
        if (r40 <= 0) goto L_0x00df;
    L_0x00b4:
        r4 = 2131690323; // 0x7f0f0353 float:1.9009686E38 double:1.053194956E-314;
        r9 = r8.findViewById(r4);
        r21 = r9;
        r21 = (android.widget.ImageView) r21;
        r20 = r21;
        r0 = r41;
        r34 = r0.toLowerCase();
        r0 = r34;
        r40 = com.waze.ResManager.GetDrawableId(r0);
        r0 = r20;
        r1 = r40;
        r0.setImageResource(r1);
        r4 = 2131690322; // 0x7f0f0352 float:1.9009684E38 double:1.0531949557E-314;
        r9 = r8.findViewById(r4);
        r4 = 0;
        r9.setVisibility(r4);
    L_0x00df:
        r4 = 2131690315; // 0x7f0f034b float:1.900967E38 double:1.053194952E-314;
        r9 = r8.findViewById(r4);
        r22 = new com.waze.MsgBox$32;
        r0 = r22;
        r1 = r37;
        r2 = r43;
        r0.<init>(r8, r1, r2);
        r0 = r22;
        r9.setOnClickListener(r0);
        r4 = 2131690318; // 0x7f0f034e float:1.9009676E38 double:1.0531949537E-314;
        r23 = r8.findViewById(r4);
        r24 = new com.waze.MsgBox$33;
        r0 = r24;
        r1 = r37;
        r2 = r43;
        r0.<init>(r8, r1, r2);
        r0 = r23;
        r1 = r24;
        r0.setOnClickListener(r1);
        if (r46 == 0) goto L_0x012d;
    L_0x0111:
        r4 = 2131690327; // 0x7f0f0357 float:1.9009694E38 double:1.053194958E-314;
        r25 = r8.findViewById(r4);
        r27 = r25;
        r27 = (android.widget.FrameLayout) r27;
        r26 = r27;
        r4 = 0;
        r0 = r26;
        r0.setVisibility(r4);
        r0 = r26;
        r1 = r46;
        r2 = r47;
        r0.addView(r1, r2);
    L_0x012d:
        r0 = r42;
        r8.setOnCancelListener(r0);
        r0 = r23;
        setAnimation(r9, r0);
        showDialogAnimated(r8);
        return r8;
        goto L_0x013f;
    L_0x013c:
        goto L_0x0013;
    L_0x013f:
        r7 = r38;
        goto L_0x013c;
        goto L_0x0146;
    L_0x0143:
        goto L_0x0015;
    L_0x0146:
        r38 = r39;
        goto L_0x0143;
        goto L_0x014d;
    L_0x014a:
        goto L_0x001e;
    L_0x014d:
        r5 = com.waze.AppService.getActiveActivity();
        goto L_0x014a;
        goto L_0x0156;
    L_0x0153:
        goto L_0x0031;
    L_0x0156:
        r4 = 2130903151; // 0x7f03006f float:1.7413112E38 double:1.0528060415E-314;
        r8.setContentView(r4);
        goto L_0x0153;
    L_0x015d:
        r4 = 2131690315; // 0x7f0f034b float:1.900967E38 double:1.053194952E-314;
        r9 = r8.findViewById(r4);
        goto L_0x0168;
    L_0x0165:
        goto L_0x0045;
    L_0x0168:
        r4 = 8;
        r9.setVisibility(r4);
        goto L_0x0165;
    L_0x016e:
        if (r36 == 0) goto L_0x01a7;
    L_0x0170:
        r4 = 2131690320; // 0x7f0f0350 float:1.900968E38 double:1.0531949547E-314;
        r9 = r8.findViewById(r4);
        r28 = r9;
        r28 = (com.waze.view.timer.TimerBar) r28;
        r12 = r28;
        r0 = r40;
        r12.setTime(r0);
        r4 = 2131690320; // 0x7f0f0350 float:1.900968E38 double:1.0531949547E-314;
        r9 = r8.findViewById(r4);
        r29 = r9;
        r29 = (com.waze.view.timer.TimerBar) r29;
        r12 = r29;
        r12.start();
        r4 = 2131690317; // 0x7f0f034d float:1.9009674E38 double:1.053194953E-314;
        r9 = r8.findViewById(r4);
        r30 = r9;
        r30 = (com.waze.view.timer.TimerBar) r30;
        r12 = r30;
        goto L_0x01a3;
    L_0x01a0:
        goto L_0x0067;
    L_0x01a3:
        r12.stop();
        goto L_0x01a0;
    L_0x01a7:
        r4 = 2131690317; // 0x7f0f034d float:1.9009674E38 double:1.053194953E-314;
        r9 = r8.findViewById(r4);
        r31 = r9;
        r31 = (com.waze.view.timer.TimerBar) r31;
        r12 = r31;
        r0 = r40;
        r12.setTime(r0);
        r4 = 2131690317; // 0x7f0f034d float:1.9009674E38 double:1.053194953E-314;
        r9 = r8.findViewById(r4);
        r32 = r9;
        r32 = (com.waze.view.timer.TimerBar) r32;
        r12 = r32;
        r12.start();
        r4 = 2131690320; // 0x7f0f0350 float:1.900968E38 double:1.0531949547E-314;
        r9 = r8.findViewById(r4);
        r33 = r9;
        r33 = (com.waze.view.timer.TimerBar) r33;
        r12 = r33;
        goto L_0x01da;
    L_0x01d7:
        goto L_0x0067;
    L_0x01da:
        r12.stop();
        goto L_0x01d7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.MsgBox.openConfirmDialogJavaCallback(java.lang.String, java.lang.String, boolean, android.content.DialogInterface$OnClickListener, java.lang.String, java.lang.String, int, java.lang.String, android.content.DialogInterface$OnCancelListener, boolean, boolean, boolean, android.view.View, android.widget.FrameLayout$LayoutParams):android.app.Dialog");
    }

    static void InitNativeLayer() throws  {
        getInstance().InitMsgBoxNTV();
    }

    public static MsgBox getInstance() throws  {
        if (mInstance == null) {
            mInstance = new MsgBox();
        }
        return mInstance;
    }

    void setBlocking(boolean $z0) throws  {
        mInstance.mIsBlocking = $z0;
    }

    public void Show(byte[] $r1, byte[] $r2, byte[] $r3, byte[] $r4, long $l0) throws  {
        NativeManager $r7 = AppService.getNativeManager();
        MainActivity $r8 = AppService.getMainActivity();
        if ($r8 != null && $r7 != null) {
            if ($r7.IsNativeThread()) {
                final byte[] bArr = $r1;
                final byte[] bArr2 = $r2;
                final byte[] bArr3 = $r3;
                final byte[] bArr4 = $r4;
                final long j = $l0;
                $r8.runOnUiThread(new Runnable() {
                    public void run() throws  {
                        MsgBox.this.ShowRun(bArr, bArr2, bArr3, bArr4, j);
                    }
                });
                if (this.mIsBlocking) {
                    try {
                        synchronized (mInstance) {
                            mInstance.wait();
                            this.mIsBlocking = false;
                        }
                        Log.w(Logger.TAG, "Continue running the native thread");
                        return;
                    } catch (Exception e) {
                        Logger.m43w("Error waiting for the message to finish");
                        return;
                    }
                }
                return;
            }
            ShowRun($r1, $r2, $r3, $r4, $l0);
        }
    }

    public void Show(String $r1, String $r2, String $r3, String $r4, DialogInterface.OnClickListener $r5, DialogInterface.OnClickListener $r6) throws  {
        NativeManager $r9 = AppService.getNativeManager();
        MainActivity $r10 = AppService.getMainActivity();
        if ($r10 != null) {
            if ($r9 == null || !$r9.IsNativeThread()) {
                ShowRun($r1, $r2, $r3, $r4, $r5, $r6);
                return;
            }
            final String str = $r1;
            final String str2 = $r2;
            final String str3 = $r3;
            final String str4 = $r4;
            final DialogInterface.OnClickListener onClickListener = $r5;
            final DialogInterface.OnClickListener onClickListener2 = $r6;
            $r10.runOnUiThread(new Runnable() {
                public void run() throws  {
                    MsgBox.this.ShowRun(str, str2, str3, str4, onClickListener, onClickListener2);
                }
            });
            if (this.mIsBlocking) {
                try {
                    synchronized (mInstance) {
                        mInstance.wait();
                        this.mIsBlocking = false;
                    }
                    Log.w(Logger.TAG, "Continue running the native thread");
                } catch (Exception e) {
                    Logger.m43w("Error waiting for the message to finish");
                }
            }
        }
    }

    public static void Notify() throws  {
        try {
            synchronized (mInstance) {
                mInstance.notify();
            }
        } catch (Exception $r0) {
            Logger.m43w("Error notifying the message box: " + $r0.getMessage());
        }
    }

    public static void ShowOk(String $r0, String $r1, String $r2, DialogInterface.OnClickListener $r3) throws  {
        mInstance.Show($r0, $r1, $r2, null, $r3, null);
    }

    public void ShowRun(String $r1, String $r2, String $r3, String $r4, DialogInterface.OnClickListener $r5, DialogInterface.OnClickListener $r6) throws  {
        AlertDialog $r8 = builder().create();
        $r8.setCancelable(false);
        if (!($r3 == null || $r5 == null)) {
            $r8.setButton(-1, new String($r3), $r5);
        }
        if (!($r4 == null || $r6 == null)) {
            $r8.setButton(-2, new String($r4), $r6);
        }
        $r8.setTitle($r1);
        $r8.setMessage($r2);
        $r8.show();
        TextView $r11 = (TextView) $r8.findViewById(16908299);
        if ($r11 != null) {
            Linkify.addLinks($r11, 1);
        }
    }

    private void ShowRun(byte[] $r1, byte[] $r2, byte[] $r3, byte[] $r4, long $l0) throws  {
        AlertDialog $r6 = builder().create();
        $r6.setCancelable(false);
        if ($r3 != null) {
            $r6.setButton(-1, new String($r3), new MsgBoxOnClick(1, $l0));
        }
        if ($r4 != null) {
            $r6.setButton(-2, new String($r4), new MsgBoxOnClick(0, $l0));
        }
        $r6.setTitle(new String($r1));
        $r6.setMessage(new String($r2));
        $r6.show();
        TextView $r10 = (TextView) $r6.findViewById(16908299);
        if ($r10 != null) {
            Linkify.addLinks($r10, 1);
        }
    }

    public void OpenAsrPopup() throws  {
        AppService.Post(new C12033());
    }

    public void CloseAsrPopup() throws  {
        AppService.Post(new C12044());
    }

    public void ShowAsrLabel(final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().getLayoutMgr().changeVoicePopupState($i0 != 0);
                }
            }
        });
    }

    public void setHijackingDialogActivity(ActivityBase $r1) throws  {
        this.mHijackingDialogActivity = $r1;
    }

    private ActivityBase getActivity() throws  {
        if (this.mHijackingDialogActivity == null) {
            return AppService.getActiveActivity();
        }
        ActivityBase $r1 = this.mHijackingDialogActivity;
        this.mHijackingDialogActivity = null;
        return $r1;
    }

    public void OpenMessageBoxTimeoutCb(String $r1, String $r2, int $i0, long $l1) throws  {
        OpenMessageBoxTimeoutCb($r1, $r2, null, $i0, $l1);
    }

    public void OpenMessageBoxTimeoutCb(String $r1, String $r2, String $r3, int $i0, long $l1) throws  {
        final String str = $r1;
        final String str2 = $r2;
        final long j = $l1;
        final String str3 = $r3;
        final int i = $i0;
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r3 = MsgBox.this.getActivity();
                if (ActivityBase.getRunningCount() >= 1 && $r3 != null) {
                    NativeManager $r4 = AppService.getNativeManager();
                    final Dialog $r1 = new Dialog($r3, C1283R.style.PopInDialog);
                    $r3.setDialog($r1);
                    $r1.setContentView(C1283R.layout.confirm_dialog);
                    ((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)).stop();
                    View $r5 = $r1.findViewById(C1283R.id.confirmClose);
                    $r5.setVisibility(8);
                    ((TextView) $r1.findViewById(C1283R.id.confirmSendText)).setText($r4.getLanguageString((int) DisplayStrings.DS_OK));
                    ((TextView) $r1.findViewById(C1283R.id.confirmTitle)).setText(str);
                    TextView $r8 = (TextView) $r1.findViewById(C1283R.id.confirmText);
                    $r8.setText(Html.fromHtml(str2));
                    $r8.setMovementMethod(LinkMovementMethod.getInstance());
                    $r1.findViewById(C1283R.id.confirmSend).setOnClickListener(new OnClickListener() {

                        class C12061 implements Runnable {
                            C12061() throws  {
                            }

                            public void run() throws  {
                                MsgBox.this.MsgBoxCallbackNTV(j);
                            }
                        }

                        public void onClick(View v) throws  {
                            try {
                                $r1.cancel();
                                $r1.dismiss();
                            } catch (Exception e) {
                            }
                            if (j != -1) {
                                NativeManager.Post(new C12061());
                            }
                        }
                    });
                    if (str3 != null) {
                        ((TextView) $r1.findViewById(C1283R.id.confirmSendText)).setText(str3);
                    }
                    if (i <= 0) {
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmSendTimer)).stop();
                    } else {
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmSendTimer)).setTime(i);
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmSendTimer)).start();
                    }
                    if (VERSION.SDK_INT >= 21) {
                        AnimationUtils.api21RippleInit($r5);
                    }
                    MsgBox.showDialogAnimated($r1);
                }
            }
        });
    }

    public void OpenMessageBoxTimeoutJavaCb(String $r1, String $r2, String $r3, int $i0, DialogInterface.OnClickListener $r4) throws  {
        final int i = $i0;
        final String str = $r3;
        final String str2 = $r1;
        final String str3 = $r2;
        final DialogInterface.OnClickListener onClickListener = $r4;
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (ActivityBase.getRunningCount() >= 1 && AppService.getActiveActivity() != null) {
                    AppService.getNativeManager();
                    final Dialog $r1 = new Dialog(AppService.getActiveActivity(), C1283R.style.PopInDialog);
                    AppService.getActiveActivity().setDialog($r1);
                    $r1.setContentView(C1283R.layout.confirm_dialog);
                    if (i <= 0) {
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)).stop();
                    } else {
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)).setTime(i);
                        ((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)).start();
                    }
                    $r1.findViewById(C1283R.id.confirmSend).setVisibility(8);
                    ((TextView) $r1.findViewById(C1283R.id.confirmCloseText)).setText(str);
                    ((TextView) $r1.findViewById(C1283R.id.confirmTitle)).setText(str2);
                    ((TextView) $r1.findViewById(C1283R.id.confirmText)).setText(str3);
                    View $r3 = $r1.findViewById(C1283R.id.confirmClose);
                    $r3.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) throws  {
                            try {
                                $r1.cancel();
                                $r1.dismiss();
                                if (onClickListener != null) {
                                    onClickListener.onClick($r1, 0);
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                    if (VERSION.SDK_INT >= 21) {
                        AnimationUtils.api21RippleInit($r3);
                    }
                    MsgBox.showDialogAnimated($r1);
                }
            }
        });
    }

    @Deprecated
    public static void OpenMutePopup(@Deprecated final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (ActivityBase.getRunningCount() >= 1 && AppService.getActiveActivity() != null) {
                    AppService.getNativeManager();
                    final Dialog $r1 = new Dialog(AppService.getActiveActivity(), C1283R.style.ProgressBarDialog);
                    AppService.getActiveActivity().setDialog($r1);
                    $r1.setContentView(C1283R.layout.mute_popup);
                    int $i0 = $i0;
                    this = this;
                    if ($i0 == 0) {
                        ((TextView) $r1.findViewById(C1283R.id.confirmTitle)).setText(NativeManager.getInstance().getLanguageString(6));
                        ((ImageView) $r1.findViewById(C1283R.id.confirmImage)).setImageResource(C1283R.drawable.big_sounds_on);
                    } else {
                        $i0 = $i0;
                        this = this;
                        if ($i0 == 2) {
                            ((TextView) $r1.findViewById(C1283R.id.confirmTitle)).setText(NativeManager.getInstance().getLanguageString(8));
                            ((ImageView) $r1.findViewById(C1283R.id.confirmImage)).setImageResource(C1283R.drawable.big_sounds_off);
                        } else {
                            ((TextView) $r1.findViewById(C1283R.id.confirmTitle)).setText(NativeManager.getInstance().getLanguageString(7));
                            ((ImageView) $r1.findViewById(C1283R.id.confirmImage)).setImageResource(C1283R.drawable.big_alerts_only);
                        }
                    }
                    new Handler().postDelayed(new Runnable() {
                        public void run() throws  {
                            $r1.dismiss();
                        }
                    }, 1000);
                    $r1.show();
                }
            }
        });
    }

    public void OpenConfirmDialogCustomTimeoutCb(final String $r1, final String $r2, final boolean $z0, final long $l0, final long $l1, final String $r3, final String $r4, final int $i2, final String $r5, final boolean $z1, final boolean $z2, final String $r6) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.this.openConfirmDialog($r1, $r2, $z0, $l0, $l1, $r3, $r4, $i2, $r5, $z1, $z2, $r6);
            }
        });
    }

    public void OpenConfirmDialogCustomTimeoutVerticalButtonsUserImgCb(final String $r1, final String $r2, final boolean $z0, final long $l0, final long $l1, final String $r3, final String $r4, final int $i2, final String $r5, final String $r6, final String $r7) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.this.openConfirmDialogButtons($r1, $r2, $z0, $l0, $l1, $r3, $r4, $i2, $r5, $r6, $r7, true);
            }
        });
    }

    public void OpenChoiceDialogCustomTimeoutCb(final String $r1, final String $r2, final boolean $z0, final int $i0, final int $i1, final long $l2, final long $l3, final String $r3, final int $i4, final String $r4, final int $i5, final int $i6, final String $r5) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.this.openChoiceDialog($r1, $r2, $z0, $i0, $i1, $l2, $l3, $r3, $i4, $r4, $i5, $i6, $r5, false);
            }
        });
    }

    public void OpenChoiceBottomSheetCb(final int $i0, final String $r1, final int $i1, final String $r2, final int $i2, final long $l3, final long $l4) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.this.openChoiceBottomSheet($i0, $r1, $i1, $r2, $i2, $l3, $l4);
            }
        });
    }

    public void OpenTripDialog(String $r1, int $i0, String $r2, String $r3, long $l1, long $l2, int $i3, String $r4) throws  {
        NativeManager $r5 = AppService.getNativeManager();
        if ($r5.getIsAllowTripDialog()) {
            final String str = $r1;
            final int i = $i0;
            final String str2 = $r2;
            final String str3 = $r3;
            final long j = $l1;
            final long j2 = $l2;
            final int i2 = $i3;
            final String str4 = $r4;
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (AppService.getActiveActivity() != null && (AppService.getActiveActivity() instanceof MainActivity)) {
                        MsgBox.this.openTripConfirmDialog(str, i, str2, str3, j, j2, i2, str4);
                    }
                }
            });
            return;
        }
        $r5.setIsAllowTripDialog(true);
    }

    public void StartTripDialogCountdown(final int $i0) throws  {
        if (AppService.getNativeManager().getIsAllowTripDialog() && this.tripDialog != null) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    if (MsgBox.this.tripDialog != null && AppService.getActiveActivity() != null && (AppService.getActiveActivity() instanceof MainActivity)) {
                        PillButton $r5 = (PillButton) MsgBox.this.tripDialog.findViewById(C1283R.id.confirmSend);
                        if ($i0 > 0) {
                            $r5.setTimeoutMilliSec($i0 * 1000);
                            $r5.startTimer();
                            return;
                        }
                        $r5.stopTimer();
                    }
                }
            });
        }
    }

    public void OpenChooseNumberDialog(final String $r1, final String $r2, final String $r3, final DialogInterface.OnClickListener $r4) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.this.openChooseNumberDialog($r1, $r2, $r3, $r4);
            }
        });
    }

    public void OpenConfirmDialogCustomTimeoutCbJava(final String $r1, final String $r2, final boolean $z0, final DialogInterface.OnClickListener $r3, final String $r4, final String $r5, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.openConfirmDialogJavaCallback($r1, $r2, $z0, $r3, $r4, $r5, $i0);
            }
        });
    }

    public void OpenConfirmDialogCustomTimeoutCbJava(final String $r1, final String $r2, final boolean $z0, final DialogInterface.OnClickListener $r3, final String $r4, final String $r5, final int $i0, final String $r6, final OnCancelListener $r7) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.openConfirmDialogJavaCallback($r1, $r2, $z0, $r3, $r4, $r5, $i0, $r6, $r7);
            }
        });
    }

    public void OpenConfirmDialogCustomTimeoutCbJava(final String $r1, final String $r2, final boolean $z0, final DialogInterface.OnClickListener $r3, final String $r4, final String $r5, final int $i0, final String $r6, final OnCancelListener $r7, final boolean $z1, final boolean $z2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MsgBox.openConfirmDialogJavaCallback($r1, $r2, $z0, $r3, $r4, $r5, $i0, $r6, $r7, $z1, $z2, false);
            }
        });
    }

    private int getCheckBoxStatus(Dialog $r1) throws  {
        if ($r1.findViewById(C1283R.id.confirmCheckbox) == null || $r1.findViewById(C1283R.id.confirmCheckboxFrame).getVisibility() != 0) {
            return -1;
        }
        return ((WazeCheckBoxView) $r1.findViewById(C1283R.id.confirmCheckbox)).isChecked() ? 1 : 0;
    }

    private void confirnDialogNoHandler(Dialog $r1, long $l0, long $l1, int $i2) throws  {
        Logger.d_("MsgBox", "Dialog No Handler");
        if ($r1 != null) {
            if (((TimerBar) $r1.findViewById(C1283R.id.confirmSendTimer)) != null) {
                ((TimerBar) $r1.findViewById(C1283R.id.confirmSendTimer)).stop();
            }
            if (((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)) != null) {
                ((TimerBar) $r1.findViewById(C1283R.id.confirmCloseTimer)).stop();
            }
            int $i3 = getCheckBoxStatus($r1);
            try {
                $r1.cancel();
                $r1.dismiss();
            } catch (Exception e) {
            }
            if ($l0 != -1) {
                final int i = $i2;
                final long j = $l0;
                final long j2 = $l1;
                final int i2 = $i3;
                NativeManager.Post(new Runnable() {
                    public void run() throws  {
                        MsgBox.this.ConfirmDialogCallbackNTV(i, j, j2, (long) i2);
                    }
                });
            }
        }
    }

    private void openConfirmDialog(String $r1, String $r2, boolean $z0, long $l0, long $l1, String $r3, String $r4, int $i2, String $r5, boolean $z1, boolean $z2, String $r6) throws  {
        int i = $z0 ? 0 : 1;
        int i2 = $z0 ? 3 : 4;
        String $r7 = $z1 ? $r4 : $r3;
        int i3 = $z1 ? 4 : 3;
        if (!$z1) {
            $r3 = $r4;
        }
        openChoiceDialog($r1, $r2, false, i, i2, $l0, $l1, $r7, i3, $r3, $z1 ? 3 : 4, $i2, $r5, $z2, $r6);
    }

    public Dialog openConfirmDialogButtons(String $r1, String $r2, boolean $z0, long $l0, long $l1, String $r3, String $r4, int $i2, String $r5, String $r6, String $r7, boolean $z1) throws  {
        return openChoiceDialogUserImg($r1, $r2, true, $z0 ? 0 : 1, 2, $l0, $l1, $r3, 3, $r4, 4, $i2, $r5, $r6, $r7, $z1);
    }

    private void openChoiceDialog(String $r1, String $r2, boolean $z0, int $i0, int $i1, long $l2, long $l3, String $r3, int $i4, String $r4, int $i5, int $i6, String $r5, boolean $z1) throws  {
        openChoiceDialog($r1, $r2, $z0, $i0, $i1, $l2, $l3, $r3, $i4, $r4, $i5, $i6, $r5, $z1, null);
    }

    private void openChoiceBottomSheet(int $i0, String $r1, int $i1, String $r2, int $i2, long $l3, long $l4) throws  {
        SimpleBottomSheetValue[] $r4 = new SimpleBottomSheetValue[]{new SimpleBottomSheetValue($i1, $r1, null), new SimpleBottomSheetValue($i2, $r2, null)};
        SimpleBottomSheet simpleBottomSheet = new SimpleBottomSheet(AppService.getActiveActivity(), Mode.COLUMN_TEXT, $i0, $r4, null);
        final long j = $l3;
        final long j2 = $l4;
        final SimpleBottomSheet simpleBottomSheet2 = simpleBottomSheet;
        simpleBottomSheet.setListener(new SimpleBottomSheetListener() {
            public void onComplete(final SimpleBottomSheetValue $r1) throws  {
                NativeManager.Post(new Runnable() {
                    public void run() throws  {
                        MsgBox.this.ConfirmDialogCallbackNTV($r1.id, j, j2, 0);
                        SimpleBottomSheet $r4 = simpleBottomSheet2;
                        $r4.dismiss();
                    }
                });
            }
        });
        simpleBottomSheet.show();
    }

    private void openChoiceDialog(String $r1, String $r2, boolean $z0, int $i0, int $i1, long $l2, long $l3, String $r3, int $i4, String $r4, int $i5, int $i6, String $r5, boolean $z1, String $r6) throws  {
        if (ActivityBase.getRunningCount() >= 1 && AppService.getActiveActivity() != null) {
            NativeManager $r9 = AppService.getNativeManager();
            Dialog dialog = new Dialog(AppService.getActiveActivity(), C1283R.style.PopInDialog);
            AppService.getActiveActivity().setDialog(dialog);
            if ($z1) {
                dialog.setContentView(C1283R.layout.confirm_dialog_v);
            } else {
                dialog.setContentView(C1283R.layout.confirm_dialog);
            }
            setChoiceDialogTimer($r1, $r2, $i0, $r3, $r4, $i6, dialog);
            if ($r5 != null && $r5.length() > 0) {
                ((ImageView) dialog.findViewById(C1283R.id.confirmImage)).setImageResource(ResManager.GetDrawableId($r5.toLowerCase()));
                dialog.findViewById(C1283R.id.confirmImageContainer).setVisibility(0);
            }
            View $r10 = dialog.findViewById(C1283R.id.confirmClose);
            View $r12 = dialog.findViewById(C1283R.id.confirmSend);
            setButtons($z0, $i0, $i1, $l2, $l3, $i4, $i5, dialog, $r10, $r12);
            View $r13 = dialog.findViewById(C1283R.id.confirmCheckboxFrame);
            if ($r6 == null || $r6.length() <= 0) {
                $r13.setVisibility(8);
            } else {
                if ($r9.getLanguageRtl()) {
                    ((TextView) dialog.findViewById(C1283R.id.confirmCheckboxTextRtl)).setText($r6);
                    dialog.findViewById(C1283R.id.confirmCheckboxTextRtl).setVisibility(0);
                    dialog.findViewById(C1283R.id.confirmCheckboxText).setVisibility(8);
                } else {
                    ((TextView) dialog.findViewById(C1283R.id.confirmCheckboxText)).setText($r6);
                    dialog.findViewById(C1283R.id.confirmCheckboxText).setVisibility(0);
                    dialog.findViewById(C1283R.id.confirmCheckboxTextRtl).setVisibility(8);
                }
                WazeCheckBoxView $r16 = (WazeCheckBoxView) dialog.findViewById(C1283R.id.confirmCheckbox);
                $r16.setValue(false);
                final WazeCheckBoxView wazeCheckBoxView = $r16;
                $r16.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        wazeCheckBoxView.toggle();
                    }
                });
                $r13.setVisibility(0);
            }
            setAnimation($r10, $r12);
            showDialogAnimated(dialog);
        }
    }

    public Dialog openChoiceDialogUserImg(String $r1, String $r2, boolean $z0, int $i0, int $i1, long $l2, long $l3, String $r3, int $i4, String $r4, int $i5, int $i6, String $r5, String $r6, String $r7, boolean $z1) throws  {
        if (ActivityBase.getRunningCount() < 1 || AppService.getActiveActivity() == null) {
            return null;
        }
        Dialog dialog = new Dialog(AppService.getActiveActivity(), C1283R.style.PopInDialog);
        AppService.getActiveActivity().setDialog(dialog);
        dialog.setContentView(C1283R.layout.confirm_dialog);
        setChoiceDialogTimer($r1, $r2, $i0, $r3, $r4, $i6, dialog);
        View $r10 = dialog.findViewById(C1283R.id.confirmClose);
        View $r11 = dialog.findViewById(C1283R.id.confirmSend);
        setButtons($z0, $i0, $i1, $l2, $l3, $i4, $i5, dialog, $r10, $r11);
        if ($z1) {
            ((LinearLayout) dialog.findViewById(C1283R.id.confirmButtons)).setOrientation(1);
            LayoutParams $r15 = (LinearLayout.LayoutParams) $r11.getLayoutParams();
            $r15.weight = 0.0f;
            $r15.width = -1;
            $r11.setLayoutParams($r15);
            $r15 = (LinearLayout.LayoutParams) $r10.getLayoutParams();
            $r15.weight = 0.0f;
            $r15.width = -1;
            $r10.setLayoutParams($r15);
        }
        if ($r5 != null && $r5.length() > 0) {
            ImageView $r16 = (ImageView) dialog.findViewById(C1283R.id.confirmImage);
            $r16.setImageResource(C1283R.drawable.ridecard_profilepic_placeholder);
            StringBuilder stringBuilder = new StringBuilder();
            if ($r6 == null) {
                $r6 = "";
            }
            StringBuilder $r17 = stringBuilder.append($r6).append(" ");
            if ($r7 == null) {
                $r7 = "";
            }
            TextView $r18 = (TextView) dialog.findViewById(C1283R.id.confirmImageText);
            $r18.setText(ShareUtility.getInitials($r17.append($r7).toString()));
            $r18.setVisibility(0);
            final ImageView imageView = $r16;
            final TextView textView = $r18;
            final String str = $r5;
            VolleyManager.getInstance().loadImageFromUrl($r5, new ImageRequestListener() {
                public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                    imageView.setImageDrawable(new CircleShaderDrawable($r1, 0));
                    textView.setVisibility(8);
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                    Logger.m38e("openChoiceDialogUserImgVertical: Failed to load image " + str);
                }
            });
            dialog.findViewById(C1283R.id.confirmImageContainer).setVisibility(0);
        }
        setAnimation($r10, $r11);
        showDialogAnimated(dialog);
        return dialog;
    }

    private static void setAnimation(View $r0, View $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInit($r0);
            AnimationUtils.api21RippleInit($r1);
        }
    }

    private void setButtons(boolean $z0, int $i0, int $i1, long $l2, long $l3, int $i4, int $i5, Dialog $r1, View $r2, View $r3) throws  {
        final boolean z = $z0;
        final long j = $l2;
        final long j2 = $l3;
        final int i = $i1;
        final int i2 = $i0;
        final int i3 = $i4;
        final int i4 = $i5;
        $r1.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface $r1) throws  {
                if (z) {
                    MsgBox.this.confirnDialogNoHandler((Dialog) $r1, j, j2, i);
                } else {
                    MsgBox.this.confirnDialogNoHandler((Dialog) $r1, j, j2, i2 == 0 ? i3 : i4);
                }
            }
        });
        final Dialog dialog = $r1;
        j = $l2;
        j2 = $l3;
        i = $i5;
        $r2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                dialog.setOnCancelListener(null);
                MsgBox.this.confirnDialogNoHandler(dialog, j, j2, i);
            }
        });
        dialog = $r1;
        j = $l2;
        final int i5 = $i4;
        final long j3 = $l3;
        $r3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                ((TimerBar) dialog.findViewById(C1283R.id.confirmSendTimer)).stop();
                ((TimerBar) dialog.findViewById(C1283R.id.confirmCloseTimer)).stop();
                final int $i0 = MsgBox.this.getCheckBoxStatus(dialog);
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                }
                if (j != -1) {
                    NativeManager.Post(new Runnable() {
                        public void run() throws  {
                            MsgBox.this.ConfirmDialogCallbackNTV(i5, j, j3, (long) $i0);
                        }
                    });
                }
            }
        });
    }

    private void setChoiceDialogTimer(String $r1, String $r2, int $i0, String $r3, String $r4, int $i1, Dialog $r5) throws  {
        ((TextView) $r5.findViewById(C1283R.id.confirmCloseText)).setText($r4);
        if ($i1 <= 0) {
            ((TimerBar) $r5.findViewById(C1283R.id.confirmSendTimer)).stop();
            ((TimerBar) $r5.findViewById(C1283R.id.confirmCloseTimer)).stop();
        } else if ($i0 == 0) {
            ((TimerBar) $r5.findViewById(C1283R.id.confirmSendTimer)).setTime($i1);
            ((TimerBar) $r5.findViewById(C1283R.id.confirmSendTimer)).start();
            ((TimerBar) $r5.findViewById(C1283R.id.confirmCloseTimer)).stop();
        } else {
            ((TimerBar) $r5.findViewById(C1283R.id.confirmCloseTimer)).setTime($i1);
            ((TimerBar) $r5.findViewById(C1283R.id.confirmCloseTimer)).start();
            ((TimerBar) $r5.findViewById(C1283R.id.confirmSendTimer)).stop();
        }
        ((TextView) $r5.findViewById(C1283R.id.confirmSendText)).setText($r3);
        ((TextView) $r5.findViewById(C1283R.id.confirmTitle)).setText($r1);
        ((TextView) $r5.findViewById(C1283R.id.confirmText)).setText($r2);
    }

    private void openChooseNumberDialog(String $r1, String $r2, String $r3, DialogInterface.OnClickListener $r4) throws  {
        if (ActivityBase.getRunningCount() >= 1 && AppService.getActiveActivity() != null) {
            final Dialog $r5 = new Dialog(AppService.getActiveActivity(), C1283R.style.ReportDialog);
            AppService.getActiveActivity().setDialog($r5);
            $r5.setContentView(C1283R.layout.select_number_popup);
            ((TextView) $r5.findViewById(C1283R.id.PhoneNumber1)).setText($r2);
            ((TextView) $r5.findViewById(C1283R.id.PhoneNumber2)).setText($r3);
            ((TextView) $r5.findViewById(C1283R.id.ChooseNumberText)).setText($r1);
            final DialogInterface.OnClickListener onClickListener = $r4;
            $r5.findViewById(C1283R.id.CancelButton).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    try {
                        $r5.cancel();
                        $r5.dismiss();
                    } catch (Exception e) {
                    }
                    onClickListener.onClick($r5, 0);
                }
            });
            onClickListener = $r4;
            $r5.findViewById(C1283R.id.PhoneNumber1).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    try {
                        $r5.cancel();
                        $r5.dismiss();
                    } catch (Exception e) {
                    }
                    onClickListener.onClick($r5, 1);
                }
            });
            onClickListener = $r4;
            $r5.findViewById(C1283R.id.PhoneNumber2).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    try {
                        $r5.cancel();
                        $r5.dismiss();
                    } catch (Exception e) {
                    }
                    onClickListener.onClick($r5, 2);
                }
            });
            $r5.show();
        }
    }

    private void openTripConfirmDialog(String $r1, int $i0, String $r2, String $r3, long $l1, long $l2, int $i3, String $r4) throws  {
        ActivityBase $r5 = AppService.getActiveActivity();
        if (ActivityBase.getRunningCount() >= 1 && $r5 != null) {
            NativeManager $r6 = AppService.getNativeManager();
            this.tripDialog = new Dialog($r5, C1283R.style.SlideDownDialog);
            $r5.setDialog(this.tripDialog);
            this.tripDialog.setContentView(C1283R.layout.trip_dialog);
            PillButton $r9 = (PillButton) this.tripDialog.findViewById(C1283R.id.confirmSend);
            if ($i3 > 0) {
                int $i32 = $i3 * 1000;
                $r9.setTimeoutMilliSec($i32);
                $r9.startTimer();
            }
            ((TextView) this.tripDialog.findViewById(C1283R.id.TripTitleText)).setText($r1);
            ((TextView) this.tripDialog.findViewById(C1283R.id.TripBodyText)).setText($r6.getLanguageString((int) DisplayStrings.DS_ARE_YOU_ON_YOUR_WAY_TO_Q));
            ((TextView) this.tripDialog.findViewById(C1283R.id.TripBodyText2)).setText($r6.getLanguageString($r2));
            TextView $r10 = (TextView) this.tripDialog.findViewById(C1283R.id.TripBodyText3);
            if ($r3 == null || $r3.isEmpty()) {
                $r10.setVisibility(8);
            } else {
                $r10.setText($r3);
            }
            this.mTripTypeStr = "UNKNOWN" + $i0;
            this.mTripSuggestButtonPressed = false;
            ImageView $r12 = (ImageView) this.tripDialog.findViewById(C1283R.id.TripTypeImage);
            if ($i0 == 5) {
                this.mTripTypeStr = "CARPOOL_PICKUP";
                $r12.setImageResource(C1283R.drawable.ridecard_profilepic_placeholder);
                if (!($r4 == null || $r4.isEmpty())) {
                    ImageRepository.instance.getImage($r4, 3, $r12, null, $r5);
                }
                $r9.setColor(C1283R.color.CarpoolGreen);
                ((ImageView) this.tripDialog.findViewById(C1283R.id.confirmSendIcon)).setImageResource(C1283R.drawable.go_icon_text_white);
            } else if ($i0 == 3) {
                this.mTripTypeStr = "FB";
                $r12.setBackgroundResource(C1283R.drawable.calendar_event_illustration);
                this.tripDialog.findViewById(C1283R.id.TripTypeFacebookBadge).setVisibility(0);
            } else if ($i0 == 4) {
                this.mTripTypeStr = "PICKUP";
                $r12.setBackgroundResource(C1283R.drawable.default_location_illustration);
            } else if ($i0 == 10) {
                this.mTripTypeStr = AnalyticsEvents.ANALYTICS_TRIP_TYPE_CALENDAR;
                $r12.setBackgroundResource(C1283R.drawable.calendar_event_illustration);
            } else if ($r2.equals("Home")) {
                this.mTripTypeStr = "HOME";
                $r12.setBackgroundResource(C1283R.drawable.home_illustration);
            } else if ($r2.equals("Work")) {
                this.mTripTypeStr = "WORK";
                $r12.setBackgroundResource(C1283R.drawable.work_illustration);
            } else {
                this.mTripTypeStr = "FAV";
                $r12.setBackgroundResource(C1283R.drawable.favorite_illustration);
            }
            View $r8 = this.tripDialog.findViewById(C1283R.id.confirmClose);
            final int i = $i0;
            final long j = $l1;
            final long j2 = $l2;
            $r8.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_TRIP_POPUP).addParam("ACTION", "CLOSE").addParam("TYPE", MsgBox.this.mTripTypeStr).send();
                    MsgBox.this.mTripSuggestButtonPressed = true;
                    if (i == 5) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_TRIP_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, AnalyticsEvents.ANALYTICS_NO);
                    } else {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TRIP_SUGGEST_SHOWN, "ACTION|TYPE", "NO|" + MsgBox.this.mTripTypeStr);
                    }
                    MsgBox.this.confirnDialogNoHandler(MsgBox.this.tripDialog, j, j2, 4);
                    MsgBox.this.tripDialog = null;
                }
            });
            View $r15 = this.tripDialog.findViewById(C1283R.id.confirmSend);
            final PillButton pillButton = $r9;
            final int i2 = $i0;
            final long j3 = $l1;
            final long j4 = $l2;
            $r15.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    if (MsgBox.this.tripDialog != null) {
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_TRIP_POPUP).addParam("ACTION", "SEND").addParam("TYPE", MsgBox.this.mTripTypeStr).send();
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_DRIVE_TYPE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, MsgBox.this.mTripTypeStr).send();
                        MsgBox.this.mTripSuggestButtonPressed = true;
                        boolean $z0 = pillButton.didTimeOut();
                        if (i2 == 5) {
                            String $r6;
                            if ($z0) {
                                $r6 = "TIMEOUT";
                            } else {
                                $r6 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_GO;
                            }
                            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_TRIP_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, $r6);
                        } else {
                            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TRIP_SUGGEST_SHOWN, "ACTION|TYPE", ($z0 ? "TIMEOUT|" : "YES|") + MsgBox.this.mTripTypeStr);
                        }
                        pillButton.stopTimer();
                        final int $i0 = MsgBox.this.getCheckBoxStatus(MsgBox.this.tripDialog);
                        try {
                            MsgBox.this.tripDialog.dismiss();
                        } catch (Exception e) {
                        }
                        if (j3 != -1) {
                            NativeManager.Post(new Runnable() {
                                public void run() throws  {
                                    MsgBox.this.ConfirmDialogCallbackNTV(3, j3, j4, (long) $i0);
                                }
                            });
                        }
                        MsgBox.this.tripDialog = null;
                    }
                }
            });
            setAnimation($r8, $r15);
            final long j5 = $l1;
            j3 = $l2;
            this.tripDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface $r1) throws  {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_TRIP_POPUP).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CANCELED).addParam("TYPE", MsgBox.this.mTripTypeStr).send();
                    if (!MsgBox.this.mTripSuggestButtonPressed) {
                        MsgBox.this.mTripSuggestButtonPressed = true;
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TRIP_SUGGEST_SHOWN, "ACTION|TYPE", "X|" + MsgBox.this.mTripTypeStr);
                        MsgBox.this.confirnDialogNoHandler((Dialog) $r1, j5, j3, 4);
                    }
                    MsgBox.this.tripDialog = null;
                }
            });
            this.tripDialog.getWindow().setGravity(48);
            this.tripDialog.show();
        }
    }

    public static Dialog openConfirmDialogJavaCallback(String $r0, String $r1, boolean $z0, DialogInterface.OnClickListener $r2, String $r3, String $r4, int $i0) throws  {
        return openConfirmDialogJavaCallback($r0, $r1, $z0, $r2, $r3, $r4, $i0, null, null, false, false, false, null, null);
    }

    public static Dialog openConfirmDialogJavaCallback(String $r0, String $r1, boolean $z0, DialogInterface.OnClickListener $r2, String $r3, String $r4, int $i0, String $r5, OnCancelListener $r6) throws  {
        return openConfirmDialogJavaCallback($r0, $r1, $z0, $r2, $r3, $r4, $i0, $r5, $r6, false, false, false, null, null);
    }

    public static Dialog openConfirmDialogJavaCallback(String $r0, String $r1, boolean $z0, DialogInterface.OnClickListener $r2, String $r3, String $r4, int $i0, String $r5, OnCancelListener $r6, boolean $z1) throws  {
        return openConfirmDialogJavaCallback($r0, $r1, $z0, $r2, $r3, $r4, $i0, $r5, $r6, false, false, $z1, null, null);
    }

    public static Dialog openConfirmDialogJavaCallback(String $r0, String $r1, boolean $z0, DialogInterface.OnClickListener $r2, String $r3, String $r4, int $i0, String $r5, OnCancelListener $r6, boolean $z1, boolean $z2, boolean $z3) throws  {
        return openConfirmDialogJavaCallback($r0, $r1, $z0, $r2, $r3, $r4, $i0, $r5, $r6, $z1, $z2, $z3, null, null);
    }

    static void showDialogAnimated(Dialog $r0) throws  {
        $r0.show();
    }

    public static void openMessageBox(String $r0, String $r1, boolean $z0) throws  {
        if ($z0) {
            NativeManager $r2 = AppService.getNativeManager();
            $r0 = $r2.getLanguageString($r0);
            $r1 = $r2.getLanguageString($r1);
        }
        getInstance().OpenMessageBoxTimeoutCb($r0, $r1, -1, -1);
    }

    public static void openMessageBoxTimeout(String $r0, String $r1, int $i0, DialogInterface.OnClickListener $r2) throws  {
        getInstance().OpenMessageBoxTimeoutJavaCb($r0, $r1, AppService.getNativeManager().getLanguageString((int) DisplayStrings.DS_OK), $i0, $r2);
    }

    public static void openMessageBoxFull(String $r0, String $r1, String $r2, int $i0, DialogInterface.OnClickListener $r3) throws  {
        getInstance().OpenMessageBoxTimeoutJavaCb($r0, $r1, $r2, $i0, $r3);
    }

    public static void openMessageBoxWithCallback(String $r1, String $r2, boolean $z0, DialogInterface.OnClickListener $r0) throws  {
        NativeManager $r3 = AppService.getNativeManager();
        if ($z0) {
            $r1 = $r3.getLanguageString($r1);
            $r2 = $r3.getLanguageString($r2);
        }
        getInstance().OpenMessageBoxTimeoutJavaCb($r1, $r2, $r3.getLanguageString((int) DisplayStrings.DS_OK), -1, $r0);
    }

    private Builder builder() throws  {
        return new Builder(AppService.getActiveActivity());
    }

    private MsgBox() throws  {
    }
}
