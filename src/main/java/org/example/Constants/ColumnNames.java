package org.example.Constants;

public class ColumnNames {
    // Menu Items Table Columns
    public static class MenuItems {
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
    }

    // Order Items Table Columns
    public static class OrderItems {
        public static final String ORDER_ITEM_ID = "order_item_id";
        public static final String ORDER_ID = Orders.ORDER_ID;
        public static final String ITEM_ID = MenuItems.ITEM_ID;
        public static final String SPECIAL_INSTRUCTIONS = "special_instructions";
        public static final String QUANTITY = "quantity";
        public static final String SUBTOTAL = "subtotal";
    }

    // Orders Table Columns
    public static class Orders {
        public static final String ORDER_ID = "order_id";
        public static final String TABLE_NUMBER = Tables.TABLE_NUMBER;
        public static final String USER_ID = Users.USER_ID;
        public static final String TIME = "time";
        public static final String TOTAL = "total";
        public static final String IS_PAID = "is_paid";
        public static final String IS_CANCELED = "is_canceled";
    }

    // Reservations Table Columns
    public static class Reservations {
        public static final String RESERVATION_ID = "reservation_id";
        public static final String TABLE_NUMBER = Tables.TABLE_NUMBER;
        public static final String DATE = "date";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
    }

    // Tables Table Columns
    public static class Tables {
        public static final String TABLE_NUMBER = "table_number";
        public static final String CAPACITY = "capacity";
    }

    // Users Table Columns
    public static class Users {
        public static final String USER_ID = "user_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String STAFF_TYPE = "staff_type";
    }
}
