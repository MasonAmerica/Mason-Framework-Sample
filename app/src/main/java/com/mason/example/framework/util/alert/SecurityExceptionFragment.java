package com.mason.example.framework.util.alert;
;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SecurityExceptionFragment extends DialogFragment {
    public SecurityExceptionFragment() {} // Empty constructor required for DialogFragment

    public static SecurityExceptionFragment newInstance() {
        SecurityExceptionFragment frag = new SecurityExceptionFragment();
        Bundle args = new Bundle();
        args.putString("title", "Security Exception");
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Ensure your application is declared in your Project and " +
                "that the project has been deployed to your device.");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }
}
