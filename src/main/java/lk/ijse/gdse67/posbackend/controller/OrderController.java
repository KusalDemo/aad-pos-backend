package lk.ijse.gdse67.posbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse67.posbackend.bo.BoFactory;
import lk.ijse.gdse67.posbackend.bo.custom.OrderBo;
import lk.ijse.gdse67.posbackend.dto.OrderItemDto;
import lk.ijse.gdse67.posbackend.dto.PlaceOrderDto;
import lk.ijse.gdse67.posbackend.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orders/*",loadOnStartup = 3)
public class OrderController extends HttpServlet {
    OrderBo orderBo = (OrderBo) BoFactory.getBoFactory().getBo(BoFactory.BoFactoryTypes.ORDER);
    static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            PlaceOrderDto placeOrderDto = jsonb.fromJson(req.getReader(), PlaceOrderDto.class);
            boolean isOrderPlaced = orderBo.placeOrder(placeOrderDto);
            StandardResponse standardResponse;
            if (isOrderPlaced) {
                resp.setStatus(200);
                standardResponse=new StandardResponse(200,"Order placed successfully",null);
            }else {
                resp.setStatus(400);
                standardResponse=new StandardResponse(400,"Order not placed",null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (SQLException |ClassNotFoundException e) {
            logger.error("Error while Placing Order : ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jsonb jsonb = JsonbBuilder.create();
        try (Writer writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            String searchedId = (pathInfo == null || pathInfo.isEmpty()) ? null : pathInfo.substring(1);
            System.out.println("Search id : " + searchedId);
            StandardResponse standardResponse;
            if (searchedId!=null){
                List<OrderItemDto> orderItems = orderBo.getOrderItems(searchedId);
                if(orderItems!=null){
                    resp.setStatus(200);
                    standardResponse = new StandardResponse(200, "Order fetched successfully", orderItems);
                }else{
                    resp.setStatus(400);
                    standardResponse = new StandardResponse(400, "Order not fetched", null);
                }
                jsonb.toJson(standardResponse, writer);
            }else {
                List<PlaceOrderDto> orderDtos = orderBo.getAllOrders();
                if(!orderDtos.isEmpty()){
                    resp.setStatus(200);
                    standardResponse = new StandardResponse(200, "Order fetched successfully", orderDtos);
                }else{
                    resp.setStatus(400);
                    standardResponse = new StandardResponse(400, "Order not fetched", null);
                }
                jsonb.toJson(standardResponse, writer);
            }
        } catch (SQLException e) {
            logger.error("Error while Placing Order : ", e);
            throw new RuntimeException(e);
        }
    }
}
