package ru.ordersforbigdata;


import ru.ordersforbigdata.domain.Goods;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/good")
public class BuyServlet extends HttpServlet {


    @Inject
    private  GoodsBean goodsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idFormMain = req.getParameter("id");
        Integer goodId = Integer.parseInt(idFormMain.trim());
        List<Goods> popularGoods =  goodsBean.getPopularGoods(goodId);
        req.setAttribute("firstPopular" ,popularGoods.get(0).getName());
        req.setAttribute("secondPopular", popularGoods.get(1).getName());
        getServletContext().getRequestDispatcher("/main.xhtml").forward(
                req, resp);

    }
}
