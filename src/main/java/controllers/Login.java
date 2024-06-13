package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import models.User;
import services.GMailer;
import services.Mailing;
import services.UserServiceCRUD;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable
{
    @FXML
    private AnchorPane AP1_login;

    @FXML
    private AnchorPane AP2_login;

    @FXML
    private Button btn_cnx_login;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_inscription;

    @FXML
    private Button btn_password_oublie;

    @FXML
    private ImageView imageview_logo_login;

    @FXML
    private Label label_cnx_login;

    @FXML
    private PasswordField paswwordfield_login;

    @FXML
    private TextField textfield_login;

    @FXML
    private Label control_saisi_email_login;

    @FXML
    private Label control_saisi_password_login;

    UserServiceCRUD usc = new UserServiceCRUD();

    private double x = 0,y = 0;

    private Stage stage;
    public Stage getStage(){return stage;}
    public void setStage(Stage stage){this.stage = stage;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        AP1_login.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        AP1_login.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    @FXML
    void Exit(ActionEvent event)
    {
        if (stage != null) {
            System.exit(0);
        } else {
            // Handle the case when stage is null, perhaps log a message or display an error
            System.err.println("Stage login is null. Unable to close.");
        }
    }

    @FXML
    void PageSuivante(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent parent = loader.load();
        SignUp signup = loader.getController();
        Scene scene = new Scene(parent);

        Stage currentStage = (Stage) AP1_login.getScene().getWindow(); // Get the reference to the current stage
        currentStage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        currentStage.initStyle(StageStyle.TRANSPARENT);

        signup.setStage(currentStage); // Pass the reference of the current stage to the signup controller
    }

    @FXML
    void Login(ActionEvent event) throws SQLException
    {
        String emailOUphone = this.textfield_login.getText();
        if(emailOUphone.isEmpty())
        {
            control_saisi_email_login.setText("Champ Email ou Phone est OBLIGATOIRE");
            control_saisi_email_login.setVisible(true);
        }
        else
            control_saisi_email_login.setVisible(false);

        String password = this.paswwordfield_login.getText();
        if(password.isEmpty())
        {
            control_saisi_password_login.setText("Champ Password est OBLIGATOIRE");
            control_saisi_password_login.setVisible(true);
        }
        else
            control_saisi_password_login.setVisible(false);

        User user1 = this.usc.LoginService(this.textfield_login.getText(), this.paswwordfield_login.getText());
        if (user1.getUser_id() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Verifier vos donnés", new ButtonType[]{ButtonType.CLOSE});
            alert.show();
        }
        else
        {
            User user2 = this.usc.LoginService(this.textfield_login.getText(), this.paswwordfield_login.getText());
            MainFx.Userconnected.setUser_id(user2.getUser_id());
            MainFx.Userconnected.setUser_name(user2.getUser_name());
            MainFx.Userconnected.setUser_phone(user2.getUser_phone());
            MainFx.Userconnected.setUser_email(user2.getUser_email());
            MainFx.Userconnected.setUser_password(user2.getUser_password());
            MainFx.Userconnected.setUser_sexe(user2.getUser_sexe());
            MainFx.Userconnected.setUser_role(user2.getUser_role());
            MainFx.Userconnected.setUser_image(user2.getUser_image());
            MainFx.Userconnected = user2;
            System.out.println("this is test");
            System.out.println(MainFx.Userconnected);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Je vous souhaite la bienvenue Mr/Mme :\n" + user1.getUser_name(), new ButtonType[]{ButtonType.OK});
            alert.show();
            if(user1.getUser_role().equals("Admin"))
            {
                FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/MenuBack.fxml"));
                try {
                    Parent root = (Parent)LOADER.load();
                    Scene sc = new Scene(root);
                    MenuBack controller = (MenuBack) LOADER.getController();
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(sc);
                    stage.show();
                } catch (IOException e) {
                    System.err.println(("Erreur dans l'affichage de new stage :"+e.getMessage()));
                }
            }
            else
            {
                if(user1.getUser_role().equals("Client"))
                {
                    FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/MenuFront.fxml"));
                    try {
                        Parent root = (Parent)LOADER.load();
                        Scene sc = new Scene(root);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        stage.setScene(sc);
                        stage.show();
                    } catch (IOException e) {
                        System.err.println(("Erreur dans l'affichage de new stage :"+e.getMessage()));
                    }
                }
                else
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Role incorrecte, vous etes ni un admin ni un client", new ButtonType[]{ButtonType.CLOSE});
                    alert1.show();
                }
            }
        }
    }

    public String GenerateCode()
    {
        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int max = 36;
        String code = "";
        int min = 0;
        for (int i = 0; i < 6; i++) {
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            code += data[rand];
        }
        System.out.println(code);
        return code;
    }

    @FXML
    void MotDePasseOublie(ActionEvent event) throws Exception {
        String code = GenerateCode();
        GMailer gmailer = new GMailer();
        String email = this.textfield_login.getText();
        String subject = "Code de modification de votre mot de passe :";
        String message = "Dear Mr/Mme ,\nThis is your secret code : "+code+"\nBest regards";
        gmailer.sendMail(email,subject, message);

        // Load the CaptchaPage.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MotDePasseOublie.fxml"));
        Parent root = loader.load();
        MotDePasseOublie controller = loader.getController();
        controller.setCode(code);
        controller.setEmail(email);
        // Pass necessary data to the CaptchaFXML controller if needed

        // Show the CaptchaPage.fxml in a new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Modification de Mot De Passe");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Logo.png")));
        controller.setStage(stage);
    }
}
