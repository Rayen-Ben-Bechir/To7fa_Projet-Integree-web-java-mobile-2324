package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Oeuvre;
import services.ServiceOeuvre;
import services.servicePanier;
import tn.esprit.fxMain;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.Ajoutertoutlescommandes2.StaticCardVeri;



public class Ajoutertoutlescommandes implements Initializable {
    @FXML
    private ChoiceBox<Integer> numberChoiceBox;
    @FXML
    private ChoiceBox<Integer> numberChoiceBox2;
    @FXML
    private Label alertcvv;

    @FXML
    private Label alertemail;

    @FXML
    private Label alertexpire;
    @FXML
    private ImageView alertimgcard1;

    @FXML
    private ImageView alertimgcard2;

    @FXML
    private ImageView alertimgcard3;
    @FXML
    private TextField CVV_number;

    @FXML
    private Button addCommande;
    private List<Oeuvre> ouevs = new ArrayList<>();
    @FXML
    private Label alertcard;
    @FXML
    private Label alertname;

    @FXML
    private ImageView alertimgcard;

    @FXML
    private VBox vbPrice;

    @FXML
    private GridPane grid;
    @FXML
    private Label TotalArticle;

    @FXML
    private Label priceVeri;

    @FXML
    private TextField stringpayment;


    @FXML
    private TextField name_card;




    private List<Oeuvre> getData() {

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

                ouev = new Oeuvre();
                ouev.setTitre(item.getTitre());
                ouev.setDescription(item.getDescription());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 1; i <= 20; i++) {
            numberChoiceBox.getItems().add(i);
        }

        for (int i = 1; i <= 20; i++) {
            numberChoiceBox2.getItems().add(i);
        }

float somme = 0.f;
        ouevs.addAll(getData());

        int column = 0;
        int row = 1;
        for (Oeuvre item : ouevs) {

            // Accumulate the price
            somme += item.getPrix();
        }
     

      //  for (int i = 0; i < ouevs.size(); i++) {
              /*
                Label label = new Label("Price: " + item.getPrice());
                vbPrice.getChildren().add(label);*/
        try {
            for (int i = 0; i < ouevs.size(); i++) {


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/commandeprix.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                commandesPrix commandesPrixX = fxmlLoader.getController();
                commandesPrixX.setDataa(ouevs.get(i));
                grid.add(anchorPane, column,row++);
                GridPane.setMargin(anchorPane, new Insets(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TotalArticle.setText(fxMain.CURRENCY +String.valueOf(somme));





            }

    @FXML
    void Nextstep(ActionEvent event) throws IOException {
        if (!stringpayment.getText().matches("\\d{0,8}") || stringpayment.getText().length() != 8) {
            Image image = new Image(getClass().getResourceAsStream("/img/card.png"));
            alertimgcard.setImage(image);
            alertcard.setText("Please Check Your Card !");
        }
            else if(name_card.getText().length() == 0 ) {
            alertcard.setText("");
            Image image = new Image(getClass().getResourceAsStream("/img/card2.png"));
            alertimgcard.setImage(image);
            Image image2 = new Image(getClass().getResourceAsStream("/img/card.png"));
            alertimgcard2.setImage(image2);
            alertname.setText("Put Your Name");
        }   else if(CVV_number.getText().length() != 3 ||!CVV_number.getText().matches("\\d{0,8}")) {
            alertname.setText("");
            Image image2 = new Image(getClass().getResourceAsStream("/img/card2.png"));
            alertimgcard2.setImage(image2);
            Image image = new Image(getClass().getResourceAsStream("/img/card.png"));
            alertimgcard3.setImage(image);
            alertcvv.setText("Put Your Cvv");
        }   else if(numberChoiceBox.getSelectionModel().isEmpty() || numberChoiceBox2.getSelectionModel().isEmpty() ) {
            alertcvv.setText("");
            alertexpire.setText("Select Expire");
        }
            else {

            Parent root = FXMLLoader.load(getClass().getResource("/views/ajouterroutlescommandes2.fxml"));
            Stage window = (Stage) addCommande.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticCardVeri.setText(stringpayment.getText());




        }

    }
    @FXML
    private Button backo;
    @FXML
    void backpanier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));
    }

    @FXML
    void cancel(ActionEvent event) {
        stringpayment.setText("");
    }

    }

