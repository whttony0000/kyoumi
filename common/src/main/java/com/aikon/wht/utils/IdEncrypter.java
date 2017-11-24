package com.aikon.wht.utils;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author haitao.wang
 */
public class IdEncrypter {
    private static final Logger logger             = Logger.getLogger(IdEncrypter.class);
    private static final byte[] ID_CRYPTER_SALT_BYTES = "N0thing_gReat_wAs_eveR_achieved_with0ut_enthusiasm, N0_do_n0_die."
                                                       .getBytes(Charset.forName("ISO-8859-1"));
    private static Blowfish     blowfish;

    static {
        blowfish = new Blowfish(Arrays.copyOf(ID_CRYPTER_SALT_BYTES, 32), Arrays.copyOfRange(
                ID_CRYPTER_SALT_BYTES, 32, 40));
    }

    public static String encodeId(Integer id) {
        if (id == null) {
            return null;
        }
        try {
            byte[] sourceBytes = new byte[8];
            sourceBytes[0] = (byte) ((id >> 56) & 0xFF);
            sourceBytes[1] = (byte) ((id >> 48) & 0xFF);
            sourceBytes[2] = (byte) ((id >> 40) & 0xFF);
            sourceBytes[3] = (byte) ((id >> 32) & 0xFF);
            sourceBytes[4] = (byte) ((id >> 24) & 0xFF);
            sourceBytes[5] = (byte) ((id >> 16) & 0xFF);
            sourceBytes[6] = (byte) ((id >> 8) & 0xFF);
            sourceBytes[7] = (byte) ((id) & 0xFF);
            byte[] encryptedBytes = blowfish.encrypt(sourceBytes);
            char[] base64Chars = Base64.encode(encryptedBytes);
            int i;
            for (i = 0; i < base64Chars.length; i++) {
                if (base64Chars[i] == '=') {
                    break; // = only appear at end, so i is the ending
                } else if (base64Chars[i] == '+') {
                    base64Chars[i] = '-';
                } else if (base64Chars[i] == '/') {
                    base64Chars[i] = '_';
                }
            }
            return new String(base64Chars, 0, i);
        } catch (Exception e) {
            logger.error("encode error, id=" + id, e);
        }
        return null;
    }

    public static String encodeId(Long id) {
        if (id == null) {
            return null;
        }
        try {
            byte[] sourceBytes = new byte[8];
            sourceBytes[0] = (byte) ((id >> 56) & 0xFF);
            sourceBytes[1] = (byte) ((id >> 48) & 0xFF);
            sourceBytes[2] = (byte) ((id >> 40) & 0xFF);
            sourceBytes[3] = (byte) ((id >> 32) & 0xFF);
            sourceBytes[4] = (byte) ((id >> 24) & 0xFF);
            sourceBytes[5] = (byte) ((id >> 16) & 0xFF);
            sourceBytes[6] = (byte) ((id >> 8) & 0xFF);
            sourceBytes[7] = (byte) ((id) & 0xFF);
            byte[] encryptedBytes = blowfish.encrypt(sourceBytes);
            char[] base64Chars = Base64.encode(encryptedBytes);
            int i;
            for (i = 0; i < base64Chars.length; i++) {
                if (base64Chars[i] == '=') {
                    break; // = only appear at end, so i is the ending
                } else if (base64Chars[i] == '+') {
                    base64Chars[i] = '-';
                } else if (base64Chars[i] == '/') {
                    base64Chars[i] = '_';
                }
            }
            return new String(base64Chars, 0, i);
        } catch (Exception e) {
            logger.error("encode error, id=" + id, e);
        }
        return null;
    }

    public static int decodeId(String code) {
        if (code == null || "".equals(code)) {
            return 0;
        }
        try {
            StringBuilder sb = new StringBuilder(code);
            int i = code.length();
            while (i % 4 != 0) {
                sb.append('=');
                i++;
            }
            for (i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '-') {
                    sb.setCharAt(i, '+');
                } else if (sb.charAt(i) == '_') {
                    sb.setCharAt(i, '/');
                }
            }
            byte[] sourceBytes = Base64.decode(sb.toString());
            byte[] decryptedBytes = blowfish.decrypt(sourceBytes);

            ByteBuffer bf = ByteBuffer.wrap(decryptedBytes);
            return bf.getInt();
        } catch (Exception e) {
            logger.error("decode error, code=" + code, e);
        }
        return 0;
    }

    public static long decodeId2Long(String code) {
        if (code == null || "".equals(code)) {
            return 0L;
        }
        try {
            StringBuilder sb = new StringBuilder(code);
            int i = code.length();
            while (i % 4 != 0) {
                sb.append('=');
                i++;
            }
            for (i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '-') {
                    sb.setCharAt(i, '+');
                } else if (sb.charAt(i) == '_') {
                    sb.setCharAt(i, '/');
                }
            }
            byte[] sourceBytes = Base64.decode(sb.toString());
            byte[] decryptedBytes = blowfish.decrypt(sourceBytes);

            ByteBuffer bf = ByteBuffer.wrap(decryptedBytes);
            return bf.getLong();
        } catch (Exception e) {
            logger.error("decode error, code=" + code, e);
        }
        return 0L;
    }
}