package bo.custom;


import bo.SuperBO;
import dto.SignUpDTO;
import entity.SignUp;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface SignUpBO extends SuperBO {
    boolean addCashier(DataSource dataSource, SignUp signUp) throws SQLException, ClassNotFoundException;
    SignUpDTO search(DataSource dataSource, String id) throws SQLException, ClassNotFoundException;
}
