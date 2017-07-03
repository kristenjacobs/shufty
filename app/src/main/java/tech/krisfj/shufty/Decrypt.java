package tech.krisfj.shufty;

import android.text.TextUtils;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.examples.ByteArrayHandler;

import java.io.InputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {
    //public static String run(InputStream encryptedStream, String password) throws Exception {
    //    //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    //    byte[] encryptedByteArray = IOUtils.toByteArray(encryptedStream);
    //    byte[] decryptedByteArray = ByteArrayHandler.decrypt(encryptedByteArray, password.toCharArray());
    //    return new String(decryptedByteArray);
    //}

    private static String getPassphraseSize16(String key) {
        if (key == "") {
            return null;
        }
        char controlChar = '\u0014';
        String key16 = key + controlChar;
        if (key16.length() < 16) {
            while (key16.length() < 16) {
                key16 += key + controlChar;
            }
        }
        if (key16.length() > 16) {
            key16 = key16.substring(key16.length() - 16, key16.length());
        }
        return key16;
    }

    public static String run(InputStream encryptedStream, String passphrase) throws Exception {
        String passphrase16 = getPassphraseSize16(passphrase);
        SecretKeySpec secretKey = new SecretKeySpec(passphrase16.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedByteArray = IOUtils.toByteArray(encryptedStream);
        byte[] decodedText = cipher.doFinal(encryptedByteArray);
        return new String(decodedText);
    }
}
