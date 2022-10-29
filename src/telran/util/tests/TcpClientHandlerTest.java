package telran.util.tests;

import static org.junit.jupiter.api.Assertions.*;
import static telran.util.Level.TRACE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.util.Logger;
import telran.util.TcpClientHandler;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TcpClientHandlerTest {
	
	private static final String HOST = "localhost";
	private static final int PORT = 3000;
	private static Logger logger;
	private static TcpClientHandler handler;
	private static BufferedReader reader;
	
	
	@BeforeAll
	static void createLogger() {
		handler = new TcpClientHandler(HOST, PORT);
		logger = new Logger(handler, "my_logger");
	}
	
	@BeforeEach
	void setUp() {
		try {
			reader = new BufferedReader(new FileReader("response.log"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void closeLogger() {		
		handler.close();
	}
	
	@Test
	@Order(1)
	void logErrorRequestTest() throws Exception{
		logger.setLevel(TRACE);
		logger.error("ERROR!!!");
		assertEquals("OK", reader.readLine());		
	}	
	
	
	@Test
	@Order(2)
	void errorRequestTest() throws Exception{
		handler.getCount("error");
		assertEquals("1", reader.readLine());
	}
	
	@Test
	@Order(3)
	void logDebugRequestTest() throws Exception{
		logger.debug("DEBUG!!!");
		logger.debug("DEBUG!!!");
		assertEquals("OK", reader.readLine());		
	}
	
	@Test
	@Order(4)
	void debugRequestTest() throws Exception{
		handler.getCount("debug");
		assertEquals("2", reader.readLine());
	}

}
