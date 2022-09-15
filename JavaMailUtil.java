/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaildemo;

import java.sql.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.user.OrderDetailDTO;
import sun.util.calendar.CalendarDate;

/**
 *
 * @author Admin
 */
public class JavaMailUtil {

    public static void sendMail(String recepient, String ncustomer, List<OrderDetailDTO> list) throws Exception {
        String customer = ncustomer;
        System.out.println("Preparing to send mail");
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        String myAccountEmail = "thinhpqse162011@fpt.edu.vn";
        String password = "thinh0938081927";

        Session session = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(list, session, myAccountEmail, recepient, customer);
        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    private static Message prepareMessage(List<OrderDetailDTO> list, Session session, String myAccountEmail, String recepient, String customer) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Thanks for your purchasing");
            String listDetail = "";
            double total=0;
            for (OrderDetailDTO detail : list) {
                listDetail = listDetail.concat(detail.getProductID());
                listDetail = listDetail.concat("\t"+"\t");
                listDetail = listDetail.concat(detail.getName());
                listDetail = listDetail.concat("\t"+"=>"+"\t");
                listDetail = listDetail.concat(String.valueOf(detail.getPrice()));
                listDetail = listDetail.concat("\t"+"\t"+"x"+"\t");
                listDetail = listDetail.concat(String.valueOf(detail.getQuantity()));
                listDetail = listDetail.concat("\t"+"\t"+"="+"\t");
                listDetail = listDetail.concat(String.valueOf(detail.getTotal()));
                listDetail = listDetail.concat("\n");
                total += detail.getTotal();
            }
            message.setText("Dear " + customer + "\nThank you for giving us your trust! \n"
                    + "\n"
                    + "We have just confirmed you received your order have orderID: "+list.get(0).getOrderID()+", and hope you are enjoying. Every item is handmade by our team, with care to the details, so we can always provide you with the best experience.\n"
                    + "\n"
                    +listDetail
                    +"\n"
                    +"\t => Total = "+total
                    +"\n"
                    +"\n"
                    + "Please, let us know what you found about your order. We value your opinion to improve continuously. \n"
                    + "\n"
                    + "Take care,"
                    + "\n"
                    + "ShoudTube");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
