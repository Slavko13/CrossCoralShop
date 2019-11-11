package ru.ordersforbigdata;

import ru.ordersforbigdata.ejb.OrdersManagerBean;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class OrderBean implements Serializable {

    @EJB
    OrdersManagerBean ordersManagerBean;

    public void createOrder() {
        ordersManagerBean.createOrder();
    }

    public int getId() {
       return ordersManagerBean.getId();

    }
}
