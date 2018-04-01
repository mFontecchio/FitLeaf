package com.bignerdeanch.android.fitleaf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFragment = fm.findFragmentById(R.id.login_fragment);
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.login_fragment, fragment)
                    .commit();
        }

        if (fragment == null) {
            fragment = new CustomerFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
