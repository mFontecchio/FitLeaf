package com.bignerdeanch.android.fitleaf.database;

/**
 * Created by MFontecchio on 4/10/2018.
 */
public class CustomerDBSchema {

    //Define table
    public static final class CustomerTable {
        public static final String NAME = "customers";

        //Define columns
        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String CUSTOMER = "name";
            public static final String SESSION = "session";
            public static final String PHOTO = "photo";
            public static final String COMPLETED = "completed";
        }
    }
}
