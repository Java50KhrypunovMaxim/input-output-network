package telran.io.test;

import telran.copy.CopyFile;

public class CopyFilePerformanceTest extends PerformanceTest {

String sourceFile;
String destinationFile;
CopyFile copyFile;
public CopyFilePerformanceTest(String testName, int nRuns, String sourceFile, String destinationFile,
		CopyFile copyFile) {
	super(testName, nRuns);
	this.sourceFile = sourceFile;
	this.destinationFile = destinationFile;
	this.copyFile = copyFile;
}
	@Override
	protected void runTest() {
		copyFile.copy(sourceFile, destinationFile);

	}

}
