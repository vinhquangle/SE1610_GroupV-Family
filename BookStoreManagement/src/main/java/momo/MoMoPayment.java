
package momo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 *
 */
//Quốc Thịnh >>>>>>>>>>
public class MoMoPayment extends HttpServlet {

    public static final String PARTNER_CODE = ""; // sau khi đăng ký tài khoản thành công
    public static final String ACCESS_KEY = "";   // sau khi đăng ký tài khoản thành công
    public static final String SECRET_KEY = ""; // sau khi đăng ký tài khoản thành công
    public static final String END_POINT = "https://test-payment.momo.vn/v2/gateway/api/create"; // đường link để gửi post request
    public static final String RETURN_URL = "http://localhost:8080/demo/MoMoResponse"; // cái này là sau khi thanh toán thành công hoặc thất bại nó gửi về
    public static final String NOTIFY_URL = "http://localhost:8080/demo/MoMoNotify";
    public static final String ORDER_ID = UUID.randomUUID().toString(); // hash ra cái mã cho cái order
    public static final String AMOUNT = "30000";  // số tiền cần thành toán 
    public static final String ORDER_INFOR = "PAY WITH MOMO";

    public static final String PUBLIC_KEY = ""; // sau khi đăng ký tài khoản thành công

    public static final String REQUEST_ID = UUID.randomUUID().toString();
    public static final String REQUEST_TYPE = "captureWallet";
    public static final String EXTRA_DATA = "";

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

    public HttpResponse<String> Post(final String partnerCode, final String partnerName, final String storeId,
            final String requestId, final String amount, final String orderId, final String orderInfo,
            final String redirectUrl, final String ipnUrl, final String extraData, final String requestType,
            final String signature) throws IOException, InterruptedException, URISyntaxException {

        HashMap<String, String> values = new HashMap<String, String>() {
            {
                put("partnerCode", partnerCode);
                put("partnerName", partnerName);
                put("storeId", storeId);
                put("requestId", requestId);
                put("amount", amount);
                put("orderId", orderId);
                put("orderInfo", orderInfo);
                put("redirectUrl", redirectUrl);
                put("ipnUrl", ipnUrl);
                put("lang", "en");
                put("extraData", extraData);
                put("requestType", requestType);
                put("signature", signature);
            }
        };
        ObjectMapper objectMapper = new ObjectMapper();
        
        String requestBody = objectMapper.writeValueAsString(values);
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/json")
                .uri(new URI(END_POINT))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String requestRawData = new StringBuilder()
                .append("accessKey").append("=").append(ACCESS_KEY).append("&")
                .append("amount").append("=").append(AMOUNT).append("&")
                .append("extraData").append("=").append(EXTRA_DATA).append("&")
                .append("ipnUrl").append("=").append(NOTIFY_URL).append("&")
                .append("orderId").append("=").append(ORDER_ID).append("&")
                .append("orderInfo").append("=").append(ORDER_INFOR).append("&")
                .append("partnerCode").append("=").append(PARTNER_CODE).append("&")
                .append("redirectUrl").append("=").append(RETURN_URL).append("&")
                .append("requestId").append("=").append(REQUEST_ID).append("&")
                .append("requestType").append("=").append(REQUEST_TYPE)
                .toString();

            String signRequest = signHmacSHA256(requestRawData, SECRET_KEY);
            System.out.println(signRequest);

            HttpResponse<String> res = Post(PARTNER_CODE, "Huy", "MoMoStore", REQUEST_ID, AMOUNT, ORDER_ID, 
                    ORDER_INFOR, RETURN_URL, NOTIFY_URL, EXTRA_DATA, REQUEST_TYPE, signRequest);

            System.out.println(res.body());
            JSONObject jj = new JSONObject(res.body());
            response.sendRedirect(jj.get("payUrl").toString());
        } catch (Exception e) {
        }
    }
}
//<<<<<<<<<<