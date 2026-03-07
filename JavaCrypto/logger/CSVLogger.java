package JavaCrypto.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVLogger {

    private static final String FILE_NAME = "results.csv";

    public static void log(
            String algorithm,
            int keySize,
            long keyGenTime,
            long encryptionTime,
            long decryptionTime,
            int ciphertextSize,
            String attackResult) {

        try {

            File file = new File(FILE_NAME);
            boolean fileExists = file.exists();

            FileWriter writer = new FileWriter(FILE_NAME, true);

            if (!fileExists) {
                writer.append("Algorithm,KeySize,KeyGenerationTime(us),EncryptionTime(us),DecryptionTime(us),CiphertextSize(bytes),AttackResult\n");
            }

            writer.append(algorithm).append(",")
                    .append(String.valueOf(keySize)).append(",")
                    .append(String.valueOf(keyGenTime)).append(",")
                    .append(String.valueOf(encryptionTime)).append(",")
                    .append(String.valueOf(decryptionTime)).append(",")
                    .append(String.valueOf(ciphertextSize)).append(",")
                    .append(attackResult).append("\n");

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}