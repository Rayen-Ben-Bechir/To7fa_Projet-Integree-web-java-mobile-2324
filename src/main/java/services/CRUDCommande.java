package services;

import javafx.collections.ObservableList;
import models.commande;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface CRUDCommande<T> {
    void insertOne(T t) throws SQLException;
    void deleteOne(int i) throws SQLException ;
     void updateOne(T t);

    List<T> selectAll() throws SQLException ;
     ObservableList<commande> selectAllTableview() throws SQLException, ParseException;
   // List<G> selectoeuvre() throws SQLException ;
}
