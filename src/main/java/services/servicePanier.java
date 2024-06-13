package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.panierat;
import test.MainFx;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicePanier implements CRUD_panier<panierat>{
    private Connection cnx;

    int user_id = MainFx.Userconnected.getUser_id();


    public servicePanier() {
        cnx = DBConnection.getInstance().getCnx();


    }

  /*  @Override
    public int searchByIdOeuvre(int id_oeuvre) throws SQLException {
        String req = "SELECT COUNT(*) FROM panier WHERE id_oeuvre = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, id_oeuvre);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0 ? 1 : 0;
            } else {
                return 0;
            }
        }
    }*/

    @Override
    public void insertOne(panierat pan) throws SQLException {

        String req = "INSERT INTO panier(id_user,id_oeuvre,quantity) VALUES (?,?,?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, pan.getId_oeuvre());
            pstmt.setInt(3, 1);

            pstmt.executeUpdate();
        }
    }
    @Override
    public List<panierat> selectAll() throws SQLException {
        System.out.println(user_id);
        List<panierat> panie =new ArrayList<>();
        String req = "SELECT * FROM panier WHERE id_user = user_id";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            panierat p = new panierat();
            p.setId_panier(rs.getInt("id_panier"));
            p.setId_user(rs.getInt("id_user"));
            p.setId_oeuvre(rs.getInt("id_oeuvre"));
            panie.add(p);
        }

        return panie;
    }



    @Override
    public ObservableList<panierat> selectAlloeuvreTableview() throws SQLException {
        ObservableList<panierat> paniers = FXCollections.observableArrayList();
        String req = "SELECT * FROM oeuvre,panier where oeuvre.id_oeuvre=panier.id_oeuvre ";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            panierat p = new panierat();
            p.setId_panier(rs.getInt("id_panier"));
            p.setId_user(rs.getInt("id_user"));
            p.setId_oeuvre(rs.getInt("id_oeuvre"));

            paniers.add(p);

        }

        return paniers;
    }


    @Override
    public void deleteOne(int id_panier,int id_oeuvre) throws SQLException {
        String req = "DELETE FROM panier WHERE id_oeuvre = ? and id_user = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
        //    pst.setInt(1, id_panier);
            pst.setInt(1, id_oeuvre);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        }
    }

    @Override
    public void deleteTwo(int id_panier) throws SQLException {
        String req = "DELETE FROM panier WHERE id_panier = ? and id_user = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            //    pst.setInt(1, id_panier);
            pst.setInt(1, id_panier);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        }
    }

    @Override
    public void Update(panierat pan) throws SQLException{

        String req = "UPDATE panier SET id_panier= ?, id_user= ?,id_oeuvre= ? ";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1,pan.getId_panier());
            pstmt.setInt(2, pan.getId_user());
            pstmt.setInt(3, pan.getId_oeuvre());

            pstmt.executeUpdate();
        }

    }



}

