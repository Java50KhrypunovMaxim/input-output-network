package telran.io.test;

public class CopyAppl {

	public static void main (String[] args)
	{
		
	    
	      
	        boolean overwrite = args.length >= 3 && args[2].equals("overwrite");

	        FileCopyUtility.copyFile("C:\\Documents\\Contract China N.doc", "C:\\Documents\\New_Copy.doc", overwrite);
	    }
	}
	

