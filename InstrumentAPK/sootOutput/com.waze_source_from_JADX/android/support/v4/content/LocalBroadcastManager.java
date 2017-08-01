package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap();

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(@Signature({"(", "Landroid/content/Intent;", "Ljava/util/ArrayList", "<", "Landroid/support/v4/content/LocalBroadcastManager$ReceiverRecord;", ">;)V"}) Intent $r1, @Signature({"(", "Landroid/content/Intent;", "Ljava/util/ArrayList", "<", "Landroid/support/v4/content/LocalBroadcastManager$ReceiverRecord;", ">;)V"}) ArrayList<ReceiverRecord> $r2) throws  {
            this.intent = $r1;
            this.receivers = $r2;
        }
    }

    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter $r1, BroadcastReceiver $r2) throws  {
            this.filter = $r1;
            this.receiver = $r2;
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder(128);
            $r1.append("Receiver{");
            $r1.append(this.receiver);
            $r1.append(" filter=");
            $r1.append(this.filter);
            $r1.append("}");
            return $r1.toString();
        }
    }

    public static LocalBroadcastManager getInstance(Context $r0) throws  {
        LocalBroadcastManager $r2;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager($r0.getApplicationContext());
            }
            $r2 = mInstance;
        }
        return $r2;
    }

    private LocalBroadcastManager(Context $r1) throws  {
        this.mAppContext = $r1;
        this.mHandler = new Handler($r1.getMainLooper()) {
            public void handleMessage(Message $r1) throws  {
                switch ($r1.what) {
                    case 1:
                        LocalBroadcastManager.this.executePendingBroadcasts();
                        return;
                    default:
                        super.handleMessage($r1);
                        return;
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver $r1, IntentFilter $r2) throws  {
        synchronized (this.mReceivers) {
            ReceiverRecord $r3 = new ReceiverRecord($r2, $r1);
            ArrayList $r7 = (ArrayList) this.mReceivers.get($r1);
            if ($r7 == null) {
                $r7 = new ArrayList(1);
                this.mReceivers.put($r1, $r7);
            }
            $r7.add($r2);
            for (int $i0 = 0; $i0 < $r2.countActions(); $i0++) {
                String $r8 = $r2.getAction($i0);
                $r7 = (ArrayList) this.mActions.get($r8);
                if ($r7 == null) {
                    $r7 = new ArrayList(1);
                    this.mActions.put($r8, $r7);
                }
                $r7.add($r3);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver $r1) throws  {
        synchronized (this.mReceivers) {
            ArrayList $r5 = (ArrayList) this.mReceivers.remove($r1);
            if ($r5 == null) {
                return;
            }
            for (int $i0 = 0; $i0 < $r5.size(); $i0++) {
                IntentFilter $r6 = (IntentFilter) $r5.get($i0);
                for (int $i1 = 0; $i1 < $r6.countActions(); $i1++) {
                    String $r7 = $r6.getAction($i1);
                    ArrayList $r8 = (ArrayList) this.mActions.get($r7);
                    if ($r8 != null) {
                        int $i2 = 0;
                        while ($i2 < $r8.size()) {
                            BroadcastReceiver broadcastReceiver = ((ReceiverRecord) $r8.get($i2)).receiver;
                            BroadcastReceiver $r10 = broadcastReceiver;
                            if (broadcastReceiver == $r1) {
                                $r8.remove($i2);
                                $i2--;
                            }
                            $i2++;
                        }
                        if ($r8.size() <= 0) {
                            this.mActions.remove($r7);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r37) throws  {
        /*
        r36 = this;
        r0 = r36;
        r7 = r0.mReceivers;
        monitor-enter(r7);
        r0 = r37;
        r8 = r0.getAction();	 Catch:{ Throwable -> 0x0193 }
        r0 = r36;
        r9 = r0.mAppContext;	 Catch:{ Throwable -> 0x0193 }
        r10 = r9.getContentResolver();	 Catch:{ Throwable -> 0x0193 }
        r0 = r37;
        r11 = r0.resolveTypeIfNeeded(r10);	 Catch:{ Throwable -> 0x0193 }
        r0 = r37;
        r12 = r0.getData();	 Catch:{ Throwable -> 0x0193 }
        r0 = r37;
        r13 = r0.getScheme();	 Catch:{ Throwable -> 0x0193 }
        r0 = r37;
        r14 = r0.getCategories();	 Catch:{ Throwable -> 0x0193 }
        r0 = r37;
        r15 = r0.getFlags();	 Catch:{ Throwable -> 0x0193 }
        r15 = r15 & 8;
        if (r15 == 0) goto L_0x012c;
    L_0x0035:
        r16 = 1;
    L_0x0037:
        if (r16 == 0) goto L_0x0081;
    L_0x0039:
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
        r18 = "Resolving type ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r17 = r0.append(r11);	 Catch:{ Throwable -> 0x0193 }
        r18 = " scheme ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r17 = r0.append(r13);	 Catch:{ Throwable -> 0x0193 }
        r18 = " of intent ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r1 = r37;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0193 }
        r18 = "LocalBroadcastManager";
        r0 = r18;
        r1 = r19;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x0081:
        r0 = r36;
        r0 = r0.mActions;	 Catch:{ Throwable -> 0x0193 }
        r20 = r0;
        r0 = r37;
        r19 = r0.getAction();	 Catch:{ Throwable -> 0x0193 }
        r0 = r20;
        r1 = r19;
        r21 = r0.get(r1);	 Catch:{ Throwable -> 0x0193 }
        r23 = r21;
        r23 = (java.util.ArrayList) r23;	 Catch:{ Throwable -> 0x0193 }
        r22 = r23;
        if (r22 == 0) goto L_0x0236;
    L_0x009d:
        if (r16 == 0) goto L_0x00cb;
    L_0x009f:
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
        r18 = "Action list: ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        goto L_0x00b4;
    L_0x00b1:
        goto L_0x0037;
    L_0x00b4:
        r0 = r17;
        r1 = r22;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0193 }
        r18 = "LocalBroadcastManager";
        r0 = r18;
        r1 = r19;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x00cb:
        r24 = 0;
        r15 = 0;
    L_0x00ce:
        r0 = r22;
        r25 = r0.size();	 Catch:{ Throwable -> 0x0193 }
        r0 = r25;
        if (r15 >= r0) goto L_0x01d5;
    L_0x00d8:
        r0 = r22;
        r21 = r0.get(r15);	 Catch:{ Throwable -> 0x0193 }
        r27 = r21;
        r27 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r27;	 Catch:{ Throwable -> 0x0193 }
        r26 = r27;
        if (r16 == 0) goto L_0x0114;
    L_0x00e6:
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
        r18 = "Matching against filter ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r26;
        r0 = r0.filter;	 Catch:{ Throwable -> 0x0193 }
        r28 = r0;
        r0 = r17;
        r1 = r28;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0193 }
        r18 = "LocalBroadcastManager";
        r0 = r18;
        r1 = r19;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x0114:
        r0 = r26;
        r0 = r0.broadcasting;	 Catch:{ Throwable -> 0x0193 }
        r29 = r0;
        if (r29 == 0) goto L_0x012f;
    L_0x011c:
        if (r16 == 0) goto L_0x0129;
    L_0x011e:
        r18 = "LocalBroadcastManager";
        r30 = "  Filter's target already added";
        r0 = r18;
        r1 = r30;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x0129:
        r15 = r15 + 1;
        goto L_0x00ce;
    L_0x012c:
        r16 = 0;
        goto L_0x00b1;
    L_0x012f:
        r0 = r26;
        r0 = r0.filter;	 Catch:{ Throwable -> 0x0193 }
        r28 = r0;
        r18 = "LocalBroadcastManager";
        r0 = r28;
        r1 = r8;
        r2 = r11;
        r3 = r13;
        r4 = r12;
        r5 = r14;
        r6 = r18;
        r25 = r0.match(r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x0193 }
        if (r25 < 0) goto L_0x0196;
    L_0x0146:
        if (r16 == 0) goto L_0x017a;
    L_0x0148:
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
        r18 = "  Filter matched!  match=0x";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r25;
        r19 = java.lang.Integer.toHexString(r0);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r1 = r19;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0193 }
        goto L_0x0171;
    L_0x016e:
        goto L_0x0129;
    L_0x0171:
        r18 = "LocalBroadcastManager";
        r0 = r18;
        r1 = r19;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x017a:
        if (r24 != 0) goto L_0x0183;
    L_0x017c:
        r24 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0193 }
        r0 = r24;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
    L_0x0183:
        r0 = r24;
        r1 = r26;
        r0.add(r1);	 Catch:{ Throwable -> 0x0193 }
        r31 = 1;
        r0 = r31;
        r1 = r26;
        r1.broadcasting = r0;	 Catch:{ Throwable -> 0x0193 }
        goto L_0x0129;
    L_0x0193:
        r32 = move-exception;
        monitor-exit(r7);	 Catch:{ Throwable -> 0x0193 }
        throw r32;
    L_0x0196:
        if (r16 == 0) goto L_0x0129;
    L_0x0198:
        switch(r25) {
            case -4: goto L_0x01cb;
            case -3: goto L_0x01c8;
            case -2: goto L_0x01ce;
            case -1: goto L_0x01d1;
            default: goto L_0x019b;
        };
    L_0x019b:
        goto L_0x019c;
    L_0x019c:
        r19 = "unknown reason";
    L_0x019f:
        r17 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r0.<init>();	 Catch:{ Throwable -> 0x0193 }
        r18 = "  Filter did not match: ";
        r0 = r17;
        r1 = r18;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r1 = r19;
        r17 = r0.append(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r17;
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0193 }
        r18 = "LocalBroadcastManager";
        r0 = r18;
        r1 = r19;
        android.util.Log.v(r0, r1);	 Catch:{ Throwable -> 0x0193 }
        goto L_0x016e;
    L_0x01c8:
        r19 = "action";
        goto L_0x019f;
    L_0x01cb:
        r19 = "category";
        goto L_0x019f;
    L_0x01ce:
        r19 = "data";
        goto L_0x019f;
    L_0x01d1:
        r19 = "type";
        goto L_0x019f;
    L_0x01d5:
        if (r24 == 0) goto L_0x0236;
    L_0x01d7:
        r15 = 0;
    L_0x01d8:
        r0 = r24;
        r25 = r0.size();	 Catch:{ Throwable -> 0x0193 }
        r0 = r25;
        if (r15 >= r0) goto L_0x01f9;
    L_0x01e2:
        r0 = r24;
        r21 = r0.get(r15);	 Catch:{ Throwable -> 0x0193 }
        r33 = r21;
        r33 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r33;	 Catch:{ Throwable -> 0x0193 }
        r26 = r33;
        r31 = 0;
        r0 = r31;
        r1 = r26;
        r1.broadcasting = r0;	 Catch:{ Throwable -> 0x0193 }
        r15 = r15 + 1;
        goto L_0x01d8;
    L_0x01f9:
        r0 = r36;
        r0 = r0.mPendingBroadcasts;	 Catch:{ Throwable -> 0x0193 }
        r22 = r0;
        r34 = new android.support.v4.content.LocalBroadcastManager$BroadcastRecord;	 Catch:{ Throwable -> 0x0193 }
        r0 = r34;
        r1 = r37;
        r2 = r24;
        r0.<init>(r1, r2);	 Catch:{ Throwable -> 0x0193 }
        r0 = r22;
        r1 = r34;
        r0.add(r1);	 Catch:{ Throwable -> 0x0193 }
        r0 = r36;
        r0 = r0.mHandler;	 Catch:{ Throwable -> 0x0193 }
        r35 = r0;
        r31 = 1;
        r0 = r35;
        r1 = r31;
        r16 = r0.hasMessages(r1);	 Catch:{ Throwable -> 0x0193 }
        if (r16 != 0) goto L_0x0232;
    L_0x0223:
        r0 = r36;
        r0 = r0.mHandler;	 Catch:{ Throwable -> 0x0193 }
        r35 = r0;
        r31 = 1;
        r0 = r35;
        r1 = r31;
        r0.sendEmptyMessage(r1);	 Catch:{ Throwable -> 0x0193 }
    L_0x0232:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x0193 }
        r31 = 1;
        return r31;
    L_0x0236:
        monitor-exit(r7);	 Catch:{ Throwable -> 0x0193 }
        r31 = 0;
        return r31;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent $r1) throws  {
        if (sendBroadcast($r1)) {
            executePendingBroadcasts();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executePendingBroadcasts() throws  {
        /*
        r14 = this;
    L_0x0000:
        r0 = r14.mReceivers;
        monitor-enter(r0);
        r1 = r14.mPendingBroadcasts;	 Catch:{ Throwable -> 0x003f }
        r2 = r1.size();	 Catch:{ Throwable -> 0x003f }
        if (r2 > 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x003f }
        return;
    L_0x000d:
        r3 = new android.support.v4.content.LocalBroadcastManager.BroadcastRecord[r2];	 Catch:{ Throwable -> 0x003f }
        r1 = r14.mPendingBroadcasts;	 Catch:{ Throwable -> 0x003f }
        r1.toArray(r3);	 Catch:{ Throwable -> 0x003f }
        r1 = r14.mPendingBroadcasts;	 Catch:{ Throwable -> 0x003f }
        r1.clear();	 Catch:{ Throwable -> 0x003f }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x003f }
        r2 = 0;
    L_0x001b:
        r4 = r3.length;
        if (r2 >= r4) goto L_0x0000;
    L_0x001e:
        r5 = r3[r2];
        r4 = 0;
    L_0x0021:
        r1 = r5.receivers;
        r6 = r1.size();
        if (r4 >= r6) goto L_0x0042;
    L_0x0029:
        r1 = r5.receivers;
        r7 = r1.get(r4);
        r9 = r7;
        r9 = (android.support.v4.content.LocalBroadcastManager.ReceiverRecord) r9;
        r8 = r9;
        r10 = r8.receiver;
        r11 = r14.mAppContext;
        r12 = r5.intent;
        r10.onReceive(r11, r12);
        r4 = r4 + 1;
        goto L_0x0021;
    L_0x003f:
        r13 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x003f }
        throw r13;
    L_0x0042:
        r2 = r2 + 1;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
