package kz.tech.smartgrades.authentication.fragments.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends androidx.fragment.app.Fragment {
    private SplashView splashView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        splashView = new SplashView(getActivity());
        return splashView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (splashView != null) {
            splashView = null;
        }
    }
}
