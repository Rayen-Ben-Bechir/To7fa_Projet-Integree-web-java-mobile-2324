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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.User;
import org.apache.poi.ss.usermodel.Cell;
import services.UserServiceCRUD;
import test.MainFx;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuBack implements Initializable
{
    @FXML
    private AnchorPane AP_ABOVE;

    @FXML
    private AnchorPane AP_LEFT;

    @FXML
    private AnchorPane AP_SEMI_ABOVE;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_excel;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_gestion_users;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_insert_image;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_profil;

    @FXML
    private Button btn_update;

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
    private TableView<User> users_tableview;

    @FXML
    private TableColumn<User, String> col_email;

    @FXML
    private TableColumn<User, String> col_name;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private TableColumn<User, String> col_phone;

    @FXML
    private TableColumn<User, String> col_sexe;

    @FXML
    private TableColumn<User, String> col_image;

    @FXML
    private TableColumn<User, Integer> col_id;

    @FXML
    private ComboBox<String> combobox_sexe;

    @FXML
    private ComboBox<String> combobox_sexe_gestion;

    @FXML
    private AnchorPane gestion_users_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    private ImageView imageview_MenuBack;

    @FXML
    private ImageView imageview_updateuser;

    @FXML
    private ImageView imageview_user;

    @FXML
    private Label label_email;

    @FXML
    private Label label_email_gestion;

    @FXML
    private Label label_name;

    @FXML
    private Label label_name_gestion;

    @FXML
    private Label label_password_gestion;

    @FXML
    private Label label_phone;

    @FXML
    private Label label_phone_gestion;

    @FXML
    private Label label_pw_actuel;

    @FXML
    private Label label_pw_nouvel;

    @FXML
    private Label label_pw_nouvel_confirm;

    @FXML
    private Label label_sexe;

    @FXML
    private Label label_sexe_gestion;

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
    private TextField textfield_email_gestion;

    @FXML
    private TextField textfield_name;

    @FXML
    private TextField textfield_name_gestion;

    @FXML
    private TextField textfield_password_gestion;

    @FXML
    private TextField textfield_phone;

    @FXML
    private TextField textfield_phone_gestion;

    @FXML
    private TextField textfield_pw_actuel;

    @FXML
    private TextField textfield_search;

    @FXML
    private Label control_saisi_gestion_email;

    @FXML
    private Label control_saisi_gestion_name;

    @FXML
    private Label control_saisi_gestion_phone;

    @FXML
    private Label control_saisi_gestion_pw;

    UserServiceCRUD usc = new UserServiceCRUD();
    private ObservableList<User> UsersList;
    private Connection cnx;
    public String imagePath = "Image non sélectionnée";
    private User userSelected;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String str1 = MainFx.Userconnected.getUser_image();
        /*Image image = null;
        if(str1.equals("Image non sélectionnée"))
            if(MainFx.Userconnected.getUser_sexe().equals("Homme"))
                image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Integration/src/main/resources/images/AdminHomme.png", 90.0, 86.0, false, true);
            else
                image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Integration/src/main/resources/images/AdminFemme.png", 90.0, 86.0, false, true);
        else
            image = new Image(str1, 90.0, 86.0, false, true);
        imageview_MenuBack.setImage(image);*/

        String str = MainFx.Userconnected.getUser_name();
        label_username.setText(str);

        ObservableList<String> list_sexe = FXCollections.observableArrayList("Homme","Femme");
        combobox_sexe.setItems(list_sexe);
        combobox_sexe_gestion.setItems(list_sexe);

        this.textfield_name.setText(MainFx.Userconnected.getUser_name());
        this.textfield_phone.setText(MainFx.Userconnected.getUser_phone());
        this.textfield_email.setText(MainFx.Userconnected.getUser_email());
        this.combobox_sexe.setValue(MainFx.Userconnected.getUser_sexe());
        //this.imageview_updateuser.setImage(image);

        try {
            this.UsersShowListData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void close(ActionEvent event) {System.exit(0);}

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)this.main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void logout(ActionEvent event) {
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
    void switchForm(ActionEvent event) throws SQLException {
        if (event.getSource() == this.btn_home) {
            this.home_form.setVisible(true);
            this.profil_form.setVisible(false);
            this.gestion_users_form.setVisible(false);
            this.btn_home.setStyle("-fx-background-color:linear-gradient(to bottom right, #9b7a56, #593a00);");
            this.btn_profil.setStyle("-fx-background-color:transparent");
            this.btn_gestion_users.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == this.btn_profil) {
            this.home_form.setVisible(false);
            this.profil_form.setVisible(true);
            this.gestion_users_form.setVisible(false);
            this.btn_home.setStyle("-fx-background-color:transparent");
            this.btn_profil.setStyle("-fx-background-color:linear-gradient(to bottom right, #9b7a56, #593a00);");
            this.btn_gestion_users.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == this.btn_gestion_users) {
            this.home_form.setVisible(false);
            this.profil_form.setVisible(false);
            this.gestion_users_form.setVisible(true);
            this.btn_home.setStyle("-fx-background-color:transparent");
            this.btn_profil.setStyle("-fx-background-color:transparent");
            this.btn_gestion_users.setStyle("-fx-background-color:linear-gradient(to bottom right, #9b7a56, #593a00);");
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
            // Check if there are other admins in the database
            if (usc.countAdmins() == 1) {
                // If the user is the only admin, prevent deletion and show a warning
                Alert adminAlert = new Alert(Alert.AlertType.WARNING);
                adminAlert.setTitle("Suppression du compte");
                adminAlert.setContentText("Vous êtes le seul administrateur dans la base de données. Vous ne pouvez pas supprimer votre compte.");
                adminAlert.showAndWait();
                return; // Exit the method
            }
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
        User admin1 = new User();
        admin1.setUser_name(this.textfield_name.getText());
        admin1.setUser_phone(this.textfield_phone.getText());
        admin1.setUser_email(this.textfield_email.getText());
        admin1.setUser_sexe(this.combobox_sexe.getValue());
        admin1.setUser_image(imagePath);

        if (usc.UpdateUser(admin1,MainFx.Userconnected.getUser_id()))
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
                    image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/AdminHomme.png", 90.0, 86.0, false, true);
                else
                    image = new Image("D:/Study Documents/3A13/Semestre 2/PIDEV/JAVA/Gestion_des_utilisateurs/src/main/resources/images/AdminFemme.png", 90.0, 86.0, false, true);
            else
                image = new Image(str1, 90.0, 86.0, false, true);
            imageview_MenuBack.setImage(image);

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

    public void UsersShowListData() throws SQLException {
        this.UsersList = usc.UsersListData();
        this.col_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        this.col_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        this.col_phone.setCellValueFactory(new PropertyValueFactory<>("user_phone"));
        this.col_email.setCellValueFactory(new PropertyValueFactory<>("user_email"));
        this.col_password.setCellValueFactory(new PropertyValueFactory<>("user_password"));
        this.col_sexe.setCellValueFactory(new PropertyValueFactory<>("user_sexe"));
        this.col_image.setCellValueFactory(new PropertyValueFactory<>("user_image"));
        this.users_tableview.setItems(this.UsersList);
    }

    public void SelectUser(MouseEvent mouseEvent)
    {
        int num = this.users_tableview.getSelectionModel().getSelectedIndex();
        if (num >= 0)
        {
            userSelected = this.users_tableview.getSelectionModel().getSelectedItem();
            this.textfield_name_gestion.setText(userSelected.getUser_name());
            this.textfield_phone_gestion.setText(userSelected.getUser_phone());
            this.textfield_email_gestion.setText(userSelected.getUser_email());
            this.textfield_password_gestion.setText(userSelected.getUser_password());
            this.combobox_sexe_gestion.setValue(userSelected.getUser_sexe());
            Image image = new Image(userSelected.getUser_image(), 120.0, 149.0, false, true);
            this.imageview_user.setImage(image);
        }
        else
            System.out.println("No row is selected !!");
    }

    @FXML
    void search_user(ActionEvent event) throws SQLException
    {
        String searchText = textfield_search.getText(); // Assuming you have a TextField named textFieldSearch for user input
        ObservableList<User> filteredUsers = FXCollections.observableArrayList();

        if(searchText.isEmpty())
        {
            UsersShowListData();
            return;
        }

        // Loop through your existing data (assuming you have a list of users displayed in a TableView)
        for (User user : users_tableview.getItems())
        {
            if (user.getUser_name().toLowerCase().contains(searchText.toLowerCase()))
            {
                // If the user's name contains the search text, add it to the filtered list
                filteredUsers.add(user);
            }
        }

        // Clear the TableView and add the filtered list
        users_tableview.getItems().clear();
        users_tableview.setItems(filteredUsers);
    }

    void clear_textfield()
    {
        this.textfield_pw_actuel.setText("");
        this.passwordfield_pw_nouvel.setText("");
        this.passwordfield_pw_nouvel_confirm.setText("");

        this.textfield_name_gestion.setText("");
        this.textfield_phone_gestion.setText("");
        this.textfield_email_gestion.setText("");
        this.textfield_password_gestion.setText("");
        this.combobox_sexe_gestion.getSelectionModel().clearSelection();
        this.imageview_user.setImage((Image)null);
    }

    @FXML
    void clear_textfield(ActionEvent event) {clear_textfield();}

    @FXML
    void delete_user(ActionEvent event) throws SQLException
    {
        String name = this.textfield_name_gestion.getText();
        if(name.isEmpty())
        {
            control_saisi_gestion_name.setText("Champ Name est VIDE");
            control_saisi_gestion_name.setVisible(true);
        }
        else
            control_saisi_gestion_name.setVisible(false);

        String phone = this.textfield_phone_gestion.getText();
        if(phone.isEmpty())
        {
            control_saisi_gestion_phone.setText("Champ Phone est VIDE");
            control_saisi_gestion_phone.setVisible(true);
        }
        else
            control_saisi_gestion_phone.setVisible(false);

        String email = this.textfield_email_gestion.getText();
        if(email.isEmpty())
        {
            control_saisi_gestion_email.setText("Champ Email est VIDE");
            control_saisi_gestion_email.setVisible(true);
        }
        else
            control_saisi_gestion_email.setVisible(false);

        String pw = this.textfield_password_gestion.getText();
        if(pw.isEmpty())
        {
            control_saisi_gestion_pw.setText("Champ Mot De Passe est VIDE");
            control_saisi_gestion_pw.setVisible(true);
        }
        else
            control_saisi_gestion_pw.setVisible(false);

        Alert alert = new Alert(Alert.AlertType.WARNING);
        System.out.println((userSelected));
        alert.setTitle("Suppression du compte");
        alert.setContentText("Voulez-vous vraiment supprimer Cette compte ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            if (usc.DeleteUser(userSelected))
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Suppression du compte avec success", new ButtonType[]{ButtonType.CLOSE});
                alert.show();

                UsersShowListData();
                clear_textfield();
            }
            else
            {
                alert = new Alert(Alert.AlertType.ERROR, "Suppresion failed", ButtonType.CLOSE);
                alert.show();
            }
        }
    }

    @FXML
    void add_user(ActionEvent event) throws SQLException {
        String name = this.textfield_name_gestion.getText();
        if(name.isEmpty())
        {
            control_saisi_gestion_name.setText("Champ Name est VIDE");
            control_saisi_gestion_name.setVisible(true);
        }
        else
            control_saisi_gestion_name.setVisible(false);

        String phone = this.textfield_phone_gestion.getText();
        if(phone.isEmpty())
        {
            control_saisi_gestion_phone.setText("Champ Phone est VIDE");
            control_saisi_gestion_phone.setVisible(true);
        }
        else
            control_saisi_gestion_phone.setVisible(false);

        String email = this.textfield_email_gestion.getText();
        if(email.isEmpty())
        {
            control_saisi_gestion_email.setText("Champ Email est VIDE");
            control_saisi_gestion_email.setVisible(true);
        }
        else
            control_saisi_gestion_email.setVisible(false);

        String pw = this.textfield_password_gestion.getText();
        if(pw.isEmpty())
        {
            control_saisi_gestion_pw.setText("Champ Mot De Passe est VIDE");
            control_saisi_gestion_pw.setVisible(true);
        }
        else
            control_saisi_gestion_pw.setVisible(false);


        Alert alert;
        User user1 = new User();
        user1.setUser_name(this.textfield_name_gestion.getText());
        user1.setUser_phone(this.textfield_phone_gestion.getText());
        user1.setUser_email(this.textfield_email_gestion.getText());
        user1.setUser_password(this.textfield_password_gestion.getText());
        user1.setUser_sexe(this.combobox_sexe_gestion.getValue());
        user1.setUser_role("Client");
        user1.setUser_image(imagePath);

        if(usc.getUserByEmail(this.textfield_email_gestion.getText())==1)
        {
            alert = new Alert(Alert.AlertType.ERROR, "User existe deja", new ButtonType[]{ButtonType.CLOSE});
            alert.show();
        }
        else
        {
            try {
                this.usc.AddUser(user1);
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Bienvenue Mr/Mme :\n" + this.textfield_name.getText(), new ButtonType[]{ButtonType.CLOSE});
                alert.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            UsersShowListData();
            clear_textfield();
        }
    }

    @FXML
    void update_user(ActionEvent event) throws SQLException
    {
        String name = this.textfield_name_gestion.getText();
        if(name.isEmpty())
        {
            control_saisi_gestion_name.setText("Champ Name est VIDE");
            control_saisi_gestion_name.setVisible(true);
        }
        else
            control_saisi_gestion_name.setVisible(false);

        String phone = this.textfield_phone_gestion.getText();
        if(phone.isEmpty())
        {
            control_saisi_gestion_phone.setText("Champ Phone est VIDE");
            control_saisi_gestion_phone.setVisible(true);
        }
        else
            control_saisi_gestion_phone.setVisible(false);

        String email = this.textfield_email_gestion.getText();
        if(email.isEmpty())
        {
            control_saisi_gestion_email.setText("Champ Email est VIDE");
            control_saisi_gestion_email.setVisible(true);
        }
        else
            control_saisi_gestion_email.setVisible(false);

        String pw = this.textfield_password_gestion.getText();
        if(pw.isEmpty())
        {
            control_saisi_gestion_pw.setText("Champ Mot De Passe est VIDE");
            control_saisi_gestion_pw.setVisible(true);
        }
        else
            control_saisi_gestion_pw.setVisible(false);


        User user1 = new User();
        user1.setUser_name(this.textfield_name_gestion.getText());
        user1.setUser_phone(this.textfield_phone_gestion.getText());
        user1.setUser_email(this.textfield_email_gestion.getText());
        user1.setUser_sexe(this.combobox_sexe_gestion.getValue());
        user1.setUser_image(imagePath);

        if (usc.UpdateUser(user1, userSelected.getUser_id()))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme : \n"+ textfield_name.getText()+ " "+ ", Vos donnés personelles sont modifiés !", ButtonType.CLOSE);
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, " Il ya un petit probleme au niveau de update profil, ressayer plus tard !", ButtonType.CLOSE);
            alert.show();
        }


        if (usc.UpdatePW(this.textfield_password_gestion.getText(), userSelected.getUser_id())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme : \n" + MainFx.Userconnected.getUser_name() + ", Votre mot de passe a été bien modifier !", ButtonType.CLOSE);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, " Il ya un petit probleme  au niveau de update PW, ressayer plus tard !", ButtonType.CLOSE);
            alert.show();
        }

        UsersShowListData();
        clear_textfield();
    }

    @FXML
    void inserer_image(ActionEvent event) throws FileNotFoundException, IOException
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
            this.imageview_user.setImage(imageUser);
            this.imagePath = imagePath;
            System.out.println("Image Path: " + imagePath);
        } else if (f == null) {
            System.out.println("Erreur, image non selectionnée !!!");
        }
    }

    @FXML
    void exporter_excel (ActionEvent event)
    {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();

        // Créer une feuille dans le classeur Excel
        Sheet sheet = workbook.createSheet("Utilisateurs");

        // Créer la première ligne contenant les en-têtes de colonne
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < users_tableview.getColumns().size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(users_tableview.getColumns().get(i).getText());
        }

        // Remplir les données à partir du TableView
        for (int row = 0; row < users_tableview.getItems().size(); row++) {
            Row excelRow = sheet.createRow(row + 1);
            for (int col = 0; col < users_tableview.getColumns().size(); col++) {
                TableColumn<User, ?> column = users_tableview.getColumns().get(col);
                Cell cell = excelRow.createCell(col);
                cell.setCellValue(column.getCellData(row).toString());
            }
        }

        // Obtenir la date et l'heure actuelles
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dateTime = now.format(formatter);

        // Enregistrer le classeur Excel
        try {
            FileOutputStream fileOut = new FileOutputStream("Utilisateurs " + dateTime + " .xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Excel");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été exportées avec succès vers le fichier Utilisateurs.xlsx");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'exportation des données vers Excel.");
            alert.showAndWait();
        }
    }
    ////////////////////////////










    @FXML
    private Button btngestionmusee;

    @FXML
    private Button btngestionreservation;
    @FXML
    void onGestionReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationBackFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Gestion reservations");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    void onGestionmusee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonneFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Gestion musées");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));

    }

    @FXML
    private Button btn_gestion_evenement;

    @FXML
    void evenementAdmin (ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/AfficherEvenements.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Gestion évenements");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_gestion_commande;

    @FXML
    void commande (ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/backviews/back.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();

        stage.setTitle("Gestion Commande");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_gestion_oeuvre;

    @FXML
    void oeuvre (ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/views/OeuvreBackFXML.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Gestion Oeuvre");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_gestion_categorie;

    @FXML
    void categorie (ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/views/CategorieBackFXML.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Gestion Catégorie");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_gestion_livraison;

    @FXML
    void livraison (ActionEvent event) throws IOException
    {
        FXMLLoader LOADER = new FXMLLoader(this.getClass().getResource("/views/livraison.fxml"));
        Parent parent = LOADER.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Gestion Livraison");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    private Button btn_gestion_livreur;

    @FXML
    void livreur (ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/livreurFXML.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Ajouter une personne ");
        stage.setScene(scene);

        stage.show();
    }
}
