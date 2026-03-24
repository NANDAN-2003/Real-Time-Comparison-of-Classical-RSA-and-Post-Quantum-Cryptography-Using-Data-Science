package JavaCrypto;

import JavaCrypto.classical.RSAEncryption;
import JavaCrypto.pqc.PQCEncryption;
import JavaCrypto.attack.QuantumAttackSimulator;
import JavaCrypto.logger.CSVLogger;

import java.security.SecureRandom;
import java.util.Base64;

public class MainApp {

    public static void main(String[] args) throws Exception {

        int samples = 5000; // 5000 RSA + 5000 PQC = 10000 rows
        SecureRandom random = new SecureRandom();

        System.out.println("Generating dataset...\n");

        for (int i = 0; i < samples; i++) {

            /* Generate random plaintext */
            byte[] msg = new byte[32];
            random.nextBytes(msg);
            String message = Base64.getEncoder().encodeToString(msg);

            /* ---------------- RSA SECTION (REAL) ---------------- */
            RSAEncryption rsa = new RSAEncryption();

            rsa.generateKeys();
            byte[] rsaEncrypted = rsa.encrypt(message);
            rsa.decrypt(rsaEncrypted);

            String rsaAttackResult =
                    QuantumAttackSimulator.simulateAttack("RSA");

            CSVLogger.log(
                    "RSA",
                    rsa.getKeySize(),
                    rsa.getKeyGenerationTime(),
                    rsa.getEncryptionTime(),
                    rsa.getDecryptionTime(),
                    rsa.getCiphertextSize(),
                    rsaAttackResult
            );


            /* ---------------- PQC SECTION (SIMULATED REALISTIC) ---------------- */
            PQCEncryption pqc = new PQCEncryption();

            pqc.generateKeys();
            String pqcEncrypted = pqc.encrypt(message);
            pqc.decrypt(pqcEncrypted);

            String pqcAttackResult =
                    QuantumAttackSimulator.simulateAttack("PQC");

            CSVLogger.log(
                    "PQC",
                    pqc.getKeySize(),
                    pqc.getKeyGenerationTime(),
                    pqc.getEncryptionTime(),
                    pqc.getDecryptionTime(),
                    pqc.getCiphertextSize(),
                    pqcAttackResult
            );

            if (i % 500 == 0) {
                System.out.println("Generated " + (i * 2) + " samples...");
            }
        }

        System.out.println("\nDataset generation completed.");
        System.out.println("Results saved in results.csv");
    }
}