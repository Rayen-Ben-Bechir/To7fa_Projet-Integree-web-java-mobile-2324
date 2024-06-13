package test;

import utils.*;
import models.*;
import services.*;

import java.sql.SQLException;

public class MainClass
{
    public static void main(String[] args) throws SQLException {
        DBConnection cnx1 = DBConnection.getInstance();

        User user1 = new User("Rayen Ben","+21652609928","rayen.benbechir@esprit.tn","abcd1234","Male","Admin","");

        UserServiceCRUD usc = new UserServiceCRUD();

        //usc.AddUser(user1);
        //usc.DeleteUser(user1);
        //usc.UpdateUser(user1);
        //System.out.println(usc.SelectAll());
    }
}
