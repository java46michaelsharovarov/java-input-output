package telran.io;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
class FileTests {
	
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
		printDirectory("..", 4);
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
		printNodesWithLevels(new File(pathName), pathName, level, "   ");
	}	

	private void printNodesWithLevels(File file, String pathName, int level, String indent) {
		System.out.printf(indent);
		printNode(file);
		printLevel(getNextPath(pathName, file), level - 1, indent.concat(indent));
	}

	private void printNode(File file) {
		System.out.printf("<%s> type = %s\n", file.getName(), getTypeName(file));
	}
		
	private void printLevel(String pathName, int level, String indent) {
		if(level == 0) {
			return;
		}
		File file = new File(pathName);
		File[] fileList = file.listFiles();
		if(fileList == null || fileList.length == 0) {
			return;
		}
		for (int i = 0; i < fileList.length; i++) {
			printNodesWithLevels(fileList[i], pathName, level, indent);
		}		
	}

	private String getNextPath(String pathName, File file) {
		return pathName.concat("\\").concat(file.getName());
	}

	private String getTypeName(File file) {
		return file.isDirectory() ? "dir" : "file";
	}

}