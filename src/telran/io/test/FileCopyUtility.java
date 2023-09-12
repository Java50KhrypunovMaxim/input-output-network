package telran.io.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopyUtility {
	
    public static void copyFile(String sourceFilePath, String destinationFilePath, boolean overwrite) {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);

        if (!sourceFile.exists()) {
            System.out.println("Source file " + sourceFilePath + " must exist");
            return;
        }

        File destinationDirectory = destinationFile.getParentFile();
        if (!destinationDirectory.exists()) {
            System.out.println("The directory " + destinationDirectory.getName() + " must exist");
            return;
        }

        if (destinationFile.exists() && !overwrite) {
            System.out.println("File " + destinationFilePath + " cannot be overwritten");
            return;
        }

        long startTime = System.currentTimeMillis();

        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024 * 1024]; // 1MB buffer
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            long endTime = System.currentTimeMillis();
            long runningTime = endTime - startTime;

            System.out.println("Successful copying of " + sourceFile.length() + " bytes have been copied from the file "
                    + sourceFilePath + " to the file " + destinationFilePath + ". Time " + runningTime + " ms");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during file copying.");
        }
    }
}



