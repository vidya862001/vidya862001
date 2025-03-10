package com.code.testcode;



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.xml.bind.DatatypeConverter;

public class TestEncodeDecode {

    private static final BaseNCodec BASE_N_CODEC = new Base64 (Integer.MAX_VALUE, new byte[0], true);
    private static Cipher encryptCipher;
    private static SecretKey secretKey;
    private static final byte[] DES_KEY_BYTES = DatatypeConverter.parseHexBinary("54E39BAD3E7940B3");
    public static void main(String[] args) throws Exception {
        secretKey = new SecretKeySpec (DES_KEY_BYTES, "DES");
            encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            encryptCipher.init(1, secretKey);

        int systemId = 1;
        String entityId = "Chino Cozy";
        System.out.println (encode(new CompositeId(systemId, entityId))
                .toString());

    }
    public static String encode(CompositeId compositeId) throws Exception {
        String encoded = encodeAsString(compositeId);
        //return (Code)Code.factory().create(encoded);
        return encoded;
    }


    public static String encodeAsString(CompositeId compositeId) throws Exception {

        byte[] systemEntityIdBytes = compositeId.getSystemEntityId().getBytes(StandardCharsets.UTF_8);
        int totalLength = 12 + systemEntityIdBytes.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        buffer.putLong(0L);
        buffer.putInt(compositeId.getSystemKey());
        buffer.put(systemEntityIdBytes);
        byte[] tempBufferBytes = buffer.array();
        Checksum checksum = new CRC32 ();
        checksum.update(tempBufferBytes, 8, 4 + systemEntityIdBytes.length);
        long checksumValue = checksum.getValue();
        buffer.position(0);
        buffer.putLong(checksumValue);
        byte[] resultBytes = new byte[totalLength - 4];
        buffer.position(4);
        buffer.get(resultBytes);
        byte[] encryptedBytes = null;

      //  byte[] encryptedBytes;
        try {
            encryptedBytes = encrypt(resultBytes);
        } catch (Exception e) {
            throw new Exception (e);
        }

        String encoded = BASE_N_CODEC.encodeToString(encryptedBytes);
        return encoded;
    }

    private static byte[] encrypt(byte[] unencryptedBytes) throws GeneralSecurityException {
        byte[] encryptedBytes = encryptCipher.doFinal(unencryptedBytes);
        return encryptedBytes;
    }
}
