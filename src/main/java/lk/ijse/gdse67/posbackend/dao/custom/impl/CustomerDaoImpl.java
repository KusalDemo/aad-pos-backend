package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.CustomerDao;
import lk.ijse.gdse67.posbackend.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Customer search(String id) throws SQLException {
        return SQLUtil.execute("SELECT * FROM customer WHERE id=?",id);
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?)",customer.getPropertyId(),customer.getName(),customer.getEmail(),customer.getAddress(),customer.getBranch());
        /*return SQLUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?)",customer.getPropertyId(),"Hello","Hello","Hello","Hello");*/
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
            obList.add(new Customer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        return obList;
    }
}
