package telran.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferToCopy implements CopyFile {

    @Override
    public void copy(String pathToSource, String pathToDestination) {
        try (InputStream input = new FileInputStream(pathToSource);
             OutputStream output = new FileOutputStream(pathToDestination)) {
            input.transferTo(output);
            System.out.println("Successful copying from " + pathToSource + " to " + pathToDestination);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during file copying.");
        }
    }
}


