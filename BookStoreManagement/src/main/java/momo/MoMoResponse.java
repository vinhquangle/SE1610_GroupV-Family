/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package momo;

import static momo.MoMoPayment.ACCESS_KEY;
import static momo.MoMoPayment.SECRET_KEY;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 */
//Quốc Thịnh >>>>>>>>>>
public class MoMoResponse extends HttpServlet {

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return sb.toString();
    }

    public static String signHmacSHA256(String data, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return toHexString(rawHmac);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String partnerCode = request.getParameter("partnerCode");
            String orderId = request.getParameter("orderId");
            String requestId = request.getParameter("requestId");
            String amount = request.getParameter("amount");
            String orderInfo = request.getParameter("orderInfo");
            String extraData = request.getParameter("extraData");
            String signature = request.getParameter("signature");
            String resultCode = request.getParameter("resultCode");
            String message = request.getParameter("message");
            String orderType = request.getParameter("orderType");
            String payType = request.getParameter("payType");
            String responseTime = request.getParameter("responseTime");
            String transId = request.getParameter("transId");

            String requestRawData = new StringBuilder()
                    .append("accessKey").append("=").append(ACCESS_KEY).append("&")
                    .append("amount").append("=").append(amount).append("&")
                    .append("extraData").append("=").append(extraData).append("&")
                    .append("message").append("=").append(message).append("&")
                    .append("orderId").append("=").append(orderId).append("&")
                    .append("orderInfo").append("=").append(orderInfo).append("&")
                    .append("orderType").append("=").append(orderType).append("&")
                    .append("partnerCode").append("=").append(partnerCode).append("&")
                    .append("payType").append("=").append(payType).append("&")
                    .append("requestId").append("=").append(requestId).append("&")
                    .append("responseTime").append("=").append(responseTime).append("&")
                    .append("resultCode").append("=").append(resultCode).append("&")
                    .append("transId").append("=").append(transId)
                    .toString();

            String signRequest = signHmacSHA256(requestRawData, SECRET_KEY);

            if (!signRequest.equals(signature)) {
                request.setAttribute("WRONG", "Thông tin không hợp lệ");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }
            if (!resultCode.equals("0")) {
                request.setAttribute("FAIL", "Thanh toán thất bại");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                request.setAttribute("SUCCESS", "Thanh toán thành công");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }
        }catch(Exception e){
            
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


}
//<<<<<<<<<<