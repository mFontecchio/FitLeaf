package com.bignerdeanch.android.fitleaf.database;

/**
 * Created by MFontecchio on 4/10/2018.
 */
public class SessionsDBSchema {

    //Session relational DB for session history. May or may not have time to implement.
    public static final class SessionTable {
        public static final String NAME = "sessions";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String CUSTOMER_ID = "customer_id";
            public static final String DATE = "date";
            public static final String COMPLETE = "complete";
        }
    }
}
