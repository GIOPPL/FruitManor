package com.gioppl.fruitmanor.tool;

public class ClientData {
    private int reduceCouponNum;//优惠券数量
    private int goodsCouponNum;//商品券数量
    private int money;//余额
    private int shopCartNum;//购物车商品数量
    private int gCoin;//积分

    public int getReduceCouponNum() {
        return reduceCouponNum;
    }

    public void setReduceCouponNum(int reduceCouponNum) {
        this.reduceCouponNum = reduceCouponNum;
    }

    public int getGoodsCouponNum() {
        return goodsCouponNum;
    }

    public void setGoodsCouponNum(int goodsCouponNum) {
        this.goodsCouponNum = goodsCouponNum;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getShopCartNum() {
        return shopCartNum;
    }

    public void setShopCartNum(int shopCartNum) {
        this.shopCartNum = shopCartNum;
    }

    public int getgCoin() {
        return gCoin;
    }

    public void setgCoin(int gCoin) {
        this.gCoin = gCoin;
    }
}
