package telran.util;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SimpleStreamHandler implements Handler {

	private PrintStream stream;
	
	public SimpleStreamHandler(PrintStream stream) {
		this.stream = stream;
	}

	@Override
	public void publish(LoggerRecord loggerRecord) {
		stream.printf("%s %s %s %s\n", getTime(loggerRecord), 
				loggerRecord.level, loggerRecord.loggerName, loggerRecord.message);
	}

	private LocalDateTime getTime(LoggerRecord loggerRecord) {
		return LocalDateTime.ofInstant(loggerRecord.timestamp, ZoneId.of(loggerRecord.zoneId));
	}

}
