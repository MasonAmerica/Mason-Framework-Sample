package com.mason.example.framework.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mason.example.framework.R;

import masonamerica.platform.MasonFramework;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    @SuppressWarnings("FieldCanBeLocal")
    private TextView customerId, deviceId, productId, projectId, projectVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        customerId = rootView.findViewById(R.id.mason_customer_id_value);
        deviceId = rootView.findViewById(R.id.mason_device_id_value);
        productId = rootView.findViewById(R.id.mason_product_id_value);
        projectId = rootView.findViewById(R.id.mason_project_id_value);
        projectVersion = rootView.findViewById(R.id.mason_project_version_value);

        customerId.setText(MasonFramework.MASON_CUSTOMER);
        deviceId.setText(MasonFramework.MASON_DEVICE);
        productId.setText(MasonFramework.MASON_PRODUCT);
        projectId.setText(MasonFramework.MASON_PROJECT);
        projectVersion.setText(String.valueOf(MasonFramework.MASON_PROJECT_VERSION));

        return rootView;
    }
}
