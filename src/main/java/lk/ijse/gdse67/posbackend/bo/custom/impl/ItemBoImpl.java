package lk.ijse.gdse67.posbackend.bo.custom.impl;

import lk.ijse.gdse67.posbackend.bo.custom.ItemBo;
import lk.ijse.gdse67.posbackend.dao.DaoFactory;
import lk.ijse.gdse67.posbackend.dao.custom.ItemDao;
import lk.ijse.gdse67.posbackend.dto.ItemDto;
import lk.ijse.gdse67.posbackend.entity.Item;
import lk.ijse.gdse67.posbackend.util.IdGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    ItemDao itemDao= (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DaoFactoryTypes.ITEM);
    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(IdGenerator.generateId(),itemDto.getName(),itemDto.getDescription(),itemDto.getPrice(),itemDto.getQty(),null));
    }

    @Override
    public boolean updateItem(String id, ItemDto itemDto) throws SQLException {
        return itemDao.update(new Item(id,itemDto.getName(),itemDto.getDescription(),itemDto.getPrice(),itemDto.getQty(),null));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> searchItem(String id) throws SQLException {
        ArrayList<Item> searchedItem = itemDao.search(id);
        List<ItemDto> itemDtoList = searchedItem.stream().map(item ->
                new ItemDto(item.getPropertyId(), item.getName(), item.getDescription(), item.getPrice(), item.getQty())).toList();
        return itemDtoList;
    }

    @Override
    public List<ItemDto> getAllItems() throws SQLException {
        ArrayList<Item> allItems = itemDao.getAll();
        List<ItemDto> itemDtos= allItems.stream().map(item -> new ItemDto(item.getPropertyId(),item.getName(),item.getDescription(),item.getPrice(),item.getQty())).toList();
        return itemDtos;
    }
}
