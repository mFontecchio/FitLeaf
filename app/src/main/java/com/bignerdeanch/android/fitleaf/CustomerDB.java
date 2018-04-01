package com.bignerdeanch.android.fitleaf;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MFontecchio on 3/31/2018.
 */
public class CustomerDB {

    private static CustomerDB sCustomerDB;

    private List<Customer> mCustomerList;

    public static CustomerDB get(Context context) {
        if (sCustomerDB == null) {
            sCustomerDB = new CustomerDB(context);
        }
        return sCustomerDB;
    }

    private CustomerDB(Context context) {
        mCustomerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setName("Michael Fontecchio");
        mCustomerList.add(customer);
    }

    public List<Customer> getCustomerList(){
        return mCustomerList;
    }

    public Customer getCustomer(UUID id) {
        for (Customer customer : mCustomerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }
}
