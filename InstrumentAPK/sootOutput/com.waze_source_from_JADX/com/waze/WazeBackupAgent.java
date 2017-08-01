package com.waze;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import java.io.IOException;

public class WazeBackupAgent extends BackupAgentHelper {
    static final String PREFS_BACKUP = "prefs_backup";
    static final String PREFS_BACKUP_KEY = "backup_key";

    public void onCreate() throws  {
        addHelper(PREFS_BACKUP_KEY, new SharedPreferencesBackupHelper(this, new String[]{PREFS_BACKUP}));
    }

    public static void putString(Context $r0, String $r1, String $r2) throws  {
        $r0.getSharedPreferences(PREFS_BACKUP, 0).edit().putString($r1, $r2).commit();
    }

    public static String getString(Context $r0, String $r1, String $r2) throws  {
        return $r0.getSharedPreferences(PREFS_BACKUP, 0).getString($r1, $r2);
    }

    public void onBackup(ParcelFileDescriptor $r1, BackupDataOutput $r2, ParcelFileDescriptor $r3) throws IOException {
        super.onBackup($r1, $r2, $r3);
    }

    public void onRestore(BackupDataInput $r1, int $i0, ParcelFileDescriptor $r2) throws IOException {
        super.onRestore($r1, $i0, $r2);
    }
}
