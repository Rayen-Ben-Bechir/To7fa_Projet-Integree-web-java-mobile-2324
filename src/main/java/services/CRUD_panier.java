package services;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CRUD_panier<T> {

 //  public int searchByIdOeuvre(int i) throws SQLException;
   void insertOne(T t) throws SQLException;
    void Update(T t) throws SQLException;
   void deleteOne(int i,int j) throws SQLException ;
    void deleteTwo(int i) throws SQLException ;
    //void updateOne(int personId, String newtype_commande, String newname_commande, String newdate_commande, String newpayment) throws SQLException;
    List<T> selectAll() throws SQLException ;
     ObservableList<T> selectAlloeuvreTableview() throws SQLException;


}
