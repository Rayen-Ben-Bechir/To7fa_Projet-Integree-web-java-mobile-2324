package services;

import javafx.collections.ObservableList;
import models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserCRUD <T>
{
    void AddUser(T t) throws SQLException;
    boolean DeleteUser(T t) throws SQLException;
    boolean UpdateUser(T t,int var1) throws SQLException;
    boolean UpdatePW(String var1, int var2) throws SQLException;
    List<T> SelectAll() throws SQLException;
    User LoginService(String var1, String var2) throws SQLException;
    int countAdmins() throws SQLException;

    ObservableList<User> UsersListData() throws SQLException;
}
