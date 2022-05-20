package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery(dataSource,"SELECT itemId FROM Item WHERE itemId=?",id).next();
    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(dataSource, "SELECT itemId FROM Customer ORDER BY itemId DESC LIMIT -1");
        if (rst.next()) {
            String id = rst.getString("itemId");
            int newId = Integer.parseInt(id.replace("I", ""));
            return String.format("I%03d",newId);
        }else {
            return "I001";
        }
    }

    @Override
    public boolean save(Item item, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"INSERT INTO Item VALUES(?,?,?,?)",item.getItemId(),
                item.getItemName(),item.getItemPrice(),item.getItemQuantity());

    }

    @Override
    public boolean update(Item item, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"UPDATE Item SET itemName=?,itemPrice=?,itemQuantity=? WHERE itemId=?",
                item.getItemName(),item.getItemPrice(),item.getItemQuantity(),item.getItemId());

    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"DELETE FROM Item WHERE itemId=?",s);
    }

    @Override
    public ArrayList<Item> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItem=new ArrayList();
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM Customer");
        while (set.next()) {
           allItem.add(new Item(set.getString("itemId"),
                    set.getString("itemName"),set.getDouble("itemPrice"),
                    set.getInt("itemQuantity")));
        }
        return allItem;
    }

    @Override
    public Item search(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM Item WHERE itemId=?");
        set.next();
        return new Item(s,set.getString("itemName"),
                set.getDouble("itemPrice"),
                set.getInt("itemQuantity"));
    }
}
