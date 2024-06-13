package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UserServiceCRUD;
import test.MainFx;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class MotDePasseOublie {

    @FXML
    private Button Btn_modifierPW_MotDePasseOublie;

    @FXML
    private TextField textfield_code_MotDePasseOublie;

    @FXML
    private PasswordField passwordfield_confirm_nouvelPW_MotDePasseOublie;

    @FXML
    private PasswordField passwordfield_nouvelPW_MotDePasseOublie;
    String code;
    String email;
    Stage stage;
    public void setCode(String code){this.code=code;}
    public void setEmail(String email){this.email=email;}
    public void setStage(Stage stage){this.stage=stage;}
    UserServiceCRUD usc = new UserServiceCRUD();

    public void ModifierPW(javafx.event.ActionEvent actionEvent) throws SQLException
    {
        if(this.passwordfield_nouvelPW_MotDePasseOublie.getText().equals(this.passwordfield_confirm_nouvelPW_MotDePasseOublie.getText()) && textfield_code_MotDePasseOublie.getText().equals(String.valueOf(code)))
        {
            if (usc.UpdatePWoublie(passwordfield_nouvelPW_MotDePasseOublie.getText(),email)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mr/Mme : \nVotre mot de passe a été bien modifier !", ButtonType.CLOSE);
                alert.show();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Il ya un petit probleme  au niveau de update PW oublie, ressayer plus tard !", ButtonType.CLOSE);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, " L'une des mot de passes ou le code ne correspand pas, merci de ressayer !", ButtonType.CLOSE);
            alert.show();
        }
    }
}
