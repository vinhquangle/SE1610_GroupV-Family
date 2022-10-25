/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 *
 */
//Quốc Thịnh >>>>>>>>>>
public class MyAES {

    public static final int AES_128 = 128;
    public static final int AES_192 = 192;
    public static final int AES_256 = 256;
    String key;
    int AES_KeyLen = 128;
    byte[] AES_key = null;

    public MyAES(String akey) {
        key = akey;
        AES_key = AES_createKey();
    }

    public MyAES(String akey, int AES_KeyLen) {
        key = akey;
        if (AES_KeyLen == AES_256) {
            this.AES_KeyLen = AES_256;
        }
        AES_key = AES_createKey();
    }

    //Tạo key
    private byte[] AES_createKey() {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] keyBytes = key.getBytes("UTF-8");
            keyBytes = sha.digest(keyBytes);
            AES_key = Arrays.copyOf(keyBytes, AES_KeyLen / 8);
            return AES_key;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //Mã hóa AES
    public String AES_encrypt(String source) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(source.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println("\n AES Encrypt: " + e.toString());
        }
        return null;
    }

    //Giải mã AES
    public String AES_decrypt(String encryptedStr) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = Base64.getDecoder().decode(encryptedStr);
            byte[] result = cipher.doFinal(bytes);
            return new String(result);
        } catch (Exception e) {
            System.out.println("\n AES Decrypt: " + e.toString());
        }
        return null;
    }
}
//<<<<<<<<<<
