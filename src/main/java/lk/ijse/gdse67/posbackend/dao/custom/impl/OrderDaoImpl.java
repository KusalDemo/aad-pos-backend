package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.dao.custom.ItemDao;
import lk.ijse.gdse67.posbackend.dao.custom.OrderDao;
import lk.ijse.gdse67.posbackend.entity.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private final CustomerDao customerDao;
    private final ItemDao itemDao;

    public OrderDaoImpl(CustomerDao customerDao, ItemDao itemDao) {
        this.customerDao = customerDao;
        this.itemDao = itemDao;
    }

    @Override
    public List<OrderItem> fetchOrderItems(String id) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            resultSet = SQLUtil.execute("SELECT * FROM order_items WHERE order_id = ?", id);

            while (resultSet.next()) {
                PlaceOrder placeOrder = fetchPlaceOrderById(id);

                String itemId = resultSet.getString("item_id");
                ArrayList<Item> itemSearch = itemDao.search(itemId);


                if (itemSearch.isEmpty()) {
                    throw new SQLException("Item not found for ID: " + itemId);
                }
                Item item = itemSearch.get(0);

                OrderItem orderItem = OrderItem.builder()
                        .propertyId(OrderItemId.builder()
                                .orderId(id)
                                .itemId(itemId)
                                .build())
                        .item(item)
                        .itemCount(resultSet.getInt("item_count"))
                        .unitPrice(resultSet.getDouble("unit_price"))
                        .total(resultSet.getDouble("total"))
                        .placeOrder(placeOrder)
                        .build();

                orderItems.add(orderItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error while fetching order items (Dao) : ", e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return orderItems;
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

    private PlaceOrder fetchPlaceOrderById(String id) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM place_order WHERE orderId = ?", id);
        if (resultSet.next()) {
            ArrayList<Customer> search = customerDao.search(resultSet.getString("customer_id"));
            Customer customer = search.get(0);
            return PlaceOrder.builder()
                    .orderId(resultSet.getString("orderId"))
                    .customer(customer)
                    .orderDate(resultSet.getDate("orderDate"))
                    .paid(resultSet.getDouble("paid"))
                    .discount(resultSet.getInt("discount"))
                    .balance(resultSet.getDouble("balance"))
                    .build();
        } else {
            throw new SQLException("PlaceOrder not found for ID: " + id);
        }
    }

    @Override
    public boolean save(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into place_order (orderId,customer_id,orderDate,paid,discount,balance) values (?,?,?,?,?,?)",
                placeOrder.getOrderId(),placeOrder.getCustomer().getPropertyId(),placeOrder.getOrderDate(),placeOrder.getPaid(),placeOrder.getDiscount(),placeOrder.getBalance());
    }
    @Override
    public ArrayList<PlaceOrder> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(PlaceOrder placeOrder) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

}
