package com.gioppl.fruitmanor.sql;

public class Table {
    /**
     * 购物车表
     */
    public class ShopCartTable{
        public static final String TABLE_NAME="ShopCartTable";

        public static final String ID="id";
        public static final String SHOP_ID="shop_id";
        public static final String GOODS_ID="goods_id";
        public static final String CLASSIFY="classify";
        public static final String PRICE="price";
        public static final String IMAGE_URL="image_url";
        public static final String SUBTITLE="subtitle";
        public static final String DISCOUNT="discount";
        public static final String TITLE="title";
        public static final String TOTAL_SALE="totalSale";
    }

    /**
     * 优惠券表
     */
    public class CouponTable{
        public static final String TABLE_NAME="CouponTable";

        public static final String ID="id";
        public static final String GOODS_ID="goods_id";
        public static final String REDUCE_MONEY="reduce_money";
        public static final String END_TIME="end_time";
        public static final String IMAGE_URL="image_url";
        public static final String TITLE="title";
    }


}
