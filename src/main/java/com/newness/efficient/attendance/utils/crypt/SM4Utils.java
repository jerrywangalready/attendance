package com.newness.efficient.attendance.utils.crypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

/**
 * sm4加密算法工具类
 */
public class SM4Utils {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";
    // 加密算法/分组加密模式/分组填充方式
    // PKCS5Padding-以8个字节为一组进行分组加密
    // 定义分组加密模式使用：PKCS5Padding
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    // 128-32位16进制；256-64位16进制
    public static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 生成ECB暗号
     *
     * @param mode          模式
     * @param key
     * @return
     * @throws Exception
     */
    private static Cipher generateEcbCipher(int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(SM4Utils.ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    public static String generateKey() throws Exception {
        return ByteUtils.toHexString(generateHexKey());
    }

    public static byte[] generateHexKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }


    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * sm4加密
     * 加密模式: ECB
     * 密文长度不固定,会随着被加密字符串长度的变化而变化
     *
     * @param hexKey   16进制秘钥(忽略大小写)
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     */
    public static String encryptEcb(byte[] hexKey, String paramStr) throws Exception {
        String cipherText = null;
        byte[] keyData = hexKey;

        byte[] srcData = paramStr.getBytes(ENCODING);
        byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
        cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    public static String encryptEcb(String key, String paramStr) throws Exception {
        return encryptEcb(ByteUtils.fromHexString(key), paramStr);
    }

    /**
     * 加密模式之Ecb
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * sm4解密
     * 解密模式 采用 ECB
     *
     * @param hexKey     16进制秘钥
     * @param cipherText 解密后的字符串
     * @return
     * @throws Exception
     */
    public static String decryptEcb(byte[] hexKey, String cipherText) throws Exception {
        String decryptStr = "";
        byte[] keyData = hexKey;
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        byte[] srcData = decrypt_Ecb_Padding(keyData, cipherData);
        decryptStr = new String(srcData, ENCODING);
        return decryptStr;
    }

    public static String decryptEcb(String key, String cipherText) throws Exception {
        return decryptEcb(ByteUtils.fromHexString(key), cipherText);
    }

    /**
     * 解密
     *
     * @param key
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     *
     * @param hexKey     16进制秘钥(忽略大小写)
     * @param cipherText 16进制加密后的字符串
     * @param paramStr   加密前的字符串
     * @return 校验结果
     * @throws Exception
     */
    public static boolean verifyEcb(byte[] hexKey, String cipherText, String paramStr) throws Exception {
        boolean flag = false;
        byte[] keyData = hexKey;
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        byte[] decryptData = decrypt_Ecb_Padding(keyData, cipherData);
        byte[] srcData = paramStr.getBytes(ENCODING);
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }

    public static boolean verifyEcb(String key, String cipherText, String paramStr) throws Exception {
        return verifyEcb(ByteUtils.fromHexString(key), cipherText, paramStr);
    }


    public static void main(String[] args) {
        String json = "加密测试文件";
        try {
            String key = SM4Utils.generateKey();
            System.out.println(key);
//            String key = "0123456789abcdeffedcba9876543210";
//            System.out.println("key=" + key);
            String cipher = SM4Utils.encryptEcb(key, json);
            System.out.println(cipher);
//            System.out.println(SM4Utils.verifyEcb(key, cipher, json));
//            json = SM4Utils.decryptEcb("0123456789abcdeffedcba9876543210", "0e395deb10f6e8a17e17823e1fd9bd98a1bff1df508b5b8a1efb79ec633d1bb129432ac1b74972dbe97bab04f024e89c");
//            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

