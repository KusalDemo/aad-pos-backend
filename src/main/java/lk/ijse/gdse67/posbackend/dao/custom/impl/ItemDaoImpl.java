package lk.ijse.gdse67.posbackend.dao.custom.impl;

import lk.ijse.gdse67.posbackend.dao.SQLUtil;
import lk.ijse.gdse67.posbackend.dao.custom.ItemDao;
import lk.ijse.gdse67.posbackend.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDaoImpl implements ItemDao {
    @Override
    public Item search(String id) throws SQLException {
        return SQLUtil.execute("SELECT * FROM item WHERE propertyId=?",id);
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item VALUES(?,?,?,?,?)",item.getPropertyId(),item.getName(),item.getDescription(),item.getPrice(),item.getQty());
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return SQLUtil.execute("UPDATE item SET name=?,description=?,price=?,qty=? WHERE property_id=?",item.getName(),item.getDescription(),item.getPrice(),item.getQty(),item.getPropertyId());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM item WHERE property_id=?",id);
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> obList = new ArrayList<>();
        while (rs.next()){
            obList.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5)));
        }
        return obList;

    }
}
