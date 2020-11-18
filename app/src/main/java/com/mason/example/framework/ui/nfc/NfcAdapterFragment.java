package com.mason.example.framework.ui.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.tech.NfcA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mason.example.framework.R;
import com.mason.example.framework.util.alert.SecurityExceptionFragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import masonamerica.platform.MasonFramework;
import masonamerica.platform.NfcAdapterPrivileged;

public class NfcAdapterFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "NfcAdapterFragment";
    private static final int STATE_UNKNOWN = 0;
    private NfcAdapterPrivileged nfc;

    @SuppressWarnings("FieldCanBeLocal")
    private Button enableNfcButton, disableNfcButton;
    public TextView nfcStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nfc_adapter, container, false);

        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
        getContext().registerReceiver(nfcReceiver, filter);

        nfc = MasonFramework.get(getContext(), NfcAdapterPrivileged.class);

        enableNfcButton = rootView.findViewById(R.id.nfc_adapter_enable_nfc_button);
        disableNfcButton = rootView.findViewById(R.id.nfc_adapter_disable_nfc_button);
        nfcStatus = rootView.findViewById(R.id.nfc_adapter_status);

        enableNfcButton.setOnClickListener(this);
        disableNfcButton.setOnClickListener(this);

        if (!isNfcAvailable()) {
            enableNfcButton.setEnabled(false);
            disableNfcButton.setEnabled(false);
            updateNfcState(STATE_UNKNOWN);
        } else {
            updateNfcState(checkNfcState());
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
        } catch (SecurityException e) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }

    // Handle NFC module state changes
    private final BroadcastReceiver nfcReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
                final int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, NfcAdapter.STATE_OFF);
                updateNfcState(state);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(nfcReceiver);
    }

    private void updateNfcState(int state) {
        switch (state) {
            case NfcAdapter.STATE_OFF:
                nfcStatus.setText(R.string.nfc_state_off);
                nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                break;
            case NfcAdapter.STATE_TURNING_ON:
                nfcStatus.setText(R.string.nfc_state_turning_on);
                nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                break;
            case NfcAdapter.STATE_ON:
                nfcStatus.setText(R.string.nfc_state_on);
                nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green));
                break;
            case NfcAdapter.STATE_TURNING_OFF:
                nfcStatus.setText(R.string.nfc_state_turning_off);
                nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                break;
            default:
                nfcStatus.setText(R.string.nfc_state_unknown);
                nfcStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
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

    private int checkNfcState() {
        NfcManager nfcManager = (NfcManager) requireContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = null;
        if (nfcManager != null) {
            adapter = nfcManager.getDefaultAdapter();
        }
        return (adapter.isEnabled()) ? NfcAdapter.STATE_ON : NfcAdapter.STATE_OFF;
    }
}
