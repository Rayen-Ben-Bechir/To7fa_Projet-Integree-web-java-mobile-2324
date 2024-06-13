package controllers;



import java.io.*;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Oeuvre;
import models.commande;
import nl.captcha.Captcha;
import org.controlsfx.control.Notifications;
import services.ServiceOeuvre;
import services.serviceCommande;
import services.servicePanier;
import test.MainFx;
import tn.esprit.fxMain;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.embed.swing.SwingFXUtils;




public class Ajoutertoutlescommandes2 implements Initializable {
    @FXML
    private VBox vboxImg;
    @FXML
    private Label captch_incorrect;

    @FXML
    private ImageView cap;

    @FXML
    private Button backo;
    @FXML
    private Button backp;

    @FXML
    private Label cardVeri;

    @FXML
    private Button orderter;

    @FXML
    private Label priceVeri;

    @FXML
    private AnchorPane scene2;
    @FXML
    private Label nbr;
    @FXML
    private GridPane grid;

    @FXML
    private TextField captchaTF;

    public static Label StaticCardVeri;
    private Captcha captcha;



    private List<Oeuvre> ouevs = new ArrayList<>();

    public Captcha setCaptcha()
    {
        Captcha captchaV = new Captcha.Builder(250, 150)
                .addText()
                .addBackground()
                .addNoise()
                .addBorder()
                .build();
        System.out.println(captchaV.getImage());
        Image image = SwingFXUtils.toFXImage(captchaV.getImage(), null);
        cap.setImage(image);
        return captchaV;
    }









    private List<Oeuvre> getData() {

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAllOeuvre()) {

                ouev = new Oeuvre();
                ouev.setTitre(item.getTitre());
                ouev.setDescription(item.getDescription());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());
                ouev.setLienImg(item.getLienImg());
                ouev.setId_oeuvre(item.getId_oeuvre());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       captcha=setCaptcha();

        int somme = 0;
        ouevs.addAll(getData());


        for (Oeuvre item : ouevs) {
            somme += item.getPrix();

        }
        priceVeri.setText(fxMain.CURRENCY +String.valueOf(somme));
        nbr.setText(String.valueOf(ouevs.size()));

        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/imgCommande.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                imgCommande ImgCommande = fxmlLoader.getController();
                ImgCommande.setData(ouevs.get(i));



                grid.add(anchorPane, column, row++); //(child,column,row)
                //set grid width

                GridPane.setMargin(anchorPane, new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticCardVeri=cardVeri;


    }
    @FXML
    void buy(ActionEvent event) throws StripeException {

        if (!captcha.isCorrect(captchaTF.getText())) {
            captcha = setCaptcha();
            captchaTF.setText("");
            captch_incorrect.setText("Incorrect !,Try Again");


        } else {


            ouevs.clear();
            ouevs.addAll(getData());
            Gmailer gm = new Gmailer();
           gm.send(MainFx.Userconnected.getUser_email(), priceVeri.getText());
           System.out.println(MainFx.Userconnected.getUser_email());
            try {

                for (Oeuvre item : ouevs) {

                    float floatValue = (float) item.getPrix();
                    commande p = new commande(floatValue, item.getTitre(), cardVeri.getText(), item.getId_oeuvre());
                    serviceCommande sp = new serviceCommande();
                    sp.insertOne(p);

                    servicePanier dbHandler = new servicePanier();
                     dbHandler.deleteOne(4, item.getId_oeuvre());
                }

                Image originalImage = new Image(String.valueOf(getClass().getResource("/img/verif.png")));
                double targetWidth = 50; // Set the desired width
                double targetHeight = 50; // Set the desired height
                Image resizedImage = new Image(originalImage.getUrl(), targetWidth, targetHeight, true, true);
                Notifications notification = Notifications.create();
                notification.graphic(new ImageView(resizedImage));
                notification.text("Order suuceful,check you Email!");
                notification.title("Ajout Effectué");
                notification.hideAfter(Duration.seconds(4));
                notification.position(Pos.BOTTOM_RIGHT);
                notification.darkStyle();
                notification.show();

                Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
                Stage window = (Stage) orderter.getScene().getWindow();
                window.setScene(new Scene(root, 1315, 800));

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Stripe.apiKey = "sk_test_51OpqGvDXGjZ4Cjv6NQSsDOKMh9QRwkw15NkS5eJR6ziiBunQwqsamzyBSR3BzN2SYqfb73hb1Y3NsoWcj7A7UVYM00K687EWQi";
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount(1000L) // Amount in cents (e.g., $10.00)
                        .setCurrency("usd")
                        .build();

                PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
                System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            } catch (
                    StripeException e) {
// If there was an error processing the payment, display the error message
                System.out.println("Payment failed. Error: " + e.getMessage());
            }

        }
    }







    @FXML
    void backpanier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));

    }



    }


