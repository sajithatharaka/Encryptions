package txtfile.encryption;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;
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

    public static StringBuffer encryptData(String key, StringBuffer sb) {
        String strDataToEncrypt = sb.toString();
        StringBuffer strCipherText = new StringBuffer();
        try {
            SecretKey secretKey = getSecretKey(key);

            byte[] iv = new byte[KEYLENGTH / 8];

            Cipher aesCipherForEncryption = Cipher.getInstance(CIPHER); // Must specify the mode explicitly as most JCE providers default to ECB mode!!
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
            strCipherText.append(new BASE64Encoder().encode(byteCipherText));
            strCipherText.append(DatatypeConverter.printBase64Binary(byteCipherText));//http://stackoverflow.com/questions/3954611/encrypt-and-decrypt-with-aes-and-base64-encoding

        } catch (Exception illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        }
        return strCipherText;
    }

    public static StringBuffer decryptData(String key, StringBuffer sb) throws IOException {
        StringBuffer decrypted = new StringBuffer();
        try {
            byte[] iv = new byte[KEYLENGTH / 8];

            Cipher aesCipherForDecryption = Cipher.getInstance(CIPHER); // Must specify the mode explicitly as most JCE providers default to ECB mode!!				
            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, getSecretKey(key), new IvParameterSpec(iv));
            byte[] byteCipherText = DatatypeConverter.parseBase64Binary(sb.toString());
            byte[] byteDecryptedText = aesCipherForDecryption.doFinal(byteCipherText);

            decrypted.append(new String(byteDecryptedText));
            System.out.println(" Decryption Completed ");

        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
            illegalBlockSize.printStackTrace();
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
        }
        return decrypted;
    }
}
