package bo.custom.impl;

import bo.custom.SignUpBO;
import dao.DAOFactory;
import dao.custom.SignUpDAO;
import dto.CustomerDTO;
import dto.SignUpDTO;
import entity.Customer;
import entity.SignUp;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class SignUpBOImpl implements SignUpBO {
    private final SignUpDAO signUpDAO= (SignUpDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SIGNUP);

    @Override
    public boolean addCashier(DataSource dataSource, SignUp signUp) throws SQLException, ClassNotFoundException {
        return signUpDAO.save(new SignUp(signUp.getUserName(),signUp.getEmail(),signUp.getPassword()),dataSource);
    }

    @Override
    public SignUpDTO search(DataSource dataSource, String id) throws SQLException, ClassNotFoundException {
        SignUp search = signUpDAO.search(id, dataSource);
        return new SignUpDTO(search.getUserName(),search.getEmail(),search.getPassword());

    }
}
