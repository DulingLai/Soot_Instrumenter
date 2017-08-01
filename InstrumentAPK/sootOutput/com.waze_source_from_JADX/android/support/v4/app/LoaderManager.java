package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {

    public interface LoaderCallbacks<D> {
        Loader<D> onCreateLoader(@Signature({"(I", "Landroid/os/Bundle;", ")", "Landroid/support/v4/content/Loader", "<TD;>;"}) int i, @Signature({"(I", "Landroid/os/Bundle;", ")", "Landroid/support/v4/content/Loader", "<TD;>;"}) Bundle bundle) throws ;

        void onLoadFinished(@Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;TD;)V"}) Loader<D> loader, @Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;TD;)V"}) D d) throws ;

        void onLoaderReset(@Signature({"(", "Landroid/support/v4/content/Loader", "<TD;>;)V"}) Loader<D> loader) throws ;
    }

    public abstract void destroyLoader(int i) throws ;

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws ;

    public abstract <D> Loader<D> getLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int i) throws ;

    public boolean hasRunningLoaders() throws  {
        return false;
    }

    public abstract <D> Loader<D> initLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int i, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) Bundle bundle, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) LoaderCallbacks<D> loaderCallbacks) throws ;

    public abstract <D> Loader<D> restartLoader(@Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) int i, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) Bundle bundle, @Signature({"<D:", "Ljava/lang/Object;", ">(I", "Landroid/os/Bundle;", "Landroid/support/v4/app/LoaderManager$LoaderCallbacks", "<TD;>;)", "Landroid/support/v4/content/Loader", "<TD;>;"}) LoaderCallbacks<D> loaderCallbacks) throws ;

    public static void enableDebugLogging(boolean $z0) throws  {
        LoaderManagerImpl.DEBUG = $z0;
    }
}
