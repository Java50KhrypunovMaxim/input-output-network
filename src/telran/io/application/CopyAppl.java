package telran.io.application;

import telran.copy.FilesCopy;

public class CopyAppl {

	public static void main (String[] args)
	{
		 FilesCopy filesCopy = new FilesCopy();
	        String sourcePath = "C:\\Documents\\Урок 50.mp4"; 
	        String destinationPath = "C:\\Documents\\Tolik.mp4"; 

	        filesCopy.copy(sourcePath, destinationPath);
	    }
	}
	

