package com.mason.example.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.mason.example.framework.ui.fota.FotaManagerFragment;

public class FotaReceiver extends BroadcastReceiver {
    private static final String ACTION_INSTALL_UPDATE = "com.bymason.platform.INSTALL_UPDATE";
    private static final String EXTRA_TARGET_UUID = "target_uuid";
    public static final String OTA_KEY = "ota.key";
    private static final String TAG = "FotaReceiver";

    public void onReceive(Context context, Intent intent) {
        if (ACTION_INSTALL_UPDATE.equals(intent.getAction())) {
            Log.d(TAG, "received action: " + intent.getAction());
            Log.d(TAG, "received extras: " + intent.getExtras().toString());
            context.getSharedPreferences(FotaManagerFragment.SHARED_PREFS, 0).edit().putString(OTA_KEY, intent.getStringExtra(EXTRA_TARGET_UUID)).apply();
        }
    }
}
