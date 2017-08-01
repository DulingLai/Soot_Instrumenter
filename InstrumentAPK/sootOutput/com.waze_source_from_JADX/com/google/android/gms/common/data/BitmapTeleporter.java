package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: dalvik_source_com.waze.apk */
public class BitmapTeleporter extends AbstractSafeParcelable {
    public static final Creator<BitmapTeleporter> CREATOR = new zza();
    private Bitmap GI;
    private boolean GJ;
    private File GK;
    final int bG;
    final int mVersionCode;
    ParcelFileDescriptor zzcct;

    BitmapTeleporter(int $i0, ParcelFileDescriptor $r1, int $i1) throws  {
        this.mVersionCode = $i0;
        this.zzcct = $r1;
        this.bG = $i1;
        this.GI = null;
        this.GJ = false;
    }

    public BitmapTeleporter(Bitmap $r1) throws  {
        this.mVersionCode = 1;
        this.zzcct = null;
        this.bG = 0;
        this.GI = $r1;
        this.GJ = true;
    }

    private void zza(Closeable $r1) throws  {
        try {
            $r1.close();
        } catch (IOException $r2) {
            Log.w("BitmapTeleporter", "Could not close stream", $r2);
        }
    }

    private FileOutputStream zzavb() throws  {
        if (this.GK == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            File $r1 = File.createTempFile("teleporter", ".tmp", this.GK);
            try {
                FileOutputStream $r3 = new FileOutputStream($r1);
                this.zzcct = ParcelFileDescriptor.open($r1, 268435456);
                $r1.delete();
                return $r3;
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("Temporary file is somehow already deleted");
            }
        } catch (IOException $r5) {
            throw new IllegalStateException("Could not create temporary file", $r5);
        }
    }

    public Bitmap get() throws  {
        boolean $z0 = this.GJ;
        this = this;
        if (!$z0) {
            DataInputStream $r1 = new DataInputStream(new AutoCloseInputStream(this.zzcct));
            try {
                byte[] $r4 = new byte[$r1.readInt()];
                int $i0 = $r1.readInt();
                int $i1 = $r1.readInt();
                Config $r6 = Config.valueOf($r1.readUTF());
                $r1.read($r4);
                zza($r1);
                ByteBuffer $r7 = ByteBuffer.wrap($r4);
                Bitmap $r8 = Bitmap.createBitmap($i0, $i1, $r6);
                $r8.copyPixelsFromBuffer($r7);
                this.GI = $r8;
                this.GJ = true;
            } catch (IOException $r9) {
                throw new IllegalStateException("Could not read from parcel file descriptor", $r9);
            } catch (Throwable th) {
                zza($r1);
            }
        }
        return this.GI;
    }

    public void release() throws  {
        if (!this.GJ) {
            try {
                this.zzcct.close();
            } catch (IOException $r2) {
                Log.w("BitmapTeleporter", "Could not close PFD", $r2);
            }
        }
    }

    public void setTempDir(File $r1) throws  {
        if ($r1 == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.GK = $r1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        if (this.zzcct == null) {
            Bitmap $r4 = this.GI;
            ByteBuffer $r5 = ByteBuffer.allocate($r4.getRowBytes() * $r4.getHeight());
            $r4.copyPixelsToBuffer($r5);
            byte[] $r6 = $r5.array();
            DataOutputStream $r2 = new DataOutputStream(zzavb());
            try {
                $r2.writeInt($r6.length);
                $r2.writeInt($r4.getWidth());
                $r2.writeInt($r4.getHeight());
                $r2.writeUTF($r4.getConfig().toString());
                $r2.write($r6);
                zza($r2);
            } catch (IOException $r10) {
                throw new IllegalStateException("Could not write into unlinked file", $r10);
            } catch (Throwable th) {
                zza($r2);
            }
        }
        zza.zza(this, $r1, $i0 | 1);
        this.zzcct = null;
    }
}
