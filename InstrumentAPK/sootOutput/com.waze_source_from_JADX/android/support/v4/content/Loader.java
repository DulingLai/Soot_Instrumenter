package android.support.v4.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.util.DebugUtils;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class Loader<D> {
    boolean mAbandoned = false;
    boolean mContentChanged = false;
    Context mContext;
    int mId;
    OnLoadCompleteListener<D> mListener;
    OnLoadCanceledListener<D> mOnLoadCanceledListener;
    boolean mProcessingChange = false;
    boolean mReset = true;
    boolean mStarted = false;

    public interface OnLoadCanceledListener<D> {
        void onLoadCanceled(@Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;)V"}) Loader<D> loader) throws ;
    }

    public interface OnLoadCompleteListener<D> {
        void onLoadComplete(@Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;TD;)V"}) Loader<D> loader, @Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;TD;)V"}) D d) throws ;
    }

    public final class ForceLoadContentObserver extends ContentObserver {
        public boolean deliverSelfNotifications() throws  {
            return true;
        }

        public ForceLoadContentObserver() throws  {
            super(new Handler());
        }

        public void onChange(boolean selfChange) throws  {
            Loader.this.onContentChanged();
        }
    }

    protected boolean onCancelLoad() throws  {
        return false;
    }

    public Loader(Context $r1) throws  {
        this.mContext = $r1.getApplicationContext();
    }

    public void deliverResult(@Signature({"(TD;)V"}) D $r1) throws  {
        if (this.mListener != null) {
            this.mListener.onLoadComplete(this, $r1);
        }
    }

    public void deliverCancellation() throws  {
        if (this.mOnLoadCanceledListener != null) {
            this.mOnLoadCanceledListener.onLoadCanceled(this);
        }
    }

    public Context getContext() throws  {
        return this.mContext;
    }

    public int getId() throws  {
        return this.mId;
    }

    public void registerListener(@Signature({"(I", "Landroid/support/v4/content/Loader$OnLoadCompleteListener", "<TD;>;)V"}) int $i0, @Signature({"(I", "Landroid/support/v4/content/Loader$OnLoadCompleteListener", "<TD;>;)V"}) OnLoadCompleteListener<D> $r1) throws  {
        if (this.mListener != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.mListener = $r1;
        this.mId = $i0;
    }

    public void unregisterListener(@Signature({"(", "Landroid/support/v4/content/Loader$OnLoadCompleteListener", "<TD;>;)V"}) OnLoadCompleteListener<D> $r1) throws  {
        if (this.mListener == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.mListener != $r1) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.mListener = null;
        }
    }

    public void registerOnLoadCanceledListener(@Signature({"(", "Landroid/support/v4/content/Loader$OnLoadCanceledListener", "<TD;>;)V"}) OnLoadCanceledListener<D> $r1) throws  {
        if (this.mOnLoadCanceledListener != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.mOnLoadCanceledListener = $r1;
    }

    public void unregisterOnLoadCanceledListener(@Signature({"(", "Landroid/support/v4/content/Loader$OnLoadCanceledListener", "<TD;>;)V"}) OnLoadCanceledListener<D> $r1) throws  {
        if (this.mOnLoadCanceledListener == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.mOnLoadCanceledListener != $r1) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.mOnLoadCanceledListener = null;
        }
    }

    public boolean isStarted() throws  {
        return this.mStarted;
    }

    public boolean isAbandoned() throws  {
        return this.mAbandoned;
    }

    public boolean isReset() throws  {
        return this.mReset;
    }

    public final void startLoading() throws  {
        this.mStarted = true;
        this.mReset = false;
        this.mAbandoned = false;
        onStartLoading();
    }

    protected void onStartLoading() throws  {
    }

    public boolean cancelLoad() throws  {
        return onCancelLoad();
    }

    public void forceLoad() throws  {
        onForceLoad();
    }

    protected void onForceLoad() throws  {
    }

    public void stopLoading() throws  {
        this.mStarted = false;
        onStopLoading();
    }

    protected void onStopLoading() throws  {
    }

    public void abandon() throws  {
        this.mAbandoned = true;
        onAbandon();
    }

    protected void onAbandon() throws  {
    }

    public void reset() throws  {
        onReset();
        this.mReset = true;
        this.mStarted = false;
        this.mAbandoned = false;
        this.mContentChanged = false;
        this.mProcessingChange = false;
    }

    protected void onReset() throws  {
    }

    public boolean takeContentChanged() throws  {
        boolean $z0 = this.mContentChanged;
        this.mContentChanged = false;
        this.mProcessingChange |= $z0;
        return $z0;
    }

    public void commitContentChanged() throws  {
        this.mProcessingChange = false;
    }

    public void rollbackContentChanged() throws  {
        if (this.mProcessingChange) {
            this.mContentChanged = true;
        }
    }

    public void onContentChanged() throws  {
        if (this.mStarted) {
            forceLoad();
        } else {
            this.mContentChanged = true;
        }
    }

    public String dataToString(@Signature({"(TD;)", "Ljava/lang/String;"}) D $r1) throws  {
        StringBuilder $r2 = new StringBuilder(64);
        DebugUtils.buildShortClassTag($r1, $r2);
        $r2.append("}");
        return $r2.toString();
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder(64);
        DebugUtils.buildShortClassTag(this, $r1);
        $r1.append(" id=");
        $r1.append(this.mId);
        $r1.append("}");
        return $r1.toString();
    }

    public void dump(String $r1, FileDescriptor fd, PrintWriter $r3, String[] args) throws  {
        $r3.print($r1);
        $r3.print("mId=");
        $r3.print(this.mId);
        $r3.print(" mListener=");
        $r3.println(this.mListener);
        if (this.mStarted || this.mContentChanged || this.mProcessingChange) {
            $r3.print($r1);
            $r3.print("mStarted=");
            $r3.print(this.mStarted);
            $r3.print(" mContentChanged=");
            $r3.print(this.mContentChanged);
            $r3.print(" mProcessingChange=");
            $r3.println(this.mProcessingChange);
        }
        if (this.mAbandoned || this.mReset) {
            $r3.print($r1);
            $r3.print("mAbandoned=");
            $r3.print(this.mAbandoned);
            $r3.print(" mReset=");
            $r3.println(this.mReset);
        }
    }
}
