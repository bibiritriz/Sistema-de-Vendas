/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.service;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author isaqu
 */
public class EmailService {
    private final Properties props = new Properties();
    private final String fromEmail;
    private final String password;
        
    public EmailService() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("resource/config.properties")) {
            props.load(input);
            this.fromEmail = props.getProperty("mail.smtp.user");
            this.password = props.getProperty("mail.smtp.password");
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar o config.properties", e);
        }
    }
    
    public void sendOtpEmail(String to, String otp) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Seu Código de Verificação");
            message.setText("Olá,\n\nSeu código de verificação é: " + otp + "\n\nEste código é válido por 10 minutos.");

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
    
    public void sendEmailWithAttachment(String to, String subject, String filePath) {
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // 4. Criar a segunda parte: o anexo PDF
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            // Define o nome que o arquivo terá no e-mail
            attachmentPart.setFileName(new File(filePath).getName()); 

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Segue o relatório solicitado");

        
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("E-mail com anexo enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail com anexo", e);
        }
    }
    

}
