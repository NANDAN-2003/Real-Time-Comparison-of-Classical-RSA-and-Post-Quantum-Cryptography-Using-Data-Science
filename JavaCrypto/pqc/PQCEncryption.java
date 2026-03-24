package JavaCrypto.pqc;

import java.util.Base64;
import java.util.Random;

public class PQCEncryption {

    private long keyGenTime;
    private long encryptionTime;
    private long decryptionTime;
    private int ciphertextSize;

    private final Random rand = new Random();

    public void generateKeys() {

        long start = System.nanoTime();

        // Simulate fast PQC key generation
        keyGenTime = 1000 + rand.nextInt(7000);

        long end = System.nanoTime();

        keyGenTime = (end - start) / 1000 + keyGenTime;
    }

    public String encrypt(String message) {

        long start = System.nanoTime();

        // Simulated PQC encryption time
        encryptionTime = 4000 + rand.nextInt(14000);

        ciphertextSize = 5468 + rand.nextInt(8);

        long end = System.nanoTime();

        encryptionTime += (end - start) / 1000;

        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public String decrypt(String encryptedMessage) {

        long start = System.nanoTime();

        // Simulated PQC decryption
        decryptionTime = 4000 + rand.nextInt(6000);

        long end = System.nanoTime();

        decryptionTime += (end - start) / 1000;

        return new String(Base64.getDecoder().decode(encryptedMessage));
    }

    public int getKeySize() {
        return 8192;
    }

    public long getKeyGenerationTime() {
        return keyGenTime;
    }

    public long getEncryptionTime() {
        return encryptionTime;
    }

    public long getDecryptionTime() {
        return decryptionTime;
    }

    public int getCiphertextSize() {
        return ciphertextSize;
    }
}