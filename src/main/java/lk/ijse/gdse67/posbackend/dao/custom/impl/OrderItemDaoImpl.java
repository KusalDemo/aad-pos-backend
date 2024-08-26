package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.OrderItemDao;
import lk.ijse.gdse67.posbackend.entity.OrderItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemDaoImpl implements OrderItemDao {
    @Override
    public ArrayList<OrderItem> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(OrderItem orderItem) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into order_items (propertyId,order_id,item_id,itemCount,unitPrice,total) values (?,?,?,?,?,)",
                orderItem.getPropertyId(),orderItem.getPlaceOrder().getOrderId(),orderItem.getItem().getPropertyId(),orderItem.getItemCount(),orderItem.getUnitPrice(),orderItem.getTotal());
    }

    @Override
    public boolean update(OrderItem orderItem) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderItem> getAll() throws SQLException {
        return null;
    }
}
