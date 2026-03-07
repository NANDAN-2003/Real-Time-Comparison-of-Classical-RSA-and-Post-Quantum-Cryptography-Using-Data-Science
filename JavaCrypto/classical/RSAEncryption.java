package JavaCrypto.classical;

import javax.crypto.Cipher;
import java.security.*;

public class RSAEncryption {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private long keyGenerationTime;
    private long encryptionTime;
    private long decryptionTime;
    private int ciphertextSize;

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public void generateKeys() throws Exception {

        long start = System.nanoTime();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();

        long end = System.nanoTime();
        keyGenerationTime = (end - start) / 1_000; // microseconds

        encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    }

    public byte[] encrypt(String message) throws Exception {

        // Warm-up
        encryptCipher.doFinal(message.getBytes());

        long start = System.nanoTime();
        byte[] encrypted = encryptCipher.doFinal(message.getBytes());
        long end = System.nanoTime();

        encryptionTime = (end - start) / 1_000; // microseconds
        ciphertextSize = encrypted.length;

        return encrypted;
    }

    public String decrypt(byte[] encryptedData) throws Exception {

        // Warm-up
        decryptCipher.doFinal(encryptedData);

        long start = System.nanoTime();
        byte[] decrypted = decryptCipher.doFinal(encryptedData);
        long end = System.nanoTime();

        decryptionTime = (end - start) / 1_000; // microseconds

        return new String(decrypted);
    }

    public int getKeySize() { return 2048; }
    public long getKeyGenerationTime() { return keyGenerationTime; }
    public long getEncryptionTime() { return encryptionTime; }
    public long getDecryptionTime() { return decryptionTime; }
    public int getCiphertextSize() { return ciphertextSize; }
}