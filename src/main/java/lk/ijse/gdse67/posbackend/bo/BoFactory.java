package lk.ijse.gdse67.posbackend.bo;

import lk.ijse.gdse67.posbackend.bo.custom.impl.CustomerBoImpl;
import lk.ijse.gdse67.posbackend.bo.custom.impl.ItemBoImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBoFactory(){
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }
    public enum BoFactoryTypes{
        CUSTOMER,
        ITEM
    }
    public SuperBO getBo(BoFactoryTypes boFactoryTypes){
        switch (boFactoryTypes){
            case CUSTOMER:
                return new CustomerBoImpl();
            case ITEM:
                return new ItemBoImpl();
            default:
                return null;
        }
    }
}
