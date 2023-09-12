package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ByStreamsTest {

	private static final String DATA = "Hello world!!!";
	private static final String FILE_NAME = "HI.txt";
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Order(1)
		void FileOutputsStreamTest() throws Exception
		{
			try(OutputStream output = new FileOutputStream(FILE_NAME);)
			{
				output.write(DATA.getBytes());
			}
		}
	@Test
	@Order(2)
	void FileInputStreamTest() throws Exception
	{
		try(InputStream input = new FileInputStream(FILE_NAME)) {
			int length = input.available();
			byte[] buffer = new byte[length];
			input.read(buffer);
			assertEquals(DATA, new String (buffer));
		}
	}

}
