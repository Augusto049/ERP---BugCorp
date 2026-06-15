package model;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.MessagingException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarEmail {

    private static final String REMETENTE = "bugcorpnoreply@gmail.com";
    private static final String SENHA = "nwye wboz szvl vjye";

    public static void enviarNovaSenha(String email, String novaSenha) {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
            props,

            new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(REMETENTE, SENHA);
                }
            }
        );

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(REMETENTE));

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            );

            message.setSubject("Redefinição de Senha");

            message.setText(
                "Sua nova senha é: " + novaSenha
            );

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {

            e.printStackTrace();
        }
    }
}