package dao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public interface CrudDAO<T,ID> extends SuperDAO{

    public boolean save(T t, DataSource dataSource) throws SQLException, ClassNotFoundException;

    public boolean update(T t, DataSource dataSource) throws SQLException, ClassNotFoundException;

    public boolean delete(ID id, DataSource dataSource) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll(DataSource dataSource) throws SQLException, ClassNotFoundException;

    public T search(ID id, DataSource dataSource) throws SQLException, ClassNotFoundException;
}
