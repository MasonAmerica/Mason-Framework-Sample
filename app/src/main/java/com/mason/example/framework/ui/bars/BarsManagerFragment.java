package com.mason.example.framework.ui.bars;

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

import masonamerica.platform.BarsManagerPrivileged;
import masonamerica.platform.MasonFramework;

public class BarsManagerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BarsManagerFragment";
    private BarsManagerPrivileged bmp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bars_manager, container, false);

        bmp = MasonFramework.get(getContext(), BarsManagerPrivileged.class);

        final Button enableNavigationBarButton = rootView.findViewById(R.id.bars_manager_enable_navigation_bar_button);
        final Button disableNavigationBarButton = rootView.findViewById(R.id.bars_manager_disable_navigation_bar_button);
        final Button enableStatusBarButton = rootView.findViewById(R.id.bars_manager_enable_status_bar_button);
        final Button disableStatusBarButton = rootView.findViewById(R.id.bars_manager_disable_status_bar_button);

        enableNavigationBarButton.setOnClickListener(this);
        disableNavigationBarButton.setOnClickListener(this);
        enableStatusBarButton.setOnClickListener(this);
        disableStatusBarButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.bars_manager_enable_navigation_bar_button) {
                bmp.setNavigationBarEnabled(true);
            } else if (id == R.id.bars_manager_disable_navigation_bar_button) {
                bmp.setNavigationBarEnabled(false);
            } else if (id == R.id.bars_manager_enable_status_bar_button) {
                bmp.setStatusBarEnabled(true);
            } else if (id == R.id.bars_manager_disable_status_bar_button) {
                bmp.setStatusBarEnabled(false);
            }
        } catch (SecurityException err) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }
}