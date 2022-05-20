package dao.custom.impl;

import dao.custom.OrderDetailsDAO;
import entity.Order;
import entity.OrderDetails;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderDetails orderDetails, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"INSERT INTO `OrderDetails` VALUES (?,?,?,?,?,?)",orderDetails.getoId(),orderDetails.getCusId()
                                      ,orderDetails.getItemId(),orderDetails.getDiscount(),orderDetails.getQuantity(),orderDetails.getTotalPrice());
    }

    @Override
    public boolean update(OrderDetails orderDetails, DataSource dataSource) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrderDetails=new ArrayList();
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM `OrderDetails`");
        while (set.next()) {
            allOrderDetails.add(new OrderDetails(set.getString("oId"),
                    set.getString("cusId"),set.getString("itemId"),
                    set.getInt("discount"),set.getInt("quantity"),set.getDouble("totalPrice")));
        }
        return allOrderDetails;
    }

    @Override
    public OrderDetails search(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM `OrderDetails` WHERE oId=?", s);
        set.next();
        return new OrderDetails(set.getString("oId"),
                set.getString("cusId"),set.getString("itemId"),
                set.getInt("discount"),set.getInt("quantity"),set.getDouble("totalPrice"));
    }
}
