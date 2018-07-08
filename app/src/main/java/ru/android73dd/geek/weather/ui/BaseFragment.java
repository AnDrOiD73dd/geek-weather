package ru.android73dd.geek.weather.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import ru.android73dd.geek.weather.ui.dialog.AboutDeveloperDialogFragment;

public class BaseFragment extends Fragment {

    protected void showAboutDeveloper() {
        FragmentManager manager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (manager != null) {
            AboutDeveloperDialogFragment aboutDeveloperFragment = new AboutDeveloperDialogFragment();
            aboutDeveloperFragment.show(manager, AboutDeveloperDialogFragment.TAG);
        }
    }

    protected boolean isAboutDeveloperDialogNull(AppCompatActivity activity) {
        return activity.getSupportFragmentManager().findFragmentByTag(AboutDeveloperDialogFragment.TAG) == null;
    }
}
