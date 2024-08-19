package lk.ijse.gdse67.posbackend.bo;

import lk.ijse.gdse67.posbackend.bo.custom.impl.CustomerBoImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBoFactory(){
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }
    public enum BoFactoryTypes{
        CUSTOMER
    }
    public SuperBO getBo(BoFactoryTypes boFactoryTypes){
        switch (boFactoryTypes){
            case CUSTOMER:
                return new CustomerBoImpl();
            default:
                return null;
        }
    }
}
