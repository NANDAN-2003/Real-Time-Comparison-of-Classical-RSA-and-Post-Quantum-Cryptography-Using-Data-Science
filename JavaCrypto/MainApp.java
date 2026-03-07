package JavaCrypto;

import JavaCrypto.classical.RSAEncryption;
import JavaCrypto.pqc.PQCEncryption;
import JavaCrypto.attack.QuantumAttackSimulator;
import JavaCrypto.logger.CSVLogger;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the message to encrypt: ");
        String message = scanner.nextLine();

        System.out.println("\nOriginal Message: " + message);
        System.out.println("===================================");

        /* ---------------- RSA SECTION ---------------- */
        RSAEncryption rsa = new RSAEncryption();

        rsa.generateKeys();
        byte[] rsaEncrypted = rsa.encrypt(message);
        String rsaDecrypted = rsa.decrypt(rsaEncrypted);
        String rsaAttackResult =
                QuantumAttackSimulator.simulateAttack("RSA");

        System.out.println("\n----- RSA RESULTS -----");
        System.out.println("Decrypted Message      : " + rsaDecrypted);
        System.out.println("Key Generation Time    : " + rsa.getKeyGenerationTime() + " µs");
        System.out.println("Encryption Time        : " + rsa.getEncryptionTime() + " µs");
        System.out.println("Decryption Time        : " + rsa.getDecryptionTime() + " µs");
        System.out.println("Ciphertext Size        : " + rsa.getCiphertextSize() + " bytes");
        System.out.println("Quantum Attack Result  : " + rsaAttackResult);

        CSVLogger.log(
                "RSA",
                rsa.getKeySize(),
                rsa.getKeyGenerationTime(),
                rsa.getEncryptionTime(),
                rsa.getDecryptionTime(),
                rsa.getCiphertextSize(),
                rsaAttackResult
        );

        System.out.println("-----------------------------------");

        /* ---------------- PQC SECTION ---------------- */
        PQCEncryption pqc = new PQCEncryption();

        pqc.generateKeys();
        String pqcEncrypted = pqc.encrypt(message);
        String pqcDecrypted = pqc.decrypt(pqcEncrypted);
        String pqcAttackResult =
                QuantumAttackSimulator.simulateAttack("PQC");

        System.out.println("\n----- PQC RESULTS -----");
        System.out.println("Decrypted Message      : " + pqcDecrypted);
        System.out.println("Key Generation Time    : " + pqc.getKeyGenerationTime() + " µs");
        System.out.println("Encryption Time        : " + pqc.getEncryptionTime() + " µs");
        System.out.println("Decryption Time        : " + pqc.getDecryptionTime() + " µs");
        System.out.println("Ciphertext Size        : " + pqc.getCiphertextSize() + " bytes");
        System.out.println("Quantum Attack Result  : " + pqcAttackResult);

        CSVLogger.log(
                "PQC",
                pqc.getKeySize(),
                pqc.getKeyGenerationTime(),
                pqc.getEncryptionTime(),
                pqc.getDecryptionTime(),
                pqc.getCiphertextSize(),
                pqcAttackResult
        );

        System.out.println("===================================");
        System.out.println("Results saved in results.csv");

        scanner.close();
    }
}