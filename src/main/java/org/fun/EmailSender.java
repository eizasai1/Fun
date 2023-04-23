package org.fun;


import java.util.Properties;
import javax.activation.*;
import javax.mail.internet.*;
import javax.mail.*;
import javax.sql.DataSource;


public class EmailSender {
    private String to = "eizakuasai@gmail.com";
    private final String user = "fungame.javadeveloper@gmail.com";
    private final String pass = "qwhzizeksicuxcyvuwobnwojwudwtyxivo";
    public void send(String filename) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(user, pass);
            }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Message alert");
            BodyPart messageBody = new MimeBodyPart();
            DataSource source = (DataSource) new FileDataSource(filename);
            messageBody.setDataHandler(new DataHandler((javax.activation.DataSource) source));
            messageBody.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBody);
            message.setContent(multipart);
            Transport.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("email sent");
    }
}
