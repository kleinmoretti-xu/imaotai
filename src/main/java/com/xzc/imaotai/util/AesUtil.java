package com.xzc.imaotai.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesUtil {

    private static final String ALGO = "AES";

    private static final String ALGO_MODE = "AES/CBC/NoPadding";

    private static final String KEY = "qbhajinldepmucsonaaaccgypwuvcjaa";

    private static final String IV = "2018534749963515";

    /**
     * 解密
     *
     * @param encryptedData 加密数据
     */
    public static String decrypt(String encryptedData) {
        try {
            byte[] encrypted1 = Base64.decodeBase64(encryptedData.getBytes());
            byte[] iv = IV.getBytes();
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     */
    public String encrypt(String data) {
        try {
            //因为要求IV为16byte，而此处aiv串为32位字符串，所以将32位字符串转为16byte
            byte[] iv = IV.getBytes();
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            return null;
        }
    }

}
