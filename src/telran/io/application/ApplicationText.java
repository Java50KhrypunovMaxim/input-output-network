package telran.io.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ApplicationText {
	public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Application <inputFile> <commentsFile> <textFile>");
            return;
        }
        String inputFile = args[0];
        String commentsFile = args[1];
        String textFile = args[2];
        try
        {
        	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter commentsWriter = new BufferedWriter(new FileWriter(commentsFile));
            BufferedWriter textWriter = new BufferedWriter(new FileWriter(textFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("//")) {
                    
                    commentsWriter.write(line.replaceFirst("//\\s*", "") + "\n");
                } else {
                    textWriter.write(line + "\n");
                }
            }

            reader.close();
            commentsWriter.close();
            textWriter.close();
            System.out.println("Operation completed successfully");
        }
        catch (IOException e) {
            System.out.println("Error");
        }
}
}
