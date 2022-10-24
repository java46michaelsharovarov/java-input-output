package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;
import static telran.util.Level.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Level;
import telran.util.Logger;
import telran.util.SimpleStreamHandler;

class LoggerTests {
	private static final String NAME = "logger.log";
	private File file = new File(NAME);
	private Logger logger;

	@BeforeEach
	void setUp() throws Exception {
		file.delete();
	}

	@Test
	void testError() {
		runLogger(ERROR); 
		assertEquals(getLogsLevels(), Set.of(ERROR));
	}

	@Test
	void testWarn() {
		runLogger(WARN);
		assertEquals(getLogsLevels(), Set.of(ERROR, WARN));
	}

	@Test
	void testInfo() {
		runLogger(INFO);
		assertEquals(getLogsLevels(), Set.of(ERROR, WARN, INFO));
	}

	@Test
	void testDebug() {
		runLogger(DEBUG);
		assertEquals(getLogsLevels(), Set.of(ERROR, WARN, INFO, DEBUG));
	}

	@Test
	void testTrace() {
		runLogger(TRACE);
		assertEquals(getLogsLevels(), Set.of(ERROR, WARN, INFO, DEBUG, TRACE));
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

	private Set<Level> getLogsLevels() {
		try (BufferedReader reader = new BufferedReader(new FileReader(NAME))) {			
			return reader.lines()
			.map(s -> Level.valueOf(s.split(" ")[1]))
			.collect(Collectors.toSet());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void runAllMethods() {
		logger.error("ERROR!!!");
		logger.warn("WARNING!!!");
		logger.info("INFO!!!");
		logger.debug("DEBUG!!!");
		logger.trace("TRACE!!!");		
	}

}
