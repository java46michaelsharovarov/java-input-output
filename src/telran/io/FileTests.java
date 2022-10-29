package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

class FileTests {
	private static final int SPACES_PER_LEVEL = 2;
	File file;

	@BeforeEach
	void setUp() {
		file = new File("dir1/dir2");
		file.delete();
		new File("file1.txt").delete();

	}

	@Test
	void newDirectoryTest() {

		assertFalse(file.exists());
		file.mkdirs();
		assertTrue(file.exists());
	}

	@Test
	void printDirectoryContent() {
		printDirectory("..", 2);
	}

	/**
	 * 
	 * @param pathName - name of path to initial directory
	 * @param level    - level of sub-directories printing example, level = 1
	 *                 printing only first level of the initial directory content
	 *                 level = 2 content + sub-directories content '''''''' level =
	 *                 -1 printing all levels
	 */
	private void printDirectory(String pathName, int level) {
		/*
		 * <dir> type=dir <dir> type=dir <file> type=file <dir> type=dir ...
		 */
		File directory = new File(pathName);
		if (!directory.exists() || !directory.isDirectory()) {
			throw new RuntimeException(String.format("%s is not directory", pathName));
		}

		try {
			System.out.println("Content of directory " + directory.getCanonicalPath());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		Arrays.asList(directory.listFiles())
				.forEach(n -> displayDirectoryContent(n, level < 0 ? Integer.MAX_VALUE : level, 1));

	}

	private void displayDirectoryContent(File node, int maxLevel, int currentLevel) {

		if (currentLevel <= maxLevel) {
			boolean flDir = node.isDirectory();

			try {
				String name = node.getName();
				System.out.printf("%s%s  type=%s\n", getIndent(currentLevel), name, flDir ? "dir" : "file");
				if (flDir) {
					Arrays.stream(node.listFiles())
							.forEach(n -> displayDirectoryContent(n, maxLevel, currentLevel + 1));
				}
			} catch (Exception e) {

			}
		}

	}

	private String getIndent(int level) {

		return " ".repeat(level * SPACES_PER_LEVEL);
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
