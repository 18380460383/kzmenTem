package com.kzmen.sczxjf.utils;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private final static String IV_KEY = "70c3fb494f99ed2a";
    private final static String KEY = "70c3fb494f99ed2a";

    /**
     * 加密数据
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            SecretKeySpec keyspec = new SecretKeySpec(fullZore(KEY,blockSize), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(fullZore(IV_KEY,blockSize));
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(fullZore(data,blockSize));
            return new String(Base64.encode(encrypted, Base64.DEFAULT)).trim();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密数据
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            SecretKeySpec keyspec = new SecretKeySpec(fullZore(KEY,blockSize), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(fullZore(IV_KEY,blockSize));
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] decrypted = cipher.doFinal(Base64. decode(data, Base64.DEFAULT));
            return new String(decrypted).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] fullZore(String data,int blockSize){
        byte[] dataBytes = data.getBytes();
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        return plaintext;
    }

}
