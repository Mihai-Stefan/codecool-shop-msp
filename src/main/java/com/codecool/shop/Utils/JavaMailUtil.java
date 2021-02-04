package com.codecool.shop.Utils;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailUtil {

    public static void sendMail(String recipient) throws MessagingException {



        System.out.println("Prepairing email");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        String myAccountGmail = "shopteamhmmm@gmail.com";
        String password = "unudoitrei";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountGmail, password);
            }
        });

        //the message to send
        Message message = prepareMessage(session, myAccountGmail, recipient);

        Transport.send(message);
        System.out.println("Message sent successfully");
        return;
    }



    private static Message prepareMessage(Session session,  String myAccountGmail, String recipient) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountGmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Successful registration to Codecool Hmmm WebShop");
            message.setText("Hy there, how are you?\n\nWe are pleased to have you among our clients.\n\nHappy shopping!\n\n\nHmmm Team ");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }



}
