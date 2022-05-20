package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class ItemBOImpl  implements ItemBO {
    private final ItemDAO itemDAO= (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public ArrayList<ItemDTO> getAllItems(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll(dataSource);
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemId(),item.getItemName(),item.getItemPrice(),item.getItemQuantity()));

        }
        return allItems;
    }

    @Override
    public boolean addItem(DataSource dataSource, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getItemId(),dto.getItemName(),dto.getItemPrice(),dto.getItemQuantity()),dataSource);
    }

    @Override
    public boolean deleteItem(DataSource dataSource, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code,dataSource);
    }

    @Override
    public boolean updateItem(DataSource dataSource, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemId(),dto.getItemName(),dto.getItemPrice(),dto.getItemQuantity()),dataSource);
    }

    @Override
    public boolean ifItemExist(DataSource dataSource, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifCustomerExist(dataSource,code);
    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID(dataSource);
    }

    @Override
    public ItemDTO search(DataSource dataSource, String itemCode) throws SQLException, ClassNotFoundException {
        Item search = itemDAO.search(itemCode, dataSource);
        return new ItemDTO(search.getItemId(),search.getItemName(),search.getItemPrice(),search.getItemQuantity());
    }
}
