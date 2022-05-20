package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery(dataSource,"SELECT cusId FROM Customer WHERE cusId=?",id).next();
    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(dataSource, "SELECT cusId FROM Customer ORDER BY cusId DESC LIMIT -1");
        if (rst.next()) {
            String id = rst.getString("cusId");
            int newId = Integer.parseInt(id.replace("C", ""));
            return String.format("C%03d",newId);
        }else {
            return "C001";
        }

    }



    @Override
    public boolean save(Customer customer, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"INSERT INTO ITEM VALUES(?,?,?,?)",customer.getCusId(),customer.getCusName(),customer.getCusAddress(),
                customer.getCusContact());
    }

    @Override
    public boolean update(Customer customer, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"UPDATE Customer SET cusName=?,cusAddress=?,cusContact=? WHERE cusId=?",customer.getCusName(),customer.getCusAddress(),
                customer.getCusContact(),customer.getCusId());
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"DELETE FROM Customer WHERE cusId=?",s);
    }

    @Override
    public ArrayList<Customer> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException {
       ArrayList<Customer> allCustomer=new ArrayList();
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM Customer");
        while (set.next()) {
            allCustomer.add(new Customer(set.getString("cusId"),set.getString("cusName"),set.getString("cusAddress"),set.getString("cusContact")));
        }
        return allCustomer;
    }

    @Override
    public Customer search(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM Customer WHERE cusId=?");
        set.next();
        return new Customer(s,set.getString("cusName"),
                set.getString("cusAddress"),
                set.getString("cusContact"));
    }



}
