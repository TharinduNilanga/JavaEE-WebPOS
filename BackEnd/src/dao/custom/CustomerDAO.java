package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface CustomerDAO extends CrudDAO<Customer, String> {
    boolean ifCustomerExist(DataSource dataSource,String id) throws SQLException, ClassNotFoundException;
    String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException;


}
