package lk.ijse.gdse67.posbackend.bo.custom;

import lk.ijse.gdse67.posbackend.bo.SuperBO;
import lk.ijse.gdse67.posbackend.dto.PlaceOrderDto;
import lk.ijse.gdse67.posbackend.entity.OrderItem;
import lk.ijse.gdse67.posbackend.entity.PlaceOrder;

import java.sql.SQLException;

public interface OrderBo extends SuperBO {
boolean placeOrder(PlaceOrderDto placeOrderdto) throws SQLException, ClassNotFoundException;
}
