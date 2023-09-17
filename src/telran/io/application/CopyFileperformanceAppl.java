package telran.io.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import telran.copy.FilesCopy;
import telran.copy.ReadBufferCopy;
import telran.copy.TransferToCopy;
import telran.io.test.CopyFilePerformanceTest;
import telran.io.test.PerformanceTest;

public class CopyFileperformanceAppl {
	static final String pathoToSource = "C:\\Documents\\Урок 50.mp4";
    static final String pathoToDestination = "C:\\Documents\\Урок 50 - copy.mp4";

    public static void main(String[] args) {
        Integer[] bufferLengthValues = { 1_000_000 };
        try {
            long size = Files.size(Path.of(pathoToSource));
            Arrays.stream(bufferLengthValues).map(bl -> getPerformanceTest(bl, size)).forEach(t -> t.run());

            // Create instances of the three implementations and run their tests
            PerformanceTest testTransfer =
                    new CopyFilePerformanceTest(String.format("%s ; size:%d", "TransferToCopy", size),
                            1, pathoToSource, pathoToDestination, new TransferToCopy());
            testTransfer.run();

            PerformanceTest testFilesCopy =
                    new CopyFilePerformanceTest(String.format("%s ; size:%d", "FilesCopy", size),
                            1, pathoToSource, pathoToDestination, new FilesCopy());
            testFilesCopy.run();

            PerformanceTest testReadBufferCopy =
                    new CopyFilePerformanceTest(String.format("%s ; size:%d", "ReadBufferCopy", size),
                            1, pathoToSource, pathoToDestination, new ReadBufferCopy());
            testReadBufferCopy.run();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static CopyFilePerformanceTest getPerformanceTest(Integer bl, long size) {
        CopyFilePerformanceTest test =
                new CopyFilePerformanceTest(String.format("%s implementation buffer length %d; size:%d",
                        "FileCopy", bl, size),
                        1, pathoToSource, pathoToDestination, new FilesCopy());
        return test;
    }
}