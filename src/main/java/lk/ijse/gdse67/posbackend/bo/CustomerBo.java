package lk.ijse.gdse67.posbackend.bo;

import lk.ijse.gdse67.posbackend.dto.CustomerDto;

public interface CustomerBo extends SuperBO {
    boolean saveCustomer(CustomerDto customerDto);

    boolean updateCustomer(CustomerDto customerDto);

    boolean deleteCustomer(String id);

    CustomerDto searchCustomer(String id);
}
