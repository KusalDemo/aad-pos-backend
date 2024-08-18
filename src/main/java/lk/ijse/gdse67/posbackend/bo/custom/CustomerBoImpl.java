package lk.ijse.gdse67.posbackend.bo.custom;

import lk.ijse.gdse67.posbackend.bo.CustomerBo;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;

public class CustomerBoImpl implements CustomerBo {
    @Override
    public boolean saveCustomer(CustomerDto customerDto) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }
}
