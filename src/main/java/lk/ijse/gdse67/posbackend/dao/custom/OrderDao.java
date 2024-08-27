package lk.ijse.gdse67.posbackend.dao.custom;

import lk.ijse.gdse67.posbackend.dao.CRUDDao;
import lk.ijse.gdse67.posbackend.entity.OrderItem;
import lk.ijse.gdse67.posbackend.entity.PlaceOrder;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends CRUDDao<PlaceOrder> {
    List<OrderItem>fetchOrderItems(String id) throws SQLException;
}
