package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailsDAO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetails;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderBOImpl implements OrderBO {
    private final CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO= (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO= (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailsDAO orderDetailsDAO= (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSourcee;

    @Override
    public boolean purchaseOrder(DataSource dataSource, OrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = dataSourcee.getConnection();

        boolean orderId=orderDAO.ifCustomerExist(dataSource,dto.getoId());
        if (orderId){
            return false;
        }
        connection.setAutoCommit(false);
        Order order = new Order(dto.getoId(), dto.getCusId(), dto.getDiscount(), dto.getTotalPrice(), dto.getDate(), dto.getTime());
        if (!orderDAO.save(order,dataSource)){
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailsDTO d:dto.getOrderDetail()){
            OrderDetails orderDetails = new OrderDetails(d.getoId(), d.getCusId(), d.getItemId(), d.getDiscount(), d.getQuantity(), d.getTotalPrice());
            if (!orderDetailsDAO.save(orderDetails,dataSource)) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            Item search = itemDAO.search(orderDetails.getItemId(), dataSource);

            search.setItemQuantity(search.getItemQuantity()-orderDetails.getQuantity());
            if (!itemDAO.update(search,dataSource)){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;


    }

    @Override
    public String generateNewOrderId(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID(dataSource);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrder(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allCustomers = new ArrayList<>();
        ArrayList<Order> all = orderDAO.getAll(dataSource);
        for (Order order : all) {
            allCustomers.add(new OrderDTO(order.getoId(),order.getCusId(),order.getDiscount(),order.getTotalPrice(),order.getDate(),order.getTime()));
        }
        return allCustomers;
    }

    @Override
    public ItemDTO searchItem(DataSource dataSource, String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean ifItemExist(DataSource dataSource, String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(DataSource dataSource, String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
