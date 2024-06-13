package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import models.User;
import services.UserServiceCRUD;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp implements Initializable {

    @FXML
    private AnchorPane AP1_signup;

    @FXML
    private AnchorPane AP2_signup;

    @FXML
    private Button btn_cnx_signup;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_pageprecedente;

    @FXML
    private Button btn_parcourir_image;

    @FXML
    private ComboBox<String> combobox_sexe;

    @FXML
    private ImageView imageuser_signup;

    @FXML
    private ImageView imageview_logo_signup;

    @FXML
    private Label label_cnx_signup;

    @FXML
    private PasswordField passwordfield1_signup;

    @FXML
    private PasswordField passwordfield2_signup;

    @FXML
    private TextField textfield_email;

    @FXML
    private TextField textfield_name;

    @FXML
    private TextField textfield_phone;

    @FXML
    private Label label_captcha;

    @FXML
    private TextField textfield_captcha;

    @FXML
    private Button btn_refresh_captcha;

    @FXML
    private Label control_saisi_captcha_signup;

    @FXML
    private Label control_saisi_email_signup;

    @FXML
    private Label control_saisi_nom_signup;

    @FXML
    private Label control_saisi_phone_signup;

    @FXML
    private Label control_saisi_pw_signup;

    @FXML
    private Label control_saisi_pwconfrirm_signup;

    @FXML
    private Label control_saisi_sexe_signup;

    String captcha;

    UserServiceCRUD usc = new UserServiceCRUD();
    private double x = 0,y = 0;
    private Stage stage;
    public String imagePath = "Image non sélectionnée";

    public void setStage(Stage stage){this.stage = stage;}

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        AP1_signup.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        AP1_signup.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

        ObservableList<String> list_sexe = FXCollections.observableArrayList("Homme","Femme");
        combobox_sexe.setItems(list_sexe);

        captcha = GenerateCaptcha();
        label_captcha.setText(captcha);
        System.out.println(captcha);
    }

    @FXML
    void Exit(ActionEvent event)
    {
        if (stage != null) {
            System.exit(0);
        } else {
            // Handle the case when stage is null, perhaps log a message or display an error
            System.err.println("Stage sign up is null. Unable to close.");
        }
    }

    @FXML
    void PagePrecedente(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent parent = loader.load();
        Login login = loader.getController();
        Scene scene = new Scene(parent);

        Stage currentStage = (Stage) AP1_signup.getScene().getWindow(); // Get the reference to the current stage
        currentStage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        currentStage.initStyle(StageStyle.TRANSPARENT);

        login.setStage(currentStage); // Pass the reference of the current stage to the login controller
    }

    @FXML
    void SignUp(ActionEvent event)
    {
        Alert alert;
        User user1 = new User();

        String nom = this.textfield_name.getText();
        if(nom.isEmpty())
        {
            control_saisi_nom_signup.setText("Champ Nom est OBLIGATOIRE");
            control_saisi_nom_signup.setVisible(true);
        }
        else
            control_saisi_nom_signup.setVisible(false);

        String phone = this.textfield_phone.getText();
        if(phone.isEmpty())
        {
            control_saisi_phone_signup.setText("Champ Phone est OBLIGATOIRE");
            control_saisi_phone_signup.setVisible(true);
        }
        else
            control_saisi_phone_signup.setVisible(false);

        String email = this.textfield_email.getText();
        if(email.isEmpty())
        {
            control_saisi_email_signup.setText("Champ Email est OBLIGATOIRE");
            control_saisi_email_signup.setVisible(true);
        }
        else
            control_saisi_email_signup.setVisible(false);

        String pw = this.passwordfield1_signup.getText();
        if(pw.isEmpty())
        {
            control_saisi_pw_signup.setText("Champ Mot De Passe est OBLIGATOIRE");
            control_saisi_pw_signup.setVisible(true);
        }
        else
            control_saisi_pw_signup.setVisible(false);

        String pwconf = this.passwordfield2_signup.getText();
        if(pwconf.isEmpty())
        {
            control_saisi_pwconfrirm_signup.setText("Champ Confirm Mot De Passe est OBLIGATOIRE");
            control_saisi_pwconfrirm_signup.setVisible(true);
        }
        else
            control_saisi_pwconfrirm_signup.setVisible(false);

        String captcha1 = this.textfield_captcha.getText();
        if(captcha1.isEmpty())
        {
            control_saisi_captcha_signup.setText("Champ Captcha est OBLIGATOIRE");
            control_saisi_captcha_signup.setVisible(true);
        }
        else if (!textfield_captcha.getText().equals(captcha))
        {
            control_saisi_captcha_signup.setText("You have entered the wrong code");
            control_saisi_captcha_signup.setVisible(true);
        }
        else
            control_saisi_captcha_signup.setVisible(false);

        String sexe = this.combobox_sexe.getValue();
        /*if(sexe.equals("null"))
        {
            control_saisi_sexe_signup.setText("Champ Sexe est OBLIGATOIRE");
            control_saisi_sexe_signup.setVisible(true);
        }
        else
            control_saisi_sexe_signup.setVisible(false);*/


        if(usc.getUserByEmail(this.textfield_email.getText())==1)
        {
            alert = new Alert(Alert.AlertType.ERROR, "User existe deja", new ButtonType[]{ButtonType.CLOSE});
            alert.show();
        }
        else
            if(this.passwordfield1_signup.getText().equals(this.passwordfield2_signup.getText()) && textfield_captcha.getText().equals(String.valueOf(captcha))) {
            user1.setUser_name(this.textfield_name.getText());
            user1.setUser_phone(this.textfield_phone.getText());
            user1.setUser_email(this.textfield_email.getText());
            user1.setUser_password(this.passwordfield1_signup.getText());
            user1.setUser_sexe(this.combobox_sexe.getValue());
            user1.setUser_role("Client");
            user1.setUser_image(imagePath);

            try {
                this.usc.AddUser(user1);
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Bienvenue Mr/Mme :\n" + this.textfield_name.getText(), new ButtonType[]{ButtonType.CLOSE});
                alert.show();

                try {
                    FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/Login.fxml"));
                    Parent parent = LOADER.load();
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.err.println(("Erreur dans l'affichage de new stage :" + e.getMessage()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            alert = new Alert(Alert.AlertType.ERROR, "PW incorrect", new ButtonType[]{ButtonType.CLOSE});
            alert.show();
        }
    }

    @FXML
    void ParcourirImage(ActionEvent event) throws FileNotFoundException, IOException
    {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog((Stage)null);
        if (f != null) {
            String imagePath = f.getAbsolutePath();
            InputStream is = null;
            OutputStream os = null;

            try {
                is = new FileInputStream(f);
                os = new FileOutputStream(new File("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images" + f.getName()));
                byte[] buffer = new byte[1024];

                while(true) {
                    int length;
                    if ((length = is.read(buffer)) <= 0) {
                        System.out.println("ok");
                        break;
                    }

                    os.write(buffer, 0, length);
                }
            } finally {
                is.close();
                os.close();
            }

            File file = new File("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images" + f.getName());
            Image imageUser = new Image(file.toURI().toString(), 119.0, 123.0, false, true);
            this.imageuser_signup.setImage(imageUser);
            this.imagePath = imagePath;
            System.out.println("Image Path: " + imagePath);
        } else if (f == null) {
            System.out.println("Erreur, image non selectionnée !!!");
        }
    }

    public String GenerateCaptcha()
    {
        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int max = 36;
        String captcha = "";
        int min = 0;
        for (int i = 0; i < 6; i++) {
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            captcha += data[rand];
        }
        System.out.println(captcha);
        return captcha;
    }

    @FXML
    void refresh_captcha(ActionEvent event)
    {
        captcha = GenerateCaptcha();
        label_captcha.setText(captcha);
        System.out.println(captcha);
        textfield_captcha.setText("");
    }
}
