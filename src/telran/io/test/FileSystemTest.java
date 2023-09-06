package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() throws IOException {
		Path current = Path.of(".");
		System.out.printf("current.getFileName() -> %s\n", current.getFileName());
		assertTrue(Files.isDirectory(current));
		assertFalse(current.isAbsolute());
		current = current.toAbsolutePath();
		assertTrue(current.isAbsolute());
		System.out.printf("current.getFileName() -> %s\n", current.getFileName());
		current = current.normalize();
		System.out.printf("current.getFileName() -> %s\n", current.getFileName());
		System.out.printf("current.toRealPath -> %s\n", current.toRealPath());
		System.out.printf("current.getNameCount -> %s\n", current.getNameCount());
		System.out.printf("current.getNameCount -> %s\n", current.getName(3));
	}

}
