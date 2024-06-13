package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Oeuvre;
import javafx.scene.image.Image;
import models.commande;
import services.serviceCommande;
import services.servicePanier;
import tn.esprit.MyListener;
import tn.esprit.fxMain;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class itemController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Namelabel;

    @FXML
    private ImageView imgLabel;

    @FXML
    private Label prixLabel;
    private Oeuvre oeuv;

    @FXML
    private Label idouvre;
    private MyListener myListener;
    public static Label satatic_id;

    private Image image;
    @FXML
    void btnhi(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert Namelabel != null : "fx:id=\"Namelabel\" was not injected: check your FXML file 'items.fxml'.";
        assert imgLabel != null : "fx:id=\"imgLabel\" was not injected: check your FXML file 'items.fxml'.";
        assert prixLabel != null : "fx:id=\"prixLabel\" was not injected: check your FXML file 'items.fxml'.";

    }


    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(oeuv);
    }

    public void setData(Oeuvre oev,MyListener myListener) {

        this.oeuv = oev;
        this.myListener=myListener;
        Namelabel.setText(oev.getTitre());

        idouvre.setText(Integer.toString(oev.getId_oeuvre()));

        prixLabel.setText(fxMain.CURRENCY + oev.getPrix());

        String imageUrl = oev.getLienImg();
        image = new Image(imageUrl);
        imgLabel.setImage(image);
    }






    @FXML
    void deleteoeuvre(ActionEvent event) {

      /*  try {
            servicePanier sp = new servicePanier();
            System.out.println("88");
            sp.deleteOne(3,  Integer.parseInt(idouvre.getText()));
            System.out.println("99");
            // Optionally, you can display a message or update your UI after deletion
            System.out.println("Record deleted successfully.");
        } catch (SQLException ex) {
            System.err.println("Error deleting record: " + ex.getMessage());
            // Optionally, you can show an error message to the user
        }*/
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        satatic_id=idouvre;

    }


}
