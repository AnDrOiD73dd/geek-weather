package ru.android73dd.geek.weather.ui.dialog;

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

    public static final String TAG = "Add city dialog";

    private ActionListener listener;

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
                if (listener != null) {
                    listener.onTextChanged(s.toString());
                }
            }
        });
        AppCompatButton button = layout.findViewById(R.id.bt_add_city);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddClick(editText.getText().toString());
                }
            }
        });

        return builder.create();
    }

    public interface ActionListener {
        void onTextChanged(String s);
        void onAddClick(String s);
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
}
