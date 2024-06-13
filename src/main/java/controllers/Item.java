package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Oeuvre;
import models.panierat;
import services.servicePanier;
import tn.esprit.MyListener;
import tn.esprit.fxMain;

import java.io.IOException;
import java.sql.SQLException;

import static controllers.Ajoutercommande.*;

public class Item {
    private Oeuvre oeuv;
    @FXML
    private Label dateItem;
    private MyListener myListener;

    @FXML
    private Label idItem;

    @FXML
    private Label titreItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private Label prixItem;

    @FXML
    private Label statusItem;
    @FXML
    private Button commandebtn;

    @FXML
    void addToBasket(ActionEvent event) throws SQLException {
        servicePanier sp = new servicePanier();
        panierat p = new panierat(15, Integer.parseInt(idItem.getText()));
        System.out.println(p.getId_oeuvre());

        sp.insertOne(p);

    }
    @FXML
    void addToCommande(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutercommande.fxml"));
        Stage window = (Stage) commandebtn.getScene().getWindow();
        window.setScene(new Scene(root, 1315, 800));
        StaticName.setText(titreItem.getText());
        StaticPrice.setText(prixItem.getText());
        StaticTotal.setText(prixItem.getText());

    }







    public void setData(Oeuvre oev) {
        this.oeuv = oev;
        idItem.setText(Integer.toString(oev.getId_oeuvre()));
        titreItem.setText(oev.getTitre());
        prixItem.setText(Float.toString(oev.getPrix()));
        statusItem.setText(oev.getStatus());
        dateItem.setText(oev.getDate());

        String imageUrl = oev.getLienImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageItem.setImage(image);
            } catch (Exception e) {
                // Handle the exception (e.g., log it, show an error message)
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                // You might want to set a default image here
                // Image image = new Image("default_image.png");
                // imageItem.setImage(image);
            }
        } else {
            // Si l'URL de l'image est null ou vide, vous pouvez définir une image par défaut ici
            // Image image = new Image("default_image.png");
            // imageItem.setImage(image);
        }
        System.out.println("Titre: " + oev.getTitre());
    }

}
