package com.codecool.shop.dao;

import com.codecool.shop.model.order.Address;

public interface AddressDao {

    void add(Address address);
    Address find(int id);
    void remove(int id);


}
