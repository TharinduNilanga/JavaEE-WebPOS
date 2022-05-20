package bo.custom.impl;

import bo.custom.OrderDetailsBO;
import dao.DAOFactory;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Customer;
import entity.Order;
import entity.OrderDetails;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderDetailsBOImpl  implements OrderDetailsBO {
    private final OrderDetailsDAO orderDetailsDAO= (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public OrderDetailsDTO searchOrder(DataSource dataSource, String s) throws SQLException, ClassNotFoundException {
        OrderDetails search = orderDetailsDAO.search(s, dataSource);
        return new OrderDetailsDTO(search.getoId(),search.getCusId(),search.getItemId(),search.getDiscount(),search.getQuantity(),search.getTotalPrice());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrder(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsDTO> allOrderdetails = new ArrayList<>();
        ArrayList<OrderDetails> all = orderDetailsDAO.getAll(dataSource);
        for (OrderDetails order : all) {
            allOrderdetails.add(new OrderDetailsDTO(order.getoId(),order.getCusId(),order.getItemId(),order.getDiscount(),order.getQuantity(),order.getTotalPrice()));
        }
        return allOrderdetails;
    }
}
