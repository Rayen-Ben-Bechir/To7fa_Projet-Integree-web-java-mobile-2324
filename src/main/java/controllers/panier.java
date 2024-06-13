package controllers;


import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import models.Oeuvre;
import models.commande;
import services.ServiceOeuvre;
import tn.esprit.fxMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.servicePanier;
import tn.esprit.MyListener;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class panier extends Ajoutercommande implements Initializable {


    @FXML
    private ImageView artImgLab;
    @FXML
    private ChoiceBox<Integer> choice_piece;

    @FXML
    private Label artNameLab;

    @FXML
    private Label artPrixLab;

    @FXML
    private VBox chosenartcard;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button handScenetwo;
    @FXML
    private AnchorPane scene1;
    @FXML
    private Label artNameLab11;

    @FXML
    private Button gotoAdd;
    @FXML
    private Button reloadbtn;
    @FXML
    private TextField searchbtn;
    private commande com;
    public static int qunt;

    @FXML
    private Button triebtn;

    @FXML
    private Button acceuilbtn;

    @FXML
    void acceuil(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/views/Acceuil.fxml"));
        Stage window = (Stage) handScenetwo.getScene().getWindow();
        window.setScene(new Scene(root, 1315, 800));
    }


    @FXML
    void Tri(ActionEvent event) {
        // Clear existing items from the grid
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();

        ouevs.addAll(getDatatri());
        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));



                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                itemController itemController = fxmlLoader.getController();

                itemController.setData(ouevs.get(i), myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    void switchToScene2(ActionEvent event)throws IOException {
        ouevs.clear();
        ouevs.addAll(getData());
        if(ouevs.size()==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Basket Alert");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item.");
            alert.showAndWait();

        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutercommande.fxml"));
            Stage window = (Stage) handScenetwo.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticName.setText(artNameLab.getText());
            StaticPrice.setText(artPrixLab.getText());
            StaticTotal.setText(artPrixLab.getText());
            artPrixLab.setText("");
            //  StaticimgVeri.setImage(image);

        }

    }

    private MyListener myListener;
    @FXML
    private HBox hboxx;
    private List<Oeuvre> ouevs = new ArrayList<>();
    private Image image;

    private Oeuvre chosenOeuvre;
    // private MyListener myListener;

    @FXML
    void default2(ActionEvent event) {
        refreshUI();

    }

    private List<Oeuvre> getDatatri() {

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.trie()) {

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

    private List<Oeuvre> getData() {

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAllOeuvre()) {

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
    private List<Oeuvre> getDataselected() {

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {

            for (Oeuvre item : sp.selectByname(searchbtn.getText())) {

                ouev = new Oeuvre();
                System.out.println("titre"+item.getTitre());
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

    private void setChosenoeuvre(Oeuvre ouev) {
        artNameLab.setText(ouev.getTitre());
        artPrixLab.setText(fxMain.CURRENCY + ouev.getPrix());

        // Assuming ouev.getLienImg() returns the URL or file path of the image
        String imageUrl = ouev.getLienImg();
        image = new Image(imageUrl);
        artImgLab.setImage(image);

        chosenartcard.setStyle("-fx-background-color: white;\n" +
                "-fx-background-radius: 30;");
    }
    public void setChosenOeuvre(Oeuvre chosenOeuvre) {
        this.chosenOeuvre = chosenOeuvre;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();
        qunt=1;
        for (int i = 1; i <= 20; i++) {
            choice_piece.getItems().add(i);
        }
        System.out.println(choice_piece.getSelectionModel().selectedItemProperty());
        List<String> possibleWordsList = new ArrayList<>();
        String[] words={"aa","nn","b"};
        ouevs.addAll(getData());


        for (Oeuvre item : ouevs) {

            possibleWordsList.add(item.getTitre());
        }


           // TextFields.bindAutoCompletion(searchbtn,words);



        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));


                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                itemController itemController = fxmlLoader.getController();

                itemController.setData(ouevs.get(i), myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshUI() {
        // Clear existing items from the grid
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();

        ouevs.addAll(getData());
        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));



                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                itemController itemController = fxmlLoader.getController();

                itemController.setData(ouevs.get(i), myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshUIselcted() {
        // Clear existing items from the grid
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();

        ouevs.addAll(getDataselected());
        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));



                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                itemController itemController = fxmlLoader.getController();

                itemController.setData(ouevs.get(i), myListener);


                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void cancel(ActionEvent event) {


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete?");

        // Show the alert and wait for user response
        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    // Assuming you have a reference to your database handler or DAO class
                    servicePanier dbHandler = new servicePanier();
                    dbHandler.deleteOne(4, Integer.parseInt(artNameLab11.getText()));
                    // Optionally, you can show a success message or update your UI here
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception according to your application's error handling strategy
                    // Optionally, you can show an error message to the user here
                }
            }
        });
        artNameLab11.setText("select an item !");
        artNameLab.setText("");
       Image image = new Image(getClass().getResourceAsStream("/img/e.png"));
        artPrixLab.setText("");
        artImgLab.setImage(image);

        refreshUI();
    }

    @FXML
    void goToAddCommandes(ActionEvent event) throws IOException {
      //  qunt=choice_piece.getValue();
        ouevs.clear();
        ouevs.addAll(getData());
        if(ouevs.size()==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Basket Alert");
            alert.setHeaderText(null);
            alert.setContentText("The basket is empty!\nPlease select an item from shop.");
            alert.showAndWait();

        } else
        {

            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutertoutlescommandes.fxml"));

            Stage window = (Stage) gotoAdd.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
        }
    }
    @FXML
    void search(KeyEvent event) throws SQLException {
        refreshUIselcted();

    }
    @FXML
    void search2(KeyEvent event) {
        refreshUIselcted();
    }

    @FXML
    void reload(ActionEvent event) {
       refreshUI();
    }

    }






