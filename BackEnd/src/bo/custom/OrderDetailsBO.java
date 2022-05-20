package bo.custom;

import bo.SuperBO;
import dto.OrderDetailsDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface OrderDetailsBO extends SuperBO {
    OrderDetailsDTO searchOrder(DataSource dataSource, String s) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetailsDTO> getAllOrder(DataSource dataSource) throws SQLException, ClassNotFoundException;
}
