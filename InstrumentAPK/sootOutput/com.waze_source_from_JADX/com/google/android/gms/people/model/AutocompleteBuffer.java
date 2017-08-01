package com.google.android.gms.people.model;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.internal.zzb;

/* compiled from: dalvik_source_com.waze.apk */
public class AutocompleteBuffer extends DataBufferWithSyncInfo<AutocompleteEntry> {
    public AutocompleteBuffer(DataHolder $r1) throws  {
        super($r1);
    }

    public AutocompleteEntry get(int $i0) throws  {
        return new zzb(this, this.DW, $i0, zzava());
    }

    public String toString() throws  {
        return "AutocompleteList:size=" + getCount();
    }

    public DataHolder zzbfb() throws  {
        return this.DW;
    }
}
