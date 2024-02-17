package edu.niu.android.stoplight;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LightControlFragment extends Fragment
{
    public LightControlFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_light_control, container, false);
    }
}
