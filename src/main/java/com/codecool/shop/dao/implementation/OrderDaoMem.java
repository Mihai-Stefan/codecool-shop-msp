package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.User;
import com.codecool.shop.model.order.Order;
import com.codecool.shop.model.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDaoMem implements OrderDao {

    private List<Order> data = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {}

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        if (order.getId() == -1) {
            order.setId(data.size() + 1);
            data.add(order);
        }
    }

    @Override
    public Order find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }


    @Override
    public List<Order> getBy(User user) {
        return data.stream().filter(t -> t.getUser().equals(user)).collect(Collectors.toList());
    }
    @Override
    public Order getActiveOrderForUser(User user) {

        List<Order> orderForUser = this.getBy(user);

        if (orderForUser.size() != 0) {
            for (Order order: orderForUser) {
                if (order.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT)) {
                    return order;
                }
            }
        }

        return null;
    }
}
