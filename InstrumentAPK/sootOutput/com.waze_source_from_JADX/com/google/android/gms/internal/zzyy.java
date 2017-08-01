package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqk.zzb;
import com.google.android.gms.people.Images;
import com.google.android.gms.people.Images.LoadImageOptions;
import com.google.android.gms.people.Images.LoadImageResult;
import com.google.android.gms.people.Images.SetAvatarResult;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.internal.zzn;
import com.google.android.gms.people.model.AvatarReference;
import dalvik.annotation.Signature;

@SuppressLint({"MissingRemoteException"})
/* compiled from: dalvik_source_com.waze.apk */
public class zzyy implements Images {

    /* compiled from: dalvik_source_com.waze.apk */
    private static abstract class zza extends com.google.android.gms.people.People.zza<LoadImageResult> {
        private zza(GoogleApiClient $r1) throws  {
            super($r1);
        }

        public /* synthetic */ Result zzb(Status $r1) throws  {
            return zzgi($r1);
        }

        public final LoadImageResult zzgi(final Status $r1) throws  {
            return new LoadImageResult(this) {
                final /* synthetic */ zza aTy;

                public int getHeight() throws  {
                    return 0;
                }

                public ParcelFileDescriptor getParcelFileDescriptor() throws  {
                    return null;
                }

                public Status getStatus() throws  {
                    return $r1;
                }

                public int getWidth() throws  {
                    return 0;
                }

                public boolean isRewindable() throws  {
                    return false;
                }

                public void release() throws  {
                }
            };
        }
    }

    public PendingResult<LoadImageResult> loadByReference(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final AvatarReference $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/model/AvatarReference;", "Lcom/google/android/gms/people/Images$LoadImageOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final LoadImageOptions $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadByReference", $r2, $r3);
        }
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zza((zzb) this, $r2, $r3));
            }
        });
    }

    public PendingResult<LoadImageResult> loadByUrl(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int $i1) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadByUrl", $r2, Integer.valueOf($i0), Integer.valueOf($i1));
        }
        final String str = $r2;
        final int i = $i0;
        final int i2 = $i1;
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zza((zzb) this, str, i, i2));
            }
        });
    }

    public PendingResult<LoadImageResult> loadContactThumbnailByContactId(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "J)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final long $l0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadContactThumbnailByContactId", Long.valueOf($l0));
        }
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zza((zzb) this, $l0));
            }
        });
    }

    public PendingResult<LoadImageResult> loadOwnerAvatar(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int $i0, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "II)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int $i1) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadOwnerAvatar", $r2, $r3, Integer.valueOf($i0), Integer.valueOf($i1));
        }
        final String str = $r2;
        final String str2 = $r3;
        final int i = $i0;
        final int i2 = $i1;
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zzb((zzb) this, str, str2, i, i2));
            }
        });
    }

    public PendingResult<LoadImageResult> loadOwnerCoverPhoto(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final String $r3) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadOwnerCoverPhoto", $r2, $r3);
        }
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zza((zzb) this, $r2, $r3, 0));
            }
        });
    }

    public PendingResult<LoadImageResult> loadOwnerCoverPhoto(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) int i) throws  {
        return loadOwnerCoverPhoto($r1, $r2, $r3);
    }

    public PendingResult<LoadImageResult> loadRemoteImage(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$LoadImageResult;", ">;"}) final String $r2) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("loadRemoteImage", $r2);
        }
        return $r1.zzc(new zza(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                zza($r1.zzw(this, $r2));
            }
        });
    }

    public PendingResult<SetAvatarResult> setAvatar(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) GoogleApiClient $r1, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) String $r3, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) Uri $r4, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/net/Uri;", "Z)", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Images$SetAvatarResult;", ">;"}) boolean $z0) throws  {
        if (zzl.isEnabled()) {
            zzl.zzh("setAvatar", $r2, $r3, $r4, Boolean.valueOf($z0));
        }
        final String str = $r2;
        final String str2 = $r3;
        final Uri uri = $r4;
        final boolean z = $z0;
        return $r1.zzd(new com.google.android.gms.people.People.zza<SetAvatarResult>(this, $r1) {
            final /* synthetic */ zzyy aTq;

            protected void zza(zzn $r1) throws  {
                $r1.zza((zzb) this, str, str2, uri, z);
            }

            protected /* synthetic */ Result zzb(Status $r1) throws  {
                return zzgh($r1);
            }

            protected SetAvatarResult zzgh(final Status $r1) throws  {
                return new SetAvatarResult(this) {
                    final /* synthetic */ C09161 aTr;

                    public Status getStatus() throws  {
                        return $r1;
                    }

                    public String getUrl() throws  {
                        return null;
                    }
                };
            }
        });
    }
}
