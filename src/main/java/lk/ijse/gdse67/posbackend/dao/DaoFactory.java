package lk.ijse.gdse67.posbackend.dao;

import lk.ijse.gdse67.posbackend.dao.custom.impl.CustomerDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory() {

    }
    public static DaoFactory getDaoFactory(){
        return daoFactory==null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public enum DaoFactoryTypes{
        CUSTOMER
    }

    public SuperDao getDao(DaoFactoryTypes daoFactoryTypes){
        switch (daoFactoryTypes){
            case CUSTOMER:
                return new CustomerDaoImpl();
            default:
                return null;
        }
    }
}
