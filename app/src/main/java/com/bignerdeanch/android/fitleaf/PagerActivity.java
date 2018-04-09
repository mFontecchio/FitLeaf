package com.bignerdeanch.android.fitleaf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;
import java.util.UUID;

/**
 * Created by MFontecchio on 4/7/2018.
 */
public class PagerActivity extends AppCompatActivity {

    private  static final String EXTRA_CUSTOMER_ID = "com.bignerdranch.android.fitleaf.customer_id";

    private ViewPager mViewPager;
    private List<Customer> mCustomers;

    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, PagerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        UUID customerId = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mCustomers = CustomerDB.get(this).getCustomerList();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Customer customer = mCustomers.get(position);
                return CustomerFragment.newInstance(customer.getId());
            }

            @Override
            public int getCount() {
                return mCustomers.size();
            }
        });

        for (int i = 0; i < mCustomers.size(); i++) {
            if (mCustomers.get(i).getId().equals(customerId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
