package lk.ijse.gdse67.posbackend.bo.custom;

import lk.ijse.gdse67.posbackend.bo.SuperBO;
import lk.ijse.gdse67.posbackend.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBO {
    boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    boolean updateItem(String id,ItemDto itemDto) throws SQLException;

    boolean deleteItem(String id) throws SQLException;

    List<ItemDto> searchItem(String id) throws SQLException;

    List<ItemDto> getAllItems() throws SQLException;
}
