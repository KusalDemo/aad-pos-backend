package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.dao.custom.OrderDao;
import lk.ijse.gdse67.posbackend.entity.Customer;
import lk.ijse.gdse67.posbackend.entity.PlaceOrder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {
    private final CustomerDao customerDao;

    public OrderDaoImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

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
        ResultSet resultSet = SQLUtil.execute("select * from place_order");
        ArrayList<PlaceOrder> placeOrders = new ArrayList<>();
        while (resultSet.next()){
            String customerId = resultSet.getString(2);
            ArrayList<Customer> customer = customerDao.search(customerId);
            Customer selectedCustomer = customer.get(0);
            placeOrders.add(PlaceOrder.builder()
                            .orderId(resultSet.getString(1))
                            .customer(selectedCustomer)
                            .orderDate(Date.valueOf(resultSet.getString(3)))
                            .paid(resultSet.getDouble(4))
                            .discount((int) resultSet.getDouble(5))
                            .balance(resultSet.getDouble(6))
                    .build());
        }
        return placeOrders;
    }
}
