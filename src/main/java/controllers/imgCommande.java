package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Oeuvre;

public class imgCommande {
    @FXML
    private ImageView imgCommande;

    private Oeuvre oeuv;

    private Image image;
    public void setData(Oeuvre oev) {

        this.oeuv = oev;

        String imageUrl = oev.getLienImg();
        image = new Image(imageUrl);
        imgCommande.setImage(image);
    }

}
