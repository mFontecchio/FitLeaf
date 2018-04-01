package com.bignerdeanch.android.fitleaf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MFontecchio on 4/1/2018.
 */
public class LoginFragment extends Fragment{

    //Inflate layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logged_in, container, false);
        return v;
    }
}
