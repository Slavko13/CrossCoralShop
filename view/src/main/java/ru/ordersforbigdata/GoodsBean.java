package ru.ordersforbigdata;


import ru.ordersforbigdata.domain.Goods;
import ru.ordersforbigdata.ejb.GoodsManagerBean;
import ru.ordersforbigdata.ejb.OrdersManagerBean;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class GoodsBean implements Serializable {

    private int id;

    @EJB
    GoodsManagerBean goodsManagerBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void addToOrder(int orderId, int id) {
        goodsManagerBean.addToOrder(id, orderId);
    }

    public List<Goods> getPopularGoods(int goodId) {
        return goodsManagerBean.getOrdersForCorrelation(goodId);
    }

    public List<Goods> getGoods() {
        return goodsManagerBean.getGoods();
    }

    public List<Goods> getGoodsInOrder(int orderId) {
        return goodsManagerBean.getGoodsInOrder(orderId);
    }

}
