package com.newness.efficient.attendance.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AESCrypt {

    private static final String PRIVATE_KEY = "Ity*t(UJb2=@1JzUIkQLz=";

    /*
     * @auther: Ragty
     * @describe: AES加密
     * @param: [content, password]
     * @return: byte[]
     * @date: 2019/1/18
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");            // 创建AES的Key生产者
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(PRIVATE_KEY.getBytes());
            keygen.init(128, random);                                         // 利用用户密码作为随机数初始化出(密码一样，就可以解密)

            SecretKey secretKey = keygen.generateKey();                       // 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();                   // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");     // 转换为AES专用密钥

            Cipher cipher = Cipher.getInstance("AES");                      // 创建密码器
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);                 // 设置转换格式
            cipher.init(Cipher.ENCRYPT_MODE, key);                          // 初始化为加密模式的密码器
            byte[] encrypt = cipher.doFinal(byteContent);                   // 加密

            return parseByte2HexStr(encrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /* @auther: Ragty
     * @describe: 将二进制转换为十六进制
     * @param: [buf]
     * @return: java.lang.String
     * @date: 2019/1/18
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(AESCrypt.encrypt("wang"));
    }
}