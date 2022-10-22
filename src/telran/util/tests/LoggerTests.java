package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Level;
import telran.util.Logger;
import telran.util.SimpleStreamHandler;

class LoggerTests {
	private static final String NAME = "logger.log";
	private File file = new File(NAME);
	private Logger logger;
	HashSet<String> set;
	final Level TRACE = Level.TRACE;
	final Level DEBUG = Level.DEBUG;
	final Level INFO = Level.INFO;
	final Level WARN = Level.WARN;
	final Level ERROR = Level.ERROR;

	@BeforeEach
	void setUp() throws Exception {
		file.delete();
	}

	@Test
	void testError() {
		runLogger(ERROR);
		set = getLogsLevels();
		assertTrue(set.contains(ERROR.name()));
		assertFalse(set.contains(TRACE.name()));
		assertFalse(set.contains(DEBUG.name()));
		assertFalse(set.contains(INFO.name()));
		assertFalse(set.contains(WARN.name()));
	}

	@Test
	void testWarn() {
		runLogger(WARN);
		set = getLogsLevels();
		assertTrue(set.contains(ERROR.name()));
		assertTrue(set.contains(WARN.name()));
		assertFalse(set.contains(TRACE.name()));
		assertFalse(set.contains(DEBUG.name()));
		assertFalse(set.contains(INFO.name()));
	}

	@Test
	void testInfo() {
		runLogger(INFO);
		set = getLogsLevels();
		assertTrue(set.contains(ERROR.name()));
		assertTrue(set.contains(WARN.name()));
		assertTrue(set.contains(INFO.name()));
		assertFalse(set.contains(TRACE.name()));
		assertFalse(set.contains(DEBUG.name()));
	}

	@Test
	void testDebug() {
		runLogger(DEBUG);
		set = getLogsLevels();
		assertTrue(set.contains(ERROR.name()));
		assertTrue(set.contains(WARN.name()));
		assertTrue(set.contains(INFO.name()));
		assertTrue(set.contains(DEBUG.name()));
		assertFalse(set.contains(TRACE.name()));
	}

	@Test
	void testTrace() {
		runLogger(TRACE);
		set = getLogsLevels();
		assertTrue(set.contains(ERROR.name()));
		assertTrue(set.contains(WARN.name()));
		assertTrue(set.contains(INFO.name()));
		assertTrue(set.contains(DEBUG.name()));
		assertTrue(set.contains(TRACE.name()));
	}
	
	@Test
	void publishingToConsoleTest() {		
		logger = new Logger(new SimpleStreamHandler(System.out), "my_logger");
		logger.setLevel(TRACE);
		runAllMethods();
	}
	
	private void runLogger(Level error) {
		try (PrintStream printStream = new PrintStream(NAME)) {
			logger = new Logger(new SimpleStreamHandler(printStream), "my_logger");
			logger.setLevel(error);
			runAllMethods();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private HashSet<String> getLogsLevels() {
		HashSet<String> setOfLevels = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(NAME))) {			
			reader.lines().forEach(s -> setOfLevels.add(s.split(" ")[1]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return setOfLevels;
	}

	private void runAllMethods() {
		logger.error("ERROR!!!");
		logger.warn("WARNING!!!");
		logger.info("INFO!!!");
		logger.debug("DEBUG!!!");
		logger.trace("TRACE!!!");		
	}

}
