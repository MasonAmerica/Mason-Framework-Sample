package com.mason.example.framework.ui.fota;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mason.example.framework.R;
import com.mason.example.framework.receiver.FotaReceiver;
import com.mason.example.framework.util.alert.SecurityExceptionFragment;
import masonamerica.platform.FotaManagerPrivileged;
import masonamerica.platform.MasonFramework;

public class FotaManagerFragment extends Fragment implements View.OnClickListener {
    public static final String SHARED_PREFS = "mason-framework-prefs";
    private static final String TAG = "FotaManagerFragment";
    private FotaManagerPrivileged fmp;
    private Button fotaInstallButton;
    private SharedPreferences sharedPreferences;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_fota_manager, viewGroup, false);
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS, 0);
        fmp = (FotaManagerPrivileged) MasonFramework.get(getContext(), FotaManagerPrivileged.class);
        fotaInstallButton = (Button) inflate.findViewById(R.id.fota_manager_install_button);
        fotaInstallButton.setOnClickListener(this);
        return inflate;
    }

    public void onClick(View view) {
        try {
            if (view.getId() == R.id.fota_manager_install_button) {
                String string = sharedPreferences.getString(FotaReceiver.OTA_KEY, (String) null);
                if (string != null) {
                    Toast.makeText(getContext(), "OTA available. Installing " + string, 0).show();
                    fmp.requestInstall(string);
                    return;
                }
                Toast.makeText(getContext(), "OTA not available.", 0).show();
            }
        } catch (SecurityException err) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }
}