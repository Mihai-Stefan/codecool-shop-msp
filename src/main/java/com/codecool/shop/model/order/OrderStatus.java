package com.codecool.shop.model.order;

public enum OrderStatus {

    PENDING_PAYMENT(1){},
    PROCESSING(2){},
    SHIPPING(3){},
    CLOSED(4){};

    private int id;
    private static final OrderStatus[] orderStatusList = {PENDING_PAYMENT, PROCESSING, SHIPPING, CLOSED};

    OrderStatus(int id) {
        this.id = id;
    }

    public static OrderStatus getStatusForId(int id) {
        return orderStatusList[id-1];
    }

    public int getId() {
        return id;
    }
}
