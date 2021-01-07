package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.model.order.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDaoMem implements AddressDao {

    private List<Address> data = new ArrayList<>();
    private static AddressDaoMem instance = null;

    private AddressDaoMem() {}

    public static AddressDaoMem getInstance() {
        if (instance == null) {
            instance = new AddressDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Address address) {
        if (address.getId() == -1) {
            address.setId(data.size() + 1);
            data.add(address);
        }
    }

    @Override
    public Address find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }
}
