package com.codecool.shop.model.cart;


public enum CartStatus {
    ACTIVE(1){},
    EXPIRED(2){},
    ORDERED(3){};

    private int id;
    private static final CartStatus[] cartStatuses = {ACTIVE, EXPIRED, ORDERED};

    CartStatus(int id) {
        this.id = id;
    }

    public static CartStatus getStatusForId(int id) {
        return cartStatuses[id-1];
    }

    public int getId() {
        return id;
    }
}
