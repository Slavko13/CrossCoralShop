package ru.ordersforbigdata.ejb;


import com.sun.mail.imap.protocol.INTERNALDATE;
import ru.ordersforbigdata.domain.Goods;
import ru.ordersforbigdata.domain.GoodsInOrders;
import ru.ordersforbigdata.domain.Orders;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@LocalBean
@Stateless
public class GoodsManagerBean {

    @PersistenceContext(unitName = "orderManager")
    private EntityManager entityManager;

    public boolean addToOrder(int goodsId, int orderID) {
        Orders order = entityManager.find(Orders.class, orderID);
        if (order == null) {
            return false;
        }

        Goods goods = entityManager.find(Goods.class, goodsId);
            if (goods == null) {
                return false;
            }

        GoodsInOrders goodsInOrders = new GoodsInOrders();
        goodsInOrders.setGoods(goods);
        goodsInOrders.setOrder(order);
        entityManager.persist(goodsInOrders);


        return true;

        }


    public List<Goods> getOrdersForCorrelation(int goodId) {
        TypedQuery<Goods> query = entityManager.createQuery("SELECT c from Goods c ", Goods.class);
        List<Goods> goodsList = query.getResultList();


        TypedQuery<GoodsInOrders> q = entityManager.createQuery("select  e from GoodsInOrders e where e.goods.id = :empNo", GoodsInOrders.class);
        q.setParameter("empNo", goodId);
        List<GoodsInOrders> goodsListInOrder = q.getResultList();

        List<Integer> tempOrdersIdWithNecessary = new ArrayList<>();
        for (GoodsInOrders goodsInOrders : goodsListInOrder) {
            tempOrdersIdWithNecessary.add(goodsInOrders.getOrder().getId());
        }
        List<Integer> OrdersIdWithNecessary = tempOrdersIdWithNecessary.stream().distinct().collect(Collectors.toList());
        List<Integer>  idGoodsInNecessaryOrder_List = new ArrayList<>();
        for (Integer orderId : OrdersIdWithNecessary) {
            TypedQuery<GoodsInOrders> orderQuery = entityManager.createQuery("select  g from GoodsInOrders g where g.order.id = :orderId", GoodsInOrders.class);
            orderQuery.setParameter("orderId", orderId);
            List<GoodsInOrders> tempForGetGoodsIdListInOrder = orderQuery.getResultList();
            for (GoodsInOrders goodsInOrders : tempForGetGoodsIdListInOrder) {
                idGoodsInNecessaryOrder_List.add(goodsInOrders.getGoods().getId());
            }
        }
        System.out.println(idGoodsInNecessaryOrder_List);

        Map<Integer, Integer> finalMap = new HashMap<>();
        for (Goods goods : goodsList) {
            Integer orderCounter = 0;
           for (int i=0; i<idGoodsInNecessaryOrder_List.size()-1;i++ ) {
               if ((Integer) idGoodsInNecessaryOrder_List.toArray()[i] == goods.getId()) {
                   orderCounter++;
               }
               if (goodId != goods.getId()) {
                   finalMap.put(goodId*10+goods.getId(),orderCounter);
               }

           }
        }
        LinkedHashMap forTest;
        forTest =  finalMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue )-> oldValue, LinkedHashMap::new));
        Integer firstPopularPair = (Integer) forTest.keySet().toArray()[forTest.size()-1];
        Integer secondPopularPair = (Integer) forTest.keySet().toArray()[forTest.size()-2];
        Integer firstPopularId = getFirstNumber(firstPopularPair);
        Integer secondPopularId = getFirstNumber(secondPopularPair);

        List<Goods> finalListWithNecessaryId = new ArrayList<>();
        Goods firstGood = entityManager.find(Goods.class, firstPopularId);
        finalListWithNecessaryId.add(firstGood);
        Goods secondGood = entityManager.find(Goods.class, secondPopularId);
        finalListWithNecessaryId.add(secondGood);

        return finalListWithNecessaryId;

    }
    private Integer getFirstNumber(Integer number) {
        Integer firstNumber = number % 10;
        return firstNumber;
    }

    public List<Goods> getGoodsInOrder(Integer orderId) {
        Orders order = entityManager.find(Orders.class, orderId);
        if (order == null) {
            return Collections.emptyList();
        }

        List<GoodsInOrders> goodsInOrders_inGoodsList = order.getGoodsInOrders();
        List<Goods> goods = new ArrayList<>();
        for (GoodsInOrders goodsInOrders : goodsInOrders_inGoodsList) {
            goods.add(goodsInOrders.getGoods());
        }
        return goods;
    }

    public List<Goods> getGoods() {
        TypedQuery<Goods> query = entityManager.createQuery("select c from Goods c", Goods.class);
        return query.getResultList();
    }
}

