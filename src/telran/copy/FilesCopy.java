package telran.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesCopy implements CopyFile {

    @Override
    public void copy(String pathToSource, String pathToDestination) {
        Path sourcePath = Path.of(pathToSource);
        Path destinationPath = Path.of(pathToDestination);

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Successful copying from " + pathToSource + " to " + pathToDestination);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during file copying.");
        }
    }
}




