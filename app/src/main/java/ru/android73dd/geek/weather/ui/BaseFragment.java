package ru.android73dd.geek.weather.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Objects;

public class BaseFragment extends Fragment {

    public void showAboutDeveloper() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (manager != null) {
            AboutDeveloperFragment aboutDeveloperFragment = new AboutDeveloperFragment();
            aboutDeveloperFragment.show(manager, "about developer dialog");
        }
    }

    public void showAddCityDialogFragment() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (manager != null) {
            final AddCityDialogFragment addCityDialog = new AddCityDialogFragment();
            addCityDialog.setListener(new AddCityDialogFragment.ActionListener() {
                @Override
                public void onTextChanged(String s) {

                }

                @Override
                public void onAddClick(String s) {
                    addCityDialog.dismiss();
                }
            });
            addCityDialog.show(manager, "Add city dialog");
        }
    }
}
