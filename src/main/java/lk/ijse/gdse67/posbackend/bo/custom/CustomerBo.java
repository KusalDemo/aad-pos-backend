package lk.ijse.gdse67.posbackend.bo.custom;

import lk.ijse.gdse67.posbackend.bo.SuperBO;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBO {
    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(String customerId,CustomerDto customerDto) throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;

    CustomerDto searchCustomer(String id) throws SQLException;

    List<CustomerDto> getAllCustomers() throws SQLException;
}
