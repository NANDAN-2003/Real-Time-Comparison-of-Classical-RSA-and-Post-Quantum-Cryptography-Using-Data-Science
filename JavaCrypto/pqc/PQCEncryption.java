package JavaCrypto.pqc;

import java.security.SecureRandom;
import java.util.Base64;

public class PQCEncryption {

    private long keyGenerationTime;
    private long encryptionTime;
    private long decryptionTime;
    private int ciphertextSize;

    private byte[] publicKey;
    private byte[] privateKey;

    private static final int MIXING_ROUNDS = 120;

    public void generateKeys() {

        long start = System.nanoTime();

        SecureRandom random = new SecureRandom();

        publicKey = new byte[8192];
        privateKey = new byte[4096];

        random.nextBytes(publicKey);
        random.nextBytes(privateKey);

        long end = System.nanoTime();
        keyGenerationTime = (end - start) / 1_000; // microseconds
    }

    public String encrypt(String message) {

        long start = System.nanoTime();

        byte[] messageBytes = message.getBytes();
        byte[] encrypted = new byte[messageBytes.length + 4096];

        System.arraycopy(messageBytes, 0, encrypted, 0, messageBytes.length);

        for (int round = 0; round < MIXING_ROUNDS; round++) {
            for (int i = 0; i < encrypted.length; i++) {
                byte pk = publicKey[(i + round) % publicKey.length];
                encrypted[i] ^= pk;
            }
        }

        String encoded = Base64.getEncoder().encodeToString(encrypted);

        long end = System.nanoTime();
        encryptionTime = (end - start) / 1_000; // microseconds

        ciphertextSize = encoded.getBytes().length;

        return encoded;
    }

    public String decrypt(String encryptedMessage) {

        long start = System.nanoTime();

        byte[] encrypted = Base64.getDecoder().decode(encryptedMessage);

        for (int round = MIXING_ROUNDS - 1; round >= 0; round--) {
            for (int i = 0; i < encrypted.length; i++) {
                byte pk = publicKey[(i + round) % publicKey.length];
                encrypted[i] ^= pk;
            }
        }

        long end = System.nanoTime();
        decryptionTime = (end - start) / 1_000; // microseconds

        return new String(encrypted).trim();
    }

    public int getKeySize() { return 8192; }
    public long getKeyGenerationTime() { return keyGenerationTime; }
    public long getEncryptionTime() { return encryptionTime; }
    public long getDecryptionTime() { return decryptionTime; }
    public int getCiphertextSize() { return ciphertextSize; }
}