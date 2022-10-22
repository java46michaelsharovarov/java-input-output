package telran.io;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Arrays;
class FileTests {
	
	private static final String INDENT = "    ";
	File file;
	
	@BeforeEach
	void setUp() {
		file = new File("dir1/dir2");
		file.delete();
	}

	@Test
	void newDirectoryTest() {
		assertFalse(file.exists());
		file.mkdirs();
		assertTrue(file.exists());
	}
	
	@Test
	void printDirectoryContent() throws IOException {
		printDirectory("/", 3);
	}

	/**
	 * 
	 * @param pathName - name of path to initial directory
	 * @param level - level of sub-directories printing
	 * example, level = 1 printing only first level of the initial directory content
	 *          level = 2 content + sub-directories content
	 *          ''''''''
	 *          level = -1 printing all levels
	 */
	private void printDirectory(String pathName, int level) {
		File file = new File(pathName);
		if(!file.exists() || file.isFile()) {
			throw new RuntimeException(String.format("%s is not directory", pathName));
		}
		try {
			System.out.println(file.getCanonicalPath() + "\n >>>");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		printNodeWithLevels(file, level, INDENT);
	}
		
	private void printNodeWithLevels(File file, int level, String indent) {
		if(level == 0) {
			return;
		}
		File[] fileList = file.listFiles();
		if(file.isDirectory() && fileList != null) {
			Arrays.stream(fileList).forEach(f -> {
				System.out.printf("%s<%s> type = %s\n", indent, f.getName(), getTypeName(f));
				printNodeWithLevels(f, level - 1, indent.concat(INDENT));
			});		
		}				
	}

	private String getTypeName(File file) {
		return file.isDirectory() ? "dir" : "file";
	}	
	
	@Test
	void printStreamTest() throws Exception {
		try (PrintStream printStream = new PrintStream("file1.txt");
				BufferedReader reader = new BufferedReader(new FileReader("file1.txt"));) {
			printStream.println("Hello");
			assertEquals("Hello", reader.readLine());
		}

	}

	@Test
	void printWriterTest() throws Exception {
		try (PrintWriter printWriter = new PrintWriter("file1.txt");
				BufferedReader reader = new BufferedReader(new FileReader("file1.txt"));) {
			printWriter.println("Hello");
			printWriter.flush();
			assertEquals("Hello", reader.readLine());
		}
	}

}