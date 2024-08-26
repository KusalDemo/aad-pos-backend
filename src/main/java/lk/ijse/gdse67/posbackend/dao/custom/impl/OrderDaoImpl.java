package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.OrderDao;
import lk.ijse.gdse67.posbackend.entity.PlaceOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {
    @Override
    public ArrayList<PlaceOrder> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into place_order (orderId,customer_id,orderDate,paid,discount,balance) values (?,?,?,?,?,?)",
                placeOrder.getOrderId(),placeOrder.getCustomer().getPropertyId(),placeOrder.getOrderDate(),placeOrder.getPaid(),placeOrder.getDiscount(),placeOrder.getBalance());
    }

    @Override
    public boolean update(PlaceOrder placeOrder) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<PlaceOrder> getAll() throws SQLException {
        return null;
    }
}
