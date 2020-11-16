package com.mason.example.framework.ui.alarm;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
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

import java.util.Calendar;

import masonamerica.platform.AlarmManagerPrivileged;
import masonamerica.platform.MasonFramework;

public class AlarmManagerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AlarmManagerFragment";
    private AlarmManagerPrivileged amp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm_manager, container, false);

        amp = MasonFramework.get(getContext(), AlarmManagerPrivileged.class);

        final Button increaseTimeButton = rootView.findViewById(R.id.alarm_manager_increase_time_button);
        final Button decreaseTimeButton = rootView.findViewById(R.id.alarm_manager_decrease_time_button);
        final Button americaTimezoneButton = rootView.findViewById(R.id.alarm_manager_set_timezone_america_los_angeles);
        final Button asiaTimezoneButton = rootView.findViewById(R.id.alarm_manager_set_timezone_asia_taipei);
        increaseTimeButton.setOnClickListener(this);
        decreaseTimeButton.setOnClickListener(this);
        americaTimezoneButton.setOnClickListener(this);
        asiaTimezoneButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.alarm_manager_increase_time_button) {
                amp.setTime(Calendar.getInstance().getTimeInMillis() + DateUtils.HOUR_IN_MILLIS);
            } else if (id == R.id.alarm_manager_decrease_time_button) {
                amp.setTime(Calendar.getInstance().getTimeInMillis() - DateUtils.HOUR_IN_MILLIS);
            } else if (id == R.id.alarm_manager_set_timezone_america_los_angeles) {
                amp.setTimeZone("America/Los_Angeles");
            } else if (id == R.id.alarm_manager_set_timezone_asia_taipei) {
                amp.setTimeZone("Asia/Taipei");
            }
        } catch (SecurityException err) {
            FragmentManager fm = getParentFragmentManager();
            SecurityExceptionFragment alertDialog = SecurityExceptionFragment.newInstance();
            alertDialog.show(fm, null);
        }
    }
}
