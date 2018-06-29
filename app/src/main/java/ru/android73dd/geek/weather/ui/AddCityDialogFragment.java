package ru.android73dd.geek.weather.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import ru.android73dd.geek.weather.R;

public class AddCityDialogFragment extends DialogFragment {

    private AddClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        Resources resources = activity.getResources();
        if (resources == null) {
            return super.onCreateDialog(savedInstanceState);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View layout = activity.getLayoutInflater().inflate(R.layout.add_city_dialog, null);
        builder.setView(layout);
        builder.setCancelable(true);

        final TextInputEditText editText = layout.findViewById(R.id.et_city_name);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AppCompatButton button = layout.findViewById(R.id.bt_add_city);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddClick(editText.getText().toString());
            }
        });

        return builder.create();
    }

    interface AddClickListener {
        void onAddClick(String s);
    }

    public void setListener(AddClickListener listener) {
        this.listener = listener;
    }
}
