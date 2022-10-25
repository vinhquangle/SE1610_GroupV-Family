/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recaptcha;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Admin
 */
//Quốc Thịnh >>>>>>>>>>
public class VerifyUtils {
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String SECRET_KEY = "6LfxTU4gAAAAAIkr8DBNEbBVW79Sk3IwDcX2Tz9j";

	public static boolean verify(String gRecaptchaResponse) {
		if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
			return false;
		}

		try {
			URL verifyUrl = new URL(SITE_VERIFY_URL);

			// Open a Connection to URL above.
			HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

			// Add the Header informations to the Request to prepare send to the server.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Data will be sent to the server.
			String postParams = "secret=" + SECRET_KEY
					+ "&response=" + gRecaptchaResponse;

			// Send Request
			conn.setDoOutput(true);

			// Get the output stream of Connection.
			// Write data in this stream, which means to send data to Server.
			OutputStream outStream = conn.getOutputStream();
			outStream.write(postParams.getBytes());

			outStream.flush();
			outStream.close();

			// Response code return from Server.
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode=" + responseCode);

			// Get the Input Stream of Connection to read data sent from the Server.
			InputStream is = conn.getInputStream();

			JsonReader jsonReader = Json.createReader(is);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			// ==> {"success": true}
			System.out.println("Response: " + jsonObject);

			boolean success = jsonObject.getBoolean("success");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
//<<<<<<<<<<