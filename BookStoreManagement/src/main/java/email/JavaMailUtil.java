/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import aes.MyAES;
import cart.Cart;
import dao.CustomerDAO;
import dto.BookDTO;
import dto.CustomerDTO;
import java.text.NumberFormat;
import java.util.Locale;
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

    public static void sendMail(String recepient, int codeVerify, String action, Cart cart, int orderID, double feeShip, double discount) throws Exception {
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
        Message message = prepareMessage(session, myAccountEmail, recepient, codeVerify, action, cart, orderID, feeShip, discount);//Gửi email
        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, int codeVerify, String action, Cart cart, int orderID, double feeShip, double discount) {
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
            } else if (action.equals("Checkout")) {
                double totalS = 0;
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                CustomerDAO cusDao = new CustomerDAO();
                CustomerDTO cus = cusDao.getCustomer(recepient);//Lấy thông tin tài khoản qua email
                String mess1 = "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h1>Cảm ơn anh/chị <strong style=\"color: #d10024\">" + cus.getName() + "</strong> đã tin tưởng mua hàng tại nhà sách Phương Nam. Đơn hàng của quý khách đang được chuẩn bị.</h1>\n"
                        + "        <div class=\"container\">\n"
                        + "            <h1 style=\"text-align: center;\">Chi tiết đơn hàng</h1>\n"
                        + "            <h3 style=\"text-align: center;\">Mã đơn hàng: " + orderID + "</h3>\n"
                        + "            <div class=\"row\">\n"
                        + "                <table class=\"row\">\n"
                        + "                    <tr style=\"background-color: black; color: white; font-weight: bold; border: 2px solid black\" class=\"row col-md-12\">\n"
                        + "                        <th>Hình ảnh</th>\n"
                        + "                        <th>Tiêu đề</th>\n"
                        + "                        <th>Số lượng</th>\n"
                        + "                        <th>Tổng</th>\n"
                        + "                    </tr>\n";
                String mess2 = "";
                String mess4 = "";
                for (BookDTO book : cart.getCart().values()) {
                    mess2 += "                    <tr style=\"border: 2px solid black\" class=\"row col-md-12\">\n"
                            + "                        <th class=\"col-md-2\"><img style=\"width: 200px\" src=\"" + book.getImg() + "\"></th>\n"
                            + "                        <th class=\"col-md-5\"><p style=\"width: 800px\">" + book.getName() + "</p></th>\n"
                            + "                        <th class=\"col-md-3\"><p style=\"width: 150px\">" + book.getQuantity() + "</p></th>\n"
                            + "                        <th class=\"col-md-2\"><p style=\"width: 250px\">" + currencyVN.format(book.getQuantity() * book.getPrice()) + "</p></th>\n"
                            + "                    </tr>\n";
                    totalS += book.getQuantity() * book.getPrice();
                }
                String mess3 = "        </table>\n";
                if (totalS == (totalS * (1 - discount) + feeShip)) {
                    mess4 = "<h1 style=\"float: right\">Tổng thành tiền: <strong style=\"color: #d10024; \">"+ currencyVN.format(totalS) +"</strong></h1>";
                }else{
                   mess4 = "<h1 style=\"float: right\">Tổng thành tiền: <strong style=\"color: #d10024; text-decoration: line-through;\">"+ currencyVN.format(totalS) +"</strong>    <strong style=\"color: #d10024\">"+ currencyVN.format(totalS * (1 - discount) + feeShip) +"</strong></h1>";
                }
                String mess5 = "            </div>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html>";
                message.setSubject("Phuong Nam Bookstore email");
                message.setContent(mess1 + mess2 + mess3 + mess4 + mess5,
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
