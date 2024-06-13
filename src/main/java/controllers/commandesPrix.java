package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Oeuvre;
import tn.esprit.fxMain;

public class commandesPrix {

    @FXML
    private Label idlab;


    @FXML
    private Label namelab;

    @FXML
    private Label pricelab;

    private Oeuvre oeuv;

    public  void setDataa(Oeuvre oev) {

        this.oeuv = oev;
        idlab.setText(String.valueOf(fxMain.Article+oev.getId_oeuvre()));

        namelab.setText(oev.getTitre());



        pricelab.setText(fxMain.CURRENCY + oev.getPrix());


    }
}
