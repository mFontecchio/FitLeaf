package com.bignerdeanch.android.fitleaf.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdeanch.android.fitleaf.Customer;
import com.bignerdeanch.android.fitleaf.database.CustomerDBSchema.CustomerTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by MFontecchio on 4/10/2018.
 */
public class CustomerCursorWrapper extends CursorWrapper {

    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Customer getCustomer() {
        String uuidString = getString(getColumnIndex(CustomerTable.Columns.UUID));
        String name = getString(getColumnIndex(CustomerTable.Columns.CUSTOMER));
        long date = getLong(getColumnIndex(CustomerTable.Columns.SESSION));
        String photo = getString(getColumnIndex(CustomerTable.Columns.PHOTO));
        int isCompleted = getInt(getColumnIndex(CustomerTable.Columns.COMPLETED));

        Customer customer = new Customer(UUID.fromString(uuidString));
        customer.setName(name);
        customer.setDate(new Date(date));
        customer.setPhoto(photo);
        customer.setCompleted(isCompleted != 0);

        return customer;
    }
}
