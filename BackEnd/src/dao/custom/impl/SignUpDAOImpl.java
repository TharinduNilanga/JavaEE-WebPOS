package dao.custom.impl;

import dao.custom.SignUpDAO;
import entity.Customer;
import entity.SignUp;
import util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class SignUpDAOImpl implements SignUpDAO {
    @Override
    public boolean ifCustomerExist(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery(dataSource,"SELECT userName FROM Item WHERE userName=?",id).next();

    }

    @Override
    public String generateNewID(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(SignUp signUp, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(dataSource,"INSERT INTO SignUp VALUES(?,?,?)",signUp.getUserName(),signUp.getEmail(),signUp.getPassword());
    }

    @Override
    public boolean update(SignUp signUp, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<SignUp> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public SignUp search(String s, DataSource dataSource) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.executeQuery(dataSource, "SELECT * FROM SignUp WHERE userName=?");
        set.next();
        return new SignUp(set.getString("userName"),set.getString("email"),set.getString("password"));

    }
}
