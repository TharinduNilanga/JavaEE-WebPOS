package dao.custom;

import dao.CrudDAO;
import entity.Order;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface OrderDAO extends CrudDAO<Order,String> {
    boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException;
    String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException;

}
