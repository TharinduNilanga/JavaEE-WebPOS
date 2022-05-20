package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO= (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomer(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll(dataSource);
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getCusId(),customer.getCusName(),
                    customer.getCusAddress(), customer.getCusContact()));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(DataSource dataSource, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCusId(),customerDTO.getCusName(),
                customerDTO.getCusAddress(),customerDTO.getCusContact()),dataSource);
    }

    @Override
    public boolean updateCustomer(DataSource dataSource, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDTO.getCusId(),customerDTO.getCusName(),
                customerDTO.getCusAddress(),customerDTO.getCusContact()),dataSource);

    }

    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(dataSource,id);
    }

    @Override
    public boolean deleteCustomer(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id,dataSource);
    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID(dataSource);
    }

    @Override
    public CustomerDTO search(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(id, dataSource);
        return new CustomerDTO(search.getCusId(),search.getCusName(),search.getCusAddress(),search.getCusContact());
    }
}
