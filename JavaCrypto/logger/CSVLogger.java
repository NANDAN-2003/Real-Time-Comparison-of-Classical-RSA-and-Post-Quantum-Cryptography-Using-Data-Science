package JavaCrypto.logger;

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

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.append(algorithm).append(",")
                    .append(String.valueOf(keySize)).append(",")
                    .append(String.valueOf(keyGenTime)).append(",")
                    .append(String.valueOf(encTime)).append(",")
                    .append(String.valueOf(decTime)).append(",")
                    .append(String.valueOf(cipherSize)).append(",")
                    .append(attackResult)
                    .append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}