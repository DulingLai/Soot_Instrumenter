package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public abstract class ShareOpenGraphValueContainer<P extends ShareOpenGraphValueContainer, E extends Builder> implements ShareModel {
    private final Bundle bundle;

    public static abstract class Builder<P extends ShareOpenGraphValueContainer, E extends Builder> implements ShareModelBuilder<P, E> {
        private Bundle bundle = new Bundle();

        public E putBoolean(@Signature({"(", "Ljava/lang/String;", "Z)TE;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Z)TE;"}) boolean $z0) throws  {
            this.bundle.putBoolean($r1, $z0);
            return this;
        }

        public E putBooleanArray(@Signature({"(", "Ljava/lang/String;", "[Z)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "[Z)TE;"}) boolean[] $r2) throws  {
            this.bundle.putBooleanArray($r1, $r2);
            return this;
        }

        public E putDouble(@Signature({"(", "Ljava/lang/String;", "D)TE;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "D)TE;"}) double $d0) throws  {
            this.bundle.putDouble($r1, $d0);
            return this;
        }

        public E putDoubleArray(@Signature({"(", "Ljava/lang/String;", "[D)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "[D)TE;"}) double[] $r2) throws  {
            this.bundle.putDoubleArray($r1, $r2);
            return this;
        }

        public E putInt(@Signature({"(", "Ljava/lang/String;", "I)TE;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I)TE;"}) int $i0) throws  {
            this.bundle.putInt($r1, $i0);
            return this;
        }

        public E putIntArray(@Signature({"(", "Ljava/lang/String;", "[I)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "[I)TE;"}) int[] $r2) throws  {
            this.bundle.putIntArray($r1, $r2);
            return this;
        }

        public E putLong(@Signature({"(", "Ljava/lang/String;", "J)TE;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "J)TE;"}) long $l0) throws  {
            this.bundle.putLong($r1, $l0);
            return this;
        }

        public E putLongArray(@Signature({"(", "Ljava/lang/String;", "[J)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "[J)TE;"}) long[] $r2) throws  {
            this.bundle.putLongArray($r1, $r2);
            return this;
        }

        public E putObject(@Signature({"(", "Ljava/lang/String;", "Lcom/facebook/share/model/ShareOpenGraphObject;", ")TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Lcom/facebook/share/model/ShareOpenGraphObject;", ")TE;"}) ShareOpenGraphObject $r2) throws  {
            this.bundle.putParcelable($r1, $r2);
            return this;
        }

        public E putObjectArrayList(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/ShareOpenGraphObject;", ">;)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/ShareOpenGraphObject;", ">;)TE;"}) ArrayList<ShareOpenGraphObject> $r2) throws  {
            this.bundle.putParcelableArrayList($r1, $r2);
            return this;
        }

        public E putPhoto(@Signature({"(", "Ljava/lang/String;", "Lcom/facebook/share/model/SharePhoto;", ")TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Lcom/facebook/share/model/SharePhoto;", ")TE;"}) SharePhoto $r2) throws  {
            this.bundle.putParcelable($r1, $r2);
            return this;
        }

        public E putPhotoArrayList(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/SharePhoto;", ">;)TE;"}) ArrayList<SharePhoto> $r2) throws  {
            this.bundle.putParcelableArrayList($r1, $r2);
            return this;
        }

        public E putString(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")TE;"}) String $r2) throws  {
            this.bundle.putString($r1, $r2);
            return this;
        }

        public E putStringArrayList(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)TE;"}) String $r1, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)TE;"}) ArrayList<String> $r2) throws  {
            this.bundle.putStringArrayList($r1, $r2);
            return this;
        }

        public E readFrom(@Signature({"(TP;)TE;"}) P $r1) throws  {
            if ($r1 == null) {
                return this;
            }
            this.bundle.putAll($r1.getBundle());
            return this;
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected ShareOpenGraphValueContainer(@Signature({"(", "Lcom/facebook/share/model/ShareOpenGraphValueContainer$Builder", "<TP;TE;>;)V"}) Builder<P, E> $r1) throws  {
        this.bundle = (Bundle) $r1.bundle.clone();
    }

    ShareOpenGraphValueContainer(Parcel $r1) throws  {
        this.bundle = $r1.readBundle(Builder.class.getClassLoader());
    }

    @Nullable
    public Object get(String $r1) throws  {
        return this.bundle.get($r1);
    }

    public boolean getBoolean(String $r1, boolean $z0) throws  {
        return this.bundle.getBoolean($r1, $z0);
    }

    @Nullable
    public boolean[] getBooleanArray(String $r1) throws  {
        return this.bundle.getBooleanArray($r1);
    }

    public double getDouble(String $r1, double $d0) throws  {
        return this.bundle.getDouble($r1, $d0);
    }

    @Nullable
    public double[] getDoubleArray(String $r1) throws  {
        return this.bundle.getDoubleArray($r1);
    }

    public int getInt(String $r1, int $i0) throws  {
        return this.bundle.getInt($r1, $i0);
    }

    @Nullable
    public int[] getIntArray(String $r1) throws  {
        return this.bundle.getIntArray($r1);
    }

    public long getLong(String $r1, long $l0) throws  {
        return this.bundle.getLong($r1, $l0);
    }

    @Nullable
    public long[] getLongArray(String $r1) throws  {
        return this.bundle.getLongArray($r1);
    }

    public ShareOpenGraphObject getObject(String $r1) throws  {
        Object $r2 = this.bundle.get($r1);
        return $r2 instanceof ShareOpenGraphObject ? (ShareOpenGraphObject) $r2 : null;
    }

    @Nullable
    public ArrayList<ShareOpenGraphObject> getObjectArrayList(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/ShareOpenGraphObject;", ">;"}) String $r1) throws  {
        ArrayList $r3 = this.bundle.getParcelableArrayList($r1);
        if ($r3 == null) {
            return null;
        }
        ArrayList $r4 = new ArrayList();
        Iterator $r5 = $r3.iterator();
        while ($r5.hasNext()) {
            Parcelable $r7 = (Parcelable) $r5.next();
            if ($r7 instanceof ShareOpenGraphObject) {
                $r4.add((ShareOpenGraphObject) $r7);
            }
        }
        return $r4;
    }

    @Nullable
    public SharePhoto getPhoto(String $r1) throws  {
        Parcelable $r2 = this.bundle.getParcelable($r1);
        return $r2 instanceof SharePhoto ? (SharePhoto) $r2 : null;
    }

    @Nullable
    public ArrayList<SharePhoto> getPhotoArrayList(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/ArrayList", "<", "Lcom/facebook/share/model/SharePhoto;", ">;"}) String $r1) throws  {
        ArrayList $r3 = this.bundle.getParcelableArrayList($r1);
        if ($r3 == null) {
            return null;
        }
        ArrayList $r4 = new ArrayList();
        Iterator $r5 = $r3.iterator();
        while ($r5.hasNext()) {
            Parcelable $r7 = (Parcelable) $r5.next();
            if ($r7 instanceof SharePhoto) {
                $r4.add((SharePhoto) $r7);
            }
        }
        return $r4;
    }

    @Nullable
    public String getString(String $r1) throws  {
        return this.bundle.getString($r1);
    }

    @Nullable
    public ArrayList<String> getStringArrayList(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        return this.bundle.getStringArrayList($r1);
    }

    public Bundle getBundle() throws  {
        return (Bundle) this.bundle.clone();
    }

    public Set<String> keySet() throws  {
        return this.bundle.keySet();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeBundle(this.bundle);
    }
}
