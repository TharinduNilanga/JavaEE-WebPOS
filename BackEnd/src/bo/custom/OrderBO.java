package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface OrderBO extends SuperBO {
    boolean purchaseOrder(DataSource dataSource, OrderDTO dto) throws SQLException, ClassNotFoundException;

    String generateNewOrderId(DataSource dataSource)throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers(DataSource dataSource)throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems(DataSource dataSource)throws SQLException, ClassNotFoundException;
    ArrayList<OrderDTO> getAllOrder(DataSource dataSource)throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(DataSource dataSource,String code)throws SQLException, ClassNotFoundException;

    boolean ifItemExist(DataSource dataSource,String code) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(DataSource dataSource,String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(DataSource dataSource,String s)throws SQLException, ClassNotFoundException;

}
