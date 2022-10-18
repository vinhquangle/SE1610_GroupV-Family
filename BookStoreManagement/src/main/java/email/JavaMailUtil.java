/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import aes.MyAES;
import dao.CustomerDAO;
import dto.CustomerDTO;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
//Quốc Thịnh >>>>>>>>>>
public class JavaMailUtil {

    public static void sendMail(String recepient, int codeVerify, String action) throws Exception {
        //Thiết đặt API
        System.out.println("Preparing to send mail");
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        p.setProperty("mail.smtp.allow8bitmime", "true");
        p.setProperty("mail.smtps.allow8bitmime", "true");
        //Thiết đặt API
        final String myAccountEmail = "thinhpqse162011@fpt.edu.vn";//Tài khoản
        final String password = "thinh0938081927";//Mật khẩu

        Session session = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recepient, codeVerify, action);//Gửi email
        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, int codeVerify, String action) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            if (action.equals("Verify")) {
                message.setSubject("Phuong Nam Bookstore email");
                message.setContent("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                        + "</head>"
                        + "<body>"
                        + "<h1>Xác thực email</h1>"
                        + "<p>Mã xác thực email của bạn là: <b>" + String.valueOf(codeVerify) + "</b></p>"
                        + "</body>"
                        + "</html>",
                        "text/html; charset=utf-8");
            } else if (action.equals("Send")) {
                CustomerDAO cusDao = new CustomerDAO();
                CustomerDTO cus = cusDao.getCustomer(recepient);//Lấy thông tin tài khoản qua email
                MyAES myCipher = new MyAES("1", MyAES.AES_192);//Cài đặt khóa cho AES
                String AES_decryptedStr = myCipher.AES_decrypt(cus.getPassword());//Giải mã AES
                message.setSubject("Phuong Nam Bookstore email");
                message.setContent("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                        + "</head>"
                        + "<body>"
                        + "<h1>Thông tin tài khoản</h1>"
                        + "<p>Email: <b>" + cus.getEmail() + "</b></p>"
                        + "<p>Tài khoản: <b style=\"color: #fe4c50;\">" + cus.getCustomerID() + "</b></p>"
                        + "<p>Mật khẩu: <b style=\"color: #fe4c50;\">" + AES_decryptedStr + "</b></p>"
                        + "</body>"
                        + "</html>",
                        "text/html; charset=utf-8");
            }
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
//<<<<<<<<<<
