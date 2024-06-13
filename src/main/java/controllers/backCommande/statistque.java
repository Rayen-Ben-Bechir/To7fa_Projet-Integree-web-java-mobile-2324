package controllers.backCommande;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import utils.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class statistque implements Initializable {
    private Connection cnx;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private AnchorPane main_form;
    public void statique() {
        String chartsql = "SELECT date_commande, SUM(prix_commande) FROM commande GROUP BY date_commande ORDER BY TIMESTAMP(date_commande) ASC LIMIT 8";
        cnx = DBConnection.getInstance().getCnx();
        try {
            XYChart.Series<String, Integer> chartData = new XYChart.Series<>();
            PreparedStatement prepare = cnx.prepareStatement(chartsql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                chartData.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
                System.out.println("kk");
            }
            System.out.println("kk");

            barChart.getData().add(chartData);
            System.out.println("bb");
        } catch (SQLException e) {
            // Log the SQL exception and handle it gracefully
            e.printStackTrace(); // This will print the exception's stack trace
            // You might want to log the exception to a logger instead
            // Handle the exception gracefully, e.g., show an error message to the user
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
  statique();
    }
}
