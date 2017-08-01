package com.google.android.gms.auth.firstparty.delegate;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.auth.firstparty.delegate.zza.zza;
import com.google.android.gms.auth.firstparty.shared.BlockingServiceClient;

/* compiled from: dalvik_source_com.waze.apk */
public class AccountSetupWorkflowServiceClient extends BlockingServiceClient {
    public AccountSetupWorkflowServiceClient(Context $r1) throws  {
        super($r1);
    }

    public PendingIntent getAccountSetupWorkflowIntent(final SetupAccountWorkflowRequest $r1) throws  {
        return (PendingIntent) exec(new Call<PendingIntent>(this) {
            final /* synthetic */ AccountSetupWorkflowServiceClient hW;

            public /* synthetic */ Object exec(IBinder $r1) throws RemoteException {
                return zzcz($r1);
            }

            public PendingIntent zzcz(IBinder $r1) throws RemoteException {
                return zza.zzda($r1).getAccountSetupWorkflowIntent($r1);
            }
        });
    }

    protected Intent getServiceIntent() throws  {
        return new Intent().setAction("com.google.android.gms.auth.setup.workflow.SETUP_WORKFLOW").addCategory("android.intent.category.DEFAULT");
    }
}
