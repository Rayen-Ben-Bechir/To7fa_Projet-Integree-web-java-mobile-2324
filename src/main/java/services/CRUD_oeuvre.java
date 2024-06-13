package services;

import models.Oeuvre;

import java.sql.SQLException;
import java.util.List;

public interface CRUD_oeuvre<T> {
    public List<T> selectMYAll()throws SQLException;
    void  insertOne(T t) throws SQLException;
   void  updateOne(T t) throws SQLException;
    public List<T> selectAllOeuvre()throws SQLException;
   void  deleteOne(int id) throws SQLException;
    public List<T> trie() throws SQLException;
    List<T> selectAll() throws SQLException;
    public List<T> selectByname(String ch) throws SQLException;
    void updateOne1(int id_oeuvre, Oeuvre updatedOeuvre, String imagePath) throws SQLException ;


    }
