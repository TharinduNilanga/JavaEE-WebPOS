package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomer(DataSource dataSource) throws SQLException, ClassNotFoundException;

    boolean addCustomer(DataSource dataSource,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer( DataSource dataSource,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist( DataSource dataSource,String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer( DataSource dataSource,String id) throws SQLException, ClassNotFoundException;

    String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException;

    CustomerDTO search(DataSource dataSource,String id) throws SQLException, ClassNotFoundException;
}
