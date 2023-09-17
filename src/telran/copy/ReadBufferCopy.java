package telran.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReadBufferCopy implements CopyFile {

	@Override
    public void copy(String pathToSource, String pathToDestination) {
        try (InputStream inputStream = new FileInputStream(pathToSource);
             OutputStream outputStream = new FileOutputStream(pathToDestination)) {
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Successful copying from " + pathToSource + " to " + pathToDestination);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during file copying.");
        }
    }
}