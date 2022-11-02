package telran.util;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import telran.net.ServerLogAppl;

public class TcpClientHandler implements Handler {
	
	Socket socket;
	PrintStream stream;
	BufferedReader input;
	
	public TcpClientHandler(String hostName, int port) {		
		try {
			socket = new Socket(hostName, port);
			stream = new PrintStream(socket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}	

	@Override
	public void publish(LoggerRecord loggerRecord) {
		LocalDateTime ldt = LocalDateTime.ofInstant(loggerRecord.timestamp,
				ZoneId.of(loggerRecord.zoneId));
		String message = String.format("%s %s %s %s", ldt, loggerRecord.level,
				loggerRecord.loggerName, loggerRecord.message);
		stream.println(ServerLogAppl.LOG_TYPE + "#" + message);
		try {
			String response = input.readLine();
			if (!response.equals(ServerLogAppl.OK)) {
				throw new RuntimeException("Response from Logger Server is " + response);
			}
		} catch (IOException e) {
			new RuntimeException(e.getMessage());
		}		
	}
	
	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			throw new RuntimeException("not closed " + e.getMessage());
		}
	}	

}