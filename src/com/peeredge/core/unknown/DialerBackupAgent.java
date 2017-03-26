package com.peeredge.core.unknown;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

/**
 * Created by samson on 3/24/17.
 */
public class DialerBackupAgent extends BackupAgentHelper
{
    private static final String SHARED_KEY = "shared_pref";

    @Override
    public void onCreate() {
        addHelper(SHARED_KEY, new SharedPreferencesBackupHelper(this,
                "SHARED_PREFS_NAME"));
       //         DialtactsActivity.SHARED_PREFS_NAME));
    }
}