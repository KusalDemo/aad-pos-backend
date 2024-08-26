package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public ArrayList<Customer> search(String id) throws SQLException {
        ResultSet rs=SQLUtil.execute("SELECT * FROM customer WHERE property_id LIKE ? OR name LIKE ? OR email LIKE ?","%" + id + "%","%" + id + "%","%" + id + "%");
        ArrayList<Customer> objects = new ArrayList<>();
        while (rs.next()){
            Customer customer=Customer.builder()
                    .propertyId(rs.getString(1))
                    .name(rs.getString(2))
                    .email(rs.getString(3))
                    .address(rs.getString(4))
                    .branch(rs.getString(5))
                    .build();
            objects.add(customer);
            /*objects.add(new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));*/
        }
        return objects;
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer (property_id, name, email, address, branch) VALUES(?,?,?,?,?)",customer.getPropertyId(),customer.getName(),customer.getEmail(),customer.getAddress(),customer.getBranch());
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name=?,address=?,branch=? WHERE email=?",customer.getName(),customer.getAddress(),customer.getBranch(),customer.getEmail());
    }

    @Override
    public boolean delete(String email) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE email=?",email);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> obList = new ArrayList<>();
        while (rs.next()){
            /*obList.add(new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));*/
            Customer customer=Customer.builder()
                    .propertyId(rs.getString(1))
                    .name(rs.getString(2))
                    .email(rs.getString(3))
                    .address(rs.getString(4))
                    .branch(rs.getString(5))
                    .build();
            obList.add(customer);
        }
        return obList;
    }
}
