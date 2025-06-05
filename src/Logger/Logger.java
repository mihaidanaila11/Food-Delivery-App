package Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String FILE_NAME = "src/Logger/log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void logOperation(String operationName) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.append(operationName)
                    .append(",")
                    .append(timestamp)
                    .append("\n");
        } catch (IOException e) {
            System.err.println("Eroare la scrierea în fișierul de log: " + e.getMessage());
        }
    }
}

