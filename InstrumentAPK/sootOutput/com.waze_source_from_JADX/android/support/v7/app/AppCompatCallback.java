package android.support.v7.app;

import android.support.annotation.Nullable;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;

public interface AppCompatCallback {
    void onSupportActionModeFinished(ActionMode actionMode) throws ;

    void onSupportActionModeStarted(ActionMode actionMode) throws ;

    @Nullable
    ActionMode onWindowStartingSupportActionMode(Callback callback) throws ;
}
