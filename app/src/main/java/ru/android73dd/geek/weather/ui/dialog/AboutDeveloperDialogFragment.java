package ru.android73dd.geek.weather.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.android73dd.geek.weather.R;

public class AboutDeveloperDialogFragment extends DialogFragment {

    public static final String TAG = "about developer dialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        Resources resources = activity.getResources();
        if (resources == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        String title = resources.getString(R.string.fragment_about_developer_title);
        String message = resources.getString(R.string.fragment_about_developer_body);
        String buttonOk = resources.getString(R.string.ok);

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonOk, null);
        builder.setCancelable(true);

        return builder.create();
    }
}
