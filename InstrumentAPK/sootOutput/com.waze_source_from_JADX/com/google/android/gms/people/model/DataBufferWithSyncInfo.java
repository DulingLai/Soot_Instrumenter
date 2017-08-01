package com.google.android.gms.people.model;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class DataBufferWithSyncInfo<T> extends AbstractDataBuffer<T> {
    protected DataBufferWithSyncInfo(DataHolder $r1) throws  {
        super($r1);
    }

    public long getLastSuccessfulSyncFinishTimestamp() throws  {
        return this.DW.zzava().getLong("last_successful_sync_finish_timestamp");
    }

    public long getLastSyncFinishTimestamp() throws  {
        return this.DW.zzava().getLong("last_sync_finish_timestamp");
    }

    public long getLastSyncStartTimestamp() throws  {
        return this.DW.zzava().getLong("last_sync_start_timestamp");
    }

    public int getLastSyncStatus() throws  {
        return this.DW.zzava().getInt("last_sync_status");
    }

    public boolean isPeriodicSyncEnabled() throws  {
        return this.DW.zzava().getBoolean("is_periodic_sync_enabled");
    }

    public boolean isTickleSyncEnabled() throws  {
        return this.DW.zzava().getBoolean("is_tickle_sync_enabled");
    }
}
