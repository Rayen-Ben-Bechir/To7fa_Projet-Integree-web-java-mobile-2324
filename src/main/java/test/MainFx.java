package test;

import controllers.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;

import java.io.IOException;

public class MainFx extends Application
{
    public static User Userconnected = new User();

    public static void main(String[] args) {launch();}

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent parent = loader.load();
        Login login = loader.getController();
        Scene scene = new Scene(parent);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);

        login.setStage(stage);
        stage.show();

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent parent = loader.load();
        SignUp signup = loader.getController();
        Scene scene = new Scene(parent);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);

        signup.setStage(stage);
        stage.show();*/


        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuFront.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();*/
    }
}
