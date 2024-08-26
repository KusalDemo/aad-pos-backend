package lk.ijse.gdse67.posbackend.dao;

import lk.ijse.gdse67.posbackend.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.gdse67.posbackend.dao.custom.impl.ItemDaoImpl;
import lk.ijse.gdse67.posbackend.dao.custom.impl.OrderDaoImpl;
import lk.ijse.gdse67.posbackend.dao.custom.impl.OrderItemDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory() {

    }
    public static DaoFactory getDaoFactory(){
        return daoFactory==null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public enum DaoFactoryTypes{
        CUSTOMER,
        ITEM,
        ORDER,
        ORDER_ITEM
    }

    public SuperDao getDao(DaoFactoryTypes daoFactoryTypes){
        switch (daoFactoryTypes){
            case CUSTOMER:
                return new CustomerDaoImpl();
            case ITEM:
                return new ItemDaoImpl();
            case ORDER:
                return new OrderDaoImpl();
            case ORDER_ITEM:
                return new OrderItemDaoImpl();
            default:
                return null;
        }
    }
}
