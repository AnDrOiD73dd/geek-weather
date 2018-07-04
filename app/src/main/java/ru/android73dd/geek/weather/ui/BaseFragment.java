package ru.android73dd.geek.weather.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Objects;

import ru.android73dd.geek.weather.ui.dialog.AboutDeveloperDialogFragment;

public class BaseFragment extends Fragment {

    public void showAboutDeveloper() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (manager != null) {
            AboutDeveloperDialogFragment aboutDeveloperFragment = new AboutDeveloperDialogFragment();
            aboutDeveloperFragment.show(manager, "about developer dialog");
        }
    }
}
