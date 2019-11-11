package ru.ordersforbigdata.ejb;


import ru.ordersforbigdata.domain.Orders;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;

@LocalBean
@Stateless
public class OrdersManagerBean {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PersistenceContext(unitName = "orderManager")
    private EntityManager entityManager;


    public boolean createOrder() {
        Orders order = new Orders();
        entityManager.persist(order);
        setId(order.getId());
        return true;
     }






}
