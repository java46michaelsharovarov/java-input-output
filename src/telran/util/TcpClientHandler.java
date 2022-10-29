package telran.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TcpClientHandler implements Handler {

	private static final String RESPONSE_LOG = "response.log";
	Socket socket; 
	PrintStream output; 
	BufferedReader input;
	
	public TcpClientHandler(String host, int port) {
		try {
			this.socket = new Socket(host, port);
			this.output = new PrintStream(socket.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publish(LoggerRecord loggerRecord) {
		String log = String.format("log#%s %s %s %s", getTime(loggerRecord), 
				loggerRecord.level, loggerRecord.loggerName, loggerRecord.message);
		this.output.println(log);
		try(PrintStream printStream = new PrintStream(RESPONSE_LOG)) {
			printStream.println(this.input.readLine());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private LocalDateTime getTime(LoggerRecord loggerRecord) {
		return LocalDateTime.ofInstant(loggerRecord.timestamp, ZoneId.of(loggerRecord.zoneId));
	}
	
	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCount(String string) {
		this.output.println(String.format("counter#%s", string));	
		try(PrintStream printStream = new PrintStream(RESPONSE_LOG)) {
			printStream.println(this.input.readLine());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
