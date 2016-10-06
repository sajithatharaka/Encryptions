package zip.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Encoder;

public class Encryption {

    private static final String KEY_GEN = "AES";
    private static final int KEYLENGTH = 128;	// change this as desired for the security level you want
    private static final String CIPHER = "AES/CBC/PKCS5PADDING";

    public static SecretKey getSecretKey(String key) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_GEN);
        keyGen.init(new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public static String encryptData(String key) {
        try {
            SecretKey secretKey = getSecretKey(key);

            byte[] iv = new byte[KEYLENGTH / 8];

            Cipher aesCipherForEncryption = Cipher.getInstance(CIPHER); // Must specify the mode explicitly as most JCE providers default to ECB mode!!
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] byteDataToEncrypt = key.getBytes();
            byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
            key = new BASE64Encoder().encode(byteCipherText);
        } catch (Exception illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        }
        return key;
    }
}
