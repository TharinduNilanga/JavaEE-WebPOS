package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems(DataSource dataSource) throws SQLException, ClassNotFoundException;
    boolean addItem(DataSource dataSource,ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(DataSource dataSource,String code) throws SQLException, ClassNotFoundException;

    boolean updateItem(DataSource dataSource,ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean ifItemExist(DataSource dataSource,String code) throws SQLException, ClassNotFoundException;

    String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException;

    ItemDTO search(DataSource dataSource,String itemCode) throws SQLException, ClassNotFoundException;
}
