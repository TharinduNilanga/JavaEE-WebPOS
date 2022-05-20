package dao.custom.impl;

import dao.custom.OrderDAO;
import entity.Item;
import entity.Order;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderDAOImpl implements OrderDAO  {
    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(dataSource,"SELECT oId FROM `Order` WHERE oId=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(dataSource,"SELECT oId FROM `Order` ORDER BY orderId DESC LIMIT 1;;");
        if (rst.next()) {
            String id = rst.getString("orderId");
            int newOrderId = Integer.parseInt(id.replace("O", "")) + 1;
            return String.format("O%03d",  newOrderId);
        } else {
            return "O001";
        }
    }

    @Override
    public boolean save(Order order, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"INSERT INTO `Order` VALUES(?,?,?,?,?,?)",order.getoId(),order.getCusId(),order.getDiscount()
                ,order.getTotalPrice(),order.getDate(),order.getTime());
    }

    @Override
    public boolean update(Order order, DataSource dataSource) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<Order> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrder=new ArrayList();
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM `Order`");
        while (set.next()) {
            allOrder.add(new Order(set.getString("oId"),
                    set.getString("cusId"),set.getInt("discount"),
                    set.getDouble("totalPrice"),set.getDate("date"),set.getTime("time")));
        }
        return allOrder;
    }

    @Override
    public Order search(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM `Order` WHERE oId=?", s);
        set.next();
        return new Order(set.getString("oId"),set.getString("cusId"),set.getInt("discount"),set.getDouble("totalPrice"),set.getDate("date")
        ,set.getTime("time"));
    }
}
