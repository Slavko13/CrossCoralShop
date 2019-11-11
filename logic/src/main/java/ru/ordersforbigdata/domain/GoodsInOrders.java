package ru.ordersforbigdata.domain;


import javax.persistence.*;
import javax.persistence.criteria.Order;

@Entity
public class GoodsInOrders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Goods goods;

    @ManyToOne
    private Orders order;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Orders getOrder() {
        return order;
    }

}
