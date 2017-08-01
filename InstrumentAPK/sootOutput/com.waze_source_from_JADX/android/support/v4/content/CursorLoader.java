package android.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public Cursor loadInBackground() throws  {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            this.mCancellationSignal = new CancellationSignal();
        }
        Cursor $r12;
        try {
            $r12 = ContentResolverCompat.query(getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if ($r12 != null) {
                $r12.getCount();
                $r12.registerContentObserver(this.mObserver);
            }
            synchronized (this) {
                this.mCancellationSignal = null;
            }
            return $r12;
        } catch (RuntimeException $r6) {
            $r12.close();
            throw $r6;
        } catch (Throwable th) {
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        }
    }

    public void cancelLoadInBackground() throws  {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
        }
    }

    public void deliverResult(Cursor $r1) throws  {
        if (!isReset()) {
            Cursor $r2 = this.mCursor;
            this.mCursor = $r1;
            if (isStarted()) {
                super.deliverResult($r1);
            }
            if ($r2 != null && $r2 != $r1 && !$r2.isClosed()) {
                $r2.close();
            }
        } else if ($r1 != null) {
            $r1.close();
        }
    }

    public CursorLoader(Context $r1) throws  {
        super($r1);
    }

    public CursorLoader(Context $r1, Uri $r2, String[] $r3, String $r4, String[] $r5, String $r6) throws  {
        super($r1);
        this.mUri = $r2;
        this.mProjection = $r3;
        this.mSelection = $r4;
        this.mSelectionArgs = $r5;
        this.mSortOrder = $r6;
    }

    protected void onStartLoading() throws  {
        if (this.mCursor != null) {
            deliverResult(this.mCursor);
        }
        if (takeContentChanged() || this.mCursor == null) {
            forceLoad();
        }
    }

    protected void onStopLoading() throws  {
        cancelLoad();
    }

    public void onCanceled(Cursor $r1) throws  {
        if ($r1 != null && !$r1.isClosed()) {
            $r1.close();
        }
    }

    protected void onReset() throws  {
        super.onReset();
        onStopLoading();
        if (!(this.mCursor == null || this.mCursor.isClosed())) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    public Uri getUri() throws  {
        return this.mUri;
    }

    public void setUri(Uri $r1) throws  {
        this.mUri = $r1;
    }

    public String[] getProjection() throws  {
        return this.mProjection;
    }

    public void setProjection(String[] $r1) throws  {
        this.mProjection = $r1;
    }

    public String getSelection() throws  {
        return this.mSelection;
    }

    public void setSelection(String $r1) throws  {
        this.mSelection = $r1;
    }

    public String[] getSelectionArgs() throws  {
        return this.mSelectionArgs;
    }

    public void setSelectionArgs(String[] $r1) throws  {
        this.mSelectionArgs = $r1;
    }

    public String getSortOrder() throws  {
        return this.mSortOrder;
    }

    public void setSortOrder(String $r1) throws  {
        this.mSortOrder = $r1;
    }

    public void dump(String $r1, FileDescriptor $r2, PrintWriter $r3, String[] $r4) throws  {
        super.dump($r1, $r2, $r3, $r4);
        $r3.print($r1);
        $r3.print("mUri=");
        $r3.println(this.mUri);
        $r3.print($r1);
        $r3.print("mProjection=");
        $r3.println(Arrays.toString(this.mProjection));
        $r3.print($r1);
        $r3.print("mSelection=");
        $r3.println(this.mSelection);
        $r3.print($r1);
        $r3.print("mSelectionArgs=");
        $r3.println(Arrays.toString(this.mSelectionArgs));
        $r3.print($r1);
        $r3.print("mSortOrder=");
        $r3.println(this.mSortOrder);
        $r3.print($r1);
        $r3.print("mCursor=");
        $r3.println(this.mCursor);
        $r3.print($r1);
        $r3.print("mContentChanged=");
        $r3.println(this.mContentChanged);
    }
}
