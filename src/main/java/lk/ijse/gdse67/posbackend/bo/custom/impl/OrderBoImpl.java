package lk.ijse.gdse67.posbackend.bo.custom.impl;

import jakarta.persistence.*;
import lk.ijse.gdse67.posbackend.bo.custom.ItemBo;
import lk.ijse.gdse67.posbackend.bo.custom.OrderBo;
import lk.ijse.gdse67.posbackend.dao.DaoFactory;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.dao.custom.ItemDao;
import lk.ijse.gdse67.posbackend.dao.custom.OrderDao;
import lk.ijse.gdse67.posbackend.dao.custom.OrderItemDao;
import lk.ijse.gdse67.posbackend.dto.OrderItemDto;
import lk.ijse.gdse67.posbackend.dto.PlaceOrderDto;
import lk.ijse.gdse67.posbackend.entity.*;
import lk.ijse.gdse67.posbackend.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBoImpl implements OrderBo {
    OrderDao orderDao = (OrderDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.ORDER);
    OrderItemDao orderItemDao = (OrderItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.ORDER_ITEM);
    CustomerDao customerDao = (CustomerDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.CUSTOMER);
    ItemDao itemDao = (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.ITEM);
    static Logger logger = LoggerFactory.getLogger(OrderBoImpl.class);
    /*@PersistenceContext
    private EntityManager entityManager;
*/
    @Override
    public boolean placeOrder(PlaceOrderDto placeOrderDto) {
        /*EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("aadJavaeePosPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();*/

        boolean isPlaceOrderProcessSuccessful=false;
        try {
            /*transaction.begin();*/

            ArrayList<Customer> fetchedCustomer = customerDao.search(placeOrderDto.getCustomerId());
            List<OrderItem> orderItems;
            if (fetchedCustomer.isEmpty()) {
                return false;
            } else {
                try {
                    Customer customer = fetchedCustomer.get(0);
                    System.out.println("Fetched Customer : " + customer.getName() + " , " + customer.getPropertyId());

                    PlaceOrder placeOrder = PlaceOrder.builder()
                            .orderId(IdGenerator.generateId())
                            .customer(customer)
                            .orderDate(placeOrderDto.getOrderDate())
                            .paid(placeOrderDto.getPaid())
                            .discount(placeOrderDto.getDiscount())
                            .balance(placeOrderDto.getBalance())
                            .build();

                    if (!placeOrderDto.getOrderItems().isEmpty()) {
                        orderItems = placeOrderDto.getOrderItems().stream().map(orderItemDto -> {
                            Item item;
                            try {
                                ArrayList<Item> searchedItem = itemDao.search(orderItemDto.getItemId());
                                item = searchedItem.get(0);
                                itemDao.update(Item.builder()
                                        .propertyId(item.getPropertyId())
                                        .name(item.getName())
                                        .description(item.getDescription())
                                        .price(item.getPrice())
                                        .qty(item.getQty()-orderItemDto.getItemCount())
                                        .build());
                                System.out.println("Fetched Item : " + item.getPropertyId() + " , " + item.getName());
                            } catch (SQLException e) {
                                logger.error("Error while creating orderItem array: " + e);
                                throw new RuntimeException(e);
                            }

                            OrderItem orderItem = OrderItem.builder()
                                    .propertyId(OrderItemId.builder()
                                            .orderId(placeOrder.getOrderId())
                                            .itemId(orderItemDto.getItemId())
                                            .build())
                                    .placeOrder(placeOrder)
                                    .item(item)
                                    .itemCount(orderItemDto.getItemCount())
                                    .unitPrice(orderItemDto.getUnitPrice())
                                    .total(orderItemDto.getItemCount() * orderItemDto.getUnitPrice())
                                    .build();
                            return orderItem;
                        }).collect(Collectors.toList());

                        placeOrder.setOrderItems(orderItems);
                        boolean isPlaceOrderSuccessful = orderDao.save(placeOrder);
                        System.out.println("Is Place Order is Successful : " + isPlaceOrderSuccessful);

                        orderItems.stream().forEach(orderItem -> {
                            try {
                                orderItemDao.save(orderItem);
                            } catch (SQLException | ClassNotFoundException e) {
                                logger.error("Error while saving OrderItems : " + e);
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } catch (Exception e) {
                    logger.error("Error while Placing an Order : " + e);
                }
            }
            /*transaction.commit();*/
            isPlaceOrderProcessSuccessful=true;
            logger.info("Transaction has been successful - Order Placed Successfully");
        } catch (Exception e) {
            /*if (transaction != null || transaction.isActive()) {
                transaction.rollback();
            }*/
            logger.error("Transaction has been failed : " + e);
        }
        return isPlaceOrderProcessSuccessful;
    }

    @Override
    public List<PlaceOrderDto> getAllOrders() throws SQLException {
        ArrayList<PlaceOrder> allOrders = orderDao.getAll();
        ArrayList<PlaceOrderDto> placeOrderDtos = new ArrayList<>();
        for (PlaceOrder order : allOrders) {
            placeOrderDtos.add(PlaceOrderDto.builder()
                    .orderId(order.getOrderId())
                    .customerId(order.getCustomer().getPropertyId())
                    .orderDate(order.getOrderDate())
                    .paid(order.getPaid())
                    .discount(order.getDiscount())
                    .balance(order.getBalance())
                    .build());
        }
        return placeOrderDtos;
    }

    @Override
    public List<OrderItemDto> getOrderItems(String orderId) throws SQLException {
        try{
            List<OrderItem> orderItems = orderDao.fetchOrderItems(orderId);
            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                orderItemDtos.add(OrderItemDto.builder()
                        .orderId(orderItem.getPlaceOrder().getOrderId())
                        .itemId(orderItem.getItem().getPropertyId())
                        .itemCount(orderItem.getItemCount())
                        .unitPrice(orderItem.getUnitPrice())
                        .total(orderItem.getTotal())
                        .build());
            }
            return orderItemDtos;
        }catch (Exception e){
            logger.error("Error while getting OrderItems : " + e);
            return null;
        }
    }
}
