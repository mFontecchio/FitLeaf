package com.bignerdeanch.android.fitleaf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdeanch.android.fitleaf.database.CustomerBaseHelper;
import com.bignerdeanch.android.fitleaf.database.CustomerCursorWrapper;
import com.bignerdeanch.android.fitleaf.database.CustomerDBSchema.CustomerTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bignerdeanch.android.fitleaf.database.CustomerDBSchema.CustomerTable.Columns.CUSTOMER;

/**
 * Created by MFontecchio on 3/31/2018.
 */
public class CustomerDB {

    private static CustomerDB sCustomerDB;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CustomerDB get(Context context) {
        if (sCustomerDB == null) {
            sCustomerDB = new CustomerDB(context);
        }
        return sCustomerDB;
    }

    private CustomerDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext).getWritableDatabase();
    }

    //new Customer to db
    public void addCustomer(Customer c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerTable.NAME, null, values);
    }

    public List<Customer> getCustomerList(){
        List<Customer> customers = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return customers;
    }

    public Customer getCustomer(UUID id) {
        CustomerCursorWrapper cursor = queryCustomers(
                CustomerTable.Columns.UUID + " = ?" , new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCustomer();
        } finally {
            cursor.close();
        }
    }

    //Write to DB
    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomerTable.Columns.UUID, customer.getId().toString());
        values.put(CUSTOMER, customer.getName());
        values.put(CustomerTable.Columns.SESSION, customer.getDate().getTime());
        values.put(CustomerTable.Columns.PHOTO, customer.getPhoto());
        values.put(CustomerTable.Columns.COMPLETED, customer.isCompleted());

        return values;
    }

    //Update customer.
    public void updateCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.update(CustomerTable.NAME, values, CustomerTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }

    //Query customers structure
    private CustomerCursorWrapper queryCustomers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomerTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                CustomerTable.Columns.CUSTOMER  //Sort customers by name
        );

        return new CustomerCursorWrapper(cursor);
    }

    public void deleteCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.delete(CustomerTable.NAME, CustomerTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }
}
