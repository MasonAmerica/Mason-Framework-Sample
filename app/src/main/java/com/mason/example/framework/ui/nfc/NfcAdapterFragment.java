package com.mason.example.framework.ui.nfc;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mason.example.framework.R;
import com.mason.example.framework.util.alert.SecurityExceptionFragment;

import masonamerica.platform.MasonFramework;
import masonamerica.platform.NfcAdapterPrivileged;

public class NfcAdapterFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "NfcAdapterFragment";
    private NfcAdapterPrivileged nfc;

    @SuppressWarnings("FieldCanBeLocal")
    private Button enableNfcButton, disableNfcButton;
    private TextView nfcStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nfc_adapter, container, false);

        nfc = MasonFramework.get(getContext(), NfcAdapterPrivileged.class);

        enableNfcButton = rootView.findViewById(R.id.nfc_adapter_enable_nfc_button);
        disableNfcButton = rootView.findViewById(R.id.nfc_adapter_disable_nfc_button);
        nfcStatus = rootView.findViewById(R.id.nfc_adapter_status);

        enableNfcButton.setOnClickListener(this);
        disableNfcButton.setOnClickListener(this);

        if (!isNfcAvailable()) {
            enableNfcButton.setEnabled(false);
            disableNfcButton.setEnabled(false);
            setNfcStatus(false);
        } else {
            updateNfcState();
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.nfc_adapter_enable_nfc_button) {
                nfc.enable();
            } else if (id == R.id.nfc_adapter_disable_nfc_button) {
                nfc.disable();
            }
            updateNfcState();
        } catch (SecurityException e) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }

    private void updateNfcState() {
        try {
            Thread.sleep(1000); // 1 seconds delay
            if (isNfcEnabled()) {
                setNfcStatus(true);
            } else {
                setNfcStatus(false);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            Log.d(TAG, "Updating NFC State task interrupted");
        }
    }

    private boolean isNfcAvailable() {
        NfcManager nfcManager = (NfcManager) requireContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = null;
        if (nfcManager != null) {
            adapter = nfcManager.getDefaultAdapter();
        }
        return adapter != null;
    }

    private boolean isNfcEnabled() {
        boolean result = false;
        NfcManager nfcManager = (NfcManager) requireContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = null;
        if (nfcManager != null) {
            adapter = nfcManager.getDefaultAdapter();
            result = adapter.isEnabled();
        }
        return result;
    }

    private void setNfcStatus(boolean enabled) {
        if (enabled) {
            nfcStatus.setText(R.string.nfc_status_enabled);
            nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
        } else {
            nfcStatus.setText(R.string.nfc_status_disabled);
            nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        }
    }
}
