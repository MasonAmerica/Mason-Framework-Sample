package com.mason.example.framework.ui.power;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mason.example.framework.R;
import com.mason.example.framework.util.alert.SecurityExceptionFragment;

import masonamerica.platform.MasonFramework;
import masonamerica.platform.PowerManagerPrivileged;

public class PowerManagerFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "PowerManagerFragment";
    private PowerManagerPrivileged pmp;

    @SuppressWarnings("FieldCanBeLocal")
    private Button rebootButton, shutdownButton, sleepButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_power_manager, container, false);

        pmp = MasonFramework.get(getContext(), PowerManagerPrivileged.class);

        rebootButton = rootView.findViewById(R.id.power_manager_reboot_button);
        shutdownButton = rootView.findViewById(R.id.power_manager_shutdown_button);
        sleepButton = rootView.findViewById(R.id.power_manager_sleep_button);

        rebootButton.setOnClickListener(this);
        shutdownButton.setOnClickListener(this);
        sleepButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.power_manager_reboot_button) {
                pmp.reboot(true, null, false);
            } else if (id == R.id.power_manager_shutdown_button) {
                pmp.shutdown(true, null, false);
            } else if (id == R.id.power_manager_sleep_button) {
                pmp.sleep();
            }
        } catch (SecurityException e) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }
}
