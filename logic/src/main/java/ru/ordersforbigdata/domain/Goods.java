package ru.ordersforbigdata.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Goods {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "goods")
    private List<GoodsInOrders> goodsInOrders ;

    public int getId() {
        return id;
    }

    public List<GoodsInOrders> getGoodsInOrders() {
        return goodsInOrders;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
