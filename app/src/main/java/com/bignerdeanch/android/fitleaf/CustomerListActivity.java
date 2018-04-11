package com.bignerdeanch.android.fitleaf;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by MFontecchio on 4/5/2018.
 */
public class CustomerListActivity extends MultiFragmentActivity {

    @Override
    protected Fragment createFragment() {

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab_add_customer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer customer = new Customer();
                CustomerDB.get(CustomerListActivity.this).addCustomer(customer);
                Intent intent = PagerActivity
                        .newIntent(CustomerListActivity.this, customer.getId());
                startActivity(intent);
            }
        });

        return new CustomerListFragment();
    }
}
