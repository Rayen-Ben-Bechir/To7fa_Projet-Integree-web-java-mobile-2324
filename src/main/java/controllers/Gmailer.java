package controllers;
import models.Oeuvre;
import services.ServiceOeuvre;
import services.servicePanier;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmailer {

    List<Oeuvre> ouevs = new ArrayList<>();
    private List<Oeuvre> getData() {
        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

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


    public  void send(String toEmail , String totale ) {
        String from = "abderrahmen.ammar@esprit.tn"; // Change this to your sender email address
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abderrahmen.ammar@esprit.tn", "54855181Ammar"); // Change this to your sender email password
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(from, "To7fa", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Successful Order");

            StringBuilder messageText = new StringBuilder("Welcome to Tohfa! Your orders :\n\n");
            ouevs.addAll(getData());
            for (Oeuvre item : ouevs) {
                messageText.append("- ").append(item.getTitre()).append(" , Price: $ ").append(String.format("%.2f", item.getPrix())).append("\n");
            }

            messageText.append("\nTotal Price: ").append(totale);


            message.setText(messageText.toString());

            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
