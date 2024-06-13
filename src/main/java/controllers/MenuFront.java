package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
import services.UserServiceCRUD;
import test.MainFx;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuFront implements Initializable
{

    @FXML
    private AnchorPane AP_ABOVE;

    @FXML
    private AnchorPane AP_LEFT;

    @FXML
    private AnchorPane AP_SEMI_ABOVE;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_profil;

    @FXML
    private Button btn_update_image;

    @FXML
    private Button btn_update_profil;

    @FXML
    private Button btn_update_pw;

    @FXML
    private AnchorPane card;

    @FXML
    private Button close;

    @FXML
    private ComboBox<String> combobox_sexe;

    @FXML
    private AnchorPane home_form;

    @FXML
    private ImageView imageview_MenuFront;
    
    @FXML
    private ImageView imageview_updateuser;

    @FXML
    private Label label_email;

    @FXML
    private Label label_name;

    @FXML
    private Label label_phone;

    @FXML
    private Label label_pw_actuel;

    @FXML
    private Label label_pw_nouvel;

    @FXML
    private Label label_pw_nouvel_confirm;

    @FXML
    private Label label_sexe;

    @FXML
    private Label label_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private PasswordField passwordfield_pw_nouvel;

    @FXML
    private PasswordField passwordfield_pw_nouvel_confirm;

    @FXML
    private AnchorPane profil_form;

    @FXML
    private TextField textfield_email;

    @FXML
    private TextField textfield_name;

    @FXML
    private TextField textfield_phone;

    @FXML
    private TextField textfield_pw_actuel;

    UserServiceCRUD usc = new UserServiceCRUD();
    private Stage stage;
    public String imagePath = "Image non sélectionnée";

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("ha nami");
        String str1 = MainFx.Userconnected.getUser_image();
        System.out.println("ha nami");
        /*Image image;
        if(str1.equals("Image non sélectionnée"))
            if(MainFx.Userconnected.getUser_sexe().equals("Homme"))
                image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/UserHomme.png", 90.0, 86.0, false, true);
            else
                image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/UserFemme.png", 90.0, 86.0, false, true);
        else
            image = new Image(str1, 90.0, 86.0, false, true);
        imageview_MenuFront.setImage(image);*/
        System.out.println("ha nami");
        String str2 = MainFx.Userconnected.getUser_name();
        label_username.setText(str2);

        ObservableList<String> list_sexe = FXCollections.observableArrayList("Homme","Femme");
        combobox_sexe.setItems(list_sexe);

        this.textfield_name.setText(MainFx.Userconnected.getUser_name());
        this.textfield_phone.setText(MainFx.Userconnected.getUser_phone());
        this.textfield_email.setText(MainFx.Userconnected.getUser_email());
        this.combobox_sexe.setValue(MainFx.Userconnected.getUser_sexe());
        //this.imageview_updateuser.setImage(image);
    }

    @FXML
    void close(ActionEvent event) {System.exit(0);}

    @FXML
    void minimize(ActionEvent event)
    {
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void logout(ActionEvent event)
    {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText((String)null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (!((ButtonType)option.get()).equals(ButtonType.OK)) {
                return;
            }

            this.btn_logout.getScene().getWindow().hide();
            Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchForm(ActionEvent event)
    {
        if (event.getSource() == this.btn_home) {
            this.home_form.setVisible(true);
            this.profil_form.setVisible(false);
            this.btn_home.setStyle("-fx-background-color:linear-gradient(to bottom right, #9b7a56, #593a00);");
            this.btn_profil.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == this.btn_profil) {
            this.home_form.setVisible(false);
            this.profil_form.setVisible(true);
            this.btn_home.setStyle("-fx-background-color:transparent");
            this.btn_profil.setStyle("-fx-background-color:linear-gradient(to bottom right, #9b7a56, #593a00);");
        }
    }

    @FXML
    void delete_profil(ActionEvent event) throws SQLException
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression du compte");
        alert.setContentText("Voulez-vous vraiment supprimer Votre compte ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            if (usc.DeleteUser(MainFx.Userconnected))
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Suppression du compte avec success", new ButtonType[]{ButtonType.CLOSE});
                alert.show();

                FXMLLoader LOADER = new FXMLLoader(getClass().getResource("/Login.fxml"));
                try {
                    Parent root = LOADER.load();
                    Scene scene = new Scene(root);
                    Login controller = LOADER.getController();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.err.println(("Erreur dans l'affichage de new stage :" + e.getMessage()));
                }
            }
        }
    }

    @FXML
    void update_profil(ActionEvent event) throws SQLException
    {
        User user1 = new User();
        user1.setUser_name(this.textfield_name.getText());
        user1.setUser_phone(this.textfield_phone.getText());
        user1.setUser_email(this.textfield_email.getText());
        user1.setUser_sexe(this.combobox_sexe.getValue());
        user1.setUser_image(imagePath);

        if (usc.UpdateUser(user1,MainFx.Userconnected.getUser_id()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme : \n"+ textfield_name.getText()+ " "+ ", Vos donnés personelles sont modifiés !", ButtonType.CLOSE);
            alert.show();

            MainFx.Userconnected.setUser_name(this.textfield_name.getText());
            MainFx.Userconnected.setUser_phone(this.textfield_phone.getText());
            MainFx.Userconnected.setUser_email(this.textfield_email.getText());
            MainFx.Userconnected.setUser_sexe(this.combobox_sexe.getValue());
            MainFx.Userconnected.setUser_image(imagePath);

            String str1 = MainFx.Userconnected.getUser_image();
            Image image;
            if(str1.equals("Image non sélectionnée"))
                if(MainFx.Userconnected.getUser_sexe().equals("Homme"))
                    image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/UserHomme.png", 90.0, 86.0, false, true);
                else
                    image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/UserFemme.png", 90.0, 86.0, false, true);
            else
                image = new Image(str1, 90.0, 86.0, false, true);
            imageview_MenuFront.setImage(image);

            String str = MainFx.Userconnected.getUser_name();
            label_username.setText(str);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, " Il ya un petit probleme au niveau de update profil, ressayer plus tard !", ButtonType.CLOSE);
            alert.show();
        }
    }

    @FXML
    void update_pw(ActionEvent event) throws SQLException
    {
        if (textfield_pw_actuel.getText().equals(MainFx.Userconnected.getUser_password()) && passwordfield_pw_nouvel.getText().equals(passwordfield_pw_nouvel_confirm.getText()))
        {
            if (usc.UpdatePW(passwordfield_pw_nouvel.getText(), MainFx.Userconnected.getUser_id())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme : \n" + MainFx.Userconnected.getUser_name() + ", Votre mot de passe a été bien modifier !", ButtonType.CLOSE);
                alert.show();
                MainFx.Userconnected.setUser_password(passwordfield_pw_nouvel.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Il ya un petit probleme  au niveau de update PW, ressayer plus tard !", ButtonType.CLOSE);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, " L'une des mot de passes ne correspand pas, merci de ressayer !", ButtonType.CLOSE);
            alert.show();
        }

        clear_textfield();
    }

    void clear_textfield() {
        this.textfield_pw_actuel.setText("");
        this.passwordfield_pw_nouvel.setText("");
        this.passwordfield_pw_nouvel_confirm.setText("");
    }

    @FXML
    void update_image(ActionEvent event) throws FileNotFoundException, IOException
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
            Image imageUser = new Image(file.toURI().toString(), 120.0, 149.0, false, true);
            this.imageview_updateuser.setImage(imageUser);
            this.imagePath = imagePath;
            System.out.println("Image Path: " + imagePath);
        } else if (f == null) {
            System.out.println("Erreur, image non selectionnée !!!");
        }
    }
    ////////////////////////////////////////////










    @FXML
    private Button btn_musee;

    @FXML
    void musee(ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/ReservationFXML.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Mussée");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_evenement;

    @FXML
    void evenementClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/EvenementClient.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        // Set the scene to fullscreen
        stage.setScene(scene);

        // Show the stage
        stage.show();
        stage.setTitle("Evenement");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_acceuil;

    @FXML
    void acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/Acceuil.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        // Set the scene to fullscreen
        stage.setScene(scene);

        // Show the stage
        stage.show();

        stage.setTitle("Home");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_panier;

    @FXML
    void panier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/Panier.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        // Set the scene to fullscreen
        stage.setScene(scene);

        // Show the stage
        stage.show();
        stage.setTitle("Panier");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }
}
