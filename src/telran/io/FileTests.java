package telran.io;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
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
		printDirectory("..", 1);
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
		printDirectoryName(file);
		printNodeWithLevels(file, level, INDENT);
	}

	private void printDirectoryName(File file) {
		String path = file.getPath();
		String absPath = file.getAbsolutePath();
		if(path.matches("(.+\\\\\\.)|(\\.)")) {
			absPath = absPath.substring(0, absPath.length() - 1);
		} else if(path.matches("(.+\\\\\\.\\.)|(\\.\\.)")) {
			absPath = absPath.replaceAll("[^\\\\.]+\\\\\\.\\.$", "");
		}
		System.out.printf("%s\n >>>\n", absPath);
	}
		
	private void printNodeWithLevels(File file, int level, String indent) {
		if(level == 0) {
			return;
		}
		File[] fileList = file.listFiles();
		if(fileList == null || fileList.length == 0) {
			return;
		}
		for (int i = 0; i < fileList.length; i++) {
			System.out.printf(indent);
			printNode(fileList[i]);
			printNodeWithLevels(getNextNode(file.getPath(), fileList[i]), level - 1, indent.concat(INDENT));
		}		
	}

	private File getNextNode(String pathName, File file) {
		return new File(pathName.concat("\\").concat(file.getName()));
	}

	private String getTypeName(File file) {
		return file.isDirectory() ? "dir" : "file";
	}	

	private void printNode(File file) {
		System.out.printf("<%s> type = %s\n", file.getName(), getTypeName(file));
	}

}