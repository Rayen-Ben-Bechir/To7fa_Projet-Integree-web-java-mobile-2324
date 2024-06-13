package services;

//import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    public void mail(String recipient) throws Exception
    {
        Properties prop = new Properties();
        final String moncompteEmail = "rayen.benbechir@esprit.tn";
        final String psw = "211JMT6970rbb";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        // After creating the Properties object
        prop.put("mail.smtp.ssl.trust", "*");


        Session ses = Session.getInstance(prop, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
        });

        try {
            Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(moncompteEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("information");
            msg.setText("salut");

            Transport.send(msg);

            System.out.println("message envoyée");

        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error sending message: " + ex.getMessage());
        }

    }

    /*public void mailingValider(String recipient, int nombre) throws Exception
    {
        Properties prop = new Properties();
        final String moncompteEmail = "rayenbenbechir8@gmail.com";
        final String psw = "52m2a5r3t7";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session ses = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
        });

        try {
            Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(moncompteEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("Code de confirmation");
            msg.setText("Merci pour votre Interet a Kiftrip , voici votre code de confirmation:   "+String.valueOf(nombre));

            Transport.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    public static void main(String[] args) throws Exception {
        Mail mail = new Mail();
        mail.mail("rayenbenbechir.7@gmail.com");
    }

}
