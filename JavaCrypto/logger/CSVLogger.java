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
            long encTime,
            long decTime,
            int cipherSize,
            String attackResult) {

        try {

            File file = new File(FILE_NAME);
            boolean fileExists = file.exists();

            FileWriter writer = new FileWriter(file, true);

            // Write header if file is new
            if (!fileExists || file.length() == 0) {

                writer.append("Algorithm,")
                      .append("KeySize,")
                      .append("KeyGenerationTime(us),")
                      .append("EncryptionTime(us),")
                      .append("DecryptionTime(us),")
                      .append("CiphertextSize(bytes),")
                      .append("AttackResult")
                      .append("\n");
            }

            // Write row data
            writer.append(algorithm).append(",")
                  .append(String.valueOf(keySize)).append(",")
                  .append(String.valueOf(keyGenTime)).append(",")
                  .append(String.valueOf(encTime)).append(",")
                  .append(String.valueOf(decTime)).append(",")
                  .append(String.valueOf(cipherSize)).append(",")
                  .append(attackResult)
                  .append("\n");

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}