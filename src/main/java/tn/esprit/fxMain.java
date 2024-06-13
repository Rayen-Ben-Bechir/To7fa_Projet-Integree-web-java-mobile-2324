package tn.esprit;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.panierat;
import services.servicePanier;
import utils.DBConnection;

import java.sql.SQLException;


public class fxMain extends Application {
    public  static  final String CURRENCY="$";
    public  static  final String Article="Article ID : ";
    double x,y = 0;
    public static void main(String[] args) throws StripeException {


        Stripe.apiKey = "sk_test_51Opt80JijSe6dticBpPkJCUoAsrV09Rq7cHJgZGmAayxatS497Dy3Xbih1jjncVNiuvxK990KJPAep2AXzMAEGMo00vwQBrjQY";
      /*  Map<String,Object> custumer =new HashMap<String,Object>();
        custumer.put("email","c@gmail.com");
        Customer newCustmer =Customer.create(custumer);
        System.out.println(newCustmer.getId());*/
        /*Customer a=Customer.retrieve("cus_Pffqnu5e6pRJFE");
        Map<String,Object> cardparm =new HashMap<String,Object>();
        cardparm.put("number","424242424242");
        cardparm.put("exp_month","11");
        cardparm.put("exp_year","2022");
        cardparm.put("cvc","123");
        Map<String,Object> tokenparm =new HashMap<String,Object>();
        tokenparm.put("card",cardparm);
        Token token=Token.create(tokenparm);
        Map<String,Object> source =new HashMap<String,Object>();
        source.put("source",token.getId());
        a.getSources().create(source);



        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(a));*/






        DBConnection cn1 = DBConnection.getInstance();



        servicePanier sp = new servicePanier();


        try {
            // Call the selectAll method to get all basket items

            // Display basket items
            for (panierat item : sp.selectAll()) {
                System.out.println("ID: " + item.getId_panier());

                System.out.println("User ID: " + item.getId_user());
                System.out.println("Art Piece ID: " + item.getId_oeuvre());
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
       Parent parent = loader.load();
        File css = new File("style.css");
        Scene scene = new Scene(parent);
        stage.setTitle("ajouter une commande");
        stage.setScene(scene);
        stage.show();*/
    Parent root = FXMLLoader.load(getClass().getResource("/views/Acceuil.fxml"));
   /*  Parent root = FXMLLoader.load(getClass().getResource("/backviews/back.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("panier");
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });*/


        stage.setScene(new Scene(root));
      stage.show();
        ////////////////////////////////////////////////////////////////////////////////////////













    }
}
