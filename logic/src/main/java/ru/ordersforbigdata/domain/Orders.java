package ru.ordersforbigdata.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch =FetchType.EAGER, mappedBy = "order")
    private List<GoodsInOrders> goodsInOrders ;

    private String orderName;

    public String getOrderName() {
        return orderName;
    }

    public List<GoodsInOrders> getGoodsInOrders() {
        return goodsInOrders;
    }

    public void setGoodsInOrders(List<GoodsInOrders> goodsInOrders) {
        this.goodsInOrders = goodsInOrders;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
