package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.sql.DataSource;

public class UserServiceCRUD implements UserCRUD<User>
{
    private Connection cnx;
    public UserServiceCRUD()
    {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void AddUser(User user) throws SQLException
    {
        try{
            String req = "INSERT INTO `user`(`user_name`,`user_phone`,`user_email`,`user_password`,`user_sexe`,`user_role`,`user_image`) VALUES "
                    + "(?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,user.getUser_name());
            ps.setString(2,user.getUser_phone());
            ps.setString(3,user.getUser_email());
            ps.setString(4,user.getUser_password());
            ps.setString(5,user.getUser_sexe());
            ps.setString(6,user.getUser_role());
            ps.setString(7,user.getUser_image());

            ps.executeUpdate();
            System.out.println("User added !!");
        }catch (SQLException addexception){
            System.err.println("Add error :"+addexception.getMessage());
        }
    }

    public boolean DeleteUser(User user) throws SQLException
    {
        try{
            String req = "delete from user where user_id=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setInt(1,user.getUser_id());

            ps.executeUpdate();
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A User was deleted successfully!");
            }
            return true;
        }catch (SQLException deletexception){
            System.err.println("Delete profil error :"+deletexception.getMessage());
        }
        return false;
    }

    public boolean UpdateUser(User user, int id) throws SQLException
    {
        try {
            String req = "update `piwj_sj`.`user` set user_name=?,user_phone=?,user_email=?,user_sexe=?,user_image=? where user_id=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1,user.getUser_name());
            ps.setString(2,user.getUser_phone());
            ps.setString(3,user.getUser_email());
            ps.setString(4,user.getUser_sexe());
            ps.setString(5,user.getUser_image());
            ps.setInt(6,id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing User was updated his profil successfully!");
                return true;
            }
        } catch (SQLException updatexception) {
            System.err.println("Update error :"+updatexception.getMessage());
        }
        return false;
    }

    public boolean UpdatePW(String password, int id) throws SQLException
    {
        try{
            String req = "UPDATE user SET user_password=? WHERE user_id=?";
            PreparedStatement ps = this.cnx.prepareStatement(req);
            ps.setString(1,password);
            ps.setInt(2,id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing User has updated his password !");
                return true;
            }
        } catch (SQLException updatexception) {
            System.err.println("Update PW error :"+updatexception.getMessage());
        }
        return false;
    }

    public boolean UpdatePWoublie(String password, String email) throws SQLException
    {
        try{
            String req = "UPDATE user SET user_password=? WHERE user_email=?";
            PreparedStatement ps = this.cnx.prepareStatement(req);
            ps.setString(1,password);
            ps.setString(2,email);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing User has updated his password !");
                return true;
            }
        } catch (SQLException updatexception) {
            System.err.println("Update PW oublie error :"+updatexception.getMessage());
        }
        return false;
    }

    public List<User> SelectAll() throws SQLException
    {
        List<User> UserList = new ArrayList<>();

        String req = "SELECT * FROM `user`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            User user = new User();

            user.setUser_id(rs.getInt(("user_id")));
            user.setUser_name(rs.getString(("user_name")));
            user.setUser_phone(rs.getString(("user_phone")));
            user.setUser_email(rs.getString(("user_email")));
            user.setUser_password(rs.getString(("user_password")));
            user.setUser_sexe(rs.getString(("user_sexe")));
            user.setUser_role(rs.getString(("user_role")));
            user.setUser_image(rs.getString(("user_image")));

            UserList.add(user);
        }

        return UserList;
    }

    public User LoginService (String email_ou_phone, String password) throws SQLException
    {
        User user1 = new User();
        try {
            String req = "SELECT * from user WHERE (user_email ='" + email_ou_phone + "' OR user_phone='" + email_ou_phone + "') AND user_password='" + password +"' ";
            PreparedStatement ps = this.cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt(1);
                String user_name = rs.getString(2);
                String user_phone = rs.getString(3);
                String user_email = rs.getString(4);
                String user_password = rs.getString(5);
                String user_sexe = rs.getString(6);
                String user_role = rs.getString(7);
                String user_image = rs.getString(8);
                user1 = new User(user_id,user_name,user_phone,user_email,user_password,user_sexe,user_role,user_image);
                System.out.println(" |||  User  authentifié  |||");
                System.out.println(user1);
            }
            else
            {
                System.out.println("non trouvé");
            }
        } catch (SQLException var14) {
            Logger.getLogger(UserCRUD.class.getName()).log(Level.SEVERE, (String)null, var14);
        }
        return user1;
    }

    public int countAdmins() throws SQLException
    {
        int adminCount = 0;
        try{
            String req = "SELECT COUNT(*) FROM user WHERE user_role = 'Admin'";
            PreparedStatement ps = this.cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            // Récupération du nombre d'administrateurs
            if (rs.next()) {
                adminCount = rs.getInt(1);
            }
        }catch (SQLException e) {
            System.err.println("Erreur lors du comptage des administrateurs: " + e.getMessage());
        }
        return adminCount;
    }

    public ObservableList<User> UsersListData() throws SQLException
    {
        ObservableList<User> listUsers = FXCollections.observableArrayList();

        try {
            String req = "SELECT * FROM user WHERE user_role = 'Client'";
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User userData = new User(rs.getInt("user_id"),rs.getString("user_name"),rs.getString("user_phone"),rs.getString("user_email"),rs.getString("user_password"),rs.getString("user_sexe"),rs.getString("user_image"));
                listUsers.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listUsers;
    }
    public User getUserById(int userId) {
        User user = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/piwj_sj", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_phone"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getString("user_sexe"),
                        resultSet.getString("user_role"),
                        resultSet.getString("user_image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int getUserByEmail(String email) {
        int userexiste=0;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/piwj_sj", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE user_email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userexiste=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userexiste;
    }
}
