package lk.ijse.gdse67.posbackend.bo.custom.impl;

import lk.ijse.gdse67.posbackend.bo.custom.CustomerBo;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.dao.DaoFactory;
import lk.ijse.gdse67.posbackend.dto.CustomerDto;
import lk.ijse.gdse67.posbackend.entity.Customer;
import lk.ijse.gdse67.posbackend.util.IdGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao= (CustomerDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException{
        return customerDao.save(new Customer(IdGenerator.generateId(),customerDto.getName(),customerDto.getEmail(),customerDto.getAddress(),customerDto.getBranch(),null));
    }

    @Override
    public boolean updateCustomer(String customerId,CustomerDto customerDto) throws SQLException {
        return customerDao.update(new Customer(customerId,customerDto.getName(),customerDto.getEmail(),customerDto.getAddress(),customerDto.getBranch(),null));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> searchCustomer(String id) throws SQLException {
        ArrayList<Customer> searchedCustomers = customerDao.search(id);
        List<CustomerDto> customerDtoList = searchedCustomers.stream().map(customer -> new CustomerDto(customer.getName(), customer.getEmail(), customer.getAddress(), customer.getBranch())).toList();
        return customerDtoList;
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException {
        List<Customer> all = customerDao.getAll();
        List<CustomerDto> customerDtos = all.stream().map(customer -> new CustomerDto(customer.getName(),customer.getEmail(),customer.getAddress(),customer.getBranch())).toList();
        return customerDtos;
    }
}
