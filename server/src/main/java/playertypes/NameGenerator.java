package playertypes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class NameGenerator {
    private static final String firstPath = "./src/main/java/playertypes/resources/firstnames.txt";
    private static final String lastPath = "./src/main/java/playertypes/resources/lastnames.txt";

    public static String getFirstName() {
        File file = new File(firstPath);

        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
            long randomlocation = (long) (Math.random() * f.length());
            f.seek(randomlocation);
            f.readLine();
            return f.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLastName() {
        File file = new File(lastPath);

        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
            long randomlocation = (long) (Math.random() * f.length());
            f.seek(randomlocation);
            f.readLine();
            return f.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}