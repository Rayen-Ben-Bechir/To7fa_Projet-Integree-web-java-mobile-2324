package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Oeuvre;
import services.ServiceOeuvre;
import services.serviceCommande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.ajoutercommande2.*;


public class Ajoutercommande  implements Initializable {

    @FXML
    private TextField CVV_number;

    @FXML
    private Label NameArticle;

    @FXML
    private Label TotalArticle;

    @FXML
    private Button addCommande;

    @FXML
    private Label alertcard;

    @FXML
    private Label alertcvv;

    @FXML
    private Label alertemail;

    @FXML
    private Label alertexpire;

    @FXML
    private ImageView alertimgcard;

    @FXML
    private ImageView alertimgcard1;

    @FXML
    private ImageView alertimgcard2;

    @FXML
    private ImageView alertimgcard3;

    @FXML
    private Label alertname;

    @FXML
    private Button backo;

    @FXML
    private Button backp;

    @FXML
    private TextField name_card;

    @FXML
    private ChoiceBox<Integer> numberChoiceBox;

    @FXML
    private ChoiceBox<Integer> numberChoiceBox2;

    @FXML
    private Label prixArticle;

    @FXML
    private AnchorPane scene2;

    @FXML
    private TextField stringEmail;

    @FXML
    private TextField stringpayment;
    public static ChoiceBox<Integer> choice_piece2;
    @FXML
    private ChoiceBox<Integer> choicee;


    @FXML
    private Label nbr;




    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tId;


    @FXML
    void ajouter(ActionEvent event) {

    }

    @FXML
    private Button Nextstep;
    public static Label StaticName;
    public static Label StaticPrice;
    public static Label StaticTotal;



    @FXML
    void backTopan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));

    }

    private List<Oeuvre> getData() {
        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

                ouev = new Oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setTitre(item.getTitre());
                ouev.setDate(item.getDate());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());
                ouev.setLienImg(item.getLienImg());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }

    @FXML
    void addCommande(ActionEvent event) throws IOException {
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
        } else if(stringEmail.getText().isEmpty()) {
            alertexpire.setText("");
            Image image = new Image(getClass().getResourceAsStream("/img/card2.png"));
            alertimgcard3.setImage(image);
            alertemail.setText("put Your Email");
            Image image2 = new Image(getClass().getResourceAsStream("/img/email.png"));
            alertimgcard1.setImage(image2);

        }else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutercommande2.fxml"));
            Stage window = (Stage) Nextstep.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticNameVeri.setText(StaticName.getText());
            StaticPriceVeri.setText(StaticPrice.getText());
            StaticCardVeri.setText(stringpayment.getText());

        }


    }
    @FXML
    void cancel(ActionEvent event) {
        stringpayment.setText("");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // nbr.setText(String.valueOf(qunt));
        for (int i = 1; i <= 20; i++) {
            numberChoiceBox.getItems().add(i);
        }

        for (int i = 1; i <= 20; i++) {
            numberChoiceBox2.getItems().add(i);
        }

StaticName=NameArticle;
StaticPrice=prixArticle;
StaticTotal=TotalArticle;
choice_piece2=choicee;


    }
}
