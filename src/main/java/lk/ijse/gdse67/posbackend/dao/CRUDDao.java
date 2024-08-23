package lk.ijse.gdse67.posbackend.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDDao<T> extends SuperDao {

    ArrayList<T> search(String id) throws SQLException;

    boolean save(T t) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException;

    boolean delete(String id) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

}
