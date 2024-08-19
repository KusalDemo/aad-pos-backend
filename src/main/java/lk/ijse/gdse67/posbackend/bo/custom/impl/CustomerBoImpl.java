package lk.ijse.gdse67.posbackend.bo.custom.impl;

import lk.ijse.gdse67.posbackend.bo.custom.CustomerBo;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.dao.DaoFactory;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;
import lk.ijse.gdse67.posbackend.entity.Customer;
import lk.ijse.gdse67.posbackend.util.IdGenerator;

import java.sql.SQLException;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao= (CustomerDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException{
        return customerDao.save(new Customer(IdGenerator.generateId(),customerDto.getName(),customerDto.getEmail(),customerDto.getAddress(),customerDto.getBranch()));
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException {
        return customerDao.update(new Customer(IdGenerator.generateId(),customerDto.getName(),customerDto.getEmail(),customerDto.getAddress(),customerDto.getBranch()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDao.delete(id);
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {
        Customer serachedCustomer = customerDao.search(id);
        return new CustomerDto(serachedCustomer.getName(),serachedCustomer.getEmail(),serachedCustomer.getAddress(),serachedCustomer.getBranch());
    }
}
